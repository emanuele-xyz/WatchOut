package watchout.admin;

import watchout.common.HeartbeatList;
import watchout.common.HeartbeatStatResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("heartbeats")
public class HeartbeatsServices {

    @Path("/{id}/{timestamp}")
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addHeartbeats(@PathParam("id") int id, @PathParam("timestamp") long timestamp, HeartbeatList heartbeatList) {
        if (!Players.getInstance().isPlayerRegistered(id)) return Response.status(Response.Status.NOT_FOUND).build();

        Heartbeats.getInstance().addHeartbeats(id, timestamp, heartbeatList);
        return Response.ok().build();
    }

    @Path("/avgoflastn/{id}/{n}")
    @GET
    public Response getAverageOfLastNHeartbeats(@PathParam("id") int id, @PathParam("n") int n) {
        if (!Players.getInstance().isPlayerRegistered(id)) return Response.status(Response.Status.NOT_FOUND).build();
        if (n < 0) return Response.status(Response.Status.BAD_REQUEST).build();

        double avg = Heartbeats.getInstance().getAverageOfLastNHeartbeats(id, n);
        HeartbeatStatResult result = new HeartbeatStatResult(avg);
        return Response.ok(result).build();
    }

    @Path("/avgbetween/{t1}/{t2}")
    @GET
    public Response getAverageOfHeartbeatsBetween(@PathParam("t1") long t1, @PathParam("t2") long t2) {
        if (t1 > t2) return Response.status(Response.Status.BAD_REQUEST).build();

        double avg = Heartbeats.getInstance().getAverageOfHeartbeatsBetween(t1, t2);
        HeartbeatStatResult result = new HeartbeatStatResult(avg);
        return Response.ok(result).build();
    }
}
