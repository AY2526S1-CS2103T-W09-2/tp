package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.noknock.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.noknock.testutil.TypicalPatients.getTypicalAddressBook;

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
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Patient;
import seedu.noknock.testutil.EditNextOfKinDescriptorBuilder;
import seedu.noknock.testutil.NextOfKinBuilder;

public class EditNextOfKinCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Helper to replace a NextOfKin at a given index in a list.
     */
    private static List<NextOfKin> replaceNextOfKin(List<NextOfKin> list, int index, NextOfKin nok) {
        List<NextOfKin> copy = new java.util.ArrayList<>(list);
        copy.set(index, nok);
        return copy;
    }

    @BeforeEach
    public void setUp() {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        if (patient.getNextOfKinList().isEmpty()) {
            NextOfKin nok = new NextOfKinBuilder().build();
            Patient updated = patient.withNextOfKinList(Collections.singletonList(nok));
            model.setPatient(patient, updated);
        }
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        NextOfKin nokToEdit = patient.getNextOfKinList().get(0);

        NextOfKin editedNextOfKin = new NextOfKinBuilder().build();
        EditNextOfKinCommand.EditNextOfKinDescriptor descriptor =
            new EditNextOfKinDescriptorBuilder(editedNextOfKin).build();

        EditNextOfKinCommand command =
            new EditNextOfKinCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1), descriptor);

        Patient editedPatient =
            patient.withNextOfKinList(replaceNextOfKin(patient.getNextOfKinList(), 0, editedNextOfKin));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(patient, editedPatient);

        String expectedMessage = String.format(EditNextOfKinCommand.MESSAGE_EDIT_NOK_SUCCESS,
            Messages.formatPatient(editedNextOfKin), Messages.formatPatient(patient));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        NextOfKin nokToEdit = patient.getNextOfKinList().get(0);

        NextOfKin editedNextOfKin = new NextOfKinBuilder(nokToEdit)
            .withName("Updated Name")
            .build();
        EditNextOfKinCommand.EditNextOfKinDescriptor descriptor =
            new EditNextOfKinDescriptorBuilder().withName("Updated Name").build();

        EditNextOfKinCommand command =
            new EditNextOfKinCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1), descriptor);

        Patient editedPatient =
            patient.withNextOfKinList(replaceNextOfKin(patient.getNextOfKinList(), 0, editedNextOfKin));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(patient, editedPatient);

        String expectedMessage = String.format(EditNextOfKinCommand.MESSAGE_EDIT_NOK_SUCCESS,
            Messages.formatPatient(editedNextOfKin), Messages.formatPatient(patient));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Patient patient = model.getFilteredPatientList().get(INDEX_FIRST_PERSON.getZeroBased());
        NextOfKin nokToEdit = patient.getNextOfKinList().get(0);

        EditNextOfKinCommand.EditNextOfKinDescriptor descriptor =
            new EditNextOfKinCommand.EditNextOfKinDescriptor();

        EditNextOfKinCommand command =
            new EditNextOfKinCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1), descriptor);

        // No change, so editedNextOfKin is the same as nokToEdit
        Patient editedPatient =
            patient.withNextOfKinList(replaceNextOfKin(patient.getNextOfKinList(), 0, nokToEdit));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(patient, editedPatient);

        String expectedMessage = String.format(EditNextOfKinCommand.MESSAGE_EDIT_NOK_SUCCESS,
            Messages.formatPatient(nokToEdit), Messages.formatPatient(patient));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        EditNextOfKinCommand.EditNextOfKinDescriptor descriptor =
            new EditNextOfKinCommand.EditNextOfKinDescriptor();
        EditNextOfKinCommand command =
            new EditNextOfKinCommand(outOfBoundIndex, Index.fromOneBased(1), descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidNextOfKinIndex_failure() {
        Index patientIndex = INDEX_FIRST_PERSON;
        Patient patient = model.getFilteredPatientList().get(patientIndex.getZeroBased());
        int nokCount = patient.getNextOfKinList().size();
        Index outOfBoundNextOfKinIndex = Index.fromOneBased(nokCount + 1);

        EditNextOfKinCommand.EditNextOfKinDescriptor descriptor =
            new EditNextOfKinCommand.EditNextOfKinDescriptor();
        EditNextOfKinCommand command =
            new EditNextOfKinCommand(patientIndex, outOfBoundNextOfKinIndex, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_NOK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditNextOfKinCommand.EditNextOfKinDescriptor descriptor =
            new EditNextOfKinCommand.EditNextOfKinDescriptor();

        final EditNextOfKinCommand standardCommand =
            new EditNextOfKinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor);

        // same values -> returns true
        EditNextOfKinCommand commandWithSameValues =
            new EditNextOfKinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON,
                new EditNextOfKinCommand.EditNextOfKinDescriptor(descriptor));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different patient index -> returns false
        assertFalse(standardCommand.equals(
            new EditNextOfKinCommand(Index.fromOneBased(2), INDEX_FIRST_PERSON, descriptor)));

        // different NOK index -> returns false
        assertFalse(standardCommand.equals(
            new EditNextOfKinCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2), descriptor)));
    }

    @Test
    public void toStringMethod() {
        Index patientIndex = Index.fromOneBased(1);
        Index nokIndex = Index.fromOneBased(1);
        EditNextOfKinCommand.EditNextOfKinDescriptor descriptor =
            new EditNextOfKinCommand.EditNextOfKinDescriptor();
        EditNextOfKinCommand command = new EditNextOfKinCommand(patientIndex, nokIndex, descriptor);

        String expected = EditNextOfKinCommand.class.getCanonicalName()
            + "{patientIndex=" + patientIndex
            + ", editNextOfKinDescriptor=" + descriptor + "}";
        assertEquals(expected, command.toString());
    }
}
