package seedu.noknock.model.person;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.session.CaringSession;

/**
 * Matches patients who have at least one session within [startDate, endDate] (inclusive),
 */
public class PatientHasSessionsInDateRangePredicate implements Predicate<Patient> {

    private final Date startDate;
    private final Date endDate;

    /**
     * Creates a predicate that matches patients having at least one session dated within
     * the inclusive range [startDate, endDate]. If {@code startDate} is after {@code endDate},
     * the values are swapped to normalize the range.
     *
     * @param startDate inclusive start date
     * @param endDate inclusive end date
     * @throws NullPointerException if {@code startDate} or {@code endDate} is null
     */
    public PatientHasSessionsInDateRangePredicate(Date startDate, Date endDate) {
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

    /**
     * Factory for a single-day predicate (i.e., matches sessions on {@code date} only).
     *
     * @param date the date to match (inclusive)
     * @return a predicate constrained to the given date
     * @throws NullPointerException if {@code date} is null
     */
    public static PatientHasSessionsInDateRangePredicate onDate(Date date) {
        return new PatientHasSessionsInDateRangePredicate(date, date);
    }

    private boolean inRange(Date d) {
        return d.compareTo(startDate) >= 0 && d.compareTo(endDate) <= 0;
    }

    @Override
    public boolean test(Patient patient) {
        Objects.requireNonNull(patient);
        return patient.getCaringSessionList().stream()
                .map(CaringSession::getDate)
                .anyMatch(this::inRange);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PatientHasSessionsInDateRangePredicate)) {
            return false;
        }

        PatientHasSessionsInDateRangePredicate otherPredicate = (PatientHasSessionsInDateRangePredicate) other;
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
