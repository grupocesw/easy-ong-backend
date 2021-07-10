package br.com.grupocesw.easyong.utils;

import br.com.grupocesw.easyong.exceptions.BadRequestException;

import java.util.regex.Pattern;

public class PasswordVerificationUtil {

    public static String isPasswordOkWithConfirmation(String password, String confirmationPassword) {
        passwordsLength(password);
        passwordsMatch(password, confirmationPassword);
        hasSecurityPatterns(password);

        return password;
    }

    public static String isPasswordOk(String password) {
        passwordsLength(password);
        hasSecurityPatterns(password);

        return password;
    }

    public static void hasSecurityPatterns(String password) {
        Pattern[] inputRegexes = new Pattern[4];

        inputRegexes[0] = Pattern.compile(".*[A-Z].*");
        inputRegexes[1] = Pattern.compile(".*[a-z].*");
        inputRegexes[2] = Pattern.compile(".*\\d.*");
        inputRegexes[3] = Pattern.compile(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");

        for (Pattern inputRegex : inputRegexes) {
            if (!inputRegex.matcher(password).matches()) {
                throw new BadRequestException("A senha deve conter letra maiúscula, minúscula, número e caracter especial");
            }
        }
    }

    public static void passwordsMatch(String password, String confirmationPassword) {
        if (!password.equals(confirmationPassword))
            throw new BadRequestException("As senhas devem ser iguais");
    }

    public static void passwordsLength(String password) {
        if (password.length() < 8 || password.length() > 100)
            throw new BadRequestException("As senhas devem ter entre 8 e 100 caracteres");
    }
}
