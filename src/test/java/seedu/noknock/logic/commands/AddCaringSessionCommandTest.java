package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.noknock.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.commons.exceptions.IllegalValueException;
import seedu.noknock.logic.Messages;
import seedu.noknock.model.Model;
import seedu.noknock.model.ModelManager;
import seedu.noknock.model.UserPrefs;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.CareType;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.session.Note;

public class AddCaringSessionCommandTest {

    private static final CaringSession VALID_SESSION =
        new CaringSession(new CareType("medication"), new Note("Check sugar"),
            new Date("2025-12-25"), new Time("14:30"));

    @Test
    public void execute_validIndex_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Patient targetPatient = model.getFilteredPatientList().get(0);

        List<CaringSession> updatedList = new ArrayList<>(targetPatient.getCaringSessionList());
        updatedList.add(VALID_SESSION);
        Patient expectedEditedPatient = targetPatient.withCaringSessionList(updatedList);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPatient(targetPatient, expectedEditedPatient);
        expectedModel.updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);

        AddCaringSessionCommand command = new AddCaringSessionCommand(Index.fromOneBased(1), VALID_SESSION);

        String expectedMessage = String.format(AddCaringSessionCommand.MESSAGE_ADD_CARING_SESSION_SUCCESS,
            Messages.formatSession(VALID_SESSION), Messages.formatPatient(expectedEditedPatient));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int outOfBoundsIndex = model.getFilteredPatientList().size() + 1;

        AddCaringSessionCommand command =
            new AddCaringSessionCommand(Index.fromOneBased(outOfBoundsIndex), VALID_SESSION);

        assertCommandFailure(command, model, MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateSession_throwsCommandException() throws IllegalValueException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Patient targetPatient = model.getFilteredPatientList().get(0);

        // add VALID_SESSION once
        List<CaringSession> sessionList = new ArrayList<>(targetPatient.getCaringSessionList());
        sessionList.add(VALID_SESSION);
        Patient updatedPatient = targetPatient.withCaringSessionList(sessionList);
        model.setPatient(targetPatient, updatedPatient);

        // now attempt to add same session again
        AddCaringSessionCommand command = new AddCaringSessionCommand(Index.fromOneBased(1), VALID_SESSION);

        assertCommandFailure(command, model,
            String.format(AddCaringSessionCommand.MESSAGE_HAS_OVERLAPPING_SESSION, VALID_SESSION.getCareType()));
    }

    @Test
    public void equals() {
        AddCaringSessionCommand command1 = new AddCaringSessionCommand(Index.fromOneBased(1), VALID_SESSION);
        AddCaringSessionCommand command2 = new AddCaringSessionCommand(Index.fromOneBased(1), VALID_SESSION);
        AddCaringSessionCommand command3 = new AddCaringSessionCommand(Index.fromOneBased(2), VALID_SESSION);

        // same object -> true
        assertTrue(command1.equals(command1));

        // same values -> true
        assertTrue(command1.equals(command2));

        // different index -> false
        assertFalse(command1.equals(command3));

        // different type -> false
        assertFalse(command1.equals(5));

        // null -> false
        assertFalse(command1.equals(null));
    }
}
