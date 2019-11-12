package edu.isa681.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.Game;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


@Path("/game")
public class GameService {
    @GET
    @Path("/{param}")
    public Response initBoardData(@PathParam("param") String msg) {
        Player player1 = new Player("Avi", "a@gmail.com");
        Player player2 = new Player("sravya", "a@gmail.com");
        Player player3 = new Player("sinchu", "a@gmail.com");
        player1.setPlayerSate(PlayerSate.Online);
        player2.setPlayerSate(PlayerSate.Online);
        player3.setPlayerSate(PlayerSate.Online);
        Game game = new Game(new Player[]{player1, player2, player3});
        Gson gson = new GsonBuilder().serializeNulls().create();
        String returnJson = gson.toJson(game.getGameState());
        System.out.println(returnJson);
        return Response.status(200).entity(returnJson).build();
    }
}
