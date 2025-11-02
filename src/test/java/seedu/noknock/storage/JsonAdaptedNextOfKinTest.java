package seedu.noknock.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.noknock.commons.exceptions.IllegalValueException;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Phone;
import seedu.noknock.model.person.Relationship;

public class JsonAdaptedNextOfKinTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_PHONE = "91234567";
    private static final String VALID_RELATIONSHIP = "SPOUSE";

    private static final String INVALID_NAME = "";
    private static final String INVALID_PHONE = "abc";
    private static final String INVALID_RELATIONSHIP = "INVALID_REL";

    @Test
    public void toModelType_validFields_success() throws Exception {
        NextOfKin original = new NextOfKin(
            new Name(VALID_NAME),
            new Phone(VALID_PHONE),
            Relationship.of(VALID_RELATIONSHIP));

        JsonAdaptedNextOfKin adapted = new JsonAdaptedNextOfKin(original);
        NextOfKin model = adapted.toModelType();
        assertEquals(original, model);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedNextOfKin adapted = new JsonAdaptedNextOfKin(
            INVALID_NAME, VALID_PHONE, VALID_RELATIONSHIP);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedNextOfKin adapted = new JsonAdaptedNextOfKin(
            VALID_NAME, INVALID_PHONE, VALID_RELATIONSHIP);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidRelationship_throwsIllegalValueException() {
        JsonAdaptedNextOfKin adapted = new JsonAdaptedNextOfKin(
            VALID_NAME, VALID_PHONE, INVALID_RELATIONSHIP);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedNextOfKin adapted = new JsonAdaptedNextOfKin(
            null, VALID_PHONE, VALID_RELATIONSHIP);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedNextOfKin adapted = new JsonAdaptedNextOfKin(
            VALID_NAME, null, VALID_RELATIONSHIP);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_nullRelationship_throwsIllegalValueException() {
        JsonAdaptedNextOfKin adapted = new JsonAdaptedNextOfKin(
            VALID_NAME, VALID_PHONE, null);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }
}
