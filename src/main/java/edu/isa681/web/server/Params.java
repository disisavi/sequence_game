package edu.isa681.web.server;

public class Params {
    private static Boolean isProd = false;
    private static Boolean isIDE = false;

    public static String getLoginParam() {
        if (isProd) {
            return "https://sequencegame.appspot.com/sq/login";
        }
        if (isIDE) {
            return "http://localhost:8080/seq_war_exploded/sq/login";
        }
        return "http://localhost:8080/sq/login";
    }

    public static String getBoardParam() {
        if (isProd) {
            return "https://sequencegame.appspot.com/sq/game/";
        }
        if (isIDE) {
            return "http://localhost:8080/seq_war_exploded/sq/game/";
        }
        return "http://localhost:8080/sq/game/";
    }

}
