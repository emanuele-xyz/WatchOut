package watchout.admin;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import watchout.common.HeartbeatStatResult;
import watchout.common.PlayerList;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminClient {
    private static final String SERVER_ADDRESS = "http://localhost:1337";
    private static final String PLAYERS_ENDPOINT = SERVER_ADDRESS + "/players";
    private static final String HEARTBEATS_ENDPOINT = SERVER_ADDRESS + "/heartbeats";
    private static final String BROKER_ADDRESS = "tcp://localhost:1883";
    private static final String GAME_START_TOPIC = "game/start";
    private static final String MESSAGE_TOPIC = "message";
    private static final int GAME_START_QOS = 2; // TODO: do you need 2 or can we just use 1?
    private static final int MESSAGE_QOS = 2;

    private static BufferedReader keyboard = null;
    private static Client restClient = null;
    private static String mqttClientId = null;
    private static MqttClient mqttClient = null;

    private static void printWelcome() {
        System.out.println("Welcome to admin client!");
    }

    private static void tryToConnectToMQTTBroker() {
        System.out.println("Trying to connect to MQTT broker ...");

        String connectionErrorMsg = null;
        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            mqttClient.connect(connOpts);
        } catch (MqttException e) {
            connectionErrorMsg = e.getMessage() + " (" + e.getReasonCode() + ")";
        }

        if (mqttClient.isConnected()) {
            System.out.println("Successfully connected to MQTT broker!");
        } else {
            System.out.println("Failed to connect to MQTT broker: " + connectionErrorMsg);
        }
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
        message.setQos(GAME_START_QOS);

        try {
            mqttClient.publish(GAME_START_TOPIC, message);
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

    private static void getAverageOfLastN() {
        try {
            System.out.print("Player id > ");
            String id = keyboard.readLine();
            System.out.print("N > ");
            String n = keyboard.readLine();

            WebResource webResource = restClient.resource(HEARTBEATS_ENDPOINT + "/avgoflastn/" + id + "/" + n);
            ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                int statusCode = response.getStatus();
                String statusStr = Response.Status.fromStatusCode(statusCode).toString();
                System.out.println("Something went wrong ... " + statusStr + "(" + statusCode + ")");
            } else {
                HeartbeatStatResult result = response.getEntity(HeartbeatStatResult.class);
                System.out.println("The average of the last " + n + " heartbeats coming from player " + id + " is " + result.getResult());
            }
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getAverageBetween() {
        try {
            System.out.print("t0 > ");
            String t0 = keyboard.readLine();
            System.out.print("t1 > ");
            String t1 = keyboard.readLine();

            WebResource webResource = restClient.resource(HEARTBEATS_ENDPOINT + "/avgbetween/" + t0 + "/" + t1);
            ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                int statusCode = response.getStatus();
                String statusStr = Response.Status.fromStatusCode(statusCode).toString();
                System.out.println("Something went wrong ... " + statusStr + "(" + statusCode + ")");
            } else {
                HeartbeatStatResult result = response.getEntity(HeartbeatStatResult.class);
                System.out.println("The average of the all the heartbeats between " + t0 + " and " + t1 + " is " + result.getResult());
            }
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage() {
        try {
            System.out.print("Message > ");
            String input = keyboard.readLine().trim().toLowerCase();

            MqttMessage message = new MqttMessage(input.getBytes());
            message.setQos(MESSAGE_QOS);

            try {
                mqttClient.publish(MESSAGE_TOPIC, message);
                System.out.println("Message '" + input + "' sent to all players");
            } catch (MqttException e) {
                System.out.println("Failed to publish MQTT message: " + e.getMessage() + " (" + e.getReasonCode() + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, MqttException {
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        restClient = Client.create();
        mqttClientId = MqttClient.generateClientId();
        mqttClient = new MqttClient(BROKER_ADDRESS, mqttClientId);

        printWelcome();
        tryToConnectToMQTTBroker();
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

        mqttClient.disconnect();
    }
}
