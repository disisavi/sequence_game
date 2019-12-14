package edu.isa681.web.hearbeat;

import edu.isa681.web.game.GameController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/heartBeat")
public class HeartBeat {
    GameController gameController = GameController.getGameController();

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public void heartBeat(String playerSub) {
        if (gameController.getHeartBeatMap().get(playerSub) != null) {
            gameController.getHeartBeatMap().put(playerSub, new Date());
        }
    }
}
