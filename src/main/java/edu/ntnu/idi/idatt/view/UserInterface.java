package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.entity.Author;
import edu.ntnu.idi.idatt.model.entity.DiaryEntry;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * Handles all user interface interactions for the Diary Application.
 *
 * <p>This class is responsible for displaying menus, prompts, and messages to the user,
 * as well as reading user input from the console. It provides methods to print various
 * sections of the application, including the main menu, author menu, search menu,
 * diary entries, search results, and author statistics.</p>
 */
public class UserInterface {
  private static final String LIST_SEPARATOR = "------------------------------";
  private static final String STATS_SEPARATOR = "--------------------------------------------%n";
  private static final String NO_ENTRIES_MESSAGE = "No entries found.";
  private static final String CANCEL_MESSAGE = "Press Enter to Cancel";

  private final Scanner scanner;

  /**
   * Constructs a new UserInterface instance with a Scanner for reading user input.
   */
  public UserInterface() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * Initializes the Diary Application by printing a welcome message.
   */
  public void init() {
    System.out.println("--- Diary Application Initialized ---");
  }

  /**
   * Prints the main menu options to the console.
   */
  public void printMainMenu() {
    System.out.println("\n--- MAIN MENU ---");
    System.out.println("1. Write new entry");
    System.out.println("2. Delete entry");
    System.out.println("3. Show all entries");
    System.out.println("4. Search for entries");
    System.out.println("5. Show author statistics");
    System.out.println("0. Exit");
    System.out.print("Choose a number");
  }

  /**
   * Prints the author menu options to the console.
   */
  public void printAuthorMenu() {
    System.out.println("\n--- AUTHOR MENU ---");
    System.out.println("1. select existing author");
    System.out.println("2. Create new author");
    System.out.println("0. Press enter to cancel");
    System.out.print("select an option");
  }

  /**
   * Prints the search menu options to the console.
   */
  public void printSearchMenu() {
    System.out.println("\n--- SEARCH MENU ---");
    System.out.println("1. Search by keyword");
    System.out.println("2. Search by date");
    System.out.println("3. Search between dates");
    System.out.println("4. Search by author");
    System.out.println("0. Press enter to cancel");
    System.out.print("Choose a number");
  }

  /**
   * Prints a single diary entry to the console in a formatted manner.
   *
   * @param entry the DiaryEntry to print
   */
  public void printSingleEntry(DiaryEntry entry) {
    System.out.println(LIST_SEPARATOR);
    System.out.println("id: " + entry.getEntryId() + " - " + entry.getFormatedCreationTime());
    System.out.println(entry.getAuthor().getFullName() + " " + entry.getAuthor().getEmail());
    System.out.println(LIST_SEPARATOR);
    System.out.println("Title: " + entry.getTitle());
    System.out.println(LIST_SEPARATOR);
    System.out.println(entry.getDescription());
    System.out.println(LIST_SEPARATOR);
    System.out.println("");
  }

  /**
   * Prints the menu for selecting an existing author.
   *
   * @param authors the list of existing authors to display
   */
  public void printSelectExistingAuthor(List<Author> authors) {
    System.out.println("\n--- EXISTING AUTHOR MENU ---");

    for (int i = 0; i < authors.size(); i++) {
      System.out.println((i + 1) + ". " + authors.get(i));
    }

    System.out.println("select an author number");
  }

  /**
   * Prints the title for creating a new author.
   */
  public void printCreateAuthorTitle() {
    System.out.println("\n--- CREATE NEW AUTHOR ---");
    System.out.println(CANCEL_MESSAGE);
  }

  /**
   * Prints the title for deleting a diary entry.
   */
  public void printDeleteEntryTitle() {
    System.out.println("\n--- DELETE ENTRY ---");
    System.out.println(CANCEL_MESSAGE);
  }

  /**
   * Prints a list of diary entries to the console.
   *
   * @param entries the list of DiaryEntry objects to print
   */
  public void printDiaryEntryList(List<DiaryEntry> entries) {
    clearScreen();
    if (entries.isEmpty()) {
      System.out.println("No diary entries found");
    } else {
      System.out.println("\n--- ENTRIES LIST ---");
      for (DiaryEntry entry : entries) {
        printSingleEntry(entry);
      }
    }
  }

  /**
   * Prints the search results for diary entries to the console.
   *
   * @param searchResults the list of DiaryEntry objects that match the search criteria
   */
  public void printSearchResults(List<DiaryEntry> searchResults) {
    if (searchResults.isEmpty()) {
      System.out.println(NO_ENTRIES_MESSAGE);
    } else  {
      System.out.println("\n--- SEARCH RESULTS ---");
      for (DiaryEntry entry : searchResults) {
        printSingleEntry(entry);
      }
    }
  }

  /**
   * Prints the author statistics to the console.
   *
   * @param authorsStats a map containing author names as keys and their corresponding
   *                     entry counts as values
   */
  public void printAuthorStatistics(Map<String, Long> authorsStats) {
    clearScreen();
    if (authorsStats.isEmpty()) {
      System.out.println("No authors found");
    } else {
      clearScreen();
      System.out.printf(STATS_SEPARATOR);
      System.out.printf("|          AUTHOR STATISTICS PAGE          |%n");
      System.out.printf(STATS_SEPARATOR);
      System.out.printf("| %-30s | %-1s |%n", "AUTHOR", "ENTRIES");
      System.out.printf(STATS_SEPARATOR);

      for (Map.Entry<String, Long> stats : authorsStats.entrySet()) {
        System.out.printf("| %-30s | %-7d |%n", stats.getKey(), stats.getValue());
        System.out.printf(STATS_SEPARATOR);
      }

      System.out.print("Press Enter to exit: ");
      scanner.nextLine();
      }
  }

  /**
   * Reads user input from the console with a given prompt.
   *
   * @param prompt the prompt message to display to the user
   * @return the user's input as a String
   */
  public String readInput(String prompt) {
    System.out.print(prompt + ": ");
    return scanner.nextLine();
  }

  /**
   * Prints an informational message to the standard output.
   *
   * @param message the message to print
   */
  public void printMessage(String message) {
    System.out.println(message);
  }

  /**
   * Prints an error message to the standard output using a prefixed label.
   *
   * @param message the error message to print
   */
  public void printError(String message) {
    System.out.println("ERROR: " + message);
  }

  /**
   * Prints a success message to the standard output using a prefixed label.
   *
   * @param message the success message to print
   */
  public void printSuccess(String message) {
    System.out.println("SUCCESS: " + message);
  }

  /**
   * Clears the console view by printing several blank lines.
   *
   * <p>Clear behavior is implemented by printing multiple newline characters
   * to approximate a cleared screen in a terminal environment.</p>
   */
  public void clearScreen() {
    System.out.println("\n".repeat(50));
  }
}
