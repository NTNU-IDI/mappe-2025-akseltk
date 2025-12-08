package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;

/**
 * Represents an author with a first name, last name and email address.
 *
 * <p>Instances are immutable except for first and last name which can be
 * changed internally during construction only. Email is immutable after construction.
 * </p>
 */
public class Author {
  private String firstName;
  private String lastName;
  private final String email;

  /**
   * Constructs a new author with the provided first name, last name and email.
   *
   * <p>All string parameters are validated for non-nullity and non-blankness.
   * The email is further validated for format using {@link Validators#validateEmail(String)}.
   * </p>
   *
   * @param firstName the author's first name; must be non-null and not blank
   * @param lastName the author's last name; must be non-null and not blank
   * @param email the author's email address; must be non-null, not blank and valid format
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public Author(String firstName, String lastName, String email) {
    Validators.validateEmail(email);

    setFirstName(firstName);
    setLastName(lastName);
    this.email = email;
  }

  /**
   * Returns the author's first name as provided at construction.
   *
   * @return the first name of the author
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the author's first name after validating it.
   *
   * @param firstName the new first name; must be non-null and not blank
   * @throws IllegalArgumentException if {@code firstName} is invalid
   */
  private void setFirstName(String firstName) {
    Validators.validateString(firstName, "First Name");
    this.firstName = firstName;
  }

  /**
   * Returns the author's last name as provided at construction.
   *
   * @return the last name of the author
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the author's last name after validating it.
   *
   * @param lastName the new last name; must be non-null and not blank
   * @throws IllegalArgumentException if {@code lastName} is invalid
   */
  private void setLastName(String lastName) {
    Validators.validateString(lastName, "Last Name");
    this.lastName = lastName;
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