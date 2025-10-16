package seedu.noknock.testutil;

import static seedu.noknock.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_WARD;

import java.util.Set;

import seedu.noknock.logic.commands.AddPatientCommand;
import seedu.noknock.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.tag.Tag;


/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddPatientCommand(Patient person) {
        return AddPatientCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }
    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Patient person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_WARD + person.getWard().toString() + " ");
        sb.append(PREFIX_IC + person.getIC().toString() + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getWard().ifPresent(ward -> sb.append(PREFIX_WARD).append(ward).append(" "));
        descriptor.getIC().ifPresent(ic -> sb.append(PREFIX_IC).append(ic).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
