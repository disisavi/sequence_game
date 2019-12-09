package edu.isa681.web.messages;

import edu.isa681.DOA.entity.type.PlayerSate;

import java.util.HashMap;
import java.util.Map;

public class PlayerInfoMessage {
    private Boolean gotMessage;
    private String errorMessage;
    private Map<String, String> playerSateMap; // <playerSub, playerName>

    public PlayerInfoMessage() {
        playerSateMap = new HashMap<>();
    }

    public Boolean getGotMessage() {
        int i = 0;
        String l = Integer.toString(i);
        return gotMessage;
    }

    public void setGotMessage(Boolean gotMessage) {
        this.gotMessage = gotMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getPlayerSateMap() {
        return playerSateMap;
    }

    public void setPlayerSateMap(Map<String, String> playerSateMap) {
        this.playerSateMap = playerSateMap;
    }
}
