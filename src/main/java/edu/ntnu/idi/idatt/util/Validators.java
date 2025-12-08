package edu.ntnu.idi.idatt.util;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * Utility class containing common input validators used across the project.
 *
 * <p>Provides simple checks for non-null references, non-blank strings and a
 * project-specific email format. Validation failures are signaled by throwing
 * {@link IllegalArgumentException} with a clear, field-specific message.
 * </p>
 */
public class Validators {
  private static final String EMAIL_REGEX =
          "(?=^.{4,40}$)[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

  private Validators() {
  }

  /**
   * Validates that {@code input} is not {@code null} and contains non-whitespace
   * characters.
   *
   * <p>Strings that are empty or consist only of whitespace are considered
   * invalid.
   * </p>
   *
   * @param input the string to validate; must be non-null and not blank
   * @param fieldName the name of the field used in the exception message
   * @throws IllegalArgumentException if {@code input} is {@code null} or blank
   */
  public static void validateString(String input, String fieldName) {
    if (input == null || input.isBlank()) {
      throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
    }
  }

  /**
   * Validates that the provided reference is not {@code null}.
   *
   * @param object the object reference to check
   * @param fieldName the name of the field used in the exception message
   * @throws IllegalArgumentException if {@code object} is {@code null}
   */
  public static void validateNotNull(Object object, String fieldName) {
    if (object == null) {
      throw new IllegalArgumentException(fieldName + " cannot be null.");
    }
  }

  /**
   * Validates that {@code date} is not in the future compared to the current
   * system time.
   *
   * @param date the date to validate
   * @throws IllegalArgumentException if {@code date} is after the current time
   */
  public static void validateDate(LocalDateTime date) {
    if (date.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Creation time cannot be in the future");
    }
  }

  /**
   * Validates that {@code email} is a non-blank string that matches the
   * project's email pattern and has a length between 4 and 40 characters.
   *
   * <p>The validation uses the regular expression:
   * {@code (?=^.{4,40}$)[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\.[a-zA-Z]{2,4}$}.
   * </p>
   *
   * @param email the email address to validate
   * @throws IllegalArgumentException if {@code email} is {@code null}, blank
   *         or does not match the required format
   */
  public static void validateEmail(String email) {
    validateString(email, "Email");

    if (!EMAIL_PATTERN.matcher(email).matches()) {
      throw new IllegalArgumentException(
              "Invalid email format. Must be 4-40 chars (e.g. user@domain.com).");
    }
  }
}