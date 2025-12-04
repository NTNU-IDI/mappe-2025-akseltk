package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


public class DiaryRegister {
  private final List<DiaryEntry> entries;
  private int nextId;

  public DiaryRegister() {
    this.entries = new ArrayList<>();
    nextId = 1;
  }

  public void addEntry(DiaryEntry entry) {
    Validators.validateNotNull(entry, "Diary entry");
    entry.setEntryId(nextId++);
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

  public List<DiaryEntry> getEntriesByAuthor(String email) {
     return entries.stream()
            .filter(entry -> entry.getAuthor().getEmail().equals(email))
            .toList();
  }

  public List<DiaryEntry> searchEntriesBetweenDates(LocalDate from, LocalDate to) {
    Validators.validateNotNull(from, "From");
    Validators.validateNotNull(to, "To");

    if (from.isAfter(to)) {
      throw new IllegalArgumentException("From must be before to");
    }

    return entries.stream()
            .filter(entry -> {
              LocalDate date = entry.getCreationTime().toLocalDate();
              return date.isAfter(from) && date.isBefore(to);
            })
            .sorted(Comparator.comparing(DiaryEntry::getCreationTime))
            .toList();
  }

  public List<DiaryEntry> searchKeyWord(String keyword) {
    return entries.stream()
            .filter(entry ->
                    entry.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                            entry.getTitle().toLowerCase().contains(keyword.toLowerCase()))
            .toList();

  }

  public boolean isEmpty() {
    return entries.isEmpty();
  }
}
