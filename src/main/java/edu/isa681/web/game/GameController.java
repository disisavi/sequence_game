package edu.isa681.web.game;

import com.google.api.client.auth.openidconnect.IdToken;
import edu.isa681.DOA.DOA;
import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.PlayerGameSession;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.DOA.util.Util;
import edu.isa681.game.Game;
import edu.isa681.web.messages.PlayerInfoMessage;
import edu.isa681.web.messages.PlayerInviteMessage;
import edu.isa681.web.game.abstractClass.AbstractGameController;
import edu.isa681.web.messages.PlayerMove;

import java.awt.*;
import java.security.GeneralSecurityException;
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

    public Player getPlayerBySub(String sub) {
        return getPlayers().get(sub);
    }

    public String getDecryptedPlayerBySub(String sub) throws GeneralSecurityException {
        return encryptionRoutine.decrypt(getPlayers().get(sub).getName(), sub);
    }


    private Player createPlayer(IdToken.Payload loginPayload) throws GeneralSecurityException {
        String email = (String) loginPayload.get("email");
        String name = (String) loginPayload.get("name");
        String sub = (String) loginPayload.get("sub");
        if (Util.validateEmailID(email)) {
            byte[] encryptedName = this.encryptionRoutine.encrypt(name, sub);
            byte[] encryptedEmail = this.encryptionRoutine.encrypt(email, sub);
            Player player = new Player(encryptedName, encryptedEmail);
            player.setPlayerSub(sub);
            return player;
        } else {
            throw new IllegalStateException("Invalid Email");
        }
    }

    private Player getRegisterdPlayer(String sub) {
        DOA doa = DOA.getDoa();
        return doa.getPlayerByEmail(sub);
    }

    private synchronized void putPlayerOnSession(IdToken.Payload loginPayload) throws GeneralSecurityException {
        String sub = (String) loginPayload.get("sub");

        Player player = getRegisterdPlayer(sub);
        if (player == null) {
            player = createPlayer(loginPayload);
        }
        getPlayers().put(sub, player);
        player.setPlayerSate(PlayerSate.Online);
        player.setPlayer();
    }

    /**
     * @param loginPayload:
     * @return playerSub
     * <p>
     * the order of events is
     * Check if player is in session
     * if yes
     * get the player, set the player as online and return the sub
     * if no
     * check if the player exists in the DB
     * if yes
     * get the player from DB, and then add player to the game session and returen the sub
     * if no
     * create the player and put it in session
     */
    public String signUporInNewPlayer(IdToken.Payload loginPayload) throws GeneralSecurityException {
        String sub = (String) loginPayload.get("sub");
        try {
            if (isPlayerSingedIn(sub)) {
                //Include ability for player to play an abandoned game within timeout
                getPlayers().remove(sub);
                putPlayerOnSession(loginPayload);
            } else {
                putPlayerOnSession(loginPayload);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        return sub;
    }

    public synchronized void createGame(PlayerInviteMessage playerInviteMessage) {
        List<Player> playersToPlay = new ArrayList<>();
        playerInviteMessage.getPlayerStubsInvited().forEach(elt -> playersToPlay.add(getPlayerBySub(elt)));
        Game newGame = new Game(playersToPlay);
        getGames().add(newGame);
    }

    public void playerOnlineRightNow(PlayerInfoMessage playerInfoMessage) {

        List<Player> players = getPlayersOnline();
        players.forEach(player -> {
            try {
                playerInfoMessage.getPlayerSateMap().put(player.getPlayerSub(), getDecryptedPlayerBySub(player.getPlayerSub()));
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

    }

    public Boolean isPlayerAttachedToGame(String PlayerSub) {
        boolean isPlayerAttachedToGame = false;
        if (gameController.getPlayerBySub(PlayerSub) != null) {
            for (Game game : gameController.getGames()) {
                for (PlayerGameSession playerGameSession : game.getPlayersGameSessions()) {
                    if (playerGameSession.player.equals(gameController.getPlayerBySub(PlayerSub))) {
                        isPlayerAttachedToGame = true;
                        break;
                    }
                }
            }
        }
        return isPlayerAttachedToGame;
    }

    public Game getGameForPlayer(String playerSub) {
        Game gameForPlayer = null;
        if (gameController.getPlayerBySub(playerSub) != null) {
            for (Game game : gameController.getGames()) {
                for (PlayerGameSession playerGameSession : game.getPlayersGameSessions()) {
                    if (playerGameSession.player.equals(gameController.getPlayerBySub(playerSub))) {
                        gameForPlayer = game;
                        break;
                    }
                }
            }
        }
        return gameForPlayer;
    }

    public void moveChip(PlayerMove playerMove) {
        Game game = this.getGameForPlayer(playerMove.getPlayerSub());
        Player player = gameController.getPlayerBySub(playerMove.getPlayerSub());
        if (game == null) {
            IllegalStateException illegalStateException = new IllegalStateException("Player and/or Game not found");
            illegalStateException.printStackTrace();
            throw illegalStateException;
        }

        Integer playerIndexOnGame = null;
        for (int i = 0; i < 3; i++) {
            if (game.getPlayersGameSessions().get(i).player.equals(player)) {
                playerIndexOnGame = i;
            }
        }

        if (playerIndexOnGame == null) {
            IllegalStateException illegalStateException = new IllegalStateException("Player index not found on game. This is not expected");
            illegalStateException.printStackTrace();
            throw illegalStateException;
        }

        if (game.getTurnIndex().equals(playerIndexOnGame)) {
            Point point = new Point(playerMove.getX(), playerMove.getY());
            game.getPlayersGameSessions().get(playerIndexOnGame).placeChip(point, playerMove.getCardIndex());
        } else {
            IllegalStateException illegalStateException = new IllegalStateException("Not Your turn!!");
            illegalStateException.printStackTrace();
            throw illegalStateException;
        }
        game.checkSequenceAndNextTurn(game.getPlayersGameSessions().get(playerIndexOnGame));
    }
}
