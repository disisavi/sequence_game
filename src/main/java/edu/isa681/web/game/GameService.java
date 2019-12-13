package edu.isa681.web.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.PlayerGameSession;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.Game;
import edu.isa681.game.GameState;
import edu.isa681.web.messages.PlayerInviteMessage;
import edu.isa681.web.messages.PlayerMove;
import org.apache.http.HttpMessage;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;


@Path("/game")
public class GameService {
    private GameController gameController;

    @POST
    @Path("/getBoard")
    @Produces(MediaType.APPLICATION_JSON)
    public GameState boardData(String playerSub) {
        GameState gameState = null;
        gameController = GameController.getGameController();
        if (gameController.getPlayerBySub(playerSub) == null) {
            IllegalStateException ex = new IllegalStateException("No such player found");
            ex.printStackTrace();
            throw ex;
        } else {
            Game game = gameController.getGameForPlayer(playerSub);
            if (game == null) {
                IllegalStateException ex = new IllegalStateException("No Game attached with the player found");
                ex.printStackTrace();
                throw ex;
            }
            gameState = game.getGameState();
        }
        return gameState;
    }


    @POST
    @Path("/registerPlayer")
    @Consumes(MediaType.TEXT_PLAIN)
    public void registerPlayerToGame(String playerSub) {
        gameController = GameController.getGameController();
        if (gameController.getPlayerBySub(playerSub) != null) {
            Player player = gameController.getPlayerBySub(playerSub);
            if (gameController.isPlayerAttachedToGame(playerSub)) {
                player.setPlayerSate(PlayerSate.Playing);
            } else {
                throw new IllegalStateException("No Such player attached to the game found");
            }
        }
    }

    /**
     * API to check if the game has been initiated.
     * If all the players have joined the game.
     **/
    @POST
    @Path("/playersJoined")
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean getPlayersJoined(PlayerInviteMessage playerSub) {
        boolean returnValue = true;
        gameController = GameController.getGameController();
        Game gameForPlayer = gameController.getGameForPlayer(playerSub.getPlayerSelfStub());

        if (gameForPlayer == null) {
            throw new IllegalStateException("No Such player attached to the game found");
        } else {
            for (PlayerGameSession playerGameSession : gameForPlayer.getPlayersGameSessions()) {
                if (playerGameSession.player.getPlayerSate() != PlayerSate.Playing) {
                    returnValue = false;
                    break;
                }
            }
        }
        return returnValue;
    }

    @POST
    @Path("/getPlayerData")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public PlayerGameSession getPlayersData(String playerSub) {
        gameController = GameController.getGameController();
        PlayerGameSession returnPlayerGameSession = null;
        Player player = gameController.getPlayerBySub(playerSub);
        if (player == null) {
            throw new IllegalStateException("No such player found on the game");
        } else {
            Game game = gameController.getGameForPlayer(playerSub);
            if (game == null) {
                throw new IllegalStateException("No game found for the player");
            } else {
                for (PlayerGameSession playerGameSession : game.getPlayersGameSessions()) {
                    if (playerGameSession.player.equals(player)) {
                        returnPlayerGameSession = playerGameSession;
                        break;
                    }
                }
            }
        }

        return returnPlayerGameSession;
    }

    @POST
    @Path("/move")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response moveChip(PlayerMove playerMove) {
        try {


            if (playerMove.getPlayerSub() == null) {
                IllegalStateException illegalStateException = new IllegalStateException("Player Information Not provided");
                illegalStateException.printStackTrace();
                throw illegalStateException;
            }

            gameController = GameController.getGameController();
            gameController.moveChip(playerMove);
            return Response.status(HttpServletResponse.SC_OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}

//todo -- players move