package edu.ntnu.idi.idatt.view;

import java.util.Scanner;


public class UserInterface {
  private final Scanner scanner;

  public UserInterface() {
    this.scanner = new Scanner(System.in);
  }

  public void init() {
    System.out.println("--- Diary Application Initialized ---");
  }

  public void printMenu() {
    System.out.println("\n--- MAIN MENU ---");
    System.out.println("1. Write new entry");
    System.out.println("2. Delete entry");
    System.out.println("3. Show all entries");
    System.out.println("4. Search for entries");
    System.out.println("0. Exit");
    System.out.print("Choose a number");
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
}


