package com.foodapp.util;
import java.util.Base64;
public class CryptoUtil {
    public static String encode(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
    }
    public static String decode(String s) {
        return new String(Base64.getDecoder().decode(s));
    }
}
