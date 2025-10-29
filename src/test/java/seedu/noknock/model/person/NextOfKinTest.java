package seedu.noknock.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.commands.CommandTestUtil.INVALID_PHONE;
import static seedu.noknock.logic.commands.CommandTestUtil.INVALID_PHONE_NON_DIGIT;
import static seedu.noknock.logic.commands.CommandTestUtil.INVALID_RELATIONSHIP;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_NAME_DAUGHTER;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_NAME_GRANDPA;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_PHONE_DAUGHTER;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_PHONE_GRANDPA;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_RELATION_DAUGHTER;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_RELATION_GRANDPA;
import static seedu.noknock.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.noknock.testutil.NextOfKinBuilder;

public class NextOfKinTest {

    @Test
    public void constructor_validFields_success() {
        // Test that a NextOfKin object is correctly created with valid fields
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
    public void constructor_nullName_throwsNullPointerException() {
        // Null name should throw NullPointerException
        assertThrows(NullPointerException.class, () ->
                new NextOfKin(null, new Phone(VALID_PHONE_DAUGHTER), Relationship.DAUGHTER));
    }

    @Test
    public void constructor_nullPhone_throwsNullPointerException() {
        // Null phone should throw NullPointerException
        assertThrows(NullPointerException.class, () ->
                new NextOfKin(new Name(VALID_NAME_DAUGHTER), null, Relationship.DAUGHTER));
    }

    @Test
    public void constructor_nullRelationship_throwsNullPointerException() {
        // Null relationship should throw NullPointerException
        assertThrows(NullPointerException.class, () ->
                new NextOfKin(new Name(VALID_NAME_DAUGHTER), new Phone(VALID_PHONE_DAUGHTER), null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        // Invalid phone numbers (too short or contains non-digit) should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () ->
                new NextOfKin(new Name(VALID_NAME_DAUGHTER), new Phone(INVALID_PHONE), Relationship.DAUGHTER));
        assertThrows(IllegalArgumentException.class, () ->
                new NextOfKin(new Name(VALID_NAME_DAUGHTER),
                        new Phone(INVALID_PHONE_NON_DIGIT), Relationship.DAUGHTER));
    }

    @Test
    public void constructor_invalidRelationship_throwsIllegalArgumentException() {
        // Invalid relationship string should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () ->
                new NextOfKin(new Name(VALID_NAME_DAUGHTER),
                        new Phone(VALID_PHONE_DAUGHTER), Relationship.valueOf(INVALID_RELATIONSHIP)));
    }

    @Test
    public void isSamePerson_sameObject_returnsTrue() {
        // Same object should be recognized as the same person
        NextOfKin nok = new NextOfKinBuilder().build();
        assertTrue(nok.isSamePerson(nok));
    }

    @Test
    public void isSamePerson_null_returnsFalse() {
        // Comparing to null should return false
        NextOfKin nok = new NextOfKinBuilder().build();
        assertFalse(nok.isSamePerson(null));
    }

    @Test
    public void isSamePerson_sameNameDifferentCase_returnsFalse() {
        // Same name but different case should return false
        NextOfKin nok = new NextOfKinBuilder().build();
        NextOfKin edited = new NextOfKinBuilder(nok).withName(VALID_NAME_DAUGHTER.toLowerCase()).build();
        assertFalse(nok.isSamePerson(edited));
    }

    @Test
    public void isSamePerson_allFieldsEqual_returnsTrue() {
        // Two NextOfKin objects with same name, phone, and relationship should return true
        NextOfKin nok = new NextOfKinBuilder().build();
        NextOfKin identicalNok = new NextOfKinBuilder(nok).build();
        assertTrue(nok.isSamePerson(identicalNok));
    }

    @Test
    public void isSamePerson_differentPhone_returnsFalse() {
        // Two NextOfKin objects with same name but different phone should return false
        NextOfKin nok = new NextOfKinBuilder().build();
        NextOfKin edited = new NextOfKinBuilder(nok).withPhone(VALID_PHONE_GRANDPA).build();
        assertFalse(nok.isSamePerson(edited));
    }

    @Test
    public void isSamePerson_differentRelationship_returnsFalse() {
        // Two NextOfKin objects with same name but different relationship should return false
        NextOfKin nok = new NextOfKinBuilder().build();
        NextOfKin edited = new NextOfKinBuilder(nok).withRelationship(VALID_RELATION_GRANDPA).build();
        assertFalse(nok.isSamePerson(edited));
    }

    @Test
    public void isSamePerson_differentName_returnsFalse() {
        // Two NextOfKin objects with different names should return false
        NextOfKin nok = new NextOfKinBuilder().build();
        NextOfKin edited = new NextOfKinBuilder(nok).withName(VALID_NAME_GRANDPA).build();
        assertFalse(nok.isSamePerson(edited));
    }


    @Test
    public void equals_sameObject_returnsTrue() {
        // Same object should be equal
        NextOfKin nok = new NextOfKinBuilder().build();
        assertEquals(nok, nok);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        // Different objects with same values should be equal
        NextOfKin nok1 = new NextOfKinBuilder().build();
        NextOfKin nok2 = new NextOfKinBuilder(nok1).build();
        assertEquals(nok1, nok2);
    }

    @Test
    public void equals_null_returnsFalse() {
        // Comparing to null should return false
        NextOfKin nok = new NextOfKinBuilder().build();
        assertNotEquals(null, nok);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        // Comparing to a different class should return false
        NextOfKin nok = new NextOfKinBuilder().build();
        assertNotEquals(nok, "NotANok");
    }

    @Test
    public void equals_differentName_returnsFalse() {
        // Objects with different names should not be equal
        NextOfKin nok = new NextOfKinBuilder().build();
        NextOfKin edited = new NextOfKinBuilder(nok).withName(VALID_NAME_GRANDPA).build();
        assertNotEquals(nok, edited);
    }

    @Test
    public void equals_differentPhone_returnsFalse() {
        // Objects with different phones should not be equal
        NextOfKin nok = new NextOfKinBuilder().build();
        NextOfKin edited = new NextOfKinBuilder(nok).withPhone(VALID_PHONE_GRANDPA).build();
        assertNotEquals(nok, edited);
    }

    @Test
    public void equals_differentRelationship_returnsFalse() {
        // Objects with different relationships should not be equal
        NextOfKin nok = new NextOfKinBuilder().build();
        NextOfKin edited = new NextOfKinBuilder(nok).withRelationship(VALID_RELATION_GRANDPA).build();
        assertNotEquals(nok, edited);
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        // Objects with same values should have same hash code
        NextOfKin nok1 = new NextOfKinBuilder().build();
        NextOfKin nok2 = new NextOfKinBuilder(nok1).build();
        assertEquals(nok1.hashCode(), nok2.hashCode());
    }

    @Test
    public void toStringMethod() {
        // toString should include all main fields
        NextOfKin nok = new NextOfKinBuilder().withRelationship("Daughter").build();
        String result = nok.toString();
        assertTrue(result.contains("name"));
        assertTrue(result.contains("phone"));
        assertTrue(result.contains("relationship"));
    }
}
