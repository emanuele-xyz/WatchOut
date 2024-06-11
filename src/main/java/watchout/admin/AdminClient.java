package watchout.admin;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import watchout.common.Heartbeat;
import watchout.common.HeartbeatList;
import watchout.common.PlayerList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdminClient {
    private static final String serverAddress = "http://localhost:1337";
    private static final String playersEndpoint = serverAddress + "/players";
    private static final String heartbeatsEndpoint = serverAddress + "/heartbeats";

    private static BufferedReader keyboard = null;
    private static Client client = null;

    private static void printWelcome() {
        System.out.println("Welcome to admin client!");
    }

    private static void printSeparator() {
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void printMenu() {
        System.out.println("Supported queries:");
        System.out.println("0) Get registered players.");
        System.out.println("1) Get the average of the last N heartbeats sent by a player.");
        System.out.println("2) Get the average of all the heartbeats between two timestamps.");
        System.out.println("Type 'quit' to exit.");
    }

    private static void printPrompt() {
        System.out.print("Please enter your query or quit > ");
    }

    private static void getRegisteredPlayers() {
        WebResource webResource = client.resource(playersEndpoint);
        try {
            ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
            PlayerList playerList = response.getEntity(PlayerList.class);
            System.out.println(playerList.toString());
        } catch (ClientHandlerException e) {
            System.out.println("Server not available");
        }
    }

    private static void getAverageOfLastN() {
        // TODO: to be implemented
    }

    private static void getAverageBetween() {
        // TODO: to be implemented
    }

    public static void main(String[] args) throws IOException {
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        client = Client.create();

        boolean isRunning = true;
        printSeparator();
        printWelcome();
        printSeparator();
        do {
            printMenu();
            printSeparator();
            printPrompt();
            String input = keyboard.readLine();
            switch (input) {
                case "0": {
                    getRegisteredPlayers();
                    printSeparator();
                }
                break;
                case "1": {
                    getAverageOfLastN();
                    printSeparator();
                }
                break;
                case "2": {
                    getAverageBetween();
                    printSeparator();
                }
                break;
                case "quit": {
                    isRunning = false;
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
