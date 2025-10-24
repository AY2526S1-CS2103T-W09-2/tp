package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.person.PatientHasSessionsInDateRangePredicate;
import seedu.noknock.model.session.CaringSessionDateInRangePredicate;

/**
 * Lists all caring sessions scheduled for the current week (Monday–Sunday) across all patients.
 */
public class SessionsWeekCommand extends Command {

    public static final String COMMAND_WORD = "sessions-week";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all caring sessions that are scheduled this "
            + "week (Monday to Sunday) across all patients.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_WEEK_SESSIONS =
            "This week's caring sessions: %1$d patients.\n";

    public SessionsWeekCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        String startStr = startOfWeek.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String endStr = endOfWeek.format(DateTimeFormatter.ISO_LOCAL_DATE);

        Date startDate = new Date(startStr);
        Date endDate = new Date(endStr);

        model.setSessionDisplayFilter(new CaringSessionDateInRangePredicate(startDate, endDate));
        model.updateFilteredPatientList(new PatientHasSessionsInDateRangePredicate(startDate, endDate));

        return new CommandResult(String.format(MESSAGE_WEEK_SESSIONS, model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof SessionsWeekCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
