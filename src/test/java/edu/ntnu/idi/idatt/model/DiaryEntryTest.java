package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiaryEntryTest {

    @Test
    void testValidEntryCreation() {
        Author author = new Author("Ola", "Normann");
        DiaryEntry entry = new DiaryEntry("Min tittel", "Min tekst", author);

        assertEquals("Min tittel", entry.getTitle());
        assertEquals("Min tekst", entry.getDescription());
        assertEquals("Ola Nordmann", entry.getAuthor().getFullName());

        assertNotNull(entry.getCreationTime(), "Tidspunktet skal settes automatisk");
    }

    @Test
    void testInvalidTitleThrowsException() {
        Author validAuthor = new Author("Gylid", "Forfatter");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DiaryEntry("", "Gyldig tekst", validAuthor);
        });
        assertEquals("Title cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testNullAuthorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DiaryEntry("Gyldig tittel", "Gyldig tekst", null);
        });
    }
}