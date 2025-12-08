package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains a registry of {@link Author} instances keyed by their email addresses.
 *
 * <p>The register enforces unique authors per email and validates inputs via
 * {@link Validators}. Authors are stored in-memory using a {@link HashMap}
 * and can be retrieved in a sorted order by full name.
 * </p>
 */
public class AuthorRegister {
  private final Map<String, Author> authors;

  /**
   * Constructs an empty {@code AuthorRegister}.
   *
   * <p>The underlying storage is initialized as an empty {@link HashMap}.
   * </p>
   */
  public AuthorRegister() {
    this.authors = new HashMap<>();
  }

  /**
   * Adds a new author to the register if no author with the same email exists.
   *
   * <p>The provided {@code author} is validated for non-nullity. If an author with
   * the same email already exists in the register, the operation fails with an
   * {@link IllegalArgumentException}.
   * </p>
   *
   * @param author the author to add; must be non-null and have a valid email
   * @throws IllegalArgumentException if {@code author} is null or an author with
   *         the same email already exists
   */
  public void addAuthor(Author author) {
    Validators.validateNotNull(author, "Author");

    if (authors.putIfAbsent(author.getEmail(), author) != null) {
      throw new IllegalArgumentException(
              "An author already exists with email " + author.getEmail());
    }
  }

  /**
   * Removes the specified author from the register.
   *
   * <p>The removal is performed by the author's email. If the author is not present,
   * the method performs no action. The provided {@code author} must be non-null.
   * </p>
   *
   * @param author the author to remove; must be non-null
   * @throws IllegalArgumentException if {@code author} is null
   */
  public void removeAuthor(Author author) {
    Validators.validateNotNull(author, "Author");
    authors.remove(author.getEmail());
  }

  /**
   * Returns all registered authors sorted by their full name in ascending order.
   *
   * <p>The returned list is a snapshot of the current registry contents and is
   * ordered using {@link Author#getFullName()}.
   * </p>
   *
   * @return a list of all authors sorted by full name (ascending)
   */
  public List<Author> getAllAuthors() {
    return authors.values().stream()
            .sorted(Comparator.comparing(Author::getFullName))
            .toList();
  }
}
