package br.com.grupocesw.easyong.entities.validators;

public class CountryValidator {

    public static void validate(Long id, String name, String abbreviation) {
        if (id != null && id < 1)
            throw new IllegalArgumentException("Id cannot be less 1");

        if (name == null || abbreviation == null)
            throw new IllegalArgumentException("Name or Abbreviation cannot be null");

        if (name.trim().length() < 3)
            throw new IllegalArgumentException("Name cannot be less 3 characteres");

        if (abbreviation.trim().length() != 3)
            throw new IllegalArgumentException("Abbreviation must have 3 characteres");

        if (abbreviation.replaceAll("[^A-Za-z]+", "").length() != 3)
            throw new IllegalArgumentException("Abbreviation must have only letters");
    }
}
