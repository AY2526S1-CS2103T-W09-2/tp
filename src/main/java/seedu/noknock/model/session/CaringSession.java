package seedu.noknock.model.session;

import static seedu.noknock.commons.util.CollectionUtil.requireAllNonNull;

import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;

/**
 * Represents a caring session for a patient.
 * Stores the care type, note, date, time, and status of the session.
 */
public final class CaringSession {
    private final CareType careType;
    private final Note note;
    private final Date date;
    private final Time time;
    private final SessionStatus status;

    /**
     * Constructs a CaringSession with status set to INCOMPLETE.
     *
     * @param careType the type of care provided
     * @param note     additional notes for the session
     * @param date     the date of the session
     * @param time     the time of the session
     */
    public CaringSession(CareType careType, Note note, Date date, Time time) {
        requireAllNonNull(careType, note, date, time);
        this.careType = careType;
        this.note = note;
        this.date = date;
        this.time = time;
        this.status = SessionStatus.INCOMPLETE;
    }

    /**
     * Constructs a CaringSession with all fields specified.
     * Only used for immutably editing an existing session.
     *
     * @param careType the type of care provided
     * @param note     additional notes for the session
     * @param date     the date of the session
     * @param time     the time of the session
     * @param status   the status of the session
     */
    public CaringSession(CareType careType, Note note, Date date, Time time, SessionStatus status) {
        requireAllNonNull(careType, note, date, time, status);
        this.careType = careType;
        this.note = note;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    /**
     * Returns the care type for this session.
     */
    public CareType getCareType() {
        return careType;
    }

    /**
     * Returns the note for this session.
     */
    public Note getNote() {
        return note;
    }

    /**
     * Returns the date of this session.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the time of this session.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns the status of this session.
     */
    public SessionStatus getStatus() {
        return status;
    }

    /**
     * Returns true if the session is marked as complete.
     */
    public boolean isComplete() {
        return status == SessionStatus.COMPLETED;
    }

    /**
     * Checks if this caring session overlaps with another session.
     * Overlap is defined as having the same date, time, and care type.
     *
     * @param other The other caring session to compare with.
     * @return True if the sessions overlap, false otherwise.
     */
    public boolean overlaps(CaringSession other) {
        return this.date.equals(other.date)
            && this.time.equals(other.time);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CaringSession otherSession)) {
            return false;
        }
        return careType.equals(otherSession.careType)
            && note.equals(otherSession.note)
            && date.equals(otherSession.date)
            && time.equals(otherSession.time)
            && status == otherSession.status;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(careType, note, date, time, status);
    }

    @Override
    public String toString() {
        return new seedu.noknock.commons.util.ToStringBuilder(this)
            .add("careType", getCareType())
            .add("note", getNote())
            .add("date", getDate())
            .add("time", getTime())
            .add("status", getStatus())
            .toString();
    }
}
