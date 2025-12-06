package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DiaryEntryTest {
  private Author testAuthor;
  private DiaryEntry testEntry;

  @BeforeEach
  void setUp() {
    testAuthor = new Author("Navn", "Navneson", "navn@ex.com");
    testEntry = new DiaryEntry("Title", "Description", testAuthor);
  }

  @Test
  void testEntryConstructorWithValidValues() {
    assertEquals("Title", testEntry.getTitle());
    assertEquals("Description", testEntry.getDescription());
    assertEquals(testAuthor, testEntry.getAuthor());
    assertEquals(0, testEntry.getEntryId());

    assertNotNull(testEntry.getCreationTime());
  }

  @Test
  void testSetEntryIdWithValidValue() {
    testEntry.setEntryId(1);
    assertEquals(1, testEntry.getEntryId());
  }

  @Test
  void testSetEntryIdThrowsOnNonPositive() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              testEntry.setEntryId(-1);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              testEntry.setEntryId(0);
            });
  }

  @Test
  void testEntryIdIsImmutable() {
    testEntry.setEntryId(1);

    assertThrows(IllegalStateException.class,
            () -> {
              testEntry.setEntryId(1);
            });
  }

  @Test
  void testInvalidTitleThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              new DiaryEntry("", "Description", testAuthor);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new DiaryEntry(null, "Description", testAuthor);
            });
  }

  @Test
  void testInvalidDescriptionThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              new DiaryEntry("Title", "", testAuthor);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new DiaryEntry("Title", null, testAuthor);
            });
  }

  @Test
  void testNullAuthorThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              new DiaryEntry("Gyldig tittel", "Gyldig tekst", null);
            });
  }

  @Test
  void testEntryWithCustomDate() {
    LocalDateTime oldDate = LocalDateTime.of(2020, 12, 24, 12, 12);
    DiaryEntry oldEntry = new DiaryEntry("Title", "Description", testAuthor, oldDate);
    assertEquals(oldDate, oldEntry.getCreationTime());
  }

  @Test
  void testToString() {
    String output = testEntry.toString();
    assertTrue(output.contains("Title"));
    assertTrue(output.contains("Navn Navneson"));
  }
}