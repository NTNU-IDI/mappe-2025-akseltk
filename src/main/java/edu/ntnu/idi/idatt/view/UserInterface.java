package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.DiaryEntry;

public class UserInterface {

    public void init() {
        System.out.println("--- Diary Application Initialized ---");
    }

    public void start() {
        System.out.println("--- Diary Application Started ---");

        try {
            DiaryEntry entry1 = new DiaryEntry("t1", "litt om meg", "Aksel");
            DiaryEntry entry2 = new DiaryEntry("t2", "litt om deg", "Aksel");
            DiaryEntry entry3 = new DiaryEntry("t3", "jeg fanget en fisk", "Aksel");

            System.out.println(entry1);
            System.out.println(entry2);
            System.out.println(entry3);

        } catch (IllegalArgumentException e) {
            System.out.println("Feil: " + e.getMessage());
        }
    }
}

