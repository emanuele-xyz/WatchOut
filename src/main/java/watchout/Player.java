package watchout;

import com.sun.jersey.api.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Player {
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

    private static void registerPlayer() {
        restClient = Client.create();
        // TODO: to be implemented
    }

    public static void main(String[] args) throws IOException {
        keyboard = new BufferedReader(new InputStreamReader(System.in));

        boolean isInitializationSuccessful = initializePlayer();
        if (!isInitializationSuccessful) return;
        registerPlayer();
    }
}
