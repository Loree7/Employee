package com.employee.progetto.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Crittografia {
    private static byte[] hash(byte[] input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] output = md.digest(input);
        return output;
    }

    private static String toHex(byte[] input) {
        StringBuilder sb = new StringBuilder();
        for (byte b : input) {
            sb.append(Integer.toHexString(0xFF & b));
        }
        return sb.toString();
    }

    public static String codifica(String password) throws NoSuchAlgorithmException{
        return toHex(hash(password.getBytes(),"MD5"));
    }
    public static String generazioneSalt(){
        StringBuilder salt = new StringBuilder();
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        Random rand = new SecureRandom();
        int random;
        int lunghezzaSalt = 10;
        while(--lunghezzaSalt != 0){
            random = rand.nextInt(s.length());
            salt.append(s.charAt(random));
        }
        return salt.toString();
    }
}
