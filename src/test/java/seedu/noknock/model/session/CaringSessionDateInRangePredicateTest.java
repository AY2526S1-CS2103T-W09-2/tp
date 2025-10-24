package seedu.noknock.model.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

import seedu.noknock.model.date.Date;
import seedu.noknock.testutil.CaringSessionBuilder;

public class CaringSessionDateInRangePredicateTest {

    @Test
    public void equals() {
        Date startA = new Date("2025-01-01");
        Date endA = new Date("2025-01-07");
        Date startB = new Date("2025-02-01");
        Date endB = new Date("2025-02-07");

        CaringSessionDateInRangePredicate firstPredicate =
                new CaringSessionDateInRangePredicate(startA, endA);
        CaringSessionDateInRangePredicate secondPredicate =
                new CaringSessionDateInRangePredicate(startB, endB);

        // same object -> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> true
        CaringSessionDateInRangePredicate firstPredicateCopy =
                new CaringSessionDateInRangePredicate(startA, endA);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> false
        assertFalse(firstPredicate.equals(secondPredicate));

        // null -> false
        assertFalse(firstPredicate.equals(null));

        // different type -> false
        assertFalse(firstPredicate.equals(5));
    }

    @Test
    public void test_sessionDateWithinRange_returnsTrue() {
        Date start = new Date("2025-03-01");
        Date end = new Date("2025-03-07");
        CaringSessionDateInRangePredicate predicate =
                new CaringSessionDateInRangePredicate(start, end);

        CaringSession atStart = new CaringSessionBuilder().withDate("2025-03-01").withTime("00:01").build();
        CaringSession inMiddle = new CaringSessionBuilder().withDate("2025-03-04").withTime("12:00").build();
        CaringSession atEnd = new CaringSessionBuilder().withDate("2025-03-07").withTime("23:59").build();

        assertTrue(predicate.test(atStart));
        assertTrue(predicate.test(inMiddle));
        assertTrue(predicate.test(atEnd));
    }

    @Test
    public void test_sessionDateOutsideRange_returnsFalse() {
        Date start = new Date("2025-03-01");
        Date end = new Date("2025-03-07");
        CaringSessionDateInRangePredicate predicate =
                new CaringSessionDateInRangePredicate(start, end);

        CaringSession before = new CaringSessionBuilder().withDate("2025-02-28").withTime("10:00").build();
        CaringSession after = new CaringSessionBuilder().withDate("2025-03-08").withTime("10:00").build();

        assertFalse(predicate.test(before));
        assertFalse(predicate.test(after));
    }

    @Test
    public void test_onDate_matchesExactly() {
        Date d = new Date("2025-04-10");
        CaringSessionDateInRangePredicate predicate = CaringSessionDateInRangePredicate.onDate(d);

        CaringSession match = new CaringSessionBuilder().withDate("2025-04-10").withTime("10:00").build();
        CaringSession nonMatch = new CaringSessionBuilder().withDate("2025-04-11").withTime("10:00").build();

        assertTrue(predicate.test(match));
        assertFalse(predicate.test(nonMatch));
    }

    @Test
    public void test_today_factory() {
        LocalDate today = LocalDate.now();
        CaringSessionDateInRangePredicate predicate = CaringSessionDateInRangePredicate.today();

        CaringSession shouldPass = new CaringSessionBuilder().withDate(today.toString()).withTime("08:00").build();
        CaringSession shouldFail = new CaringSessionBuilder().withDate(today.plusDays(1)
                .toString()).withTime("08:00").build();

        assertTrue(predicate.test(shouldPass));
        assertFalse(predicate.test(shouldFail));
    }

    @Test
    public void test_thisWeekMonToSun_factory() {
        LocalDate now = LocalDate.now();
        LocalDate start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(6);

        CaringSessionDateInRangePredicate predicate = CaringSessionDateInRangePredicate.thisWeekMonToSun();

        CaringSession inStart = new CaringSessionBuilder().withDate(start.toString()).withTime("00:01").build();
        CaringSession inEnd = new CaringSessionBuilder().withDate(end.toString()).withTime("23:59").build();
        CaringSession before = new CaringSessionBuilder().withDate(start.minusDays(1)
                .toString()).withTime("12:00").build();
        CaringSession after = new CaringSessionBuilder().withDate(end.plusDays(1).toString()).withTime("12:00").build();

        assertTrue(predicate.test(inStart));
        assertTrue(predicate.test(inEnd));
        assertFalse(predicate.test(before));
        assertFalse(predicate.test(after));
    }
}
