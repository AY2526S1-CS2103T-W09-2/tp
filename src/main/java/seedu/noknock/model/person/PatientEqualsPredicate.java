package seedu.noknock.model.person;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.noknock.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Patient} is equal to a target {@code Patient}.
 */
public class PatientEqualsPredicate implements Predicate<Patient> {
    private final Patient target;

    public PatientEqualsPredicate(Patient target) {
        this.target = Objects.requireNonNull(target);
    }

    @Override
    public boolean test(Patient patient) {
        return target.equals(patient);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PatientEqualsPredicate otherPredicate)) {
            return false;
        }
        return target.equals(otherPredicate.target);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("target", target).toString();
    }
}
