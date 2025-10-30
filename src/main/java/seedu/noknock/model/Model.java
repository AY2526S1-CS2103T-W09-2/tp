package seedu.noknock.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import seedu.noknock.commons.core.GuiSettings;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.CaringSession;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Patient> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<CaringSession> PREDICATE_SHOW_ALL_SESSIONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the address book.
     */
    boolean hasPatient(Patient patient);

    /**
     * Deletes the given patient.
     * The patient must exist in the address book.
     */
    void deletePatient(Patient target);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the address book.
     */
    void addPatient(Patient patient);

    /**
     * Replaces the given patient {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The patient identity of {@code editedPerson} must not be the same as another
     * existing patient in the address book.
     */
    void setPatient(Patient target, Patient editedPerson);

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPatientList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Patient> predicate);

    /**
     * Updates the filter of the displayed caring sessions (across all patients) to the given {@code predicate}.
     * This affects presentation only and does not modify any stored data.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void setSessionDisplayFilter(Predicate<CaringSession> predicate);

    /**
     * Returns the current predicate used to filter the displayed caring sessions (across all patients).
     */
    Predicate<CaringSession> getSessionDisplayFilter();

    /**
     * Returns a read-only property of the caring-session display filter.
     * UI may observe this property to react to filter changes.
     */
    ReadOnlyObjectProperty<Predicate<CaringSession>> sessionDisplayFilterProperty();
}
