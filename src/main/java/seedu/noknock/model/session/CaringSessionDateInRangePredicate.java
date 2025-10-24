package seedu.noknock.model.session;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.model.date.Date;

/**
 * Filters {@code CaringSession} by an inclusive date range [startDate, endDate].
 */
public class CaringSessionDateInRangePredicate implements Predicate<CaringSession> {
    private final Date startDate;
    private final Date endDate;

    /**
     * Creates a predicate that matches sessions between startDate and endDate (inclusive).
     * If startDate is after endDate, the dates are swapped so the range is always valid.
     */
    public CaringSessionDateInRangePredicate(Date startDate, Date endDate) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        if (startDate.compareTo(endDate) <= 0) {
            this.startDate = startDate;
            this.endDate = endDate;
        } else {
            this.startDate = endDate;
            this.endDate = startDate;
        }
    }

    /** Matches exactly on the given date. */
    public static CaringSessionDateInRangePredicate onDate(Date date) {
        return new CaringSessionDateInRangePredicate(date, date);
    }

    /** Matches sessions scheduled today (based on system date). */
    public static CaringSessionDateInRangePredicate today() {
        Date today = new Date(LocalDate.now().toString()); // ISO yyyy-MM-dd
        return onDate(today);
    }

    /** Matches sessions scheduled this week (Monday–Sunday, based on system date). */
    public static CaringSessionDateInRangePredicate thisWeekMonToSun() {
        LocalDate now = LocalDate.now();
        LocalDate start = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(6);
        return new CaringSessionDateInRangePredicate(new Date(start.toString()), new Date(end.toString()));
    }

    @Override
    public boolean test(CaringSession session) {
        Objects.requireNonNull(session);
        Date d = session.getDate();
        return d.compareTo(startDate) >= 0 && d.compareTo(endDate) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CaringSessionDateInRangePredicate)) {
            return false;
        }

        CaringSessionDateInRangePredicate otherPredicate = (CaringSessionDateInRangePredicate) other;
        return startDate.equals(otherPredicate.startDate) && endDate.equals(otherPredicate.endDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .toString();
    }
}
