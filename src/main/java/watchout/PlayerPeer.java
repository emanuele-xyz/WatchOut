package watchout;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import watchout.common.Player;
import watchout.common.PlayerList;

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
        }

        return out;
    }

    public static void main(String[] args) throws IOException {
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        restClient = Client.create();

        boolean isInitializationSuccessful = initializePlayer();
        if (!isInitializationSuccessful) return;
        List<Player> players = registerPlayer();
        if (players == null) return;
    }
}
