package watchout.admin;

import watchout.common.HeartbeatList;
import watchout.common.HeartbeatStatResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("heartbeats")
public class HeartbeatsServices {

    // NOTE: only for testing purposes
    @GET
    public Response printHeartbeats() {
        Heartbeats.getInstance().printHeartbeats();
        return Response.ok().build();
    }

    @Path("/{id}/{timestamp}")
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addHeartbeats(@PathParam("id") int id, @PathParam("timestamp") int timestamp, HeartbeatList heartbeatList) {
        Heartbeats.getInstance().addHeartbeats(id, timestamp, heartbeatList);
        return Response.ok().build();
    }

    @Path("/avgoflastn/{id}/{n}")
    @GET
    public Response getAverageOfLastNHeartbeats(@PathParam("id") int id, @PathParam("n") int n) {
        double avg = Heartbeats.getInstance().getAverageOfLastNHeartbeats(id, n);
        HeartbeatStatResult result = new HeartbeatStatResult(avg);
        return Response.ok(result).build();
    }

    @Path("/avgbetween/{t1}/{t2}")
    @GET
    public Response getAverageOfHeartbeatsBetween(@PathParam("t1") int t1, @PathParam("t2") int t2) {
        double avg = Heartbeats.getInstance().getAverageOfHeartbeatsBetween(t1, t2);
        HeartbeatStatResult result = new HeartbeatStatResult(avg);
        return Response.ok(result).build();
    }
}
