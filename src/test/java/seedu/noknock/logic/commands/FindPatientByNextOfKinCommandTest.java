package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.noknock.logic.Messages;
import seedu.noknock.model.AddressBook;
import seedu.noknock.model.Model;
import seedu.noknock.model.ModelManager;
import seedu.noknock.model.UserPrefs;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.PatientNokContainsKeywordsPredicate;
import seedu.noknock.testutil.NextOfKinBuilder;
import seedu.noknock.testutil.PatientBuilder;

public class FindPatientByNextOfKinCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        AddressBook addressBook = new AddressBook();

        // Create patients with empty NOK lists first
        Patient alice = new PatientBuilder().withIC("S1234567A").withName("Alice Pauline").build();
        Patient bob = new PatientBuilder().withIC("S1234567B").withName("Bob Brown").build();

        addressBook.addPatient(alice);
        addressBook.addPatient(bob);

        model = new ModelManager(addressBook, new UserPrefs());

        // Add NextOfKin if empty, using the same style as EditNextOfKinCommandTest
        addNextOfKinIfEmpty(model.getFilteredPatientList().get(0),
                new NextOfKinBuilder().withName("Jane Smith").withPhone("91234567").withRelationship("Mother").build());
        addNextOfKinIfEmpty(model.getFilteredPatientList().get(1),
                new NextOfKinBuilder().withName("John Doe").withPhone("98765432").withRelationship("Father").build());
    }

    private void addNextOfKinIfEmpty(Patient patient, NextOfKin nok) {
        if (patient.getNextOfKinList().isEmpty()) {
            Patient updated = patient.withNextOfKinList(Collections.singletonList(nok));
            model.setPatient(patient, updated);
        }
    }

    @Test
    public void equals() {
        PatientNokContainsKeywordsPredicate firstPredicate =
                new PatientNokContainsKeywordsPredicate(Collections.singletonList("Jane"));
        PatientNokContainsKeywordsPredicate secondPredicate =
                new PatientNokContainsKeywordsPredicate(Collections.singletonList("John"));

        FindPatientByNextOfKinCommand findFirstCommand = new FindPatientByNextOfKinCommand(firstPredicate);
        FindPatientByNextOfKinCommand findSecondCommand = new FindPatientByNextOfKinCommand(secondPredicate);

        assertTrue(findFirstCommand.equals(findFirstCommand)); // same object
        assertTrue(findFirstCommand.equals(new FindPatientByNextOfKinCommand(firstPredicate))); // same values
        assertNotEquals(1, findFirstCommand); // different types
        assertNotEquals(null, findFirstCommand); // null
        assertNotEquals(findFirstCommand, findSecondCommand); // different predicate
    }

    @Test
    public void execute_emptyKeywords_listsAllPatients() {
        FindPatientByNextOfKinCommand command =
                new FindPatientByNextOfKinCommand(new PatientNokContainsKeywordsPredicate(Collections.emptyList()));
        CommandResult result = command.execute(model);

        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2),
                result.getFeedbackToUser());
        assertEquals(2, model.getFilteredPatientList().size());
    }

    @Test
    public void execute_singleKeyword_singlePatientFound() {
        FindPatientByNextOfKinCommand command =
                new FindPatientByNextOfKinCommand(
                        new PatientNokContainsKeywordsPredicate(Collections.singletonList("Jane")));
        CommandResult result = command.execute(model);

        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1),
                result.getFeedbackToUser());
        assertEquals("Alice Pauline", model.getFilteredPatientList().get(0).getName().fullName);
    }

    @Test
    public void execute_multipleKeywords_multiplePatientsFound() {
        FindPatientByNextOfKinCommand command =
                new FindPatientByNextOfKinCommand(
                        new PatientNokContainsKeywordsPredicate(List.of("Jane", "John")));
        CommandResult result = command.execute(model);

        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2),
                result.getFeedbackToUser());
        assertEquals(2, model.getFilteredPatientList().size());
    }

    @Test
    public void execute_caseInsensitiveMatch_success() {
        FindPatientByNextOfKinCommand command =
                new FindPatientByNextOfKinCommand(
                        new PatientNokContainsKeywordsPredicate(Collections.singletonList("jane")));
        CommandResult result = command.execute(model);

        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1),
                result.getFeedbackToUser());
        assertEquals("Alice Pauline", model.getFilteredPatientList().get(0).getName().fullName);
    }
}
