package com.zywang.myblog.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String getMD5(String str){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        md.update(str.getBytes());
        byte[] hash = md.digest();
        StringBuilder hexStr = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            if (v < 16) hexStr.append(0);
            hexStr.append(Integer.toString(v, 16));
        }
        return hexStr.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(getMD5("098098"));
    }
}
