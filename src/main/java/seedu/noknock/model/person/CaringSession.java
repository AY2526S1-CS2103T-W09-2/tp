package seedu.noknock.model.person;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a caring session assigned to a patient.
 * <p>
 * A CaringSession includes a date, time, care type, and optional notes.
 * It provides validation for date, time, and care type formats.
 */
public class CaringSession {
    public static final String DATE_CONSTRAINTS =
            "Date must be in YYYY-MM-DD or DD-MM-YYYY format and not in the past.";
    public static final String TIME_CONSTRAINTS =
            "Time must be in HH:MM (24-hour) or H:MMam/pm format.";
    public static final String TYPE_CONSTRAINTS =
            "Care type must be 1-50 characters (letters, numbers, spaces, or hyphens).";
    public static final String NOTES_CONSTRAINTS =
            "Notes cannot exceed 200 characters.";

    private final LocalDate date;
    private final LocalTime time;
    private final String careType;
    private final String notes;

    /**
     * Constructs a {@code CaringSession}.
     *
     * @param date The date of the session (must not be in the past).
     * @param time The time of the session (must be valid).
     * @param careType The type of care (1–50 characters).
     * @param notes Optional notes (0–200 characters).
     */
    public CaringSession(LocalDate date, LocalTime time, String careType, String notes) {
        this.date = date;
        this.time = time;
        this.careType = careType.toLowerCase();
        this.notes = (notes == null) ? "" : notes;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getCareType() {
        return careType;
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Checks if this caring session overlaps with another session.
     * <p>
     * Two sessions are considered overlapping if they have the same date,
     * the same time, and the same care type (case-insensitive).
     *
     * @param other the other CaringSession to compare with
     * @return true if the sessions overlap, false otherwise
     */
    public boolean overlaps(CaringSession other) {
        return this.date.equals(other.date)
                && this.time.equals(other.time)
                && this.careType.equalsIgnoreCase(other.careType);
    }

    /**
     * Validates the date format and ensures it is today or in the future.
     */
    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate parsedDate;
            if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                parsedDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else if (dateStr.matches("\\d{2}-\\d{2}-\\d{4}")) {
                parsedDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } else {
                return false;
            }
            return !parsedDate.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Validates the time format.
     */
    public static boolean isValidTime(String timeStr) {
        try {
            if (timeStr.matches("\\d{2}:\\d{2}")) {
                LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
                return true;
            } else if (timeStr.matches("\\d{1,2}:\\d{2}(am|pm)")) {
                LocalTime.parse(timeStr.toUpperCase(), DateTimeFormatter.ofPattern("h:mma"));
                return true;
            }
            return false;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Validates the care type string.
     */
    public static boolean isValidCareType(String type) {
        return type != null && type.matches("[A-Za-z0-9\\- ]{1,50}");
    }

    /**
     * Validates the notes string.
     */
    public static boolean isValidNotes(String notes) {
        return notes == null || notes.length() <= 200;
    }

    @Override
    public String toString() {
        return String.format("%s on %s at %s%s",
                careType, date, time, (notes == null || notes.isEmpty()) ? "" : " (" + notes + ")" );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CaringSession)) {
            return false;
        }
        CaringSession otherSession = (CaringSession) other;
        return date.equals(otherSession.date)
                && time.equals(otherSession.time)
                && careType.equals(otherSession.careType)
                && notes.equals(otherSession.notes);
    }
}
