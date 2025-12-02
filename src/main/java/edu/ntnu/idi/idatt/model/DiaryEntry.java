package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class DiaryEntry {
    private final String entryId;
    private String author;
    private String title;
    private String description;
    private final LocalDateTime creationTime;

    public DiaryEntry(String title, String description, String author) {
        Validators.validateString(title, "Title");
        Validators.validateString(description, "Description");
        Validators.validateString(author, "Author");

        this.entryId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.author = author;
        this.creationTime = LocalDateTime.now();
    }

    public String getEntryId() {
        return entryId;
    }

    public String getAuthor() {
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
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return String.format("[%s] %s (%s): %s",
                creationTime.format(FORMATTER),
                title,
                author,
                description);
    }
}

