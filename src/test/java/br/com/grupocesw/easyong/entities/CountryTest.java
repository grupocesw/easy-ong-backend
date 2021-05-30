package br.com.grupocesw.easyong.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CountryTest {

    public Country createDefaultObject() {
        return new Country(null, "Brasil", "BRA");
    }

    public Country createObject(Long id, String name, String abbreviation) {
        return new Country(id, name, abbreviation);
    }

    @Test
    public void testSuccess() {
        Country obj = createObject(1L, "Brasil", "BRA");

        Assertions.assertEquals(1L, obj.getId());
        Assertions.assertEquals("Brasil", obj.getName());
        Assertions.assertEquals("BRA", obj.getAbbreviation());
    }

    @Test
    public void testIdNullSuccess() {
        Country obj = createDefaultObject();

        Assertions.assertNull(obj.getId());
        Assertions.assertEquals("Brasil", obj.getName());
        Assertions.assertEquals("BRA", obj.getAbbreviation());
    }

    @Test
    public void testIdLess1Failure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(-1L, "Brasil", "BRA"));

        Assertions.assertEquals("Id cannot be less 1", exception.getMessage());
    }

    @Test
    public void testNameNullFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, null, "BRA"));

        Assertions.assertEquals("Name or Abbreviation cannot be null", exception.getMessage());
    }

    @Test
    public void testAbbreviationNullFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, "Brasil", null));

        Assertions.assertEquals("Name or Abbreviation cannot be null", exception.getMessage());
    }

    @Test
    public void testNameOrAbbreviationNullFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, null, null));

        Assertions.assertEquals("Name or Abbreviation cannot be null", exception.getMessage());
    }

    @Test
    public void testNameOrAbbreviationEmptyFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, "", ""));

        Assertions.assertEquals("Name cannot be less 3 characteres", exception.getMessage());
    }

    @Test
    public void testNameAndAbbreviationLess3CharacteresFailure() {
        Exception exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> createObject(1L, "Br", "BR"));
        Assertions.assertEquals("Name cannot be less 3 characteres", exception.getMessage());
    }

    @Test
    public void testNameAndAbbreviationWith3CharacteresNotFailure() {
        Assertions.assertDoesNotThrow(() -> createObject(1L, "Bra", "BRA"));
    }

    @Test
    public void testAbbreviationUpperCase() {
        Country obj = createObject(null, "Brasil", "bra");
        Assertions.assertEquals("BRA", obj.getAbbreviation());
    }

    @Test
    public void testAbbreviationNoSpacesFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, "Brasil", " a "));

        Assertions.assertEquals("Abbreviation must have 3 characteres", exception.getMessage());
    }

    @Test
    public void testAbbreviationOnlyLettersFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, "Brasil", "Br4"));

        Assertions.assertEquals("Abbreviation must have only letters", exception.getMessage());
    }
}
