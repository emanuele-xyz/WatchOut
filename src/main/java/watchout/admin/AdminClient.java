package watchout.admin;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import watchout.MQTTConfig;
import watchout.common.HeartbeatStatResult;
import watchout.common.PlayerList;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminClient {
    private static final String SERVER_ADDRESS = "http://localhost:1337";
    private static final String PLAYERS_ENDPOINT = SERVER_ADDRESS + "/players";
    private static final String HEARTBEATS_ENDPOINT = SERVER_ADDRESS + "/heartbeats";

    private static BufferedReader keyboard = null;
    private static Client restClient = null;
    private static String mqttClientId = null;
    private static MqttClient mqttClient = null;

    private static void printWelcome() {
        System.out.println("Welcome to admin client!");
    }

    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("0: Start a game.");
        System.out.println("1: Get the list of all registered players.");
        System.out.println("2: Get the average of the last N heartbeats sent by a player.");
        System.out.println("3: Get the average of all the heartbeats between two timestamps.");
        System.out.println("4: Send a message to all players.");
        System.out.println("m[enu]: Print this menu.");
        System.out.println("q[uit]: Quit the application.");
    }

    private static void gameStart() {
        MqttMessage message = new MqttMessage();
        message.setQos(MQTTConfig.GAME_START_QOS);

        try {
            mqttClient.publish(MQTTConfig.GAME_START_TOPIC, message);
            System.out.println("Game start notification sent to all players");
        } catch (MqttException e) {
            System.out.println("Failed to publish MQTT message: " + e.getMessage() + " (" + e.getReasonCode() + ")");
        }
    }

    private static void getRegisteredPlayers() {
        WebResource webResource = restClient.resource(PLAYERS_ENDPOINT);
        try {
            ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
            PlayerList playerList = response.getEntity(PlayerList.class);
            System.out.println(playerList.toString());
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
        }
    }

    private static void getAverageOfLastN() throws IOException {
        try {
            System.out.print("Player id > ");
            String id = keyboard.readLine();
            System.out.print("N > ");
            String n = keyboard.readLine();

            String url = HEARTBEATS_ENDPOINT + "/avgoflastn/" + id + "/" + n;
            WebResource webResource = restClient.resource(url);
            ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                int statusCode = response.getStatus();
                String statusStr = Response.Status.fromStatusCode(statusCode).toString();
                System.out.println("Something went wrong requesting " + url + " ... " + statusStr + "(" + statusCode + ")");
            } else {
                HeartbeatStatResult result = response.getEntity(HeartbeatStatResult.class);
                System.out.printf("The average of the last %s heartbeats coming from player %s is %.2f\n", n, id, result.getResult());
            }
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameter: " + e.getMessage());
        }
    }

    private static void getAverageBetween() throws IOException {
        try {
            System.out.print("t0 > ");
            String t0 = keyboard.readLine();
            System.out.print("t1 > ");
            String t1 = keyboard.readLine();

            String url = HEARTBEATS_ENDPOINT + "/avgbetween/" + t0 + "/" + t1;
            WebResource webResource = restClient.resource(url);
            ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                int statusCode = response.getStatus();
                String statusStr = Response.Status.fromStatusCode(statusCode).toString();
                System.out.println("Something went wrong requesting " + url + " ... " + statusStr + "(" + statusCode + ")");
            } else {
                HeartbeatStatResult result = response.getEntity(HeartbeatStatResult.class);
                System.out.println("The average of the all the heartbeats between " + t0 + " and " + t1 + " is " + result.getResult());
            }
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameter: " + e.getMessage());
        }
    }

    private static void sendMessage() throws IOException {
        System.out.print("Message > ");
        String input = keyboard.readLine().trim().toLowerCase();

        MqttMessage message = new MqttMessage(input.getBytes());
        message.setQos(MQTTConfig.CUSTOM_MESSAGE_QOS);

        try {
            mqttClient.publish(MQTTConfig.CUSTOM_MESSAGE_TOPIC, message);
            System.out.println("Message '" + input + "' sent to all players");
        } catch (MqttException e) {
            System.out.println("Failed to publish MQTT message: " + e.getMessage() + " (" + e.getReasonCode() + ")");
        }
    }

    public static void main(String[] args) throws IOException {
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        restClient = Client.create();
        mqttClientId = MqttClient.generateClientId();

        printWelcome();

        try {
            mqttClient = new MqttClient(MQTTConfig.BROKER_ADDRESS, mqttClientId);
        } catch (MqttException e) {
            System.out.println("Failed to create MQTT client: " + e.getMessage() + " (" + e.getReasonCode() + ")");
        }
        if (mqttClient == null) return;

        System.out.println("Trying to connect to MQTT broker ...");
        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            mqttClient.connect(connOpts);
            System.out.println("Successfully connected to MQTT broker!");
        } catch (MqttException e) {
            System.out.println("Failed to connect to MQTT broker: " + e.getMessage() + " (" + e.getReasonCode() + ")");
        }
        if (!mqttClient.isConnected()) return;

        printMenu();
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("> ");
            String input = keyboard.readLine().trim().toLowerCase();

            switch (input) {
                case "0": {
                    gameStart();
                }
                break;
                case "1": {
                    getRegisteredPlayers();
                }
                break;
                case "2": {
                    getAverageOfLastN();
                }
                break;
                case "3": {
                    getAverageBetween();
                }
                break;
                case "4": {
                    sendMessage();
                }
                break;
                case "m":
                case "menu": {
                    printMenu();
                }
                break;
                case "q":
                case "quit": {
                    isRunning = false;
                }
                break;
                default: {
                    System.out.println("Unknown command '" + input + "'");
                }
                break;
            }
        }

        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            System.out.println("Failed to disconnect from MQTT broker " + e.getMessage() + " (" + e.getReasonCode() + ")");
        }
    }
}
