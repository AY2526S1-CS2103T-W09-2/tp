package seedu.noknock.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;

public class CaringSessionTest {

    private static final CareType VALID_CARE_TYPE = new CareType("medication");
    private static final CareType DIFFERENT_CARE_TYPE = new CareType("physiotherapy");
    private static final Note VALID_NOTE = new Note("Check sugar levels");
    private static final Note DIFFERENT_NOTE = new Note("Monitor vitals");
    private static final Date VALID_DATE = new Date("2025-12-25");
    private static final Date DIFFERENT_DATE = new Date("2025-12-26");
    private static final Time VALID_TIME = new Time("14:30");
    private static final Time DIFFERENT_TIME = new Time("15:00");

    @Test
    public void constructor_nullCareType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new CaringSession(null, VALID_NOTE, VALID_DATE, VALID_TIME));
    }

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new CaringSession(VALID_CARE_TYPE, null, VALID_DATE, VALID_TIME));
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new CaringSession(VALID_CARE_TYPE, VALID_NOTE, null, VALID_TIME));
    }

    @Test
    public void constructor_nullTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, null));
    }

    @Test
    public void constructor_allFieldsProvided_success() {
        CaringSession session = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        assertEquals(VALID_CARE_TYPE, session.getCareType());
        assertEquals(VALID_NOTE, session.getNote());
        assertEquals(VALID_DATE, session.getDate());
        assertEquals(VALID_TIME, session.getTime());
        assertEquals(SessionStatus.INCOMPLETE, session.getStatus());
    }

    @Test
    public void constructor_withStatus_success() {
        CaringSession session = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME,
            SessionStatus.COMPLETED);
        assertEquals(SessionStatus.COMPLETED, session.getStatus());
    }

    @Test
    public void constructor_withStatusNullStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME, null));
    }

    @Test
    public void isComplete_incompleteSession_returnsFalse() {
        CaringSession session = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        assertFalse(session.isComplete());
    }

    @Test
    public void isComplete_completedSession_returnsTrue() {
        CaringSession session = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME,
            SessionStatus.COMPLETED);
        assertTrue(session.isComplete());
    }

    @Test
    public void overlaps_sameDateAndTime_returnsTrue() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        CaringSession session2 = new CaringSession(DIFFERENT_CARE_TYPE, DIFFERENT_NOTE, VALID_DATE, VALID_TIME);

        assertTrue(session1.overlaps(session2));
    }

    @Test
    public void overlaps_sameDateDifferentTime_returnsFalse() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        CaringSession session2 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, DIFFERENT_TIME);

        assertFalse(session1.overlaps(session2));
    }

    @Test
    public void overlaps_differentDateSameTime_returnsFalse() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        CaringSession session2 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, DIFFERENT_DATE, VALID_TIME);

        assertFalse(session1.overlaps(session2));
    }

    @Test
    public void overlaps_differentDateAndTime_returnsFalse() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        CaringSession session2 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, DIFFERENT_DATE, DIFFERENT_TIME);

        assertFalse(session1.overlaps(session2));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        CaringSession session = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        assertEquals(session, session);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME,
            SessionStatus.INCOMPLETE);
        CaringSession session2 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME,
            SessionStatus.INCOMPLETE);
        assertEquals(session1, session2);
    }

    @Test
    public void equals_null_returnsFalse() {
        CaringSession session = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        assertNotEquals(null, session);
    }

    @Test
    public void equals_differentCareType_returnsFalse() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        CaringSession session2 = new CaringSession(DIFFERENT_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        assertNotEquals(session1, session2);
    }

    @Test
    public void equals_differentNote_returnsFalse() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        CaringSession session2 = new CaringSession(VALID_CARE_TYPE, DIFFERENT_NOTE, VALID_DATE, VALID_TIME);
        assertNotEquals(session1, session2);
    }

    @Test
    public void equals_differentDate_returnsFalse() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        CaringSession session2 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, DIFFERENT_DATE, VALID_TIME);
        assertNotEquals(session1, session2);
    }

    @Test
    public void equals_differentTime_returnsFalse() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        CaringSession session2 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, DIFFERENT_TIME);
        assertNotEquals(session1, session2);
    }

    @Test
    public void equals_differentStatus_returnsFalse() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME,
            SessionStatus.INCOMPLETE);
        CaringSession session2 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME,
            SessionStatus.COMPLETED);
        assertNotEquals(session1, session2);
    }

    @Test
    public void toStringMethod() {
        CaringSession session = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME,
            SessionStatus.INCOMPLETE);
        String result = session.toString();
        assertTrue(result.contains("careType"));
        assertTrue(result.contains("note"));
        assertTrue(result.contains("date"));
        assertTrue(result.contains("time"));
        assertTrue(result.contains("status"));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        CaringSession session1 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        CaringSession session2 = new CaringSession(VALID_CARE_TYPE, VALID_NOTE, VALID_DATE, VALID_TIME);
        assertEquals(session1.hashCode(), session2.hashCode());
    }
}
