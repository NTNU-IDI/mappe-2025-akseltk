package edu.ntnu.idi.idatt.util;

import java.util.regex.Pattern;

public class Validators {
  private static final String EMAIL_REGEX = "(?=^.{4,40}$)[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

  private Validators() {
  }

  public static void validateString(String input, String fieldName) {
    if (input == null || input.isBlank()) {
      throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
    }
  }

  public static void validateNotNull(Object object, String fieldName) {
    if (object == null) {
      throw new IllegalArgumentException(fieldName + " cannot be null.");
    }
  }

  public static void validateEmail(String email) {
    validateString(email, "Email");

    if (!EMAIL_PATTERN.matcher(email).matches()) {
      throw new IllegalArgumentException("Invalid email format. Must be 4-40 chars (e.g. user@domain.com).");
    }
  }

}