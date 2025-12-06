package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiaryEntry {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  private int entryId;
  private Author author;
  private String title;
  private String description;
  private final LocalDateTime creationTime;

  public DiaryEntry(String title, String description, Author author) {
    this(title, description, author, LocalDateTime.now());
  }


  public DiaryEntry(String title, String description, Author author, LocalDateTime creationTime) {
    Validators.validateString(title, "Title");
    Validators.validateString(description, "Description");
    Validators.validateNotNull(author, "Author");

    entryId = 0;
    this.title = title;
    this.description = description;
    this.author = author;
    this.creationTime = creationTime;
  }

  public int getEntryId() {
    return entryId;
  }

  public void setEntryId(int id) {
    if (this.entryId != 0) {
      throw new IllegalStateException("The entry already has an ID");
    }
    if (id <= 0) {
      throw new IllegalArgumentException("ID needs to be positive");
    }

    this.entryId = id;
  }

  public Author getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  public String getFormatedCreationTime() {
    return creationTime.format(FORMATTER);
  }

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

