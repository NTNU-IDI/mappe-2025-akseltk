package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;


public class Author {
    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        Validators.validateString(firstName, "First Name");
        Validators.validateString(lastName, "Last Name");


        this.firstName = firstName;
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return getFullName();
    }
}