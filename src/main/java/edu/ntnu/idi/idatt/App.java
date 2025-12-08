package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.register.AuthorRegister;
import edu.ntnu.idi.idatt.model.register.DiaryRegister;
import edu.ntnu.idi.idatt.view.UserInterface;

/**
 * Application entry point that wires together MVC components and starts the UI.
 *
 * <p>The class creates the main {@code DiaryRegister} and {@code AuthorRegister}
 * model objects, a {@code UserInterface} view and a {@code DiaryController}
 * controller which is then started. Responsibility is limited to application
 * bootstrap and wiring.
 * </p>
 */
public class App {
  /**
   * Program entry point. Constructs model, view and controller components and
   * starts the controller to begin the user interaction loop.
   *
   * @param args command line arguments (not used)
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    DiaryRegister diaryRegister = new DiaryRegister();
    AuthorRegister authorRegister = new AuthorRegister();

    DiaryController controller = new DiaryController(diaryRegister, authorRegister, ui);
    controller.start();
  }
}