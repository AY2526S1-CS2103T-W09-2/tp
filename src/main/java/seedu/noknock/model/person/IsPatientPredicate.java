package seedu.noknock.model.person;

import java.util.function.Predicate;

import seedu.noknock.commons.util.ToStringBuilder;

/**
 * A predicate that tests whether a {@code Person} is an instance of {@code Patient}.
 */
public class IsPatientPredicate implements Predicate<Patient> {

    @Override
    public boolean test(Patient person) {
        return person instanceof Patient;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other instanceof IsPatientPredicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
