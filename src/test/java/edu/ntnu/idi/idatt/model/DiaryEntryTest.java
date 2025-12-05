package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
  void testValidEntryConstructor() {
    assertEquals("Title", testEntry.getTitle());
    assertEquals("Description", testEntry.getDescription());
    assertEquals(testAuthor, testEntry.getAuthor());
    assertEquals(0, testEntry.getEntryId());

    assertNotNull(testEntry.getCreationTime());
  }

  @Test
  void testSetEntryId() {
    testEntry.setEntryId(1);
    assertEquals(1, testEntry.getEntryId());
  }

  @Test
  void testInvalidSetEntryId() {
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
  void testNoDuplicateEntryIds() {
    testEntry.setEntryId(1);

    assertThrows(IllegalStateException.class,
            () -> {
          testEntry.setEntryId(1);
            });
  }

  @Test
  void testInvalidTitleThrowsException() {
    assertThrows(IllegalArgumentException.class,
            () -> {
      new DiaryEntry("", "Description", testAuthor);
    });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new DiaryEntry("", "Description", testAuthor);
            });
  }

  @Test
  void testInvalidDescriptionThrowsException() {
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
  void testNullAuthorThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new DiaryEntry("Gyldig tittel", "Gyldig tekst", null);
    });
  }
}