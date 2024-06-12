package watchout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Player {
    private static BufferedReader keyboard = null;
    private static int id = 0;
    private static int port = 0;
    private static String adminServerAddress = null;

    private static void initializePlayer() throws IOException {
        System.out.print("ID > ");
        String idStr = keyboard.readLine().trim().toLowerCase();
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID '" + idStr + "'");
            return;
        }

        System.out.print("Port > ");
        String portStr = keyboard.readLine().trim().toLowerCase();
        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port '" + portStr + "'");
            return;
        }

        System.out.print("Admin server address > ");
        adminServerAddress = keyboard.readLine().trim().toLowerCase();
    }

    public static void main(String[] args) throws IOException {
        keyboard = new BufferedReader(new InputStreamReader(System.in));

        initializePlayer();
    }
}
