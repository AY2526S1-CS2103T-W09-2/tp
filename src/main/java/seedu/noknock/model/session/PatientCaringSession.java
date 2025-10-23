package seedu.noknock.model.session;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.noknock.model.person.Patient;

/**
 * Represents the association between a {@link Patient} and a {@link CaringSession}.
 * Immutable.
 */
public class PatientCaringSession {

    private final Patient patient;
    private final CaringSession caringSession;

    /**
     * Constructs a {@code PatientCaringSession} linking the given patient and caring session.
     */
    public PatientCaringSession(Patient patient, CaringSession caringSession) {
        requireNonNull(patient);
        requireNonNull(caringSession);
        this.patient = patient;
        this.caringSession = caringSession;
    }

    /**
     * Returns the patient in this pair.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the caring session in this pair.
     */
    public CaringSession getCaringSession() {
        return caringSession;
    }

    /**
     * Returns true if this pair refers to the same patient (by identity) and same caring session (by equality)
     * as the given patient and session.
     */
    public boolean isSamePair(Patient otherPatient, CaringSession otherSession) {
        if (otherPatient == null || otherSession == null) {
            return false;
        }
        return patient.isSamePerson(otherPatient) && caringSession.equals(otherSession);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PatientCaringSession otherPcs)) {
            return false;
        }
        return patient.equals(otherPcs.patient) && caringSession.equals(otherPcs.caringSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient, caringSession);
    }

    @Override
    public String toString() {
        return new seedu.noknock.commons.util.ToStringBuilder(this)
            .add("patient", patient)
            .add("caringSession", caringSession)
            .toString();
    }
}
