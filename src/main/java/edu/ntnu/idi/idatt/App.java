package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import edu.ntnu.idi.idatt.view.UserInterface;

public class App {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        DiaryRegister diaryRegister = new DiaryRegister();
        AuthorRegister authorRegister = new AuthorRegister();
        
        DiaryController controller = new DiaryController(diaryRegister, authorRegister, ui);
        controller.start();
    }
}