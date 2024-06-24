package watchout.player;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.eclipse.paho.client.mqttv3.*;
import watchout.utils.MQTTConfig;
import watchout.common.Player;
import watchout.common.PlayerList;
import watchout.simulators.HRSimulator;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayerPeer {
    private static BufferedReader keyboard = null;
    private static String adminServerPlayersEndpoint = null;
    private static String adminServerHeartbeatsEndpoint = null;
    private static Client restClient = null;
    private static Server gRPCServer = null;
    private static String mqttClientId = null;
    private static MqttClient mqttClient = null;
    private static HRBuffer hrBuffer = null;

    private static boolean initializeContext() throws IOException {
        System.out.print("ID > ");
        String idStr = keyboard.readLine().trim().toLowerCase();
        try {
            int id = Integer.parseInt(idStr);
            Context.getInstance().setId(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID '" + idStr + "'");
            return false;
        }

        System.out.print("Port > ");
        String portStr = keyboard.readLine().trim().toLowerCase();
        try {
            int port = Integer.parseInt(portStr);
            Context.getInstance().setPort(port);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port '" + portStr + "'");
            return false;
        }

        System.out.print("Admin server address > ");
        String adminServerAddress = keyboard.readLine().trim().toLowerCase();
        adminServerPlayersEndpoint = adminServerAddress + "/players";
        adminServerHeartbeatsEndpoint = adminServerAddress + "/heartbeats";

        return true;
    }

    private static boolean handlePlayerAdminServerRegistration() {
        boolean success = true;

        int id = Context.getInstance().getId();
        int port = Context.getInstance().getPort();
        WebResource webResource = restClient.resource(adminServerPlayersEndpoint + "/" + id + "/localhost/" + port);
        try {
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class);
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                PlayerList playerList = response.getEntity(PlayerList.class);
                Player player = playerList.getPlayers().stream().filter(p -> p.getId() == id).findFirst().get();
                System.out.println("Player starting at (" + player.getPitchStartX() + ", " + player.getPitchStartY() + ")");
                Context.getInstance().setPitchStart(player.getPitchStartX(), player.getPitchStartY());
                Context.getInstance().setPlayers(playerList.getPlayers());
            } else {
                success = false;
                int statusCode = response.getStatus();
                String statusStr = Response.Status.fromStatusCode(statusCode).toString();
                System.out.println("Failed to register player to the admin server ... " + statusStr + "(" + statusCode + ")");
            }
        } catch (ClientHandlerException e) {
            success = false;
            System.out.println("Failed to register player to the admin server ... failed to perform request");
            System.out.println(e);
        }

        return success;
    }

    private static void createAndStartPlayerPeerServiceGRPCServer() throws IOException {
        int port = Context.getInstance().getPort();
        gRPCServer = ServerBuilder.forPort(port).addService(new GRPCServiceImpl()).build();
        gRPCServer.start();
    }

    private static boolean createMQTTClient() {
        mqttClientId = MqttClient.generateClientId();
        try {
            mqttClient = new MqttClient(MQTTConfig.BROKER_ADDRESS, mqttClientId);
        } catch (MqttException e) {
            System.out.println("Failed to create MQTT client: " + e.getMessage() + " (" + e.getReasonCode() + ")");
        }
        return mqttClient != null;
    }

    private static boolean connectToMQTTBroker() {
        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            mqttClient.connect(connOpts);
            System.out.println("Successfully connected to MQTT broker!");
        } catch (MqttException e) {
            System.out.println("Failed to connect to MQTT broker: " + e.getMessage() + " (" + e.getReasonCode() + ")");
        }
        return mqttClient.isConnected();
    }

    private static boolean subscribeToMQTTTopic(String topic, int qos) {
        boolean isSubscribedSuccessfully = true;
        try {
            mqttClient.subscribe(topic, qos);
        } catch (MqttException e) {
            System.out.println("MQTT subscription failed " + "- topic:" + topic + " - qos:" + qos + " - " + e.getMessage() + " (" + e.getReasonCode() + ")");
            isSubscribedSuccessfully = false;
        }
        return isSubscribedSuccessfully;
    }

    private static boolean subscribeToMQTTTopics() {
        mqttClient.setCallback(new MQTTHandler());

        boolean isSubscribedSuccessfully = true;
        isSubscribedSuccessfully &= subscribeToMQTTTopic(MQTTConfig.GAME_START_TOPIC, MQTTConfig.GAME_START_QOS);
        isSubscribedSuccessfully &= subscribeToMQTTTopic(MQTTConfig.CUSTOM_MESSAGE_TOPIC, MQTTConfig.CUSTOM_MESSAGE_QOS);
        return isSubscribedSuccessfully;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Context.getInstance().setState(State.Idle);

        keyboard = new BufferedReader(new InputStreamReader(System.in));
        restClient = Client.create();

        boolean isInitializationSuccessful = initializeContext();
        if (!isInitializationSuccessful) return;
        boolean isPlayerAdminServerRegistrationSuccessful = handlePlayerAdminServerRegistration();
        if (!isPlayerAdminServerRegistrationSuccessful) return;
        hrBuffer =  new HRBuffer();
        new HRSimulator(hrBuffer).start();
        new HRSender(Context.getInstance().getId(), restClient, adminServerHeartbeatsEndpoint, hrBuffer).start();
        createAndStartPlayerPeerServiceGRPCServer();
        Context.getInstance().createGRPCHandles();
        Context.getInstance().greetAllPlayers();
        boolean isMQTTClientCreationSuccessful = createMQTTClient();
        if (!isMQTTClientCreationSuccessful) return;
        boolean isConnectionWithMQTTBrokerEstablished = connectToMQTTBroker();
        if (!isConnectionWithMQTTBrokerEstablished) return;
        boolean isSubscribedSuccessfully = subscribeToMQTTTopics();
        if (!isSubscribedSuccessfully) return;

        gRPCServer.awaitTermination();
    }
}
