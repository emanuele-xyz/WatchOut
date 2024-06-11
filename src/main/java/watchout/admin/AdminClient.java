package watchout.admin;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import watchout.common.Heartbeat;
import watchout.common.HeartbeatList;
import watchout.common.HeartbeatStatResult;
import watchout.common.PlayerList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdminClient {
    private static final String SERVER_ADDRESS = "http://localhost:1337";
    private static final String PLAYERS_ENDPOINT = SERVER_ADDRESS + "/players";
    private static final String HEARTBEATS_ENDPOINT = SERVER_ADDRESS + "/heartbeats";

    private static BufferedReader keyboard = null;
    private static Client client = null;

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

    private static void printPrompt() {
        System.out.print("> ");
    }

    private static void gameStart() {
        // TODO: to be implemented
        System.out.println("TO BE IMPLEMENTED ...");
    }

    private static void getRegisteredPlayers() {
        WebResource webResource = client.resource(PLAYERS_ENDPOINT);
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
            // TODO: test what happens with wrong user input
            System.out.print("Player id > ");
            String id = keyboard.readLine();
            System.out.print("N > ");
            String n = keyboard.readLine();
            WebResource webResource = client.resource(HEARTBEATS_ENDPOINT + "/avgoflastn/" + id + "/" + n);
            ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
            HeartbeatStatResult result = response.getEntity(HeartbeatStatResult.class);
            System.out.println("The average of the last " + n + " heartbeats coming from player " + id + " is " + result.getResult());
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getAverageBetween() {
        try {
            // TODO: test what happens with wrong user input
            System.out.print("t0 > ");
            String t0 = keyboard.readLine();
            System.out.print("t1 > ");
            String t1 = keyboard.readLine();
            WebResource webResource = client.resource(HEARTBEATS_ENDPOINT + "/avgbetween/" + t0 + "/" + t1);
            ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
            HeartbeatStatResult result = response.getEntity(HeartbeatStatResult.class);
            System.out.println("The average of the all the heartbeats between " + t0 + " and " + t1 + " is " + result.getResult());
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendMessage() {
        // TODO: to be implemented
        System.out.println("TO BE IMPLEMENTED ...");
    }

    public static void main(String[] args) throws IOException {
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        client = Client.create();

        boolean isRunning = true;
        printWelcome();
        printMenu();
        do {
            printPrompt();
            String input = keyboard.readLine().toLowerCase();

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
        } while (isRunning);

        /*// POST EXAMPLE
        String postPath = "/heartbeats/0/1000";

        clientResponse = postRequest(client, serverAddress + postPath);
        System.out.println(clientResponse.toString());
*/
        /*//GET REQUEST #1
        String getPath = "/users";
        clientResponse = getRequest(client,serverAddress+getPath);
        System.out.println(clientResponse.toString());
        Users users = clientResponse.getEntity(Users.class);
        System.out.println("Users List");
        for (User u : users.getUserslist()){
            System.out.println("Name: " + u.getName() + " Surname: " + u.getSurname());
        }

        //GET REQUEST #2
        String getUserPath = "/users/get/john";
        clientResponse = getRequest(client,serverAddress+getUserPath);
        System.out.println(clientResponse.toString());
        User userResponse = clientResponse.getEntity(User.class);
        System.out.println("Name: " + userResponse.getName() + " Surname: " + userResponse.getSurname());*/
    }

    public static ClientResponse postRequest(Client client, String url) {
        WebResource webResource = client.resource(url);

        HeartbeatList heartbeatList = new HeartbeatList();
        heartbeatList.setHeartbeats(new ArrayList<>());
        heartbeatList.setTimestamp(2000);
        heartbeatList.getHeartbeats().add(new Heartbeat(20, 3000));

        String input = new Gson().toJson(heartbeatList);
        System.out.println("Posting: " + input);
        try {
            return webResource.type("application/json").post(ClientResponse.class, input);
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
            return null;
        }
    }

/*    public static ClientResponse getRequest(Client client, String url){
        WebResource webResource = client.resource(url);
        try {
            return webResource.type("application/json").get(ClientResponse.class);
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
            return null;
        }
    }*/
}
