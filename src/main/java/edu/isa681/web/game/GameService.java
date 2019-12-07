package edu.isa681.web.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.PlayerGameSession;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.Game;
import edu.isa681.game.GameState;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;


@Path("/game")
public class GameService {
    private GameController gameController;

    @POST
    @Path("/getBoard")
    @Produces(MediaType.TEXT_PLAIN)
    public GameState initBoardData(String playerSub) {
        Player player1 = new Player("Avi", "a@gmail.com");
        Player player2 = new Player("sravya", "a@gmail.com");
        Player player3 = new Player("sinchu", "a@gmail.com");
        player1.setPlayerSate(PlayerSate.Online);
        player2.setPlayerSate(PlayerSate.Online);
        player3.setPlayerSate(PlayerSate.Online);
        Game game = new Game(new ArrayList<>(Arrays.asList(player1, player2, player3)));
        return game.getGameState();
    }


    @POST
    @Path("/registerPlayer")
    @Consumes(MediaType.TEXT_PLAIN)
    public void createGame(String playerSub) {
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
    @Consumes(MediaType.TEXT_PLAIN)
    public Boolean getPlayersJoined(String playerSub) {
        boolean returnValue = true;
        gameController = GameController.getGameController();
        Game gameForPlayer = gameController.getGameForPlayer(playerSub);

        if (gameForPlayer == null) {
            throw new IllegalStateException("No Such player attached to the game found");
        } else {
            for (PlayerGameSession playerGameSession : gameForPlayer.playersGameSessions) {
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
                throw new IllegalStateException("No game foud for the player");
            } else {
                for (PlayerGameSession playerGameSession : game.playersGameSessions) {
                    if (playerGameSession.player.equals(player)) {
                        returnPlayerGameSession = playerGameSession;
                        break;
                    }
                }
            }
        }

        return returnPlayerGameSession;
    }
}

//todo -- players move