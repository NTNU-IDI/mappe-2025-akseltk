package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;
import java.util.UUID;


public class Author {
    private final String authorId;
    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        Validators.validateString(firstName, "First Name");
        Validators.validateString(lastName, "Last Name");


        this.authorId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return authorId.equals(author.authorId);
    }

    @Override
    public int hashCode() {
        return authorId.hashCode();
    }

    @Override
    public String toString() {
        return getFullName();
    }
}