package watchout.admin;

import watchout.common.PlayerList;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("players")
public class PlayersServices {

    @Path("{id}/{address}/{port}")
    @POST
    public Response registerPlayer(@PathParam("id") int id, @PathParam("address") String address, @PathParam("port") int port) {
        boolean registrationSucceeded = Players.getInstance().registerPlayer(id, address, port);
        if (registrationSucceeded) {
            List<Player> players = Players.getInstance().getPlayers();
            PlayerList playerList = new PlayerList();
            playerList.getPlayers().addAll(players.stream().map(watchout.common.Player::new).collect(Collectors.toList()));
            return Response.ok(playerList).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    public Response getPlayers() {
        List<Player> players = Players.getInstance().getPlayers();
        PlayerList playerList = new PlayerList();
        playerList.getPlayers().addAll(players.stream().map(watchout.common.Player::new).collect(Collectors.toList()));
        return Response.ok(playerList).build();
    }
}
