package seedu.noknock.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_IC_AMY;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_IC_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_WARD_AMY;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_WARD_BOB;
import static seedu.noknock.testutil.Assert.assertThrows;
import static seedu.noknock.testutil.TypicalPatients.ALICE;
import static seedu.noknock.testutil.TypicalPatients.AMY;
import static seedu.noknock.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import seedu.noknock.testutil.PatientBuilder;

public class PatientTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Patient person = new PatientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(AMY.isSamePerson(AMY));

        // null -> returns false
        assertFalse(AMY.isSamePerson(null));

        // same IC, all other attributes different -> returns true
        Person editedAmy = new PatientBuilder(BOB)
                .withWard(VALID_WARD_AMY).withIC(VALID_IC_AMY)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(AMY.isSamePerson(editedAmy));

        // different ic, all other attributes same -> returns false
        editedAmy = new PatientBuilder(AMY).withIC(VALID_IC_BOB).build();
        assertFalse(AMY.isSamePerson(editedAmy));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PatientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PatientBuilder(ALICE).withWard(VALID_WARD_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PatientBuilder(ALICE).withIC(VALID_IC_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PatientBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Patient.class.getCanonicalName() + "{name=" + ALICE.getName() + ", ward=" + ALICE.getWard()
                + ", ic=" + ALICE.getIC() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
