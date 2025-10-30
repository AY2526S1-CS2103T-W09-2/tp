package seedu.noknock.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.noknock.model.date.Date;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.testutil.CaringSessionBuilder;
import seedu.noknock.testutil.PatientBuilder;

public class PatientHasSessionsInDateRangePredicateTest {

    @Test
    public void equals() {
        Date startA = new Date("2025-01-01");
        Date endA = new Date("2025-01-07");
        Date startB = new Date("2025-02-01");
        Date endB = new Date("2025-02-07");

        PatientHasSessionsInDateRangePredicate firstPredicate =
                new PatientHasSessionsInDateRangePredicate(startA, endA);
        PatientHasSessionsInDateRangePredicate secondPredicate =
                new PatientHasSessionsInDateRangePredicate(startB, endB);

        // same object -> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> true
        PatientHasSessionsInDateRangePredicate firstPredicateCopy =
                new PatientHasSessionsInDateRangePredicate(startA, endA);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> false
        assertFalse(firstPredicate.equals(secondPredicate));

        // null -> false
        assertFalse(firstPredicate.equals(null));

        // different type -> false
        assertFalse(firstPredicate.equals(5));
    }

    @Test
    public void test_patientHasAtLeastOneSessionInRange_returnsTrue() {
        Date start = new Date("2025-03-01");
        Date end = new Date("2025-03-07");
        PatientHasSessionsInDateRangePredicate predicate =
                new PatientHasSessionsInDateRangePredicate(start, end);

        CaringSession inRange = new CaringSessionBuilder()
                .withDate("2025-03-03").withTime("10:00").build();
        CaringSession outOfRange = new CaringSessionBuilder()
                .withDate("2025-02-28").withTime("09:00").build();

        var patient = new PatientBuilder().withName("Alice").build()
                .withCaringSessionList(Arrays.asList(outOfRange, inRange));

        assertTrue(predicate.test(patient));
    }

    @Test
    public void test_patientSessionsOnBoundsInclusive_returnsTrue() {
        Date start = new Date("2025-03-01");
        Date end = new Date("2025-03-07");
        PatientHasSessionsInDateRangePredicate predicate =
                new PatientHasSessionsInDateRangePredicate(start, end);

        CaringSession atStart = new CaringSessionBuilder()
                .withDate("2025-03-01").withTime("08:00").build();
        CaringSession atEnd = new CaringSessionBuilder()
                .withDate("2025-03-07").withTime("23:59").build();

        var patient1 = new PatientBuilder().withName("Bob").build()
                .withCaringSessionList(Collections.singletonList(atStart));
        var patient2 = new PatientBuilder().withName("Carol").build()
                .withCaringSessionList(Collections.singletonList(atEnd));

        assertTrue(predicate.test(patient1));
        assertTrue(predicate.test(patient2));
    }

    @Test
    public void test_patientOnlyOutOfRangeSessions_returnsFalse() {
        Date start = new Date("2025-03-01");
        Date end = new Date("2025-03-07");
        PatientHasSessionsInDateRangePredicate predicate =
                new PatientHasSessionsInDateRangePredicate(start, end);

        CaringSession before = new CaringSessionBuilder()
                .withDate("2025-02-28").withTime("10:00").build();
        CaringSession after = new CaringSessionBuilder()
                .withDate("2025-03-08").withTime("10:00").build();

        var patient = new PatientBuilder().withName("Dave").build()
                .withCaringSessionList(Arrays.asList(before, after));

        assertFalse(predicate.test(patient));
    }

    @Test
    public void test_onDate_returnsExpected() {
        LocalDate today = LocalDate.now();
        var predicate = PatientHasSessionsInDateRangePredicate.onDate(new Date(today.toString()));

        CaringSession todaySession = new CaringSessionBuilder()
                .withDate(today.toString()).withTime("09:00").build();
        CaringSession otherDay = new CaringSessionBuilder()
                .withDate(today.plusDays(1).toString()).withTime("09:00").build();

        var p1 = new PatientBuilder().withName("Eve").build()
                .withCaringSessionList(Collections.singletonList(todaySession));
        var p2 = new PatientBuilder().withName("Finn").build()
                .withCaringSessionList(Collections.singletonList(otherDay));

        assertTrue(predicate.test(p1));
        assertFalse(predicate.test(p2));
    }

    @Test
    public void test_patientHasNoSessions_returnsFalse() {
        Date start = new Date("2025-03-01");
        Date end = new Date("2025-03-07");
        PatientHasSessionsInDateRangePredicate predicate =
                new PatientHasSessionsInDateRangePredicate(start, end);

        var patient = new PatientBuilder().withName("Gina").build();
        // Assuming PatientBuilder defaults to no sessions

        assertFalse(predicate.test(patient));
    }
}
