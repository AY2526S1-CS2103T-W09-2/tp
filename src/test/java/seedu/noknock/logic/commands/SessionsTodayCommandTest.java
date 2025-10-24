package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.noknock.testutil.TypicalPatients.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.noknock.model.AddressBook;
import seedu.noknock.model.Model;
import seedu.noknock.model.ModelManager;
import seedu.noknock.model.UserPrefs;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.PatientHasSessionsInDateRangePredicate;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.session.CaringSessionDateInRangePredicate;
import seedu.noknock.testutil.CaringSessionBuilder;

public class SessionsTodayCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Give the first three patients specific sessions
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        Patient p1 = model.getFilteredPatientList().get(0);
        Patient p2 = model.getFilteredPatientList().size() > 1
                ? model.getFilteredPatientList().get(1) : p1;
        Patient p3 = model.getFilteredPatientList().size() > 2
                ? model.getFilteredPatientList().get(2) : p1;

        CaringSession sTodayMorning = new CaringSessionBuilder().withDate(today.toString()).withTime("08:00").build();
        CaringSession sTomorrow = new CaringSessionBuilder().withDate(tomorrow.toString()).withTime("09:00").build();
        CaringSession sTodayNoon = new CaringSessionBuilder().withDate(today.toString()).withTime("12:00").build();

        // p1: has today session
        model.setPatient(p1, p1.withCaringSessionList(Collections.singletonList(sTodayMorning)));

        // p2: only tomorrow's session (should not be included in today)
        if (p2 != p1) {
            model.setPatient(p2, p2.withCaringSessionList(Collections.singletonList(sTomorrow)));
        }

        // p3: has today + tomorrow (patient should be included)
        if (p3 != p1 && p3 != p2) {
            model.setPatient(p3, p3.withCaringSessionList(Arrays.asList(sTodayNoon, sTomorrow)));
        }

        // Clone model for expected
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    @Test
    public void execute_filtersPatientsAndSetsSessionFilter_success() {
        LocalDate today = LocalDate.now();

        SessionsTodayCommand command = new SessionsTodayCommand();

        // expected model: apply the same filters
        expectedModel.setSessionDisplayFilter(CaringSessionDateInRangePredicate.onDate(
                new seedu.noknock.model.date.Date(today.format(DateTimeFormatter.ISO_LOCAL_DATE))));
        expectedModel.updateFilteredPatientList(PatientHasSessionsInDateRangePredicate.onDate(
                new seedu.noknock.model.date.Date(today.format(DateTimeFormatter.ISO_LOCAL_DATE))));

        String expectedMessage = String.format(SessionsTodayCommand.MESSAGE_TODAY_SESSIONS,
                expectedModel.getFilteredPatientList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify the session filter behavior
        Predicate<CaringSession> sessionFilter = model.getSessionDisplayFilter();
        CaringSession shouldPass = new CaringSessionBuilder().withDate(today.toString()).withTime("10:00").build();
        CaringSession shouldFail = new CaringSessionBuilder().withDate(today.plusDays(1)
                .toString()).withTime("10:00").build();

        assertTrue(sessionFilter.test(shouldPass));
        assertFalse(sessionFilter.test(shouldFail));
    }

    @Test
    public void equals() {
        SessionsTodayCommand c1 = new SessionsTodayCommand();
        SessionsTodayCommand c2 = new SessionsTodayCommand();

        // same object -> true
        assertTrue(c1.equals(c1));
        // different objects, same type -> true (equals is type-based)
        assertTrue(c1.equals(c2));
        // null -> false
        assertFalse(c1.equals(null));
        // different type -> false
        assertFalse(c1.equals(new ClearCommand()));
    }
}
