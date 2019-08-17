package com.juanmcardenas.auth.util;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public class SecurityUtil {

    public static boolean containsSqlCode(String s) {
        // TODO
        return false;
    }

    public static boolean containsJsCode(String s) {
        // TODO
        return false;
    }

    public static boolean isEmailAddressValid(String s) {
        // TODO
        return !containsJsCode(s) && !containsSqlCode(s);
    }

    public static boolean isPasswordValid(String s) {
        // TODO
        return !containsJsCode(s) && !containsSqlCode(s);
    }

    public static boolean isSecurityQuestionValid(String s) {
        // TODO
        return !containsJsCode(s) && !containsSqlCode(s);
    }

    public static boolean isSecurityAnswerValid(String s) {
        // TODO
        return !containsJsCode(s) && !containsSqlCode(s);
    }

}
