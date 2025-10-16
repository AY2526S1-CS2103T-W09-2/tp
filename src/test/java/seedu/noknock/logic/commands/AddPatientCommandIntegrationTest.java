package seedu.noknock.logic.commands;

import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.noknock.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.noknock.logic.Messages;
import seedu.noknock.model.Model;
import seedu.noknock.model.ModelManager;
import seedu.noknock.model.UserPrefs;
import seedu.noknock.model.person.Patient;
import seedu.noknock.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddPatientCommand}.
 */
public class AddPatientCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Patient validPerson = new PatientBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddPatientCommand(validPerson), model,
                String.format(AddPatientCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Patient personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddPatientCommand(personInList), model,
                AddPatientCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
