package seedu.noknock.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.noknock.commons.util.StringUtil;
import seedu.noknock.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} is an instance of {@code Patient} and that their name
 * matches any of the given keywords.
 */
public class PatientNameContainsKeywordsPredicate implements Predicate<Patient> {
    private final List<String> keywords;

    public PatientNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Patient person) {
        if (!(person instanceof Patient)) {
            return false;
        }

        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    /**
     * Compares this predicate with another object for equality.
     * Two predicates are equal if they are of the same class and have the same keywords.
     *
     * @param other The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PatientNameContainsKeywordsPredicate)) {
            return false;
        }

        PatientNameContainsKeywordsPredicate otherPredicate = (PatientNameContainsKeywordsPredicate) other;
        return this.keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
