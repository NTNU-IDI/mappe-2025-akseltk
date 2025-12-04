package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import edu.ntnu.idi.idatt.view.UserInterface;

import java.util.List;

public class DiaryController {
  private static final String INVALID_CHOICE_MESSAGE = "Invalid choice, please try again.";

  private final DiaryRegister diaryRegister;
  private final AuthorRegister authorRegister;
  private final UserInterface ui;

  public DiaryController(DiaryRegister diaryRegister, AuthorRegister authorRegister, UserInterface ui) {
    this.diaryRegister = diaryRegister;
    this.authorRegister = authorRegister;
    this.ui = ui;
  }

  public void start() {
    ui.init();
    
    addTestData();

    boolean running = true;
    while (running) {
      ui.printMenu();
      String choise = ui.readInput("");

      switch (choise) {
        case "1" -> createNewEntry();
        case "4" -> {
          ui.printMessage("Shuting down... Goodbye!");
          running = false;
        }
        default -> ui.printError(INVALID_CHOICE_MESSAGE);
      }
    }
  }

  private void createNewEntry() {
    ui.printMessage("Creating entry...");

    Author author = getAuthorForEntry();

    if (author == null) {
      return;
    }

    String title = ui.readInput("Title");
    String description = ui.readInput("Description");

    try {
      DiaryEntry diaryEntry = new DiaryEntry(title, description, author);
      diaryRegister.addEntry(diaryEntry);
      ui.printSuccess("Entry created! Got ID: " +  diaryEntry.getEntryId());
    } catch (IllegalArgumentException e) {
      ui.printError(e.getMessage());
    }
  }

  private Author getAuthorForEntry() {
    if(authorRegister.getAllAuthors().isEmpty()) {
      ui.printMessage("No authors found! Creating one...");
      return createNewAuthor();
    }

    ui.printMessage("--- AUTHOR MENU ---");
    ui.printMessage("1. select existing author");
    ui.printMessage("2. Create new author");

    String choice = ui.readInput("select an option");

    return switch (choice) {
      case "1" -> selectExistingAuthor();
      case "2" -> createNewAuthor();
      default -> {
        ui.printError(INVALID_CHOICE_MESSAGE);
        yield getAuthorForEntry();
      }
    };
  }

  private Author selectExistingAuthor() {
    List<Author> authors = authorRegister.getAllAuthors();
    ui.printMessage("--- EXISTING AUTHOR MENU ---");

    for (int i=0; i<authors.size(); i++) {
      ui.printMessage((i+1) + ". " + authors.get(i));
    }

    String input = ui.readInput("select an author number");

    try {
      int authorNumber = Integer.parseInt(input) -1;
      if (authorNumber >= 0 && authorNumber < authors.size()) {
        return authors.get(authorNumber);
      } else  {
        ui.printError(INVALID_CHOICE_MESSAGE);
        return selectExistingAuthor();
      }
    } catch (NumberFormatException e) {
      ui.printError("Invalid input, please use a number.");
      return selectExistingAuthor();
    }
  }

  private Author createNewAuthor() {
    ui.printMessage("--- CREATE NEW AUTHOR ---");
    String firstName = ui.readInput("First Name");
    String lastName = ui.readInput("Last Name");
    String email = ui.readInput("Email");

    try {
      Author newAuthor = new Author(firstName, lastName, email);
      authorRegister.addAuthor(newAuthor);
      return newAuthor;
    } catch (IllegalArgumentException e) {
      ui.printError(e.getMessage());
      return createNewAuthor();
    }
  }

  private void addTestData() {
    try {
      Author a1 = new Author("Peter", "Petterson", "peter@ex.com");
      Author a2 = new Author("Alebert", "Albertson", "albert@ex.com");
      Author a3 = new Author("David", "Davidson", "david@ex.com");

      authorRegister.addAuthor(a1);
      authorRegister.addAuthor(a2);
      authorRegister.addAuthor(a3);

      DiaryEntry e1 = new DiaryEntry("Movie day", "Watched Inception! It was strange, but amazing at the same time.", a1);
      DiaryEntry e2 = new DiaryEntry("Training", "Hit chest with two of my friends, i always have a great time with them", a2);
      DiaryEntry e3 = new DiaryEntry("Dinning night", "Ait at this great restaurant with my family, i ate stake with mashed potatoes.", a3);

      diaryRegister.addEntry(e1);
      diaryRegister.addEntry(e2);
      diaryRegister.addEntry(e3);

      ui.printSuccess("Test data loaded!");

    } catch (IllegalArgumentException e) {
      ui.printError("Failed to add test data: " + e.getMessage());
    }
  }
}
