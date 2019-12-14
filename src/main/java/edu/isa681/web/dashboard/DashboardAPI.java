package edu.isa681.web.dashboard;


import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.Game;
import edu.isa681.web.messages.PlayerInfoMessage;
import edu.isa681.web.messages.PlayerInviteMessage;
import edu.isa681.web.game.GameController;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.security.GeneralSecurityException;

@Path("/dashboard")
public class DashboardAPI {
    private Logger logger = Logger.getLogger(DashboardAPI.class);
    private GameController gameController = GameController.getGameController();

    @POST
    @Path("/invite")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response invitePlayer(PlayerInviteMessage playerInviteMessage) {
        String playerName = null;
        try {
            for (String playerStub : playerInviteMessage.getPlayerStubsInvited()) {
                if (gameController.getPlayerBySub(playerStub) == null
                        || !(gameController.getPlayerBySub(playerStub).getPlayerSate().equals(PlayerSate.Online))) {
                    throw new IllegalStateException("Players selected doesnt seems to be available to play");
                }
            }
            gameController.createGame(playerInviteMessage);
            playerName = gameController.getDecryptedPlayerBySub(playerInviteMessage.getPlayerSelfStub());
        } catch (Exception ex) {
            logger.info("Game could not be created", ex);
            ex.printStackTrace();
            return Response.status(HttpServletResponse.SC_EXPECTATION_FAILED, ex.getMessage()).build();
        }
        UriBuilder uriBuilder = UriBuilder.fromPath("../views/game.jsp")
                .queryParam("playerSub", playerInviteMessage.getPlayerSelfStub())
                .queryParam("playerName", playerName);
        return Response.temporaryRedirect(uriBuilder.build()).build();
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
            if (gameController.getPlayerBySub(playerInviteMessage.getPlayerSelfStub()) == null) {
                throw new IllegalStateException("Incorrect player info provided");
            }
            gameController.playerOnlineRightNow(playerInfoMessage);
            playerInfoMessage.getPlayerSateMap().remove(playerInviteMessage.getPlayerSelfStub());
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

    @POST
    @Path("/isInvited")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean isInvited(PlayerInviteMessage playerSub) {
        Player player = gameController.getPlayerBySub(playerSub.getPlayerSelfStub());
        if (player == null) {
            throw new IllegalStateException("No such player found");
        }
        return player.getPlayerSate().equals(PlayerSate.Invited);

    }

    @POST
    @Path("/redirectToGame")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response redirectToGame(PlayerInviteMessage playerSub) throws GeneralSecurityException {
        Game game = gameController.getGameForPlayer(playerSub.getPlayerSelfStub());
        if (game == null) {
            throw new IllegalStateException("Player is not registered to any game");
        }

        Player player = gameController.getPlayerBySub(playerSub.getPlayerSelfStub());
        player.setPlayerSate(PlayerSate.Playing);
        player.setPlayer();

        String playerName = gameController.getDecryptedPlayerBySub(playerSub.getPlayerSelfStub());
        UriBuilder uriBuilder = UriBuilder.fromPath("../views/game.jsp")
                .queryParam("playerSub", playerSub.getPlayerSelfStub())
                .queryParam("playerName", playerName);
        return Response.seeOther(uriBuilder.build()).build();
    }
}
