package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;

import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.List;


public class AuthorRegister {
  private final Map<String, Author> authors;

  public AuthorRegister() {
    this.authors = new HashMap<>();
  }

  public void addAuthor(Author author) {
    Validators.validateNotNull(author, "Author");

    if (authors.putIfAbsent(author.getEmail(), author) != null) {
      throw new IllegalArgumentException("An author already exists with email " + author.getEmail());
    }
  }

  public void removeAuthor(Author author) {
    Validators.validateNotNull(author, "Author");
    authors.remove(author.getEmail());
  }

  public List<Author> getAllAuthors() {
    return authors.values().stream()
            .sorted(Comparator.comparing(Author::getFullName))
            .toList();
  }
}
