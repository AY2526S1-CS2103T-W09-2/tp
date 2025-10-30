package seedu.noknock.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.noknock.testutil.NextOfKinBuilder;
import seedu.noknock.testutil.PatientBuilder;

public class PatientNokContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("John");
        List<String> secondPredicateKeywordList = Arrays.asList("John", "Jane");

        PatientNokContainsKeywordsPredicate firstPredicate =
            new PatientNokContainsKeywordsPredicate(firstPredicateKeywordList);
        PatientNokContainsKeywordsPredicate secondPredicate =
            new PatientNokContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        PatientNokContainsKeywordsPredicate firstPredicateCopy =
            new PatientNokContainsKeywordsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different keywords -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_nokContainsKeywords_returnsTrue() {
        // One keyword
        PatientNokContainsKeywordsPredicate predicate =
            new PatientNokContainsKeywordsPredicate(Collections.singletonList("John"));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice").withNextOfKinList(
            new NextOfKinBuilder().withName("John Doe").build()).build()));

        // Multiple keywords
        predicate = new PatientNokContainsKeywordsPredicate(Arrays.asList("John", "Jane"));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice").withNextOfKinList(
            new NextOfKinBuilder().withName("John Doe").build()).build()));

        // Only one matching keyword
        predicate = new PatientNokContainsKeywordsPredicate(Arrays.asList("Jane", "Carol"));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice").withNextOfKinList(
            new NextOfKinBuilder().withName("Carol Smith").build()).build()));

        // Mixed-case keywords
        predicate = new PatientNokContainsKeywordsPredicate(Arrays.asList("jOHN", "jAnE"));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice").withNextOfKinList(
            new NextOfKinBuilder().withName("John Doe").build()).build()));

        // Multiple NOKs, one matches
        predicate = new PatientNokContainsKeywordsPredicate(Collections.singletonList("Jane"));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice").withNextOfKinList(
            new NextOfKinBuilder().withName("John Doe").build(),
            new NextOfKinBuilder().withName("Jane Smith").build()).build()));
    }

    @Test
    public void test_nokDoesNotContainKeywords_returnsFalse() {
        // Zero keywords (should list all patients)
        PatientNokContainsKeywordsPredicate predicate =
            new PatientNokContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PatientBuilder().withName("Alice").withNextOfKinList(
            new NextOfKinBuilder().withName("John Doe").build()).build()));

        // Non-matching keyword
        predicate = new PatientNokContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PatientBuilder().withName("Alice").withNextOfKinList(
            new NextOfKinBuilder().withName("John Doe").build()).build()));

        // Patient with no NOKs
        predicate = new PatientNokContainsKeywordsPredicate(Arrays.asList("John"));
        assertFalse(predicate.test(new PatientBuilder().withName("Alice").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PatientNokContainsKeywordsPredicate predicate = new PatientNokContainsKeywordsPredicate(keywords);

        String expected = PatientNokContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
