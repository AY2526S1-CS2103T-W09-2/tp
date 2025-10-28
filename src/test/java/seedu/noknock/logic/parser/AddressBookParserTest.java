package seedu.noknock.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.noknock.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.noknock.testutil.Assert.assertThrows;
import static seedu.noknock.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.noknock.logic.commands.AddCaringSessionCommand;
import seedu.noknock.logic.commands.AddNextOfKinCommand;
import seedu.noknock.logic.commands.AddPatientCommand;
import seedu.noknock.logic.commands.ClearCommand;
import seedu.noknock.logic.commands.DeleteCaringSessionCommand;
import seedu.noknock.logic.commands.DeleteCommand;
import seedu.noknock.logic.commands.DeleteNextOfKinCommand;
import seedu.noknock.logic.commands.DeletePatientCommand;
import seedu.noknock.logic.commands.EditCaringSessionCommand;
import seedu.noknock.logic.commands.EditNextOfKinCommand;
import seedu.noknock.logic.commands.EditPatientCommand;
import seedu.noknock.logic.commands.ExitCommand;
import seedu.noknock.logic.commands.FindCommand;
import seedu.noknock.logic.commands.FindPatientByNextOfKinCommand;
import seedu.noknock.logic.commands.FindPatientCommand;
import seedu.noknock.logic.commands.HelpCommand;
import seedu.noknock.logic.commands.ListCommand;
import seedu.noknock.logic.commands.ListPatientsCommand;
import seedu.noknock.logic.parser.exceptions.ParseException;
import seedu.noknock.model.person.NameContainsKeywordsPredicate;
import seedu.noknock.model.person.Patient;
import seedu.noknock.testutil.PatientBuilder;
import seedu.noknock.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Patient person = new PatientBuilder().build();
        AddPatientCommand command = (AddPatientCommand) parser.parseCommand(PersonUtil.getAddPatientCommand(person));
        assertEquals(new AddPatientCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_addCaringSession() throws Exception {
        String userInput = AddCaringSessionCommand.COMMAND_WORD + " 1 d/2025-12-25 time/14:30 type/medication";
        assertTrue(parser.parseCommand(userInput) instanceof AddCaringSessionCommand);
    }

    @Test
    public void parseCommand_addNextOfKin() throws Exception {
        String userInput = AddNextOfKinCommand.COMMAND_WORD + " 1 n/John Doe p/98765432 r/son";
        assertTrue(parser.parseCommand(userInput) instanceof AddNextOfKinCommand);
    }

    @Test
    public void parseCommand_editCaringSession() throws Exception {
        String userInput = EditCaringSessionCommand.COMMAND_WORD + " 1 1 type/consultation";
        assertTrue(parser.parseCommand(userInput) instanceof EditCaringSessionCommand);
    }

    @Test
    public void parseCommand_editPatient() throws Exception {
        String userInput = EditPatientCommand.COMMAND_WORD + " 1 n/John Doe";
        assertTrue(parser.parseCommand(userInput) instanceof EditPatientCommand);
    }

    @Test
    public void parseCommand_editNextOfKin() throws Exception {
        String userInput = EditNextOfKinCommand.COMMAND_WORD + " 1 1 n/Jane Doe";
        assertTrue(parser.parseCommand(userInput) instanceof EditNextOfKinCommand);
    }

    @Test
    public void parseCommand_deleteCaringSession() throws Exception {
        String userInput = DeleteCaringSessionCommand.COMMAND_WORD + " 1 1";
        assertTrue(parser.parseCommand(userInput) instanceof DeleteCaringSessionCommand);
    }

    @Test
    public void parseCommand_deletePatient() throws Exception {
        String userInput = DeletePatientCommand.COMMAND_WORD + " 1";
        assertTrue(parser.parseCommand(userInput) instanceof DeletePatientCommand);
    }

    @Test
    public void parseCommand_deleteNextOfKin() throws Exception {
        String userInput = DeleteNextOfKinCommand.COMMAND_WORD + " 1 1";
        assertTrue(parser.parseCommand(userInput) instanceof DeleteNextOfKinCommand);
    }

    @Test
    public void parseCommand_findPatientByNextOfKin() throws Exception {
        String userInput = FindPatientByNextOfKinCommand.COMMAND_WORD + " John";
        assertTrue(parser.parseCommand(userInput) instanceof FindPatientByNextOfKinCommand);
    }

    @Test
    public void parseCommand_findPatient() throws Exception {
        String userInput = FindPatientCommand.COMMAND_WORD + " John";
        assertTrue(parser.parseCommand(userInput) instanceof FindPatientCommand);
    }

    @Test
    public void parseCommand_listPatients() throws Exception {
        assertTrue(parser.parseCommand(ListPatientsCommand.COMMAND_WORD) instanceof ListPatientsCommand);
        assertTrue(parser.parseCommand(ListPatientsCommand.COMMAND_WORD + " 3") instanceof ListPatientsCommand);
    }
}
