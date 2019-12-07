package edu.isa681.DOA.entity;

import edu.isa681.DOA.entity.type.PlayerSate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
    String name;
    String emailID;
    Integer playerID;
    PlayerSate playerSate;

    public Player(String name, String emailID) {
        if (validateEmailID(emailID)) {
            this.emailID = emailID;
            this.name = name;
        } else throw new IllegalArgumentException("Email format Incorrect");
    }

    /***
     *
     * @param emailID  : emailID for the player
     * @return Boolean : Does it match the pattern?
     * We have all the characters permitted by RFC 5322 allowed before @
     * After @, its only gmail.com, as we are using only google SSO at launch
     */
    private Boolean validateEmailID(String emailID) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@gmail.com$");
        Matcher matcher = pattern.matcher(emailID);
        return matcher.matches();
    }

    public PlayerGameSession getNewPlayerSession() {
        this.playerSate = PlayerSate.Invited;
        return new PlayerGameSession(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
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

    @Override
    public boolean equals(Object obj) {
        return ((Player) obj).emailID.equalsIgnoreCase(this.emailID);
    }
}


