package seedu.noknock.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.noknock.storage.JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.noknock.testutil.Assert.assertThrows;
import static seedu.noknock.testutil.TypicalPatients.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.noknock.commons.exceptions.IllegalValueException;
import seedu.noknock.model.person.IC;
import seedu.noknock.model.person.Name;
import seedu.noknock.model.person.Ward;

public class JsonAdaptedPatientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_WARD = "+651234";
    private static final String INVALID_IC = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_WARD = BENSON.getWard().toString();
    private static final String VALID_IC = BENSON.getIC().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
        .map(JsonAdaptedTag::new)
        .collect(Collectors.toList());
    private static final List<JsonAdaptedNextOfKin> VALID_NOKS = BENSON.getNextOfKinList().stream()
        .map(JsonAdaptedNextOfKin::new)
        .collect(Collectors.toList());
    private static final List<JsonAdaptedCaringSession> VALID_SESSIONS = BENSON.getCaringSessionList().stream()
        .map(JsonAdaptedCaringSession::new)
        .collect(Collectors.toList());
    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(BENSON);
        assertEquals(BENSON, patient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
            new JsonAdaptedPatient(INVALID_NAME, VALID_WARD, VALID_IC, VALID_TAGS, VALID_NOKS, VALID_SESSIONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(null, VALID_WARD, VALID_IC, VALID_TAGS,
                VALID_NOKS, VALID_SESSIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidWard_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
            new JsonAdaptedPatient(VALID_NAME, INVALID_WARD, VALID_IC, VALID_TAGS, VALID_NOKS, VALID_SESSIONS);
        String expectedMessage = Ward.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullWard_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, null, VALID_IC, VALID_TAGS,
                VALID_NOKS, VALID_SESSIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Ward.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidIC_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
            new JsonAdaptedPatient(VALID_NAME, VALID_WARD, INVALID_IC, VALID_TAGS, VALID_NOKS, VALID_SESSIONS);
        String expectedMessage = IC.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullIC_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_WARD, null, VALID_TAGS, VALID_NOKS,
                VALID_SESSIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IC.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPatient patient =
            new JsonAdaptedPatient(VALID_NAME, VALID_WARD, VALID_IC, invalidTags, VALID_NOKS, VALID_SESSIONS);
        assertThrows(IllegalValueException.class, patient::toModelType);
    }
}
