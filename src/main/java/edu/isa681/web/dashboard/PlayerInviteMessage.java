package edu.isa681.web.dashboard;

import java.util.List;

/**
 * This class is used specifically for JSON data binding from UI.
 */
public class PlayerInviteMessage {

    private List<String> playerStubsInvited;
    private String playerSelfStub;


    public List<String> getPlayerStubsInvited() {
        return playerStubsInvited;
    }

    public void setPlayerStubsInvited(List<String> playerStubsInvited) {
        this.playerStubsInvited = playerStubsInvited;
    }

    public String getPlayerSelfStub() {
        return playerSelfStub;
    }

    public void setPlayerSelfStub(String playerSelfStub) {
        this.playerSelfStub = playerSelfStub;
    }
}
