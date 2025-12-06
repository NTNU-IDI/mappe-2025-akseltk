package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.Validators;import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


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

  public DiaryEntry getEntryById(int entryId) {
    if (entryId <= 0) {
      throw new IllegalArgumentException("id must be positive");
    }

    return entries.stream()
            .filter(entry -> entry.getEntryId() == entryId)
            .findFirst()
            .orElse(null);
  }

  public List<DiaryEntry> getEntriesByAuthor(String email) {
    Validators.validateString(email, "Email");

    return entries.stream()
            .filter(entry -> entry.getAuthor().getEmail().equalsIgnoreCase(email))
            .toList();
  }

  public Map<String, Long> getAuthorStatistics() {
    return entries.stream()
            .collect(Collectors.groupingBy(entry ->
                            entry.getAuthor().getEmail(), Collectors.counting()));
    }

  public List<DiaryEntry> getEntriesByDate(LocalDate date) {
    Validators.validateNotNull(date, "Date");

    return entries.stream()
            .filter(entry -> entry.getCreationTime().toLocalDate().equals(date))
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
              return !date.isBefore(from) && !date.isAfter(to);
            })
            .sorted(Comparator.comparing(DiaryEntry::getCreationTime))
            .toList();
  }

  public List<DiaryEntry> searchByKeyword(String keyword) {
    return entries.stream()
            .filter(entry ->
                    entry.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                            entry.getTitle().toLowerCase().contains(keyword.toLowerCase()))
            .toList();

  }
}
