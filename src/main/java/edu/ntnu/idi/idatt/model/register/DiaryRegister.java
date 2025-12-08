package edu.ntnu.idi.idatt.model.register;

import edu.ntnu.idi.idatt.model.entity.DiaryEntry;
import edu.ntnu.idi.idatt.util.Validators;
import java.time.LocalDate;
import java.util.*;

/**
 * Maintains an in-memory collection of {@link DiaryEntry} instances and
 * provides CRUD-style operations, search and statistics over the entries.
 *
 * <p>Entries are stored in insertion order and a monotonically increasing id is
 * assigned when an entry is added via {@link #addEntry(DiaryEntry)}.
 * Validation of input parameters is delegated to {@link Validators}.
 * </p>
 */
public class DiaryRegister {
  private final List<DiaryEntry> entries;
  private int nextId;

  /**
   * Creates an empty {@code DiaryRegister} with the initial next id set to 1.
   */
  public DiaryRegister() {
    this.entries = new ArrayList<>();
    nextId = 1;
  }

  /**
   * Adds a new diary entry to the register and assigns it a unique id.
   *
   * <p>The provided {@code entry} must be non-null. The entry receives the next
   * available positive id via {@link DiaryEntry#setEntryId(int)} before being
   * stored.
   * </p>
   *
   * @param entry the diary entry to add; must be non-null
   * @throws IllegalArgumentException if {@code entry} is null (validated by {@link Validators})
   */
  public void addEntry(DiaryEntry entry) {
    Validators.validateNotNull(entry, "Diary entry");
    entry.setEntryId(nextId++);
    entries.add(entry);
  }

  /**
   * Removes the specified diary entry from the register.
   *
   * <p>If the entry is not present the method performs no action. The parameter
   * must be non-null.
   * </p>
   *
   * @param entry the diary entry to remove; must be non-null
   * @throws IllegalArgumentException if {@code entry} is null (validated by {@link Validators})
   */
  public void removeEntry(DiaryEntry entry) {
    Validators.validateNotNull(entry, "Diary entry");
    entries.remove(entry);
  }

  /**
   * Returns all entries sorted by creation time in descending order (newest first).
   *
   * <p>The returned list is a copy and modifications to it do not affect the
   * internal register.
   * </p>
   *
   * @return a list of all diary entries sorted by creation time (descending)
   */
  public List<DiaryEntry> getAllEntries() {
    return entries.stream()
            .sorted(Comparator.comparing(DiaryEntry::getCreationTime).reversed())
            .toList();
  }

  /**
   * Retrieves entry with the specified id.
   *
   * @param entryId the positive id of the entry to find
   * @return the matching {@link DiaryEntry} or {@code null} if no entry has the id
   * @throws IllegalArgumentException if {@code entryId} is not positive
   */
  public DiaryEntry getEntryById(int entryId) {
    if (entryId <= 0) {
      throw new IllegalArgumentException("id must be positive");
    }

    return entries.stream()
            .filter(entry -> entry.getEntryId() == entryId)
            .findFirst()
            .orElse(null);
  }

  /**
   * Returns all entries authored by the user with the given email address.
   *
   * <p>Email comparison is case-insensitive. The provided email must be a
   * non-empty string.
   * </p>
   *
   * @param email the author's email to match; must be a non-empty string
   * @return a list of entries authored by the specified email
   * @throws IllegalArgumentException if {@code email} is invalid (validated by {@link Validators})
   */
  public List<DiaryEntry> getEntriesByAuthor(String email) {
    Validators.validateString(email, "Email");

    return entries.stream()
            .filter(entry -> entry.getAuthor().getEmail().equalsIgnoreCase(email))
            .toList();
  }

  /**
   * Computes statistics on the number of entries per author.
   *
   * <p>The result is a map where each key is an author's email and the corresponding
   * value is the count of entries authored by that email.
   * </p>
   *
   * @return a map of author emails to their respective entry counts
   */
  public Map<String, Long> getAuthorStatistics() {
    Map<String, Long> statistics = new HashMap<>();

    entries.forEach(entry -> {
      String email = entry.getAuthor().getEmail();
      statistics.put(email, statistics.getOrDefault(email, 0L) + 1);
    });
    return statistics;
  }

  /**
   * Returns all entries whose creation date equals the provided {@code date}.
   *
   * @param date the date to match (not null)
   * @return a list of entries created on the specified date
   * @throws IllegalArgumentException if {@code date} is null (validated by {@link Validators})
   */
  public List<DiaryEntry> getEntriesByDate(LocalDate date) {
    Validators.validateNotNull(date, "Date");

    return entries.stream()
            .filter(entry -> entry.getCreationTime().toLocalDate().equals(date))
            .toList();
  }

  /**
   * Searches for entries whose creation date falls between {@code from} and {@code to}, inclusive.
   *
   * <p>Both bounds must be non-null and {@code from} must not be after {@code to}.
   * Results are sorted by creation time in ascending order.
   * </p>
   *
   * @param from the start date (inclusive); must be non-null
   * @param to the end date (inclusive); must be non-null
   * @return a list of entries within the specified date range sorted by creation time (ascending)
   * @throws IllegalArgumentException if either parameter is null or if from is after to
   */
  public List<DiaryEntry> searchEntriesBetweenDates(LocalDate from, LocalDate to) {
    Validators.validateNotNull(from, "From");
    Validators.validateNotNull(to, "To");

    if (from.isAfter(to)) {
      throw new IllegalArgumentException("From must be before to");
    }

    return entries.stream()
            .filter(entry -> {
              LocalDate date = entry.getCreationTime().toLocalDate();
              return !date.isBefore(from) && !date.isAfter(to);
            })
            .sorted(Comparator.comparing(DiaryEntry::getCreationTime))
            .toList();
  }

  /**
   * Searches entries for the given keyword in title or description.
   *
   * <p>The search is case-insensitive. An empty or null keyword is allowed but
   * will typically result in matching all entries that contain the empty string.
   * </p>
   *
   * @param keyword the search keyword; case-insensitive
   * @return a list of entries whose title or description contains the keyword
   */
  public List<DiaryEntry> searchByKeyword(String keyword) {
    return entries.stream()
            .filter(entry ->
                    entry.getDescription().toLowerCase().contains(keyword.toLowerCase())
                            || entry.getTitle().toLowerCase().contains(keyword.toLowerCase()))
            .toList();

  }
}
