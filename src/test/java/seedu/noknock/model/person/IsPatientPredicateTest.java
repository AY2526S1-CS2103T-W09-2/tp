package seedu.noknock.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.noknock.testutil.PatientBuilder;

public class IsPatientPredicateTest {

    @Test
    public void equals() {
        IsPatientPredicate first = new IsPatientPredicate();
        IsPatientPredicate second = new IsPatientPredicate();

        // same object -> true
        assertTrue(first.equals(first));

        // same type -> true
        assertTrue(first.equals(second));

        // null -> false
        assertFalse(first.equals(null));

        // different type -> false
        assertFalse(first.equals("not a predicate"));
    }

    @Test
    public void test_patient_returnsTrue() {
        Patient patient = new PatientBuilder().build();
        assertTrue(new IsPatientPredicate().test(patient));
    }

    /*@Test
    public void test_nonPatient_returnsFalse() {
        Patient person = new PatientBuilder().build();
        assertFalse(new IsPatientPredicate().test(person));
    }*/
}
