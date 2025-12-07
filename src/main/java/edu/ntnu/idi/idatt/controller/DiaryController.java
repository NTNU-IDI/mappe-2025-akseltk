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
  private static final String CANCEL_MESSAGE = "Press Enter to Cancel";
  private static final String LIST_SEPARATOR = "------------------------------";
  private static final String STATS_SEPARATOR = "--------------------------------------------%n";

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
      String choice = ui.readInput("");

      switch (choice) {
        case "1" -> createNewEntry();
        case "2" -> deleteEntry();
        case "3" -> showAllEntries();
        case "4" -> searchMenu();
        case "5" -> showAuthorStatistics();
        case "0", "" -> {
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

    String title = ui.readInput("Title");
    String description = ui.readInput("Description");

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
      ui.printMessage("\n--- AUTHOR MENU ---");
      ui.printMessage("1. select existing author");
      ui.printMessage("2. Create new author");
      ui.printMessage("0. Press enter to cancel");

      String choice = ui.readInput("select an option");

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
      ui.printMessage("\n--- EXISTING AUTHOR MENU ---");

      for (int i = 0; i < authors.size(); i++) {
        ui.printMessage((i + 1) + ". " + authors.get(i));
      }

      String input = ui.readInput("select an author number");
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
      ui.printMessage("\n--- CREATE NEW AUTHOR ---");
      ui.printMessage(CANCEL_MESSAGE);

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

    ui.clearScreen();
    List<DiaryEntry> entries = diaryRegister.getAllEntries();
    for (DiaryEntry entry : entries) {
      ui.printMessage(entry.toString());
      ui.printMessage(LIST_SEPARATOR);
    }

    ui.printMessage("\n--- DELETE ENTRY ---");
    ui.printMessage(CANCEL_MESSAGE);
    String entryId = ui.readInput("Enter the ID of the entry you want to delete");

    try {
      int id = Integer.parseInt(entryId);
      DiaryEntry entry = diaryRegister.getEntryById(id);
      diaryRegister.removeEntry(entry);
      ui.printSuccess("Entry deleted!");
    } catch (IllegalArgumentException e) {
      ui.printError(e.getMessage());
    }
  }

  private void showAllEntries()  {
    if (diaryRegister.getAllEntries().isEmpty()) {
      ui.printMessage(NO_ENTRIES_MESSAGE);
      return;
    }

    ui.clearScreen();
    ui.printMessage("\n--- ALL ENTRIES ---");
    List<DiaryEntry> entries = diaryRegister.getAllEntries();
    for (DiaryEntry entry : entries) {
      ui.printMessage(entry.toString());
      ui.printMessage(LIST_SEPARATOR);
    }

    ui.printMessage("\n");
    ui.readInput("Press any key to exit");
  }

  private void searchMenu() {
    boolean searching = true;
    while (searching) {
      ui.printMessage("\n--- SEARCH MENU ---");
      ui.printMessage("1. Search by keyword");
      ui.printMessage("2. Search by date");
      ui.printMessage("3. Search by date range");
      ui.printMessage("4. Search by author");
      ui.printMessage("0. Press enter to cancel");
      String choice = ui.readInput("Choose a number");

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
    printSearchResults(searchResults);
  }

  private void searchByDate() {
    String dateString = ui.readInput("Enter date (yyyy-mm-dd)");

    try {
      LocalDate date = LocalDate.parse(dateString);
      List<DiaryEntry> searchResults = diaryRegister.getEntriesByDate(date);
      printSearchResults(searchResults);
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
      printSearchResults(searchResults);
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
    printSearchResults(searchResults);
  }

  private void printSearchResults(List<DiaryEntry> searchResults) {
    if (searchResults.isEmpty()) {
      ui.printMessage(NO_ENTRIES_MESSAGE);
    } else  {
      ui.printMessage("\n--- SEARCH RESULTS ---");
      for (DiaryEntry entry : searchResults) {
        ui.printMessage(entry.toString());
        ui.printMessage(LIST_SEPARATOR);
      }
    }
  }

  private void showAuthorStatistics() {
    Map<String, Long> authorStats = diaryRegister.getAuthorStatistics();

    if (authorStats.isEmpty()) {
      ui.printMessage("No authors found!");
      return;
    }

    ui.clearScreen();
    ui.printFormatMessage(STATS_SEPARATOR);
    ui.printFormatMessage("|          AUTHOR STATISTICS PAGE          |%n");
    ui.printFormatMessage(STATS_SEPARATOR);
    ui.printFormatMessage("| %-30s | %-1s |%n", "AUTHOR", "ENTRIES");
    ui.printFormatMessage(STATS_SEPARATOR);


    authorStats.forEach((email, count) -> {
      ui.printFormatMessage("| %-30s | %-7d |%n", email, count);
      ui.printFormatMessage(STATS_SEPARATOR);
    });

    ui.printMessage("\n".repeat(3));
    ui.readInput("Press any key to exit");
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
