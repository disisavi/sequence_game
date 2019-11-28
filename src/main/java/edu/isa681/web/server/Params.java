package edu.isa681.web.server;

public class Params {
    private static Boolean isProd = false;
    private static Boolean isIDE = true;

    public static String getLoginParam() {
        if (isProd) {
            return "https://sequencegame.appspot.com/sq/login";
        }
        if (isIDE) {
            return "http://localhost:8080/seq/sq/login";
        }
        return "http://localhost:8080/sq/login";
    }

    public static String getBoardParam() {
        if (isProd) {
            return "https://sequencegame.appspot.com/sq/game/";
        }
        if (isIDE) {
            return "http://localhost:8080/seq/sq/game/";
        }
        return "http://localhost:8080/sq/game/";
    }

    public static String getCallBack() {
        if (isProd) {
            return "https://sequencegame.appspot.com/sq/oauth2callback";
        }
        if (isIDE) {
            return "http://localhost:8080/seq/sq/oauth2callback";
        }
        return "http://localhost:8080/sq/oauth2callback";
    }

}
