package seedu.noknock.testutil;

import seedu.noknock.model.date.Date;
import seedu.noknock.model.date.Time;
import seedu.noknock.model.session.CareType;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.session.Note;
import seedu.noknock.model.session.SessionStatus;

/**
 * A utility class to help with building CaringSession objects for tests.
 */
public class CaringSessionBuilder {
    public static final String DEFAULT_CARE_TYPE = "Physiotherapy";
    public static final String DEFAULT_NOTE = "Routine check";
    public static final String DEFAULT_DATE = java.time.LocalDate.now().plusDays(1).toString();
    public static final String DEFAULT_TIME = java.time.LocalTime.of(10, 0).toString();
    public static final String DEFAULT_STATUS = "INCOMPLETE";

    private CareType careType;
    private Note note;
    private Date date;
    private Time time;
    private SessionStatus status;

    /**
     * Creates a {@code CaringSessionBuilder} with the default details.
     */
    public CaringSessionBuilder() {
        careType = new CareType(DEFAULT_CARE_TYPE);
        note = new Note(DEFAULT_NOTE);
        date = new Date(DEFAULT_DATE);
        time = new Time(DEFAULT_TIME);
        status = SessionStatus.of(DEFAULT_STATUS);
    }

    /**
     * Initializes the CaringSessionBuilder with the data of {@code sessionToCopy}.
     */
    public CaringSessionBuilder(CaringSession sessionToCopy) {
        careType = sessionToCopy.getCareType();
        note = sessionToCopy.getNote();
        date = sessionToCopy.getDate();
        time = sessionToCopy.getTime();
        status = sessionToCopy.getStatus();
    }

    /**
     * Sets the {@code CareType} of the {@code CaringSession} that we are building.
     */
    public CaringSessionBuilder withCareType(String careType) {
        this.careType = new CareType(careType);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code CaringSession} that we are building.
     */
    public CaringSessionBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code CaringSession} that we are building.
     */
    public CaringSessionBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code CaringSession} that we are building.
     */
    public CaringSessionBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code SessionStatus} of the {@code CaringSession} that we are building.
     */
    public CaringSessionBuilder withStatus(SessionStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Builds the {@code CaringSession} object.
     */
    public CaringSession build() {
        return new CaringSession(careType, note, date, time, status);
    }
}
