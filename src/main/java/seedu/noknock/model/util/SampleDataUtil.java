package seedu.noknock.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.noknock.model.AddressBook;
import seedu.noknock.model.ReadOnlyAddressBook;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePersons() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh")),
            new Patient(new Name("Bernice Yu")),
            new Patient(new Name("Charlotte Oliveiro")),
            new Patient(new Name("David Li")),
            new Patient(new Name("Irfan Ibrahim")),
            new Patient(new Name("Roy Balakrishnan"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Patient samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
