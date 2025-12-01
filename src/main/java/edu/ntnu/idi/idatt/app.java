package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.view.UserInterface;

public class app {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.init();
        ui.start();
    }
}
