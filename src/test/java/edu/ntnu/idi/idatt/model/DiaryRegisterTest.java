package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DiaryRegisterTest {
  private DiaryRegister diaryRegister;
  private Author author1;
  private Author author2;
  private DiaryEntry entry1;
  private DiaryEntry entry2;

  private final LocalDateTime past = LocalDateTime.now().minusDays(7);

  private final LocalDateTime before = LocalDateTime.now().minusDays(10);
  private final LocalDateTime start = LocalDateTime.now().minusDays(8);
  private final LocalDateTime middle = LocalDateTime.now().minusDays(5);
  private final LocalDateTime end  = LocalDateTime.now().minusDays(2);
  private final LocalDateTime after  = LocalDateTime.now().minusDays(0);


  @BeforeEach
  void setUp() {
    diaryRegister = new DiaryRegister();
    author1 = new Author("Per", "Petterson", "per@ex.com");
    author2 = new Author("Fredrik", "Fredrikson", "fredrik@ex.com");
    entry1 = new  DiaryEntry("Present", "What i did to day", author1);
    entry2 = new  DiaryEntry("Past", "What i did last week", author2,  past);


  }

  @Test
  void testAddValidEntryStoresInRegister() {
    diaryRegister.addEntry(entry1);

    assertTrue(diaryRegister.getAllEntries().contains(entry1));
    assertEquals(1, diaryRegister.getAllEntries().size());
  }

  @Test
  void testAddNullEntryInRegisterThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              diaryRegister.addEntry(null);
            });
  }

  @Test
  void AddEntryGeneratesId() {
    diaryRegister.addEntry(entry1);

    assertEquals(1, entry1.getEntryId());
  }

  @Test
  void AddMultipleEntryGeneratesIdInIncrements() {
    diaryRegister.addEntry(entry1);
    diaryRegister.addEntry(entry2);

    assertEquals(1, entry1.getEntryId());
    assertEquals(2, entry2.getEntryId());
  }

  @Test
  void testRemoveValidEntryStoresInRegister() {
    diaryRegister.addEntry(entry1);
    diaryRegister.addEntry(entry2);
    diaryRegister.removeEntry(entry1);

    assertTrue(diaryRegister.getAllEntries().contains(entry2));
    assertFalse(diaryRegister.getAllEntries().contains(entry1));
    assertEquals(1, diaryRegister.getAllEntries().size());
  }

  @Test
  void testRemoveNonExistingEntryDoesNothing() {
    diaryRegister.addEntry(entry1);
    DiaryEntry test = new DiaryEntry("Not here", "how do you see me?", author1);
    diaryRegister.removeEntry(test);

    assertTrue(diaryRegister.getAllEntries().contains(entry1));
    assertEquals(1, diaryRegister.getAllEntries().size());
  }

  @Test
  void testRemoveNullEntryInRegisterThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              diaryRegister.removeEntry(null);
            });
  }

  @Test
  void testGetAllEntriesSortsNewFirst() {
    diaryRegister.addEntry(entry1);
    diaryRegister.addEntry(entry2);

    assertEquals(2, diaryRegister.getAllEntries().size());
    assertEquals(entry1, diaryRegister.getAllEntries().get(0));
    assertEquals(entry2, diaryRegister.getAllEntries().get(1));
  }

  @Test
  void testWhenValidIdGetEntryByIdIsFound() {
    diaryRegister.addEntry(entry1);
    int id = entry1.getEntryId();

    assertEquals(entry1, diaryRegister.getEntryById(id));
    assertEquals(id, diaryRegister.getEntryById(id).getEntryId());
  }

  @Test
  void testWhenNonExistingIdGetEntryByIdIsNotFound() {
    int id = 99;
    assertNull(diaryRegister.getEntryById(id));
  }

  @Test
  void testWhenNegativeIdGetEntryByIdThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              diaryRegister.getEntryById(0);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              diaryRegister.getEntryById(-5);
            });

  }

  @Test
  void testWhenValidAuthorGetEntriesByAuthorIsFound() {
    diaryRegister.addEntry(entry1);
    diaryRegister.addEntry(entry2);
    DiaryEntry entry3 = new DiaryEntry("Test", "test", author1);
    diaryRegister.addEntry(entry3);
    List<DiaryEntry> test = diaryRegister.getEntriesByAuthor(author1.getEmail());

    assertEquals(2, test.size());
    assertTrue(test.contains(entry1));
    assertTrue(test.contains(entry3));
    assertFalse(test.contains(entry2));
  }

  @Test
  void testWhenNonExsistingAuthorGetEntriesByAuthorIsNotFound() {
    List<DiaryEntry> test = diaryRegister.getEntriesByAuthor("skybert@ex.com");

    assertTrue(test.isEmpty());
    assertNotNull(test);
  }

  @Test
  void testNullGetEntriesByAuthorThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              diaryRegister.getEntriesByAuthor(null);
            });
  }

  @Test
  void testGetAuthorByStatistics() {
    diaryRegister.addEntry(new DiaryEntry("title 1", "desc", author1, before));
    diaryRegister.addEntry(new DiaryEntry("title 2", "desc", author1, start));
    diaryRegister.addEntry(new DiaryEntry("title 3", "desc", author2, middle));
    diaryRegister.addEntry(new DiaryEntry("title 4", "desc", author2, end));
    diaryRegister.addEntry(new DiaryEntry("title 5", "desc", author2, after));

    Map<String, Long> testStats = diaryRegister.getAuthorStatistics();

    assertEquals(2, testStats.size());
    assertTrue(testStats.containsKey(author1.getEmail()));
    assertTrue(testStats.containsKey(author2.getEmail()));
    assertEquals(2L, testStats.get(author1.getEmail()));
    assertEquals(3L, testStats.get(author2.getEmail()));
  }

  @Test
  void testGetAuthorStatisticsIsEmpty() {
    Map<String, Long> testStats = diaryRegister.getAuthorStatistics();

    assertTrue(testStats.isEmpty());
    assertNotNull(testStats);
  }

  @Test
  void testWhenValidDateGetEntriesByDateIsFound() {
    DiaryEntry dateEntry1 = new DiaryEntry("title 1", "desc", author1, start);
    DiaryEntry dateEntry2 = new DiaryEntry("title 2", "desc", author1, start);
    DiaryEntry dateEntry3 = new DiaryEntry("title 3", "desc", author1, end);

    diaryRegister.addEntry(dateEntry1);
    diaryRegister.addEntry(dateEntry2);
    diaryRegister.addEntry(dateEntry3);

    List<DiaryEntry> test = diaryRegister.getEntriesByDate(start.toLocalDate());

    assertEquals(2, test.size());
    assertTrue(test.contains(dateEntry1));
    assertTrue(test.contains(dateEntry2));
    assertFalse(test.contains(dateEntry3));
  }

  @Test
  void testWhenNonExistingDateGetEntriesByDateIsNotFound() {
    diaryRegister.addEntry(entry1);

    LocalDate testDate = LocalDate.now().minusMonths(5);
    List<DiaryEntry> test = diaryRegister.getEntriesByDate(testDate);

    assertTrue(test.isEmpty());
    assertNotNull(test);
  }

  @Test
  void testWhenNullDateGetEntriesByDateThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              diaryRegister.getEntriesByDate(null);
            });

  }

  @Test
  void testWhenValidDateSearchEntriesBetweenDatesIsFound() {
    DiaryEntry dateEntry1 = new DiaryEntry("title 1", "desc", author1, before);
    DiaryEntry dateEntry2 = new DiaryEntry("title 2", "desc", author1, start);
    DiaryEntry dateEntry3 = new DiaryEntry("title 3", "desc", author1, middle);
    DiaryEntry dateEntry4 = new DiaryEntry("title 4", "desc", author1, end);
    DiaryEntry dateEntry5 = new DiaryEntry("title 5", "desc", author1, after);

    diaryRegister.addEntry(dateEntry1);
    diaryRegister.addEntry(dateEntry2);
    diaryRegister.addEntry(dateEntry3);
    diaryRegister.addEntry(dateEntry4);
    diaryRegister.addEntry(dateEntry5);

    List<DiaryEntry> test = diaryRegister.searchEntriesBetweenDates(
            start.minusDays(1).toLocalDate(),
            end.plusDays(1).toLocalDate());

    assertEquals(3, test.size());
    assertFalse(test.contains(dateEntry1));
    assertFalse(test.contains(dateEntry5));
  }

  @Test
  void testWhenValidDateSearchEntriesBetweenBoundaryDatesIsFound() {
    DiaryEntry dateEntry1 = new DiaryEntry("title 1", "desc", author1, before);
    DiaryEntry dateEntry2 = new DiaryEntry("title 2", "desc", author1, start);
    DiaryEntry dateEntry3 = new DiaryEntry("title 3", "desc", author1, middle);
    DiaryEntry dateEntry4 = new DiaryEntry("title 4", "desc", author1, end);
    DiaryEntry dateEntry5 = new DiaryEntry("title 5", "desc", author1, after);

    diaryRegister.addEntry(dateEntry1);
    diaryRegister.addEntry(dateEntry2);
    diaryRegister.addEntry(dateEntry3);
    diaryRegister.addEntry(dateEntry4);
    diaryRegister.addEntry(dateEntry5);

    List<DiaryEntry> test = diaryRegister.searchEntriesBetweenDates(
            start.toLocalDate(),
            end.toLocalDate());

    assertEquals(3, test.size());
    assertFalse(test.contains(dateEntry1));
    assertFalse(test.contains(dateEntry5));
  }

  @Test
  void testWhenDatesInvalidRangeSearchEntriesBetweenDatesThrows() {
    LocalDate startDate = start.toLocalDate();
    LocalDate endDate = end.toLocalDate();
    assertThrows(IllegalArgumentException.class,
            () -> {
              diaryRegister.searchEntriesBetweenDates(
                      endDate,
                      startDate
              );
            });
  }

  @Test
  void testWHenValidKeywordSearchByKeywordIsFound() {
    diaryRegister.addEntry(entry1);
    diaryRegister.addEntry(entry2);
    List<DiaryEntry> test1 = diaryRegister.searchByKeyword("Present");
    List<DiaryEntry> test2 = diaryRegister.searchByKeyword("week");

    assertEquals(1, test1.size());
    assertTrue(test1.contains(entry1));
    assertEquals(1, test2.size());
    assertTrue(test2.contains(entry2));
  }

  @Test
  void testIfSearchByKeywordIsNotCaseSensitive() {
    diaryRegister.addEntry(entry1);
    diaryRegister.addEntry(entry2);
    List<DiaryEntry> test1 = diaryRegister.searchByKeyword("Present");
    List<DiaryEntry> test2 = diaryRegister.searchByKeyword("present");

    assertEquals(1, test1.size());
    assertTrue(test1.contains(entry1));
    assertEquals(1, test2.size());
    assertTrue(test2.contains(entry1));
  }





}