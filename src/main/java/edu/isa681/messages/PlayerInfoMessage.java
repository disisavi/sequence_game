package edu.isa681.messages;

import edu.isa681.DOA.entity.type.PlayerSate;

import java.util.HashMap;
import java.util.Map;

public class PlayerInfoMessage {
    private Boolean gotMessage;
    private String errorMessage;
    private Map<String, PlayerSate> playerSateMap;

    public PlayerInfoMessage() {
        playerSateMap = new HashMap<>();
    }

    public Boolean getGotMessage() {
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

    public Map<String, PlayerSate> getPlayerSateMap() {
        return playerSateMap;
    }

    public void setPlayerSateMap(Map<String, PlayerSate> playerSateMap) {
        this.playerSateMap = playerSateMap;
    }
}
