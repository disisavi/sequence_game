package edu.isa681.web.server;

public class Params {
    private static Boolean isProd = true;

    public static String getLoginParam() {
        if (isProd) {
            return "https://sequencegame.appspot.com/sq/login";
        }
        return "http://localhost:8080/seq_war_exploded/sq/login";
    }

    public static String getBoardParam() {
        if (isProd) {
            return "https://sequencegame.appspot.com/sq/game/";
        }
        return "http://localhost:8080/seq_war_exploded/sq/game/";
    }

}
