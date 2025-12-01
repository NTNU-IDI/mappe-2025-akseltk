package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiaryEntry {
    private String author;
    private String title;
    private String description;
    private final LocalDateTime creationTime;

    public DiaryEntry(String title, String description, String author) {
        Validators.validateString(title, "Title");
        Validators.validateString(description, "Description");
        Validators.validateString(author, "Author");

        this.title = title;
        this.description = description;
        this.author = author;
        this.creationTime = LocalDateTime.now();
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
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String formattedTime = creationTime.format(formatter);

        return String.format("[%s] %s (%s): %s",
                formattedTime,
                title,
                author,
                description);
    }
}

