package seedu.noknock.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.noknock.logic.commands.AddCaringSessionCommand.MESSAGE_HAS_OVERLAPPING_SESSION;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_CARE_TYPE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.noknock.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.noknock.model.Model.PREDICATE_SHOW_ALL_SESSIONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.noknock.commons.core.index.Index;
import seedu.noknock.commons.util.CollectionUtil;
import seedu.noknock.commons.util.ToStringBuilder;
import seedu.noknock.logic.Messages;
import seedu.noknock.logic.commands.exceptions.CommandException;
import seedu.noknock.model.Model;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.CareType;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.session.Note;
import seedu.noknock.model.session.SessionStatus;

/**
 * Edits the details of a caring session for a patient in the address book.
 */
public class EditCaringSessionCommand extends Command {

    public static final String COMMAND_WORD = "edit-session";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of a caring session for a patient "
        + "identified by the index number used in the displayed patient list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: PATIENT_INDEX (must be a positive integer) "
        + "SESSION_INDEX (must be a positive integer) "
        + "[" + PREFIX_CARE_TYPE + "CARE_TYPE] "
        + "[" + PREFIX_NOTES + "NOTES] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_TIME + "TIME] "
        + "[" + PREFIX_STATUS + "STATUS (must be completed/incomplete (case-insensitive))]\n"
        + "Example: " + COMMAND_WORD + " 1 2 "
        + PREFIX_CARE_TYPE + "Physio "
        + PREFIX_NOTES + "\"Follow up\" "
        + PREFIX_STATUS + "Completed";

    public static final String MESSAGE_EDIT_SESSION_SUCCESS = "Edited CaringSession: %1$s of Patient: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index patientIndex;
    private final Index sessionIndex;
    private final EditSessionDescriptor editSessionDescriptor;

    /**
     * @param patientIndex          of the patient in the filtered patient list to edit the session for
     * @param sessionIndex          of the session in the patient's session list to be edited
     * @param editSessionDescriptor details to edit the session with
     */
    public EditCaringSessionCommand(Index patientIndex, Index sessionIndex,
                                    EditSessionDescriptor editSessionDescriptor) {
        requireNonNull(patientIndex);
        requireNonNull(sessionIndex);
        requireNonNull(editSessionDescriptor);
        this.patientIndex = patientIndex;
        this.sessionIndex = sessionIndex;
        this.editSessionDescriptor = new EditSessionDescriptor(editSessionDescriptor);
    }

    /**
     * Creates and returns a {@code CaringSession} with the details of {@code sessionToEdit}
     * edited with {@code descriptor}.
     */
    private static CaringSession createEditedSession(CaringSession sessionToEdit, EditSessionDescriptor descriptor) {
        assert sessionToEdit != null;

        CareType updatedCareType = descriptor.getCareType().orElse(sessionToEdit.getCareType());
        Note updatedNote = descriptor.getNote().orElse(sessionToEdit.getNote());
        Date updatedDate = descriptor.getDate().orElse(sessionToEdit.getDate());
        Time updatedTime = descriptor.getTime().orElse(sessionToEdit.getTime());
        SessionStatus updatedStatus = descriptor.getStatus().orElse(sessionToEdit.getStatus());

        // Add a constructor to CaringSession that accepts all fields if not present
        return new CaringSession(updatedCareType, updatedNote, updatedDate, updatedTime, updatedStatus);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> patientList = model.getFilteredPatientList();

        if (patientIndex.getZeroBased() >= patientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patient = patientList.get(patientIndex.getZeroBased());
        List<CaringSession> sessionList = patient.getCaringSessionList();

        if (sessionIndex.getZeroBased() >= sessionList.size() || sessionIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_INDEX);
        }

        CaringSession sessionToEdit = sessionList.get(sessionIndex.getZeroBased());
        CaringSession editedSession = createEditedSession(sessionToEdit, editSessionDescriptor);

        if (patient.hasOverlappingSession(editedSession, sessionToEdit)) {
            throw new CommandException(String.format(MESSAGE_HAS_OVERLAPPING_SESSION, editedSession.getCareType()));
        }

        List<CaringSession> updatedSessionList = new ArrayList<>(sessionList);
        updatedSessionList.set(sessionIndex.getZeroBased(), editedSession);

        Patient editedPatient = patient.withCaringSessionList(updatedSessionList);
        model.setPatient(patient, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);
        model.setSessionDisplayFilter(PREDICATE_SHOW_ALL_SESSIONS);

        return new CommandResult(String.format(MESSAGE_EDIT_SESSION_SUCCESS,
            Messages.formatSession(editedSession), Messages.formatPatient(patient)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditCaringSessionCommand otherEditCommand)) {
            return false;
        }
        return patientIndex.equals(otherEditCommand.patientIndex)
            && sessionIndex.equals(otherEditCommand.sessionIndex)
            && editSessionDescriptor.equals(otherEditCommand.editSessionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("patientIndex", patientIndex)
            .add("sessionIndex", sessionIndex)
            .add("editSessionDescriptor", editSessionDescriptor)
            .toString();
    }

    /**
     * Stores the details to edit the session with. Each non-empty field value will replace the
     * corresponding field value of the session.
     */
    public static class EditSessionDescriptor {
        private CareType careType;
        private Note note;
        private Date date;
        private Time time;
        private SessionStatus status;

        public EditSessionDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditSessionDescriptor(EditSessionDescriptor toCopy) {
            setCareType(toCopy.careType);
            setNote(toCopy.note);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setStatus(toCopy.status);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(careType, note, date, time, status);
        }

        public Optional<CareType> getCareType() {
            return Optional.ofNullable(careType);
        }

        public void setCareType(CareType careType) {
            this.careType = careType;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<SessionStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setStatus(SessionStatus status) {
            this.status = status;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof EditSessionDescriptor otherDescriptor)) {
                return false;
            }
            return Objects.equals(careType, otherDescriptor.careType)
                && Objects.equals(note, otherDescriptor.note)
                && Objects.equals(date, otherDescriptor.date)
                && Objects.equals(time, otherDescriptor.time)
                && Objects.equals(status, otherDescriptor.status);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("careType", careType)
                .add("note", note)
                .add("date", date)
                .add("time", time)
                .add("status", status)
                .toString();
        }
    }
}
