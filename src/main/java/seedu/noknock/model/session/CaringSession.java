package seedu.noknock.model.session;

import static seedu.noknock.commons.util.CollectionUtil.requireAllNonNull;

import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.person.Patient;

/**
 * Represents a caring session for a patient.
 * Stores the care type, note, date, time, and a reference to the patient.
 */
public final class CaringSession {
    private final Patient patient;
    private final CareType careType;
    private final Note note;
    private final Date date;
    private final Time time;

    /**
     * Constructs a CaringSession.
     *
     * @param patient  the patient receiving care
     * @param careType the type of care provided
     * @param note     additional notes for the session
     * @param date     the date of the session
     * @param time     the time of the session
     */
    public CaringSession(Patient patient, CareType careType, Note note, Date date, Time time) {
        requireAllNonNull(patient, careType, note, date, time);
        this.patient = patient;
        this.careType = careType;
        this.note = note;
        this.date = date;
        this.time = time;
    }

    /**
     * Returns the patient for this session.
     */
    public Patient getPatient() {
        return patient;
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CaringSession otherSession)) {
            return false;
        }
        return patient.equals(otherSession.patient)
            && careType.equals(otherSession.careType)
            && note.equals(otherSession.note)
            && date.equals(otherSession.date)
            && time.equals(otherSession.time);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(patient, careType, note, date, time);
    }

    @Override
    public String toString() {
        return new seedu.noknock.commons.util.ToStringBuilder(this)
            .add("patient", patient)
            .add("careType", careType)
            .add("note", note)
            .add("date", date)
            .add("time", time)
            .toString();
    }
}
