package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.model.entity.Author;
import edu.ntnu.idi.idatt.model.register.AuthorRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorRegisterTest {
  private AuthorRegister register;
  private Author author1;
  private Author author2;

  @BeforeEach
  void setUp() {
    register = new AuthorRegister();
    author1 = new Author("Per", "Petterson", "per@ex.com");
    author2 = new Author("Fredrik", "Fredrikson", "fredrik@ex.com");
  }

  @Test
  void testAddValidAuthorStoresInRegister() {
    register.addAuthor(author1);

    List<Author> authors = register.getAllAuthors();
    assertEquals(1, authors.size());
    assertEquals(author1, authors.get(0));
  }

  @Test
  void testAddDuplicateEmailThrows() {
    register.addAuthor(author1);

    Author duplicateAuthor = new Author("Evil", "Twin", "per@ex.com");

    assertThrows(IllegalArgumentException.class,
            () -> {
              register.addAuthor(duplicateAuthor);
            });
  }

  @Test
  void testAddNullAuthorThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              register.addAuthor(null);
            });
  }

  @Test
  void testRemoveAuthorUpdatesRegisterCorrectly() {
    register.addAuthor(author1);
    register.addAuthor(author2);

    register.removeAuthor(author1);

    assertEquals(1, register.getAllAuthors().size());
    assertFalse((register.getAllAuthors().contains(author1)));
    assertTrue((register.getAllAuthors().contains(author2)));

  }

  @Test
  void testRemoveNullAuthorThrows() {
    assertThrows(IllegalArgumentException.class,
            () -> {
              register.removeAuthor(null);
            });
  }
}