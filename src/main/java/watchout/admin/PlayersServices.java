package watchout.admin;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("players")
public class PlayersServices {

    @Path("{id}/{address}/{port}")
    @POST
    public Response registerPlayer(@PathParam("id") int id, @PathParam("address") String address, @PathParam("port") int port) {
        boolean registrationSucceeded = Players.getInstance().registerPlayer(id, address, port);
        if (registrationSucceeded) {
            return Response.ok(Players.getInstance()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    public Response getPlayers() {
        return Response.ok(Players.getInstance()).build();
    }
}
