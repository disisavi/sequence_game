package edu.isa681.game;

import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.PlayerGameSession;
import edu.isa681.DOA.entity.type.PlayerSate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    Player player1;
    Player player2;
    Player player3;
    Player player4;

    @BeforeEach
    void setup() {
        player1 = new Player("Avi", "a@gmail.com");
        player2 = new Player("sravya", "a@gmail.com");
        player3 = new Player("sinchu", "a@gmail.com");
        player4 = new Player("Prasad", "a@gmail.com");
        player1.setPlayerSate(PlayerSate.Online);
        player2.setPlayerSate(PlayerSate.Online);
        player3.setPlayerSate(PlayerSate.Online);
    }

    @Test
    void plaerStates() {
        player1.setPlayerSate(PlayerSate.Offline);
        player2.setPlayerSate(PlayerSate.Offline);
        player3.setPlayerSate(PlayerSate.Offline);
        assertThrows(IllegalStateException.class, () -> new Game(new Player[]{player1, player2, player3}));
    }

    @Test
    void distributeCards() {
        game = new Game(new Player[]{player1, player2, player3});
        game.distributeCards();
        assertNotEquals(game.playersGameSessions.get(0).getChip(), game.playersGameSessions.get(1).getChip());
        game.playersGameSessions.forEach(playerGameSession -> assertEquals(playerGameSession.getCardsList().size(), 6));
        for (PlayerGameSession playerGameSession : game.playersGameSessions) {
            playerGameSession.getCardsList().forEach(card -> System.out.print(card.getCardValue() + "-" + card.getCardType() + "   "));
            System.out.println("");
        }
    }

    @Test
    void game1() {
        game = new Game(new Player[]{player1, player2, player3});
        game.distributeCards();
        assertThrows(IllegalArgumentException.class, () -> game.placeChip(game.playersGameSessions.get(2), new Point(2, 2), 2));
    }
}