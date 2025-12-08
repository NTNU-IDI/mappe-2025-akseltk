package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;

/**
 * Represents an author with a first name, last name and an email address.
 *
 *<p>Instances carry the immutable email address provided at construction and
 * mutable first and last names. Input values are validated during construction
 * using {@link Validators}; invalid input will result in an
 * {@link IllegalArgumentException}.
 *</p>
 */
public class Author {
  private String firstName;
  private String lastName;
  private final String email;

  /**
   * Creates a new {@code Author} with the given name parts and email address.
   *
   * <p>All parameters are validated using {@link Validators}:
   * <ul>
   *   <li>{@code firstName} and {@code lastName} must be non-empty strings.</li>
   *   <li>{@code email} must conform to the expected email format.</li>
   * </ul>
   * </p>
   *
   * @param firstName the author's given name; must be a non-empty string
   * @param lastName the author's family name; must be a non-empty string
   * @param email the author's email address; must be a valid email
   * @throws IllegalArgumentException if any argument is invalid according to {@link Validators}
   */
  public Author(String firstName, String lastName, String email) {
    Validators.validateString(firstName, "First Name");
    Validators.validateString(lastName, "Last Name");
    Validators.validateEmail(email);

    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  /**
   * Returns the author's first (given) name as provided at construction.
   *
   * @return the first name of the author
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Returns the author's last (family) name as provided at construction.
   *
   * @return the last name of the author
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns the author's full name in the format.
   * <pre>FirstName LastName</pre>
   *
   * <p>No additional normalization is performed; the result is a simple
   * concatenation of the stored first and last name separated by a single space.
   * </p>
   *
   * @return the full name composed of first name and last name
   */
  public String getFullName() {
    return firstName + " " + lastName;
  }

  /**
   * Returns the author's email address.
   *
   * @return the email address (immutable after construction)
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns a concise textual representation of the author used for logging and display.
   *
   * <p>Format: <code>FirstName (email@example.com)</code>.</p>
   *
   * @return a short string containing the author's first name and email
   */
  @Override
  public String toString() {
    return getFirstName() + " (" + getEmail() + ")";
  }
}