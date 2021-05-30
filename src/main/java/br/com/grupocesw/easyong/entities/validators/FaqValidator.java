package br.com.grupocesw.easyong.entities.validators;

public class FaqValidator {

    public static void validate(Long id, String question, String answer) {
        if (id != null && id < 1)
            throw new IllegalArgumentException("Id cannot be less 1");

        if (question == null || answer == null)
            throw new IllegalArgumentException("Question and Answer cannot be null");

        if (question.length() < 3 || answer.length() < 3)
            throw new IllegalArgumentException("Question and Answer cannot be less 3 characteres");
    }
}
