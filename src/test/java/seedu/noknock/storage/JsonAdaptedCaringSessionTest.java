package seedu.noknock.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.noknock.commons.exceptions.IllegalValueException;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.session.CareType;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.session.Note;

public class JsonAdaptedCaringSessionTest {

    private static final String VALID_DATE = "2026-10-26";
    private static final String VALID_TIME = "09:00";
    private static final String VALID_CARE_TYPE = "FEEDING";
    private static final String VALID_NOTE = "Fed patient twice";
    private static final String VALID_STATUS = "INCOMPLETE";

    private static final String INVALID_DATE = "2024-99-99";
    private static final String INVALID_TIME = "25:00";
    private static final String INVALID_CARE_TYPE = "";
    private static final String INVALID_NOTE = "\n";
    private static final String INVALID_STATUS = "";

    @Test
    public void toModelType_validFields_success() throws Exception {
        CaringSession original = new CaringSession(
            new CareType(VALID_CARE_TYPE),
            new Note(VALID_NOTE),
            new Date(VALID_DATE),
            new Time(VALID_TIME));

        JsonAdaptedCaringSession adapted = new JsonAdaptedCaringSession(original);
        CaringSession model = adapted.toModelType();
        assertEquals(original, model);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedCaringSession adapted = new JsonAdaptedCaringSession(
            INVALID_DATE, VALID_TIME, VALID_CARE_TYPE, VALID_NOTE, VALID_STATUS);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedCaringSession adapted = new JsonAdaptedCaringSession(
            VALID_DATE, INVALID_TIME, VALID_CARE_TYPE, VALID_NOTE, VALID_STATUS);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidCareType_throwsIllegalValueException() {
        JsonAdaptedCaringSession adapted = new JsonAdaptedCaringSession(
            VALID_DATE, VALID_TIME, INVALID_CARE_TYPE, VALID_NOTE, VALID_STATUS);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedCaringSession adapted = new JsonAdaptedCaringSession(
            VALID_DATE, VALID_TIME, VALID_CARE_TYPE, INVALID_NOTE, VALID_STATUS);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedCaringSession adapted = new JsonAdaptedCaringSession(
            VALID_DATE, VALID_TIME, VALID_CARE_TYPE, VALID_NOTE, INVALID_STATUS);
        assertThrows(IllegalValueException.class, adapted::toModelType);
    }
}
