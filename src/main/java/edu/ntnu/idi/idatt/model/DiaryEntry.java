package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class DiaryEntry {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String entryId;
    private Author author;
    private String title;
    private String description;
    private final LocalDateTime creationTime;

    public DiaryEntry(String title, String description, Author author) {
        Validators.validateString(title, "Title");
        Validators.validateString(description, "Description");
        Validators.validateNotNull(author, "Author");


        this.entryId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.author = author;
        this.creationTime = LocalDateTime.now();
    }

    public String getEntryId() {
        return entryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaryEntry entry = (DiaryEntry) o;
        return entryId.equals(entry.entryId);
    }

    @Override
    public int hashCode() {
        return entryId.hashCode();
    }

    @Override
    public String toString() {

        return String.format("[%s] %s (%s): %s",
                creationTime.format(FORMATTER),
                title,
                author.getFullName(),
                description);
    }
}

