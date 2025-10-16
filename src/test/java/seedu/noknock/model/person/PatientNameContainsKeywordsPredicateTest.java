package seedu.noknock.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.noknock.testutil.PatientBuilder;

public class PatientNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        PatientNameContainsKeywordsPredicate firstPredicate =
                new PatientNameContainsKeywordsPredicate(Collections.singletonList("first"));
        PatientNameContainsKeywordsPredicate secondPredicate =
                new PatientNameContainsKeywordsPredicate(Collections.singletonList("second"));

        // same object -> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> true
        PatientNameContainsKeywordsPredicate firstPredicateCopy =
                new PatientNameContainsKeywordsPredicate(Collections.singletonList("first"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different values -> false
        assertFalse(firstPredicate.equals(secondPredicate));

        // null -> false
        assertFalse(firstPredicate.equals(null));

        // different type -> false
        assertFalse(firstPredicate.equals(5));
    }

    @Test
    public void test_patientNameContainsKeywords_returnsTrue() {
        Patient patient = new PatientBuilder().withName("Alice Bob").build();

        // One keyword
        PatientNameContainsKeywordsPredicate predicate =
                new PatientNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(patient));

        // Multiple keywords
        predicate = new PatientNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PatientBuilder().withName("Bob Carol").build()));

        // Mixed-case keyword
        predicate = new PatientNameContainsKeywordsPredicate(Collections.singletonList("aLiCe"));
        assertTrue(predicate.test(patient));
    }

    /*@Test
    public void test_nonPatient_returnsFalse() {
        Person person = new PersonBuilder().withName("Alice Bob").build();
        PatientNameContainsKeywordsPredicate predicate =
                new PatientNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertFalse(predicate.test(person));
    }*/

    @Test
    public void test_emptyKeywords_patientMatches() {
        Patient patient = new PatientBuilder().withName("Alice Bob").build();
        PatientNameContainsKeywordsPredicate predicate =
                new PatientNameContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(patient));
    }

    @Test
    public void test_patientNameDoesNotContainKeywords_returnsFalse() {
        PatientNameContainsKeywordsPredicate predicate =
                new PatientNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PatientBuilder().withName("Alice Bob").build()));
    }
}
