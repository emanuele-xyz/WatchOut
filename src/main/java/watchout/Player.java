package watchout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Player {
    private static BufferedReader keyboard = null;

    public static void main(String[] args) throws IOException {
        keyboard = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("ID > ");
        String idStr = keyboard.readLine().trim().toLowerCase();
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID '" + idStr + "'");
            return;
        }

        System.out.print("Port > ");
        String portStr = keyboard.readLine().trim().toLowerCase();
        int port = 0;
        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port '" + portStr + "'");
            return;
        }

        System.out.print("Admin server address > ");
        String serverAddress = keyboard.readLine().trim().toLowerCase();
    }
}
