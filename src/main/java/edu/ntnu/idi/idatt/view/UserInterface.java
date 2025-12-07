package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class UserInterface {
  private static final String LIST_SEPARATOR = "------------------------------";
  private static final String STATS_SEPARATOR = "--------------------------------------------%n";
  private static final String NO_ENTRIES_MESSAGE = "No entries found.";
  private static final String CANCEL_MESSAGE = "Press Enter to Cancel";

  private final Scanner scanner;

  public UserInterface() {
    this.scanner = new Scanner(System.in);
  }

  public void init() {
    System.out.println("--- Diary Application Initialized ---");
  }

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

  public void printAuthorMenu() {
    System.out.println("\n--- AUTHOR MENU ---");
    System.out.println("1. select existing author");
    System.out.println("2. Create new author");
    System.out.println("0. Press enter to cancel");
    System.out.print("select an option");
  }

  public void printSearchMenu() {
    System.out.println("\n--- SEARCH MENU ---");
    System.out.println("1. Search by keyword");
    System.out.println("2. Search by date");
    System.out.println("3. Search by date range");
    System.out.println("4. Search by author");
    System.out.println("0. Press enter to cancel");
    System.out.print("Choose a number");
  }

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

  public void printSelectExistingAuthor(List<Author> authors) {
    System.out.println("\n--- EXISTING AUTHOR MENU ---");

    for (int i = 0; i < authors.size(); i++) {
      System.out.println((i + 1) + ". " + authors.get(i));
    }

    System.out.println("select an author number");
  }

  public void printCreateAuthorTitle() {
    System.out.println("\n--- CREATE NEW AUTHOR ---");
    System.out.println(CANCEL_MESSAGE);
  }

  public void printDeleteEntryTitle() {
    System.out.println("\n--- DELETE ENTRY ---");
    System.out.println(CANCEL_MESSAGE);
  }

  public void printDiaryEntryList(List<DiaryEntry> entries) {
    clearScreen();
    if (entries.isEmpty()) {
      System.out.println("No diary entries found");
    } else {
      System.out.println("\n--- ENTRIES LIST ---");
      for (DiaryEntry entry : entries) {
        printSingleEntry(entry);
      }

      System.out.print("Press Enter to exit: ");
      scanner.nextLine();
    }
  }

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

  public String readInput(String prompt) {
    System.out.print(prompt + ": ");
    return scanner.nextLine();
  }

  public void printMessage(String message) {
    System.out.println(message);
  }

  public void printError(String message) {
    System.out.println("ERROR: " + message);
  }

  public void printSuccess(String message) {
    System.out.println("SUCCESS: " + message);
  }

  public void clearScreen() {
    System.out.println("\n".repeat(50));
  }
}


