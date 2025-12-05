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
        case "2" -> deleteEntry();
        case "3" -> showAllEntries();
        case "4" -> searchMenu();
        case "5" -> showAuthorStatistics();
        case "0" -> {
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
    } catch (IllegalArgumentException | IllegalStateException e) {
      ui.printError(e.getMessage());
    }
  }

  private Author getAuthorForEntry() {
    if(authorRegister.getAllAuthors().isEmpty()) {
      ui.printMessage("No authors found! Creating one...");
      return createNewAuthor();
    }

    while (true) {
      ui.printMessage("--- AUTHOR MENU ---");
      ui.printMessage("1. select existing author");
      ui.printMessage("2. Create new author");

      String choice = ui.readInput("select an option");

      return switch (choice) {
        case "1" -> selectExistingAuthor();
        case "2" -> createNewAuthor();
        default -> null;
      };
    }
  }

  private Author selectExistingAuthor() {
    List<Author> authors = authorRegister.getAllAuthors();

    while (true) {
      ui.printMessage("--- EXISTING AUTHOR MENU ---");

      for (int i = 0; i < authors.size(); i++) {
        ui.printMessage((i + 1) + ". " + authors.get(i));
      }

      String input = ui.readInput("select an author number");

      try {
        int authorNumber = Integer.parseInt(input) - 1;
        if (authorNumber >= 0 && authorNumber < authors.size()) {
          return authors.get(authorNumber);
        } else {
          ui.printError(INVALID_CHOICE_MESSAGE);
        }
      } catch (NumberFormatException e) {
        ui.printError("Invalid input, please use a number.");
      }
    }
  }

  private Author createNewAuthor() {
    while (true) {
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
      }
    }
  }

  private void deleteEntry() {
    ui.printMessage("--- DELETE ENTRY ---");
    showAllEntries();
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
      ui.printMessage("No entries found!");
      return;
    }

    ui.printMessage("--- ALL ENTRIES ---");
    List<DiaryEntry> entries = diaryRegister.getAllEntries();
    for (DiaryEntry entry : entries) {
      ui.printMessage(entry.toString());
      ui.printMessage("------------------------------");
    }
  }

  private void searchMenu() {
    boolean searching = true;
    while (searching) {
      ui.printMessage("--- SEARCH MENU ---");
      ui.printMessage("1. Search by keyword");
      ui.printMessage("2. Search by date");
      ui.printMessage("3. Search by date range");
      ui.printMessage("4. Search by author");
      ui.printMessage("0. Back to main menu");
      String choice = ui.readInput("Choose a number");

      switch (choice) {
        case "1" -> searchByKeyword();
        case "2" -> searchByDate();
        case "3" -> searchBetweenDate();
        case "4" -> searchByAuthor();
        case "0" -> searching = false;
        default -> ui.printError(INVALID_CHOICE_MESSAGE);
      }
    }
  }

  private void searchByKeyword() {
    String keyword = ui.readInput("Enter keyword");
    List<DiaryEntry> searchResults = diaryRegister.searchKeyWord(keyword);
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
    List<DiaryEntry> searchResults = diaryRegister.getEntriesByAuthor(selectExistingAuthor().getEmail());
    printSearchResults(searchResults);
  }

  private void printSearchResults(List<DiaryEntry> searchResults) {
    if (searchResults.isEmpty()) {
      ui.printMessage("No entries found!");
    } else  {
      ui.printMessage("--- Search RESULTS ---");
      for (DiaryEntry entry : searchResults) {
        ui.printMessage(entry.toString());
        ui.printMessage("------------------------------");
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
    ui.printFormatMessage("--------------------------------------------%n");
    ui.printFormatMessage("|          AUTHOR STATISTICS PAGE          |%n");
    ui.printFormatMessage("--------------------------------------------%n");
    ui.printFormatMessage("| %-30s | %-1s |%n", "AUTHOR", "ENTRIES");
    ui.printFormatMessage("--------------------------------------------%n");


    authorStats.forEach((email, count) -> {
      ui.printFormatMessage("| %-30s | %-7d |%n", email, count);
      ui.printFormatMessage("--------------------------------------------%n");
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
