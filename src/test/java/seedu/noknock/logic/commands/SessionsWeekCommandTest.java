package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.noknock.testutil.TypicalPatients.getTypicalAddressBook;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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

public class SessionsWeekCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        Patient p1 = model.getFilteredPatientList().get(0);
        Patient p2 = model.getFilteredPatientList().size() > 1
                ? model.getFilteredPatientList().get(1) : p1;
        Patient p3 = model.getFilteredPatientList().size() > 2
                ? model.getFilteredPatientList().get(2) : p1;
        Patient p4 = model.getFilteredPatientList().size() > 3
                ? model.getFilteredPatientList().get(3) : p1;

        CaringSession sStart = new CaringSessionBuilder().withDate(startOfWeek.toString()).withTime("08:00").build();
        CaringSession sEnd = new CaringSessionBuilder().withDate(endOfWeek.toString()).withTime("17:00").build();
        CaringSession sMid = new CaringSessionBuilder().withDate(startOfWeek.plusDays(2)
                .toString()).withTime("10:00").build();
        CaringSession sBefore = new CaringSessionBuilder().withDate(startOfWeek.minusDays(1)
                .toString()).withTime("09:00").build();
        CaringSession sAfter = new CaringSessionBuilder().withDate(endOfWeek.plusDays(1)
                .toString()).withTime("09:00").build();

        // p1: at start of week
        model.setPatient(p1, p1.withCaringSessionList(Collections.singletonList(sStart)));

        // p2: at end of week
        if (p2 != p1) {
            model.setPatient(p2, p2.withCaringSessionList(Collections.singletonList(sEnd)));
        }

        // p3: mid-week + after week (still included)
        if (p3 != p1 && p3 != p2) {
            model.setPatient(p3, p3.withCaringSessionList(Arrays.asList(sMid, sAfter)));
        }

        // p4: only before week (excluded)
        if (p4 != p1 && p4 != p2 && p4 != p3) {
            model.setPatient(p4, p4.withCaringSessionList(Collections.singletonList(sBefore)));
        }

        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    @Test
    public void execute_filtersPatientsAndSetsSessionFilter_success() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        SessionsWeekCommand command = new SessionsWeekCommand();

        // Expected model: apply the same filters
        expectedModel.setSessionDisplayFilter(new CaringSessionDateInRangePredicate(
                new seedu.noknock.model.date.Date(startOfWeek.toString()),
                new seedu.noknock.model.date.Date(endOfWeek.toString())));
        expectedModel.updateFilteredPatientList(new PatientHasSessionsInDateRangePredicate(
                new seedu.noknock.model.date.Date(startOfWeek.toString()),
                new seedu.noknock.model.date.Date(endOfWeek.toString())));

        String expectedMessage = String.format(SessionsWeekCommand.MESSAGE_WEEK_SESSIONS,
                expectedModel.getFilteredPatientList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify the session filter behavior
        Predicate<CaringSession> sessionFilter = model.getSessionDisplayFilter();
        CaringSession insideA = new CaringSessionBuilder().withDate(startOfWeek.toString()).withTime("07:00").build();
        CaringSession insideB = new CaringSessionBuilder().withDate(endOfWeek.toString()).withTime("23:59").build();
        CaringSession outsideBefore = new CaringSessionBuilder()
                .withDate(startOfWeek.minusDays(1).toString()).withTime("10:00").build();
        CaringSession outsideAfter = new CaringSessionBuilder()
                .withDate(endOfWeek.plusDays(1).toString()).withTime("10:00").build();

        assertTrue(sessionFilter.test(insideA));
        assertTrue(sessionFilter.test(insideB));
        assertFalse(sessionFilter.test(outsideBefore));
        assertFalse(sessionFilter.test(outsideAfter));
    }

    @Test
    public void equals() {
        SessionsWeekCommand c1 = new SessionsWeekCommand();
        SessionsWeekCommand c2 = new SessionsWeekCommand();

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
