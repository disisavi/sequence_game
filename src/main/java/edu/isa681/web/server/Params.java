package edu.isa681.web.server;

public class Params {
    private static Boolean isProd = true;

    public static String getParam() {
        if (isProd) {
            return "https://sequencegame.appspot.com/sq/login";
        }
        return "http://localhost:8080/war_game_war_exploded/sq/login";
    }

}
