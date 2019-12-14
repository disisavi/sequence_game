package edu.isa681.web.game.abstractClass;

import edu.isa681.DOA.entity.Player;
import edu.isa681.DOA.entity.type.PlayerSate;
import edu.isa681.game.Game;
import edu.isa681.web.game.EncryptionRoutine;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AbstractGameController {
    Logger log = Logger.getLogger(AbstractGameController.class);
    ScheduledExecutorService scheduler;
    private ConcurrentHashMap<String, Player> players;
    private Set<Game> games;
    protected EncryptionRoutine encryptionRoutine;
    private ConcurrentHashMap<String, Date> heartBeatMap;

    public AbstractGameController() {
        players = new ConcurrentHashMap<>();
        games = new HashSet<>();
        heartBeatMap = new ConcurrentHashMap<>();
        encryptionRoutine = EncryptionRoutine.getEncryptionRoutine();
        heartBeat();
    }

    protected ConcurrentHashMap<String, Player> getPlayers() {
        return players;
    }

    public Player getPlayerWithSub(String playerSub) {
        return getPlayers().get(playerSub);
    }

    public void setPlayers(ConcurrentHashMap<String, Player> players) {
        this.players = players;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void setHeartBeatMap(ConcurrentHashMap<String, Date> heartBeatMap) {
        this.heartBeatMap = heartBeatMap;
    }

    public ConcurrentHashMap<String, Date> getHeartBeatMap() {
        return heartBeatMap;
    }

    void heartBeat() {
        this.scheduler = Executors.newScheduledThreadPool(10);
        scheduler.scheduleAtFixedRate(() -> {
            Iterator<Map.Entry<String, Date>> iter = getHeartBeatMap().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Date> entry = iter.next();
                long dateDiff = new Date().getTime() - entry.getValue().getTime();
                long diff = TimeUnit.SECONDS.convert(dateDiff, TimeUnit.MILLISECONDS);
                if (diff > 60) {
                    Player player = getPlayers().get(entry.getKey());
                    synchronized (player) {
                        player.setPlayerSate(PlayerSate.Offline);
                        getPlayers().remove(entry.getKey());
                        player.setPlayer();
                        iter.remove();
                        log.info("Player being logged off, and offline" + player.getPlayerSub());
                    }
                } else if (diff > 5) {
                    Player player = getPlayers().get(entry.getKey());
                    synchronized (player) {
                        player.setPlayerSate(PlayerSate.GamePauseOff);
                        player.setPlayer();
                        log.info("Player being logged off, can still get into game" + player.getPlayerSub());
                        iter.remove();
                    }
                }
            }
        }, 10, 2, TimeUnit.SECONDS);
    }

    public List<Player> getPlayersOnline() {
        List<Player> players = new ArrayList<>();

        for (Map.Entry<String, Player> entry : getPlayers().entrySet()) {
            Player v = entry.getValue();
            if ((v.getPlayerSate().equals(PlayerSate.Online))) {
                players.add(v);
            }
        }
        return players;
    }
}
