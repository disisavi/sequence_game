package edu.isa681.DOA.entity;

import edu.isa681.DOA.entity.type.PlayerSate;

import java.util.Arrays;

public class Player {
    byte[] name;
    byte[] emailID;
    Integer playerID;
    PlayerSate playerSate;
    String playerSub;

    public Player(byte[] name, byte[] emailID) {
        this.name = name;
        this.emailID = emailID;
    }

    public PlayerGameSession getNewPlayerSession() {
        this.playerSate = PlayerSate.Invited;
        return new PlayerGameSession(this);
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public byte[] getEmailID() {
        return emailID;
    }

    public void setEmailID(byte[] emailID) {
        this.emailID = emailID;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Integer playerID) {
        this.playerID = playerID;
    }

    public PlayerSate getPlayerSate() {
        return playerSate;
    }

    public void setPlayerSate(PlayerSate playerSate) {
        this.playerSate = playerSate;
    }

    public String getPlayerSub() {
        return playerSub;
    }

    public void setPlayerSub(String playerSub) {
        this.playerSub = playerSub;
    }

    @Override
    public boolean equals(Object obj) {
        return Arrays.equals(((Player) obj).emailID, this.emailID);
    }
}


