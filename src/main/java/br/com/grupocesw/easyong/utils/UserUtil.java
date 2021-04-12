package br.com.grupocesw.easyong.utils;

import java.util.Random;

public class UserUtil {
    
    public static String createRandomCode(int length)
    {
    	final String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        while (code.length() < length) {
            int index = (int) (random.nextFloat() * SALTCHARS.length());
            code.append(SALTCHARS.charAt(index));
        }

        return code.toString();
    }
}
