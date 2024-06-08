package watchout.admin;

import watchout.Pitch;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("players")
public class AdminServerServices {

    @Path("{id}/{address}/{port}")
    @POST
    public Response registerPlayer(@PathParam("id") int id, @PathParam("address") String address, @PathParam("port") int port) {
        boolean registrationSucceeded = PlayersData.getInstance().registerPlayer(id, address, port);
        if (registrationSucceeded) {
            List<PlayerData> alreadyRegisteredPlayers = PlayersData.getInstance().getPlayersData();
            return Response.ok(alreadyRegisteredPlayers).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    public Response getPlayers() {
        // TODO: to be implemented
        return Response.ok().build();
    }

    @Path("/{id}/beats/{timestamp}/{beats}")
    @POST
    public Response addHeartbeats(@PathParam("id") int id, @PathParam("timestamp") int timestamp, @PathParam("beats") String beats) {
        System.out.println(beats);
        // TODO: to be implemented
        return Response.ok().build();
    }

    @Path("/{id}/beats/avg/{n}")
    @GET
    public Response getAverageOfLastNHeartbeats(@PathParam("id") int id, @PathParam("n") int n) {
        // TODO: to be implemented
        return Response.ok().build();
    }

    @Path("/beats/avg/{t1}/{t2}")
    @GET
    public Response getAverageOfHeartbeatsBetween(@PathParam("t1") int t1, @PathParam("t2") int t2) {
        // TODO: to be implemented
        return Response.ok().build();
    }
}
