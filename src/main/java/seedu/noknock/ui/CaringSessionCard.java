package seedu.noknock.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.noknock.model.session.PatientCaringSession;

/**
 * A UI component that displays information of a {@code PatientCaringSession}.
 */
public class CaringSessionCard extends UiPart<Region> {

    private static final String FXML = "CaringSessionListCard.fxml";

    public final PatientCaringSession session;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label careType;
    @FXML
    private Label patientName;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private FontAwesomeIconView statusIcon;
    @FXML
    private Label statusMessage;
    @FXML
    private Label notes;
    @FXML
    private VBox notesContainer;

    /**
     * Creates a {@code CaringSessionListCard} with the given {@code PatientCaringSession} and index to display.
     */
    public CaringSessionCard(PatientCaringSession session, int displayedIndex) {
        super(FXML);
        this.session = session;

        id.setText(displayedIndex + ". ");
        careType.setText(session.getCaringSession().getCareType().value);

        patientName.setText(session.getPatient().getName().fullName);
        date.setText(session.getCaringSession().getDate().printPretty());
        time.setText(session.getCaringSession().getTime().toString());

        // Status field
        if (session.getCaringSession().isComplete()) {
            statusIcon.setGlyphName("CHECK_CIRCLE");
            statusIcon.getStyleClass().add("status-complete");
            statusMessage.setText("Complete");
        } else {
            statusIcon.setGlyphName("TIMES_CIRCLE");
            statusIcon.getStyleClass().add("status-incomplete");
            statusMessage.setText("Incomplete");
        }

        // Handle notes
        String noteText = session.getCaringSession().getNote().value;
        if (noteText.isEmpty()) {
            notesContainer.setVisible(false);
            notesContainer.setManaged(false);
        } else {
            notes.setText(noteText);
        }
    }
}
