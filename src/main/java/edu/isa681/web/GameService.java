package edu.isa681.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


@Path("/game")
public class GameService {
    @GET
    @Path("/{param}")
    public Response initBoardData(@PathParam("param") String msg) {
        String output = "Jersey say : " + msg;

        return Response.status(200).entity(output).build();
    }
}
