package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.noknock.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.noknock.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.noknock.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.noknock.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.model.Model;
import seedu.noknock.model.ModelManager;
import seedu.noknock.model.UserPrefs;
import seedu.noknock.model.person.Patient;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePatientCommand}.
 */
public class DeletePatientCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patient patientToDelete = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePatientCommand.MESSAGE_DELETE_PATIENT_SUCCESS,
            Messages.formatPatient(patientToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePatient(patientToDelete);

        assertCommandSuccess(deletePatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(outOfBoundIndex);

        assertCommandFailure(deletePatientCommand, model, DeletePatientCommand.MESSAGE_INVALID_PATIENT_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Patient patientToDelete = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePatientCommand.MESSAGE_DELETE_PATIENT_SUCCESS,
            Messages.formatPatient(patientToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePatient(patientToDelete);
        showNoPatient(expectedModel);

        assertCommandSuccess(deletePatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPatientList().size());

        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(outOfBoundIndex);

        assertCommandFailure(deletePatientCommand, model, DeletePatientCommand.MESSAGE_INVALID_PATIENT_INDEX);
    }

    @Test
    public void execute_lastPersonUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPatientList().size());
        Patient lastPerson = model.getFilteredPatientList().get(lastPersonIndex.getZeroBased());
        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(lastPersonIndex);

        String expectedMessage = String.format(DeletePatientCommand.MESSAGE_DELETE_PATIENT_SUCCESS,
            Messages.formatPatient(lastPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePatient(lastPerson);

        assertCommandSuccess(deletePatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeletePatientCommand deleteFirstCommand = new DeletePatientCommand(INDEX_FIRST_PERSON);
        DeletePatientCommand deleteSecondCommand = new DeletePatientCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePatientCommand deleteFirstCommandCopy = new DeletePatientCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different type -> returns false
        assertFalse(deleteFirstCommand.equals(new ClearCommand()));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(targetIndex);
        String expected = DeletePatientCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deletePatientCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPatient(Model model) {
        model.updateFilteredPatientList(p -> false);

        assertTrue(model.getFilteredPatientList().isEmpty());
    }
}
