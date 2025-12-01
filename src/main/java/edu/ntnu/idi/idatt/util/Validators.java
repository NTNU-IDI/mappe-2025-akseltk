package edu.ntnu.idi.idatt.util;

public class Validators {
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
}