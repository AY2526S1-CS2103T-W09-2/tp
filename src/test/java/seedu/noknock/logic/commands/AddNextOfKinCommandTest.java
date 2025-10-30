package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.noknock.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.noknock.commons.core.GuiSettings;
import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.ReadOnlyAddressBook;
import seedu.noknock.model.ReadOnlyUserPrefs;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.testutil.NextOfKinBuilder;
import seedu.noknock.testutil.PatientBuilder;

public class AddNextOfKinCommandTest {

    @Test
    public void constructor_nullPatientIndex_throwsNullPointerException() {
        NextOfKin validNok = new NextOfKinBuilder().build();
        assertThrows(NullPointerException.class, () -> new AddNextOfKinCommand(null, validNok));
    }

    @Test
    public void constructor_nullNextOfKin_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddNextOfKinCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validPatientIndexAndNok_addSuccessful() throws Exception {
        Patient validPatient = new PatientBuilder().build();
        NextOfKin validNok = new NextOfKinBuilder().build();
        ModelStubAcceptingNokAdded modelStub = new ModelStubAcceptingNokAdded(validPatient);

        CommandResult commandResult = new AddNextOfKinCommand(Index.fromOneBased(1), validNok)
            .execute(modelStub);

        assertEquals(String.format(AddNextOfKinCommand.MESSAGE_ADD_NOK_SUCCESS,
                Messages.formatNextOfKin(validNok), Messages.formatPatient(validPatient)),
            commandResult.getFeedbackToUser());
        assertEquals(List.of(validNok), modelStub.noksAdded);
    }

    @Test
    public void execute_invalidPatientIndex_throwsCommandException() {
        NextOfKin validNok = new NextOfKinBuilder().build();
        AddNextOfKinCommand addCommand = new AddNextOfKinCommand(Index.fromOneBased(2), validNok);
        ModelStub modelStub = new ModelStubWithPatient(new PatientBuilder().build());

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX, () ->
            addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateNok_throwsCommandException() {
        NextOfKin validNok = new NextOfKinBuilder().build();
        Patient patientWithNok = new PatientBuilder().withNextOfKinList(validNok).build();
        AddNextOfKinCommand addCommand = new AddNextOfKinCommand(Index.fromOneBased(1), validNok);
        ModelStub modelStub = new ModelStubWithPatient(patientWithNok);

        assertThrows(CommandException.class, AddNextOfKinCommand.MESSAGE_DUPLICATE_NOK, () ->
            addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        NextOfKin nok1 = new NextOfKinBuilder().withName("Jane").build();
        NextOfKin nok2 = new NextOfKinBuilder().withName("John").build();
        AddNextOfKinCommand addNok1Command = new AddNextOfKinCommand(Index.fromOneBased(1), nok1);
        AddNextOfKinCommand addNok2Command = new AddNextOfKinCommand(Index.fromOneBased(1), nok2);

        // same object -> returns true
        assertEquals(addNok1Command, addNok1Command);

        // same values -> returns true
        AddNextOfKinCommand addNok1CommandCopy = new AddNextOfKinCommand(Index.fromOneBased(1), nok1);
        assertEquals(addNok1Command, addNok1CommandCopy);

        // different nok -> returns false
        assertNotEquals(addNok1Command, addNok2Command);

        // different index -> returns false
        AddNextOfKinCommand addNok1DifferentIndexCommand = new AddNextOfKinCommand(Index.fromOneBased(2), nok1);
        assertNotEquals(addNok1Command, addNok1DifferentIndexCommand);
    }

    @Test
    public void toStringMethod() {
        NextOfKin nok = new NextOfKinBuilder().build();
        AddNextOfKinCommand addCommand = new AddNextOfKinCommand(Index.fromOneBased(1), nok);
        String expected = AddNextOfKinCommand.class.getCanonicalName() + "{patientIndex="
            + Index.fromOneBased(1) + ", nokToAdd=" + nok + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that has all methods failing.
     */
    private static class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatient(Patient target, Patient editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSessionDisplayFilter(Predicate<CaringSession> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Predicate<CaringSession> getSessionDisplayFilter() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyObjectProperty<Predicate<CaringSession>> sessionDisplayFilterProperty() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single patient.
     */
    private static class ModelStubWithPatient extends ModelStub {
        private final Patient patient;

        ModelStubWithPatient(Patient patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            return FXCollections.observableArrayList(patient);
        }

        @Override
        public void setPatient(Patient target, Patient editedPatient) {
            requireNonNull(target);
            requireNonNull(editedPatient);
        }

        @Override
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            requireNonNull(predicate);
        }
    }

    /**
     * A Model stub that accepts NOK being added.
     */
    private static class ModelStubAcceptingNokAdded extends ModelStubWithPatient {
        final ArrayList<NextOfKin> noksAdded = new ArrayList<>();
        private Patient patient;

        ModelStubAcceptingNokAdded(Patient patient) {
            super(patient);
            this.patient = patient;
        }

        @Override
        public void setPatient(Patient target, Patient editedPatient) {
            requireNonNull(target);
            requireNonNull(editedPatient);
            this.patient = editedPatient;
            noksAdded.addAll(editedPatient.getNextOfKinList());
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            return FXCollections.observableArrayList(patient);
        }

        @Override
        public void setSessionDisplayFilter(Predicate<CaringSession> predicate) {}
    }
}
