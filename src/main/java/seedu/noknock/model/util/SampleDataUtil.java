package seedu.noknock.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.noknock.model.AddressBook;
import seedu.noknock.model.ReadOnlyAddressBook;
import seedu.noknock.model.person.IC;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.Ward;
import seedu.noknock.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Ward("2A"), new IC("S1234567A"),
                getTagSet("VIP", "Diabetic")),
            new Patient(new Name("Bernice Yu"), new Ward("3B"), new IC("T1234567B"),
                getTagSet("Allergic")),
            new Patient(new Name("Charlotte Oliveiro"), new Ward("2A"), new IC("S8654256A"),
                getTagSet("PostOp", "Dementia")),
            new Patient(new Name("David Li"), new Ward("4C"), new IC("S3124567A"),
                getTagSet("Isolation")),
            new Patient(new Name("Irfan Ibrahim"), new Ward("1B"), new IC("S1235767A"),
                getTagSet("Critical")),
            new Patient(new Name("Roy Balakrishnan"), new Ward("2B"), new IC("S3534657A"),
                new HashSet<>())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Patient samplePatient : getSamplePatients()) {
            sampleAb.addPatient(samplePatient);
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
