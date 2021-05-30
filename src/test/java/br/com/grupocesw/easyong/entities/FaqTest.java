package br.com.grupocesw.easyong.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FaqTest {

    public Faq createDefaultObject() {
        return new Faq(null, "This is my question?", "This is my answer");
    }

    public Faq createObject(Long id, String question, String answer) {
        return new Faq(id, question, answer);
    }

    @Test
    public void testSuccess() {
        Faq obj = createObject(1L, "This is my question?", "This is my answer");

        Assertions.assertEquals(1L, obj.getId());
        Assertions.assertEquals("This is my question?", obj.getQuestion());
        Assertions.assertEquals("This is my answer", obj.getAnswer());
    }

    @Test
    public void testWithIdNullSuccess() {
        Faq obj = createDefaultObject();

        Assertions.assertNull(obj.getId());
        Assertions.assertEquals("This is my question?", obj.getQuestion());
        Assertions.assertEquals("This is my answer", obj.getAnswer());
    }

    @Test
    public void testIdLess1Failure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(-1L, "This is my question?", "This is my answer"));

        Assertions.assertEquals("Id cannot be less 1", exception.getMessage());
    }

    @Test
    public void testQuestionNullFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, null, "This is my answer"));

        Assertions.assertEquals("Question and Answer cannot be null", exception.getMessage());
    }

    @Test
    public void testAnswerNullFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, "This is my question?", null));

        Assertions.assertEquals("Question and Answer cannot be null", exception.getMessage());
    }

    @Test
    public void testQuestionOrAnswerNullFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, null, null));

        Assertions.assertEquals("Question and Answer cannot be null", exception.getMessage());
    }

    @Test
    public void testQuestionOrAnswerEmptyFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> createObject(1L, "", ""));

        Assertions.assertEquals("Question and Answer cannot be less 3 characteres", exception.getMessage());
    }

    @Test
    public void testQuestionAndAnswerLess3CharacteresFailure() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> createObject(1L, "qu", "an"));

        Assertions.assertEquals("Question and Answer cannot be less 3 characteres", exception.getMessage());
    }

    @Test
    public void testQuestionAndAnswerWith3CharacteresNotFailure() {
        Assertions.assertDoesNotThrow(() -> createObject(1L, "que", "ans"));
    }

}
