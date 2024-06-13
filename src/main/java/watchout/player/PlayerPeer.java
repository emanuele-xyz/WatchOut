package watchout.player;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.eclipse.paho.client.mqttv3.*;
import watchout.MQTTConfig;
import watchout.common.Player;
import watchout.common.PlayerList;
import watchout.player.PlayerPeerServiceOuterClass.GreetingRequest;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class PlayerPeer {
    private static BufferedReader keyboard = null;
    private static int id = 0;
    private static int port = 0;
    private static String adminServerAddress = null;
    private static String adminServerPlayersEndpoint = null;
    private static String adminServerHeartbeatsEndpoint = null;
    private static Client restClient = null;
    private static int pitchStartX = 0;
    private static int pitchStartY = 0;
    private static Server gRPCServer = null;
    private static String mqttClientId = null;
    private static MqttClient mqttClient = null;

    private static boolean initializePlayer() throws IOException {
        System.out.print("ID > ");
        String idStr = keyboard.readLine().trim().toLowerCase();
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID '" + idStr + "'");
            return false;
        }

        System.out.print("Port > ");
        String portStr = keyboard.readLine().trim().toLowerCase();
        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port '" + portStr + "'");
            return false;
        }

        System.out.print("Admin server address > ");
        adminServerAddress = keyboard.readLine().trim().toLowerCase();
        adminServerPlayersEndpoint = adminServerAddress + "/players";
        adminServerHeartbeatsEndpoint = adminServerAddress + "/heartbeats";

        return true;
    }

    private static List<Player> registerPlayer() {
        List<Player> out = null;

        WebResource webResource = restClient.resource(adminServerPlayersEndpoint + "/" + id + "/localhost/" + port);
        try {
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class);
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                PlayerList playerList = response.getEntity(PlayerList.class);
                Optional<Player> player = playerList.getPlayers().stream().filter(p -> p.getId() == id).findFirst();
                if (player.isPresent()) {
                    out = playerList.getPlayers();
                    System.out.println("Player registered successfully");
                    System.out.println("Starting at (" + player.get().getPitchStartX() + "," + player.get().getPitchStartY() + ")");
                    System.out.println("Here is a list of already registered players:");
                    playerList.getPlayers().forEach(p -> System.out.println("- " + p.toString()));
                } else {
                    System.out.println("Player registered but not found");
                }
            } else {
                int statusCode = response.getStatus();
                String statusStr = Response.Status.fromStatusCode(statusCode).toString();
                System.out.println("Something went wrong ... " + statusStr + "(" + statusCode + ")");
            }
        } catch (ClientHandlerException e) {
            System.out.println("Something went wrong ... failed to perform request");
            System.out.println(e);
        }

        return out;
    }

    private static void getPitchStartPosition(List<Player> players) {
        Player player = players.stream().filter(p -> p.getId() == id).findFirst().get();
        pitchStartX = player.getPitchStartX();
        pitchStartY = player.getPitchStartY();
    }

    private static void createAndStartPlayerPeerServiceGRPCServer() throws IOException {
        gRPCServer = ServerBuilder.forPort(port).addService(new PlayerPeerServiceImpl()).build();
        gRPCServer.start();
    }

    private static void registerPlayers(List<Player> players) {
        players.stream().filter(p -> p.getId() != id).forEach(p -> PlayerRegistry.getInstance().registerPlayer(p));
    }


    private static void greetAlreadyRegisteredPlayers() {
        GreetingRequest request = GreetingRequest.newBuilder()
                .setId(id)
                .setAddress("localhost")
                .setPort(port)
                .setPitchStartX(pitchStartX)
                .setPitchStartY(pitchStartY)
                .build();
        PlayerRegistry.getInstance().forEachHandle((p, h) -> {
            h.getStub().greeting(request, new GreetingStreamObserver(p.getId(), p.getAddress(), p.getPort()));
        });
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
            System.out.println("MQTT subscription failed " + "- topic:" + topic + " - qos:" + qos + " - " +  e.getMessage() + " (" + e.getReasonCode() + ")");
            isSubscribedSuccessfully = false;
        }
        return isSubscribedSuccessfully;
    }

    private static boolean subscribeToMQTTTopics() {
        mqttClient.setCallback(new PlayerMQTTCallback());

        boolean isSubscribedSuccessfully = true;
        isSubscribedSuccessfully &= subscribeToMQTTTopic(MQTTConfig.GAME_START_TOPIC, MQTTConfig.GAME_START_QOS);
        isSubscribedSuccessfully &= subscribeToMQTTTopic(MQTTConfig.CUSTOM_MESSAGE_TOPIC, MQTTConfig.CUSTOM_MESSAGE_QOS);
        return isSubscribedSuccessfully;
    }

    public static void main(String[] args) throws IOException {
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        restClient = Client.create();

        boolean isInitializationSuccessful = initializePlayer();
        if (!isInitializationSuccessful) return;
        List<Player> players = registerPlayer();
        if (players == null) return;
        getPitchStartPosition(players);
        createAndStartPlayerPeerServiceGRPCServer();
        registerPlayers(players);
        greetAlreadyRegisteredPlayers();
        boolean isMQTTClientCreationSuccessful = createMQTTClient();
        if (!isMQTTClientCreationSuccessful) return;
        boolean isConnectionWithMQTTBrokerEstablished = connectToMQTTBroker();
        if (!isConnectionWithMQTTBrokerEstablished) return;
        boolean isSubscribedSuccessfully = subscribeToMQTTTopics();
        if (!isSubscribedSuccessfully) return;

        // TODO: implement game start callback

        try {
            gRPCServer.awaitTermination(); // TODO: when do we call server.awaitTermination()?
        } catch (InterruptedException e) {
            // TODO: how do we handle this?
            throw new RuntimeException(e);
        }
    }
}
