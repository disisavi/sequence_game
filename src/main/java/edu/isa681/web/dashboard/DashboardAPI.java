package edu.isa681.web.dashboard;


import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.messages.PlayerInfoMessage;
import edu.isa681.messages.PlayerInviteMessage;
import edu.isa681.web.game.GameController;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/dashboard")
public class DashboardAPI {
    private Logger logger = Logger.getLogger(DashboardAPI.class);
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

    @POST
    @Path("/getPlayers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PlayerInfoMessage getPlayersList(PlayerInviteMessage playerInviteMessage) {
        PlayerInfoMessage playerInfoMessage = new PlayerInfoMessage();
        playerInfoMessage.setGotMessage(false);
        try {
            if (playerInviteMessage.getPlayerSelfStub() == null) {
                throw new IllegalStateException("Player info Not included with the request");
            }
            if (!(gameController.getPlayers().containsKey(playerInviteMessage.getPlayerSelfStub()))) {
                throw new IllegalStateException("Incorrect player info provided");
            }
            gameController.playerOnlineRightNow(playerInfoMessage);
            playerInfoMessage.getPlayerSateMap().remove(gameController.getPlayerBySub(playerInviteMessage.getPlayerSelfStub()).getName());
            playerInfoMessage.setGotMessage(true);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            logger.info("Exception occurred while getting player info ", ex);
            playerInfoMessage.setErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("Unintended Exception occurred, please check the log", ex);
            playerInfoMessage.setErrorMessage("Something fishy is going on here, PLease ask the developer to check the error " + ex.getMessage());
        }

        return playerInfoMessage;
    }

}