package seedu.noknock.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_NAME_DAUGHTER;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_NAME_GRANDPA;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_PHONE_DAUGHTER;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_PHONE_GRANDPA;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_RELATION_DAUGHTER;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_RELATION_GRANDPA;

import org.junit.jupiter.api.Test;

import seedu.noknock.testutil.NextOfKinBuilder;

public class NextOfKinTest {

    @Test
    public void constructor_validFields_success() {
        // valid fields -> object is created correctly
        NextOfKin nok = new NextOfKinBuilder()
                .withName(VALID_NAME_DAUGHTER)
                .withPhone(VALID_PHONE_DAUGHTER)
                .withRelationship(VALID_RELATION_DAUGHTER)
                .build();
        assertEquals(VALID_NAME_DAUGHTER, nok.getName().fullName);
        assertEquals(VALID_PHONE_DAUGHTER, nok.getPhone().value);
        assertEquals(VALID_RELATION_DAUGHTER, nok.getRelationship().toString());
    }

    @Test
    public void isSamePerson() {
        NextOfKin nok = new NextOfKinBuilder().build();

        // same object -> returns true
        assertTrue(nok.isSamePerson(nok));

        // null -> returns false
        assertFalse(nok.isSamePerson(null));

        // same name, but different phone -> returns false
        NextOfKin editedNok = new NextOfKinBuilder(nok)
                .withPhone(VALID_PHONE_GRANDPA)
                .build();
        assertFalse(nok.isSamePerson(editedNok));

        // same name, but different relationship -> returns false
        editedNok = new NextOfKinBuilder(nok)
                .withRelationship(VALID_RELATION_GRANDPA)
                .build();
        assertFalse(nok.isSamePerson(editedNok));

        // completely same fields -> returns true
        NextOfKin copy = new NextOfKinBuilder(nok).build();
        assertTrue(nok.isSamePerson(copy));

        // name differs in case -> returns false
        editedNok = new NextOfKinBuilder(nok)
                .withName(nok.getName().fullName.toLowerCase())
                .build();
        assertFalse(nok.isSamePerson(editedNok));

        // name has trailing spaces -> returns false
        String nameWithTrailingSpaces = nok.getName().fullName + " ";
        editedNok = new NextOfKinBuilder(nok)
                .withName(nameWithTrailingSpaces)
                .build();
        assertFalse(nok.isSamePerson(editedNok));
    }


    @Test
    public void equals() {
        NextOfKin nok = new NextOfKinBuilder().build();

        // same values -> returns true
        NextOfKin copy = new NextOfKinBuilder(nok).build();
        assertTrue(nok.equals(copy));

        // same object -> returns true
        assertTrue(nok.equals(nok));

        // null -> returns false
        assertFalse(nok.equals(null));

        // different type -> returns false
        assertFalse(nok.equals("NotANok"));

        // different name -> returns false
        NextOfKin differentName = new NextOfKinBuilder(nok).withName(VALID_NAME_GRANDPA).build();
        assertFalse(nok.equals(differentName));

        // different phone -> returns false
        NextOfKin differentPhone = new NextOfKinBuilder(nok).withPhone(VALID_PHONE_GRANDPA).build();
        assertFalse(nok.equals(differentPhone));

        // different relationship -> returns false
        NextOfKin differentRelation = new NextOfKinBuilder(nok).withRelationship(VALID_RELATION_GRANDPA).build();
        assertFalse(nok.equals(differentRelation));
    }

    @Test
    public void hashCode_sameValues_sameHash() {
        // same values -> same hash code
        NextOfKin nok = new NextOfKinBuilder().build();
        NextOfKin copy = new NextOfKinBuilder(nok).build();
        assertEquals(nok.hashCode(), copy.hashCode());
    }

    @Test
    public void toStringMethod() {
        NextOfKin nok = new NextOfKinBuilder().withRelationship("Daughter").build();
        String expected = NextOfKin.class.getCanonicalName()
                + "{name=" + nok.getName()
                + ", phone=" + nok.getPhone()
                + ", relationship=" + nok.getRelationship() + "}";
        assertEquals(expected, nok.toString());
    }
}
