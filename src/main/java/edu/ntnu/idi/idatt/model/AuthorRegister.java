package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AuthorRegister {
  private final List<Author> authors;

  public AuthorRegister() {
    this.authors = new ArrayList<>();
  }

  public void addAuthor(Author author) {
    Validators.validateNotNull(author, "Author");

    boolean emailExists = authors.stream()
            .anyMatch(a -> a.getEmail().equalsIgnoreCase(author.getEmail()));

    if (emailExists) {
      throw new IllegalArgumentException("Author with email " + author.getEmail() + " already exists.");
    }

    authors.add(author);
  }

  public void removeAuthor(Author author) {
    Validators.validateNotNull(author, "Author");
    authors.remove(author);
  }

  public List<Author> getAllAuthors() {
    return authors.stream()
            .sorted(Comparator.comparing(Author::getFullName))
            .toList();
  }
}
