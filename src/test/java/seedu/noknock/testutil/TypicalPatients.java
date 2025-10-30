package seedu.noknock.testutil;

import static seedu.noknock.logic.commands.CommandTestUtil.VALID_IC_AMY;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_IC_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_WARD_AMY;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_WARD_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.noknock.model.AddressBook;
import seedu.noknock.model.person.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withWard("2A").withIC("S1234568A")
            .withTags("friends").build();
    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withWard("2A").withIC("T9876543B")
            .withTags("owesMoney", "friends").build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz")
            .withWard("2A").withIC("S9535256C").build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier").withWard("2A")
            .withIC("T8765253D").withTags("friends").build();
    public static final Patient ELLE = new PatientBuilder().withName("Elle Meyer").withWard("2A")
            .withIC("S9482224E").build();
    public static final Patient FIONA = new PatientBuilder().withName("Fiona Kunz").withWard("2A")
            .withIC("T9482427F").build();
    public static final Patient GEORGE = new PatientBuilder().withName("George Best").withWard("2A")
            .withIC("S9482442G").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withName("Hoon Meier").withWard("2A")
            .withIC("S1234568H").build();
    public static final Patient IDA = new PatientBuilder().withName("Ida Mueller").withWard("2A")
            .withIC("S1234569I").build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withName(VALID_NAME_AMY)
            .withWard(VALID_WARD_AMY).withIC(VALID_IC_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Patient BOB = new PatientBuilder().withName(VALID_NAME_BOB)
            .withWard(VALID_WARD_BOB).withIC(VALID_IC_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical patients.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Patient person : getTypicalPersons()) {
            ab.addPatient(person);
        }
        return ab;
    }

    public static List<Patient> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
