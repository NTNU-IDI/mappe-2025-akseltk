package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;


public class Author {
  private String firstName;
  private String lastName;
  private final String email;

  public Author(String firstName, String lastName, String email) {
    Validators.validateString(firstName, "First Name");
    Validators.validateString(lastName, "Last Name");
    Validators.validateEmail("email");

    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return getFullName();
  }
}