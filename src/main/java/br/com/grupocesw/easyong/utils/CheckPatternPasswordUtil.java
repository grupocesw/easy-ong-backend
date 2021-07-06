package br.com.grupocesw.easyong.utils;

import java.util.regex.Pattern;

public class CheckPatternPasswordUtil {

    public static String isPasswordOk(String password, String confirmationPassword) {
        String error = "";

        error = passwordsLength(password);
        if (!error.isEmpty()) return error;

        error = passwordsMatch(password, confirmationPassword);
        if (!error.isEmpty()) return error;

        error = hasSecurityPatterns(password);

        return error;
    }

    public static String hasSecurityPatterns(String password) {
        Pattern[] inputRegexes = new Pattern[4];

        inputRegexes[0] = Pattern.compile(".*[A-Z].*");
        inputRegexes[1] = Pattern.compile(".*[a-z].*");
        inputRegexes[2] = Pattern.compile(".*\\d.*");
        inputRegexes[3] = Pattern.compile(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");

        for (Pattern inputRegex : inputRegexes) {
            if (!inputRegex.matcher(password).matches()) {
                return "A senha deve conter letra maiúscula, minúscula, número e caracter especial";
            }
        }

        return "";
    }

    public static String passwordsMatch(String password, String confirmationPassword) {
        if (!password.equals(confirmationPassword))
            return "As senhas devem ser iguais";

        return "";
    }

    public static String passwordsLength(String password) {
        if (password.length() < 8 || password.length() > 100)
            return "As senhas devem ter entre 8 e 100 caracteres";

        return "";
    }
}
