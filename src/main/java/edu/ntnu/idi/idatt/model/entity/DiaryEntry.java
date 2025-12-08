package edu.ntnu.idi.idatt.model.entity;

import edu.ntnu.idi.idatt.util.Validators;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a diary entry authored by an {@link Author} with a title,
 * description and a creation timestamp.
 *
 * <p>Instances maintain a mutable numeric identifier which is set once via
 * {@link #setEntryId(int)} and an immutable creation time provided at
 * construction. Input values are validated through {@link Validators}.
 * </p>
 */
public class DiaryEntry {
  private static final DateTimeFormatter FORMATTER =
          DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  private int entryId;
  private Author author;
  private String title;
  private String description;
  private final LocalDateTime creationTime;

  /**
   * Constructs a new diary entry using the current time as the creation time.
   *
   * @param title the title of the entry; must be a non-empty string
   * @param description the entry's textual content; must be a non-empty string
   * @param author the entry's author; must be non-null
   * @throws IllegalArgumentException if {@code title} or {@code description} is invalid
   * @throws IllegalArgumentException if {@code author} is null
   */
  public DiaryEntry(String title, String description, Author author) {
    this(title, description, author, LocalDateTime.now());
  }

  /**
   * Constructs a new diary entry with an explicit creation time.
   *
   * <p>All string parameters are validated and the author must be non-null.
   * The numeric entry id is initialized to zero and can be set later using
   * {@link #setEntryId(int)}.
   * </p>
   *
   * @param title the title of the entry; must be a non-empty string
   * @param description the entry's textual content; must be a non-empty string
   * @param author the entry's author; must be non-null
   * @param creationTime the timestamp when the entry was created
   * @throws IllegalArgumentException if {@code title} or {@code description} is invalid
   * @throws IllegalArgumentException if {@code author} is null
   */
  public DiaryEntry(String title, String description, Author author, LocalDateTime creationTime) {
    Validators.validateNotNull(author, "Author");
    Validators.validateDate(creationTime);

    entryId = 0;
    setTitle(title);
    setDescription(description);
    this.author = author;
    this.creationTime = creationTime;
  }

  /**
   * Returns the numeric identifier of this entry.
   *
   * @return the entry id, or zero if not yet assigned
   */
  public int getEntryId() {
    return entryId;
  }

  /**
   * Assigns a positive identifier to this entry.
   *
   * <p>The identifier may only be set once; subsequent attempts will throw an
   * {@link IllegalStateException}. The provided id must be positive.
   * </p>
   *
   * @param id the identifier to assign; must be positive
   * @throws IllegalStateException if an id has already been set (non-zero)
   * @throws IllegalArgumentException if {@code id} is not positive
   */
  public void setEntryId(int id) {
    if (this.entryId != 0) {
      throw new IllegalStateException("The entry already has an ID");
    }
    if (id <= 0) {
      throw new IllegalArgumentException("ID needs to be positive");
    }

    this.entryId = id;
  }

  /**
   * Returns the author of the entry.
   *
   * @return the {@link Author} who created the entry
   */
  public Author getAuthor() {
    return author;
  }

  /**
   * Returns the entry title as supplied during construction.
   *
   * @return the title of the entry
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the entry title after validating it.
   *
   * @param title the new title; must be non-null and not blank
   * @throws IllegalArgumentException if {@code title} is invalid
   */
  private void setTitle(String title) {
    Validators.validateString(title, "Title");
    this.title = title;
  }

  /**
   * Returns the entry description as supplied during construction.
   *
   * @return the textual description of the entry
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the entry description after validating it.
   *
   * @param description the new description; must be non-null and not blank
   * @throws IllegalArgumentException if {@code description} is invalid
   */
  public void setDescription(String description) {
    Validators.validateString(description, "Description");
    this.description = description;
  }

  /**
   * Returns the creation timestamp for this entry.
   *
   * @return the immutable {@link LocalDateTime} when the entry was created
   */
  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  /**
   * Returns the creation time formatted as {@code yyyy-MM-dd HH:mm}.
   *
   * @return a formatted creation time string
   */
  public String getFormatedCreationTime() {
    return creationTime.format(FORMATTER);
  }

  /**
   * Produces a concise, human-readable representation of the entry used for
   * logging.
   *
   * <p>Format example:
   * <pre>ID: 1 - [2025-12-06 13:45] Title (Author Name): Description</pre>
   * </p>
   *
   * @return a short string summarizing the entry
   */
  @Override
  public String toString() {
    return String.format("ID: %d - [%s] %s (%s): %s",
            entryId,
            creationTime.format(FORMATTER),
            title,
            author.getFullName(),
            description);
  }
}

