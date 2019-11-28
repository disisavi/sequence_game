package edu.isa681.web.game;

import com.google.api.client.auth.openidconnect.IdToken;
import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.Game;
import edu.isa681.web.dashboard.PlayerInviteMessage;
import edu.isa681.web.game.abstractClass.AbstractGameController;

import java.util.ArrayList;
import java.util.List;


public class GameController extends AbstractGameController {
    private static GameController gameController;

    private GameController() {
        super();
    }

    /**
     * @return a singleton GameController
     */
    public static GameController getGameController() {
        if (GameController.gameController == null) {
            synchronized (GameController.class) {
                if (gameController == null) {
                    gameController = new GameController();
                }
            }
        }
        return gameController;
    }

    private Boolean isPlayerSingedIn(String sub) {
        return getPlayers().containsKey(sub);
    }

    private Player getPlayerBySub(String sub) {
        return getPlayers().get(sub);
    }


    private Player createPlayer(IdToken.Payload loginPayload) {
        String email = (String) loginPayload.get("email");
        String name = (String) loginPayload.get("name");

        Player player = new Player(name, email);
//        TODO :
//        Put it on database
        return player;
    }

    public Player getRegisterdPlayer(String email) {
        //TODO :
        // Get player from persistence layer
        return null;
    }

    private void putPlayerOnSession(IdToken.Payload loginPayload) {
        String email = (String) loginPayload.get("email");
        String sub = (String) loginPayload.get("sub");

        Player player = getRegisterdPlayer(email);
        if (player == null) {
            player = createPlayer(loginPayload);
        }
        getPlayers().put(sub, player);
    }

    public String signUporInNewPlayer(IdToken.Payload loginPayload) {
        String sub = (String) loginPayload.get("sub");
        if (isPlayerSingedIn(sub)) {
            Player player = getPlayerBySub(sub);
            if (player == null) {
                getPlayers().remove(sub);
                putPlayerOnSession(loginPayload);
            }
            player.setPlayerSate(PlayerSate.Online);
        } else {
            Player player = createPlayer(loginPayload);
            putPlayerOnSession(loginPayload);
        }
        return sub;
    }

    public synchronized void createGame(PlayerInviteMessage playerInviteMessage) {
        List<Player> playersToPlay = new ArrayList<>();
        playerInviteMessage.getPlayerStubsInvited().forEach(elt -> playersToPlay.add(getPlayerBySub(elt)));
        Game newGame = new Game(playersToPlay);
        getGames().add(newGame);
    }

}
