package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.entity.Author;
import edu.ntnu.idi.idatt.model.register.AuthorRegister;
import edu.ntnu.idi.idatt.model.entity.DiaryEntry;
import edu.ntnu.idi.idatt.model.register.DiaryRegister;
import edu.ntnu.idi.idatt.view.UserInterface;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controller class for managing diary entries and authors.
 *
 * <p>This class handles user interactions, including creating, deleting,
 * and searching diary entries, as well as managing authors. It interacts
 * with the {@code DiaryRegister} and {@code AuthorRegister} model classes
 * to perform operations and uses the {@code UserInterface} class for
 * input/output.</p>
 */
public class DiaryController {
  private static final String INVALID_CHOICE_MESSAGE = "Invalid choice, please try again.";
  private static final String NO_ENTRIES_MESSAGE = "No entries found.";
  private static final String LIST_SEPARATOR = "------------------------------";

  private final DiaryRegister diaryRegister;
  private final AuthorRegister authorRegister;
  private final UserInterface ui;

  /**
   * Constructs a controller that coordinates the diary and author registers
   * with a user interface.
   *
   * @param diaryRegister the diary register used to persist and query entries
   * @param authorRegister the author register used to persist and query authors
   * @param ui the user interface used for input and output
   */
  public DiaryController(DiaryRegister diaryRegister, AuthorRegister authorRegister, UserInterface ui) {
    this.diaryRegister = diaryRegister;
    this.authorRegister = authorRegister;
    this.ui = ui;
  }

  /**
   * Starts the main interaction loop for the diary application.
   *
   * <p>This method initializes the user interface, adds hellosdfsdf data,
   * and enters a loop to handle user commands for managing diary entries
   * and authors.</p>
   */
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

  /**
   * Creates a new diary entry by prompting the user for details.
   *
   * <p>This method guides the user through selecting or creating an author,
   * and then collects the title and description for the new diary entry.
   * It handles validation and displays appropriate messages based on the
   * success or failure of the operation.</p>
   */
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

  /**
   * Prompts the user to select an existing author or create a new one for a diary entry.
   *
   * <p>This method checks if there are existing authors. If none are found,
   * it prompts the user to create a new author. Otherwise, it presents a menu
   * allowing the user to either select an existing author or create a new one.
   * The selected or created author is returned for use in diary entry creation.</p>
   *
   * @return the selected or newly created {@link Author}, or {@code null} if the operation was cancelled
   */
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

  /**
   * Allows the user to select an existing author from the author register.
   *
   * <p>This method displays a list of all registered authors and prompts
   * the user to select one by entering the corresponding number. If the user
   * enters an invalid number or input, an error message is displayed and
   * the user is prompted again. The selected author is returned.</p>
   *
   * @return the selected {@link Author}, or {@code null} if the operation was cancelled
   */
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

  /**
   * Guides the user through creating a new author.
   *
   * <p>This method prompts the user to enter the first name, last name,
   * and email address for a new author. It validates the input and attempts
   * to create and register the new author. If successful, the new author
   * is returned; otherwise, error messages are displayed and the user
   * can try again or cancel the operation.</p>
   *
   * @return the newly created {@link Author}, or {@code null} if the operation was cancelled
   */
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

  /**
   * Deletes a diary entry based on user input.
   *
   * <p>This method first checks if there are any diary entries available.
   * If none are found, it informs the user and exits. Otherwise, it displays
   * the list of entries and prompts the user to enter the ID of the entry
   * they wish to delete. It attempts to delete the specified entry and
   * provides feedback on the success or failure of the operation.</p>
   */
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

  /**
   * Displays all diary entries to the user.
   *
   * <p>This method retrieves all diary entries from the diary register
   * and uses the user interface to print the list of entries. If no entries
   * are found, an appropriate message is displayed.</p>
   */
  private void showAllEntries() {
    ui.printDiaryEntryList(diaryRegister.getAllEntries());
    ui.readInput("press enter to exit");
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

  /**
   * Searches diary entries by a keyword provided by the user.
   *
   * <p>This method prompts the user to enter a keyword, then searches
   * the diary register for entries containing that keyword. The search
   * results are displayed to the user using the user interface.</p>
   */
  private void searchByKeyword() {
    String keyword = ui.readInput("Enter keyword");
    List<DiaryEntry> searchResults = diaryRegister.searchByKeyword(keyword);
    ui.printSearchResults(searchResults);
  }

  /**
   * Searches diary entries by a specific date provided by the user.
   *
   * <p>This method prompts the user to enter a date in the format
   * "yyyy-mm-dd", then searches the diary register for entries created
   * on that date. The search results are displayed to the user using
   * the user interface. If the date format is invalid, an error message
   * is shown.</p>
   */
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

  /**
   * Searches diary entries between two dates provided by the user.
   *
   * <p>This method prompts the user to enter a "from" date and a "to" date
   * in the format "yyyy-mm-dd". It then searches the diary register for
   * entries created within that date range. The search results are displayed
   * to the user using the user interface. If either date format is invalid,
   * an error message is shown.</p>
   */
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

  /**
   * Searches diary entries by author selected by the user.
   *
   * <p>This method checks if there are any authors available. If none
   * are found, it informs the user and exits. Otherwise, it prompts the
   * user to select an existing author and searches the diary register
   * for entries authored by that author. The search results are displayed
   * to the user using the user interface.</p>
   */
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

  /**
   * Displays statistics of diary entries per author.
   *
   * <p>This method retrieves the author statistics from the diary register,
   * which includes the count of entries per author email, and uses the
   * user interface to print these statistics to the user.</p>
   */
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
