package seedu.noknock.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.noknock.commons.util.StringUtil;
import seedu.noknock.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Patient}'s next-of-kin names contain any of the keywords given.
 */
public class PatientNokContainsKeywordsPredicate implements Predicate<Patient> {
    private final List<String> keywords;

    public PatientNokContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Patient patient) {
        // If no keywords, consider it a match (i.e., list all patients)
        if (keywords.isEmpty()) {
            return true;
        }

        return patient.getNextOfKinList().stream()
                .anyMatch(nok -> keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(nok.getName().fullName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PatientNokContainsKeywordsPredicate)) {
            return false;
        }

        PatientNokContainsKeywordsPredicate otherPredicate = (PatientNokContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
