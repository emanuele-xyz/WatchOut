package watchout.player;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import watchout.common.Heartbeat;
import watchout.common.HeartbeatList;
import watchout.simulators.Buffer;
import watchout.simulators.Measurement;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

public class HRSender extends Thread {
    private final int id;
    private final Client restClient;
    private final String adminServerHeartbeatsEndpoint;
    private final Buffer buffer;

    public HRSender(int id, Client restClient, String adminServerHeartbeatsEndpoint, Buffer buffer) {
        this.id = id;
        this.restClient = restClient;
        this.adminServerHeartbeatsEndpoint = adminServerHeartbeatsEndpoint;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Measurement> averages = buffer.readAllAndClean();
            if (!averages.isEmpty()) {
                List<Heartbeat> heartbeats = averages.stream()
                        .map(m -> new Heartbeat(m.getValue(), m.getTimestamp()))
                        .collect(Collectors.toList());

                heartbeats.forEach(b -> System.out.println("Sending heartbeat: " + b));

                HeartbeatList heartbeatList = new HeartbeatList(heartbeats);
                long timestamp = System.currentTimeMillis();

                String url = adminServerHeartbeatsEndpoint + "/" + id + "/" + timestamp;
                WebResource webResource = restClient.resource(url);
                String input = new Gson().toJson(heartbeatList);
                try {
                    ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
                    if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                        throw new IllegalStateException("Player peer should be registered to admin server before sending heartbeats");
                    }
                } catch (ClientHandlerException e) {
                    System.out.println("Failed to send heartbeat list: " + e);
                }
            }
        }
    }
}
