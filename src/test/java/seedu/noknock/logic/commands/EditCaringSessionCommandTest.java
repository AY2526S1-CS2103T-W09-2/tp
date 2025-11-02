package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.commands.AddCaringSessionCommand.MESSAGE_HAS_OVERLAPPING_SESSION;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.noknock.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.noknock.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.model.AddressBook;
import seedu.noknock.model.Model;
import seedu.noknock.model.ModelManager;
import seedu.noknock.model.UserPrefs;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.testutil.CaringSessionBuilder;
import seedu.noknock.testutil.EditSessionDescriptorBuilder;

public class EditCaringSessionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Helper to replace a session at a given index in a list.
     */
    private static List<CaringSession> replaceSession(List<CaringSession> list, int index, CaringSession session) {
        List<CaringSession> copy = new java.util.ArrayList<>(list);
        copy.set(index, session);
        return copy;
    }

    @BeforeEach
    public void setUp() {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        if (patient.getCaringSessionList().isEmpty()) {
            CaringSession session = new CaringSessionBuilder().build();
            Patient updated = patient.withCaringSessionList(Collections.singletonList(session));
            model.setPatient(patient, updated);
        }
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        CaringSession sessionToEdit = patient.getCaringSessionList().get(0);

        CaringSession editedSession = new CaringSessionBuilder().build();
        EditCaringSessionCommand.EditSessionDescriptor descriptor =
            new EditSessionDescriptorBuilder(editedSession).build();

        EditCaringSessionCommand command =
            new EditCaringSessionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1), descriptor);

        Patient editedPatient =
            patient.withCaringSessionList(replaceSession(patient.getCaringSessionList(), 0, editedSession));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(patient, editedPatient);

        String expectedMessage = String.format(EditCaringSessionCommand.MESSAGE_EDIT_SESSION_SUCCESS,
            Messages.formatSession(editedSession), Messages.formatPatient(patient));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        CaringSession sessionToEdit = patient.getCaringSessionList().get(0);

        CaringSession editedSession = new CaringSessionBuilder(sessionToEdit)
            .withNote("Updated note")
            .build();
        EditCaringSessionCommand.EditSessionDescriptor descriptor =
            new EditSessionDescriptorBuilder().withNote("Updated note").build();

        EditCaringSessionCommand command =
            new EditCaringSessionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1), descriptor);

        Patient editedPatient =
            patient.withCaringSessionList(replaceSession(patient.getCaringSessionList(), 0, editedSession));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(patient, editedPatient);

        String expectedMessage = String.format(EditCaringSessionCommand.MESSAGE_EDIT_SESSION_SUCCESS,
            Messages.formatSession(editedSession), Messages.formatPatient(patient));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        CaringSession sessionToEdit = patient.getCaringSessionList().get(0);

        EditCaringSessionCommand.EditSessionDescriptor descriptor =
            new EditCaringSessionCommand.EditSessionDescriptor();

        EditCaringSessionCommand command =
            new EditCaringSessionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1), descriptor);

        // No change, so editedSession is the same as sessionToEdit
        Patient editedPatient =
            patient.withCaringSessionList(replaceSession(patient.getCaringSessionList(), 0, sessionToEdit));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(patient, editedPatient);

        String expectedMessage = String.format(EditCaringSessionCommand.MESSAGE_EDIT_SESSION_SUCCESS,
            Messages.formatSession(sessionToEdit), Messages.formatPatient(patient));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        EditCaringSessionCommand.EditSessionDescriptor descriptor =
            new EditCaringSessionCommand.EditSessionDescriptor();
        EditCaringSessionCommand command =
            new EditCaringSessionCommand(outOfBoundIndex, Index.fromOneBased(1), descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidSessionIndex_failure() {
        Index patientIndex = INDEX_FIRST_PERSON;
        Patient patient = model.getFilteredPatientList().get(patientIndex.getZeroBased());
        int sessionCount = patient.getCaringSessionList().size();
        Index outOfBoundSessionIndex = Index.fromOneBased(sessionCount + 1);

        EditCaringSessionCommand.EditSessionDescriptor descriptor =
            new EditCaringSessionCommand.EditSessionDescriptor();
        EditCaringSessionCommand command =
            new EditCaringSessionCommand(patientIndex, outOfBoundSessionIndex, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_SESSION_INDEX);
    }

    @Test
    public void execute_duplicateSessionOverlap_failure() {
        Index patientIndex = INDEX_FIRST_PERSON;
        Patient patient = model.getFilteredPatientList().get(patientIndex.getZeroBased());

        CaringSession firstSession = new CaringSessionBuilder().build();
        CaringSession secondSession = new CaringSessionBuilder().withNote("Different note").withTime("23:59").build();

        Patient updatedPatient = patient.withCaringSessionList(Arrays.asList(firstSession, secondSession));
        model.setPatient(patient, updatedPatient);

        // Attempt to edit the second session to match the first (causes overlap)
        EditCaringSessionCommand.EditSessionDescriptor descriptor =
            new EditSessionDescriptorBuilder(firstSession).build();
        EditCaringSessionCommand command =
            new EditCaringSessionCommand(patientIndex, Index.fromOneBased(2), descriptor);

        String expectedMessage = String.format(MESSAGE_HAS_OVERLAPPING_SESSION, firstSession.getCareType());
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        EditCaringSessionCommand.EditSessionDescriptor descriptor =
            new EditCaringSessionCommand.EditSessionDescriptor();

        final EditCaringSessionCommand standardCommand =
            new EditCaringSessionCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor);

        // same values -> returns true
        EditCaringSessionCommand commandWithSameValues =
            new EditCaringSessionCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON,
                new EditCaringSessionCommand.EditSessionDescriptor(descriptor));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different patient index -> returns false
        assertFalse(standardCommand.equals(
            new EditCaringSessionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2), descriptor)));

        // different session index -> returns false
        assertFalse(standardCommand.equals(
            new EditCaringSessionCommand(Index.fromOneBased(2), INDEX_FIRST_PERSON, descriptor)));
    }

    @Test
    public void toStringMethod() {
        Index patientIndex = Index.fromOneBased(1);
        Index sessionIndex = Index.fromOneBased(1);
        EditCaringSessionCommand.EditSessionDescriptor descriptor =
            new EditCaringSessionCommand.EditSessionDescriptor();
        EditCaringSessionCommand command = new EditCaringSessionCommand(patientIndex, sessionIndex, descriptor);

        String expected = EditCaringSessionCommand.class.getCanonicalName()
            + "{patientIndex=" + patientIndex
            + ", sessionIndex=" + sessionIndex
            + ", editSessionDescriptor=" + descriptor + "}";
        assertEquals(expected, command.toString());
    }
}
