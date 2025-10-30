package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.noknock.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.AddressBook;
import seedu.noknock.model.Model;
import seedu.noknock.model.ModelManager;
import seedu.noknock.model.UserPrefs;
import seedu.noknock.testutil.PatientBuilder;

public class ViewPatientCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPatient(new PatientBuilder().withIC("S1234567A").withName("Alice Pauline").build());
        addressBook.addPatient(new PatientBuilder().withIC("S1234567B").withName("Bob Brown").build());
        model = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewPatientCommand(null));
    }

    @Test
    public void execute_validIndex_success() throws CommandException {
        ViewPatientCommand viewCommand = new ViewPatientCommand(Index.fromOneBased(1));
        CommandResult result = viewCommand.execute(model);

        assertEquals(String.format("Viewing Patient: %s", "Alice Pauline"),
            result.getFeedbackToUser());
        assertEquals(1, model.getFilteredPatientList().size());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ViewPatientCommand viewCommand = new ViewPatientCommand(Index.fromOneBased(3));

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX, () -> viewCommand.execute(model));
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() {
        ViewPatientCommand viewCommand = new ViewPatientCommand(Index.fromOneBased(10));

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX, () -> viewCommand.execute(model));
    }

    @Test
    public void equals() {
        ViewPatientCommand viewCommand1 = new ViewPatientCommand(Index.fromOneBased(1));
        ViewPatientCommand viewCommand2 = new ViewPatientCommand(Index.fromOneBased(2));
        ViewPatientCommand viewCommand1Copy = new ViewPatientCommand(Index.fromOneBased(1));

        // same object -> returns true
        assertEquals(viewCommand1, viewCommand1);

        // same values -> returns true
        assertEquals(viewCommand1, viewCommand1Copy);

        // null -> returns false
        assertNotEquals(null, viewCommand1);

        // different index -> returns false
        assertNotEquals(viewCommand1, viewCommand2);
    }

    @Test
    public void toStringMethod() {
        ViewPatientCommand viewCommand = new ViewPatientCommand(Index.fromOneBased(1));
        String expected = "ViewPatientCommand(index=" + Index.fromOneBased(1) + ")";
        assertEquals(expected, viewCommand.toString());
    }
}
