package edu.ntnu.idi.idatt.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
  private Author testAuthor;

  @BeforeEach
  void setUp() {
    testAuthor = new Author("Navn", "Navneson", "navn@ex.com");
  }



  @Test
  void testAuthorConstructorWithValidValues() {
    assertEquals("Navn", AuthorTest.this.testAuthor.getFirstName());
    assertEquals("Navneson", AuthorTest.this.testAuthor.getLastName());
    assertEquals("Navn Navneson", AuthorTest.this.testAuthor.getFullName());
    assertEquals("navn@ex.com", AuthorTest.this.testAuthor.getEmail());
  }

  @Test
  void testInvalidFirstNameThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author("", "Navneson", "navn@ex.com");
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author(null, "Navneson", "navn@ex.com");
            });
  }

  @Test
  void testInvalidLastNameThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author("Navn", "", "navn@ex.com");
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author("Navn", null, "navn@ex.com");
            });
  }

  @Test
  void testInvalidEmailThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author("Navn", "Navneson", "");
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author("Navn", "Navneson", null);
            });

    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author("Navn", "Navneson", "navnex.com");
            });
    assertThrows(IllegalArgumentException.class,
            () -> {
              new Author("Navn", "Navneson", "navn@ex");
            });
  }

  @Test
  void testValidToString() {
    assertEquals("Navn (navn@ex.com)", testAuthor.toString());
  }
}