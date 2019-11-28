package edu.isa681.web.dashboard;


import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.web.game.GameController;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/dashboard")
public class DashboardAPI {
    Logger logger = Logger.getLogger(DashboardAPI.class);
    private GameController gameController = GameController.getGameController();

    @POST
    @Path("/invite")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response invitePlayer(PlayerInviteMessage playerInviteMessage) {
        Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_OK);
        try {

            for (String playerStub : playerInviteMessage.getPlayerStubsInvited()) {
                if (gameController.getPlayers().get(playerStub) == null
                        || !(gameController.getPlayers().get(playerStub).getPlayerSate().equals(PlayerSate.Online))) {
                    throw new IllegalStateException("Players selected doesnt seems to be available to play");
                }
            }
            gameController.createGame(playerInviteMessage);
            responseBuilder.contentLocation(new URI("../views/Players.jsp"));
        } catch (Exception ex) {
            logger.info("Game could not be created", ex);
            responseBuilder.status(HttpServletResponse.SC_EXPECTATION_FAILED).entity(ex.getMessage());
        }
        return responseBuilder.build();
    }

}
