package edu.ntnu.idi.idatt.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
  String firstName = "Per";
  String lastName = "Petterson";
  String fullName = "Per Petterson";
  String email = "per@ex.com";



  @Test
  void testValidAuthorCreation() {
    Author author = new Author(firstName, lastName, email);

    assertEquals(firstName, author.getFirstName());
    assertEquals(lastName, author.getLastName());
    assertEquals(fullName, author.getFullName());
    assertEquals("per@ex.com", author.getEmail());
  }

  @Test
  void testInvalidFirstName() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author("", lastName, email);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author(null, lastName, email);
            });
  }

  @Test
  void testInvalidLastName() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author(firstName, "", email);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author(firstName, null, email);
            });
  }

  @Test
  void testInvalidEmail() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author(firstName, lastName, "");
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author(firstName, lastName, null);
            });
  }

  @Test
  void testValidToString() {
    Author author = new Author(firstName, lastName, email);

    assertEquals(firstName + " (" + email + ")", author.toString());
  }
}