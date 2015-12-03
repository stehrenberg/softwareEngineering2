package edu.hm.cs.softengii.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to generate a hashed password.
 */
public class PasswordGen {

    /**
     * Returns a hashed string representation of the given argument by using SHA-265.
     * @param pw - the password to be hased
     */
    public static String generatePassword(String pw) {

        MessageDigest md;
        StringBuilder sb = new StringBuilder();

        try {

            md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(pw.getBytes("UTF-8"));

            for (byte singleByte : bytes) {
                sb.append(Integer.toString((singleByte & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotGeneratePassword();
            Logger.getLogger(ScoreCalculator.class.getName()).log(Level.SEVERE, msg, ex);
        }

        return  sb.toString();
    }


}
