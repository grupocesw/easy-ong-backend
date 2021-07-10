package br.com.grupocesw.easyong.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGeneratorUtil {

    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
    private static final String OTHER_SYMBOL = "~$^+=<>";
    private static final String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;
    private static final int PASSWORD_LENGTH = 20;

    private static final String PASSWORD_ALLOW =
            CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;

    private static SecureRandom random = new SecureRandom();

    public static String generateStrongPassword() {
        StringBuilder stringBuilder = new StringBuilder(PASSWORD_LENGTH);

        String password = stringBuilder
            .append(generateRandomString(CHAR_LOWERCASE, 2))
            .append(generateRandomString(CHAR_UPPERCASE, 2))
            .append(generateRandomString(DIGIT, 2))
            .append(generateRandomString(OTHER_SPECIAL, 2))
            .append(generateRandomString(PASSWORD_ALLOW, PASSWORD_LENGTH - 8))
            .toString();

        return shuffleString(password);
    }

    private static String generateRandomString(String input, int size) {

        if (input == null || input.length() <= 0) throw new IllegalArgumentException("Invalid input");
        if (size < 1) throw new IllegalArgumentException("Invalid size");

        StringBuilder result = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }

        return result.toString();
    }

    public static String shuffleString(String input) {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);

        return result.stream().collect(Collectors.joining());
    }

}
