package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;

import java.text.CollationElementIterator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;



public class DiaryRegister {
    private final List<DiaryEntry> entries;

    public DiaryRegister() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(DiaryEntry entry) {
        Validators.validateNotNull(entry, "Diary entry");
        entries.add(entry);
    }

    public void removeEntry(DiaryEntry entry) {
        Validators.validateNotNull(entry, "Diary entry");
        entries.remove(entry);
    }

    public List<DiaryEntry> getAllEntries() {
        return entries.stream()
                .sorted(Comparator.comparing(DiaryEntry::getCreationTime).reversed())
                .toList();
    }


    public List<DiaryEntry> getEntryByDate(LocalDate date) {
        Validators.validateNotNull(date, "Date");

        return entries.stream()
                .filter(entry -> entry.getCreationTime().toLocalDate().equals(date))
                .toList();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }
}
