package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.person.PatientHasSessionsInDateRangePredicate;
import seedu.noknock.model.session.CaringSessionDateInRangePredicate;

/**
 * Lists all caring sessions scheduled for today across all patients.
 */
public class SessionsTodayCommand extends Command {

    public static final String COMMAND_WORD = "sessions-today";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all caring sessions that are scheduled today "
            + "across all patients.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_TODAY_SESSIONS =
            "Today's caring sessions: %1$d patients.\n";

    public SessionsTodayCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String todayStr = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        Date today = new Date(todayStr);

        model.setSessionDisplayFilter(CaringSessionDateInRangePredicate.onDate(today));
        model.updateFilteredPatientList(PatientHasSessionsInDateRangePredicate.onDate(today));

        return new CommandResult(
                String.format(MESSAGE_TODAY_SESSIONS, model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof SessionsTodayCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
