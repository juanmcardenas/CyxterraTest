package com.juanmcardenas.auth.util;


import android.util.Base64;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public class SecurityUtil {

    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.@#$%^&+=])(?=\\S+$).{8,40}$";

    public static boolean isEmailAddressValid(String s) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String s) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static boolean isSecurityQuestionValid(String s) {
//        String[] singleChoiceItems = context.getResources().getStringArray(R.array.security_questions_array);
//        return Arrays.asList(singleChoiceItems).contains(s);
        return true;
    }

    public static boolean isSecurityAnswerValid(String s) {
        return (s != null && s.length() < 40);
    }

    public static String encryptPassword(String text, String question, String answer) {
        return getMd5Hash(text + question + answer);
    }

    private static String AesEncrypt(String text, String key, String initVector) {
        try {
            IvParameterSpec iv = new IvParameterSpec(getMd5Hash(initVector).getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(getMd5Hash(key).getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(text.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String getMd5Hash(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte[] digest = md.digest();
            return new String(digest, 0, digest.length, "UTF-8");
        } catch (Exception e) {
            return String.format("%16s", s);
        }
    }

}
