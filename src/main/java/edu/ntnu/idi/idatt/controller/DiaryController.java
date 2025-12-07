package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import edu.ntnu.idi.idatt.view.UserInterface;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class DiaryController {
  private static final String INVALID_CHOICE_MESSAGE = "Invalid choice, please try again.";
  private static final String NO_ENTRIES_MESSAGE = "No entries found.";
  private static final String LIST_SEPARATOR = "------------------------------";

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
      ui.printMainMenu();
      String choice = ui.readInput("");

      switch (choice) {
        case "1" -> createNewEntry();
        case "2" -> deleteEntry();
        case "3" -> showAllEntries();
        case "4" -> searchMenu();
        case "5" -> showAuthorStatistics();
        case "0" -> {
          ui.printMessage("Shutting down... Goodbye!");
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

    ui.printMessage(LIST_SEPARATOR);
    String title = ui.readInput("Title");
    ui.printMessage(LIST_SEPARATOR);
    String description = ui.readInput("Description");
    ui.printMessage(LIST_SEPARATOR);

    try {
      DiaryEntry diaryEntry = new DiaryEntry(title, description, author);
      diaryRegister.addEntry(diaryEntry);
      ui.printSuccess("Entry created! Got ID: " +  diaryEntry.getEntryId());
    } catch (IllegalArgumentException | IllegalStateException e) {
      ui.printError(e.getMessage());
    }
  }

  private Author getAuthorForEntry() {
    if(authorRegister.getAllAuthors().isEmpty()) {
      ui.printMessage("No authors found! Creating one...");
      return createNewAuthor();
    }

    Author selected = null;
    boolean selecting = true;

    while (selecting) {
      ui.printAuthorMenu();
      String choice = ui.readInput("");

      switch (choice) {
        case "1" -> {
          selected = selectExistingAuthor();
          if (selected != null) {
            selecting = false;
          }
        }
        case "2" -> {
          selected = createNewAuthor();
          if (selected != null) {
            selecting = false;
          }
        }
        case "0", "" -> selecting = false;
        default -> ui.printError(INVALID_CHOICE_MESSAGE);
      }
    }
    return selected;
  }

  private Author selectExistingAuthor() {
    List<Author> authors = authorRegister.getAllAuthors();
    Author selected = null;
    boolean selecting = true;

    while (selecting) {
      ui.printSelectExistingAuthor(authors);

      String input = ui.readInput("");
      if (input.isEmpty()) {
        selecting = false;
      }

      try {
        int authorNumber = Integer.parseInt(input) - 1;
        if (authorNumber >= 0 && authorNumber < authors.size()) {
          selected = authors.get(authorNumber);
          selecting = false;
        } else {
          ui.printError(INVALID_CHOICE_MESSAGE);
        }
      } catch (NumberFormatException e) {
        ui.printError("Invalid input, please use a number.");
      }
    }
    return selected;
  }

  private Author createNewAuthor() {
    Author selected = null;
    boolean selecting = true;

    while (selecting) {
      ui.printCreateAuthorTitle();

      String firstName = ui.readInput("First Name");
      if (firstName.isEmpty()) {
        selecting = false;
      }

      String lastName = ui.readInput("Last Name");
      if (lastName.isEmpty()) {
        selecting = false;
      }

      String email = ui.readInput("Email");
      if (email.isEmpty()) {
        selecting = false;
      }

      try {
        Author newAuthor = new Author(firstName, lastName, email);
        authorRegister.addAuthor(newAuthor);
        selected = newAuthor;
        selecting = false;
      } catch (IllegalArgumentException e) {
        ui.printError(e.getMessage());
      }
    }
    return selected;
  }

  private void deleteEntry() {
    if (diaryRegister.getAllEntries().isEmpty()) {
      ui.printMessage(NO_ENTRIES_MESSAGE);
      return;
    }

    ui.printDiaryEntryList(diaryRegister.getAllEntries());
    ui.printDeleteEntryTitle();
    String entryId = ui.readInput("Enter ID to delete");

    try {
      int id = Integer.parseInt(entryId);
      DiaryEntry entry = diaryRegister.getEntryById(id);
      diaryRegister.removeEntry(entry);
      ui.printSuccess("Entry deleted!");
    } catch (IllegalArgumentException e) {
      ui.printError(e.getMessage());
    }
  }

  private void showAllEntries() {
    ui.printDiaryEntryList(diaryRegister.getAllEntries());
  }

  private void searchMenu() {
    boolean searching = true;
    while (searching) {
      ui.printSearchMenu();
      String choice = ui.readInput("");

      switch (choice) {
        case "1" -> searchByKeyword();
        case "2" -> searchByDate();
        case "3" -> searchBetweenDate();
        case "4" -> searchByAuthor();
        case "0", "" -> searching = false;
        default -> ui.printError(INVALID_CHOICE_MESSAGE);
      }
    }
  }

  private void searchByKeyword() {
    String keyword = ui.readInput("Enter keyword");
    List<DiaryEntry> searchResults = diaryRegister.searchByKeyword(keyword);
    ui.printSearchResults(searchResults);
  }

  private void searchByDate() {
    String dateString = ui.readInput("Enter date (yyyy-mm-dd)");

    try {
      LocalDate date = LocalDate.parse(dateString);
      List<DiaryEntry> searchResults = diaryRegister.getEntriesByDate(date);
      ui.printSearchResults(searchResults);
    } catch (DateTimeParseException | IllegalArgumentException e) {
      ui.printError(e.getMessage());
    }

  }

  private void searchBetweenDate() {
    String fromString = ui.readInput("Enter From (yyyy-mm-dd)");
    String toString =  ui.readInput("Enter To (yyyy-mm-dd)");

    try {
      LocalDate from = LocalDate.parse(fromString);
      LocalDate to = LocalDate.parse(toString);
      List<DiaryEntry> searchResults = diaryRegister.searchEntriesBetweenDates(from, to);
      ui.printSearchResults(searchResults);
    } catch (DateTimeParseException | IllegalArgumentException e) {
      ui.printError(e.getMessage());
    }
  }

  private void searchByAuthor() {
    if (authorRegister.getAllAuthors().isEmpty()) {
      ui.printMessage("No authors found!");
      return;
    }
    Author selectedAuthor = selectExistingAuthor();
    if  (selectedAuthor == null) {
      return;
    }
    List<DiaryEntry> searchResults = diaryRegister.getEntriesByAuthor(selectedAuthor.getEmail());
    ui.printSearchResults(searchResults);
  }

  private void showAuthorStatistics() {
    ui.printAuthorStatistics(diaryRegister.getAuthorStatistics());
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
