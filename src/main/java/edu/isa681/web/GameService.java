package edu.isa681.web;

import com.google.gson.*;
import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.Game;
import edu.isa681.game.GameState;
import org.codehaus.jackson.JsonNode;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/game")
public class GameService {
    GameController gameController;

    @GET
    @Path("/getBoard")
    @Produces(MediaType.APPLICATION_JSON)
    public GameState initBoardData() {
        Player player1 = new Player("Avi", "a@gmail.com");
        Player player2 = new Player("sravya", "a@gmail.com");
        Player player3 = new Player("sinchu", "a@gmail.com");
        player1.setPlayerSate(PlayerSate.Online);
        player2.setPlayerSate(PlayerSate.Online);
        player3.setPlayerSate(PlayerSate.Online);
        Game game = new Game(new Player[]{player1, player2, player3});
        return game.getGameState();
    }

    /**
     * @param incomingMessage of the format {playerTokens:[{playerToken : sampletoken},{playerToken : sampletoken2}]}
     */
    @POST
    @Path("/registerPlayer")
    @Consumes(MediaType.TEXT_PLAIN)
    public void createGame(String incomingMessage) {
        JsonObject playersToAdd = new JsonParser().parse(incomingMessage).getAsJsonObject();
        gameController = GameController.getGameController();
        try {
            if (playersToAdd.getAsJsonArray("playerTokens").size() == 3) {
                JsonArray playersTokens = playersToAdd.getAsJsonArray("playerTokens");
                for (JsonElement playertoken : playersTokens) {
                    //todo 1. make logic at gamecontroller level to check if the player is 1. registered and 2. online right now
                    JsonElement asd = playersTokens.getAsJsonObject().get("playerToken");
                }
            } else {
                throw new IllegalArgumentException("Please select correct number of players");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}