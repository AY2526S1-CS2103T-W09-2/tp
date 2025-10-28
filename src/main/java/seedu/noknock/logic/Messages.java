package seedu.noknock.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.noknock.logic.parser.Prefix;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.CaringSession;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX = "The patient index provided is invalid";
    public static final String MESSAGE_INVALID_NOK_DISPLAYED_INDEX = "The Next-of-Kin index provided is invalid";
    public static final String MESSAGE_VIEW_PATIENT_SUCCESS = "Viewing Patient: %1$s";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_SESSION_INDEX = "The caring session index provided is invalid";
    public static final String MESSAGE_DUPLICATE_FIELDS =
        "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
            Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String formatPatient(Patient patient) {
        final StringBuilder builder = new StringBuilder();
        builder.append(patient.getName());
        return builder.toString();
    }

    /**
     * Format the {@code nextOfKin} for display to the user.
     */
    public static String formatNextOfKin(NextOfKin nextOfKin) {
        final StringBuilder builder = new StringBuilder();
        builder.append(nextOfKin.getName());
        return builder.toString();
    }

    /**
     * Formats the {@code session} for display to the user.
     */
    public static String formatSession(CaringSession session) {
        final StringBuilder builder = new StringBuilder();
        builder.append(session.getCareType() + " on " + session.getDate() + " at " + session.getTime());
        return builder.toString();
    }
}
