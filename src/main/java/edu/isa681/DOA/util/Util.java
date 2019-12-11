package edu.isa681.DOA.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    /***
     *
     * @param emailID  : emailID for the player
     * @return Boolean : Does it match the pattern?
     * We have all the characters permitted by RFC 5322 allowed before @
     * After @, its only gmail.com, as we are using only google SSO at launch
     */

    public static Boolean validateEmailID(String emailID) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@gmail.com$");
        Matcher matcher = pattern.matcher(emailID);
        return matcher.matches();
    }
}
