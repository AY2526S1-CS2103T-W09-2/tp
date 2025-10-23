package seedu.noknock.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.noknock.model.person.NextOfKin;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.person.Person;
import seedu.noknock.model.session.CaringSession;
import seedu.noknock.model.session.Note;

/**
 * A UI component that displays information of a {@code Patient}.
 */
public class PatientCard extends UiPart<Region> {

    private static final String FXML = "PatientListCard.fxml";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label ic;
    @FXML
    private Label ward;
    @FXML
    private VBox nextOfKins;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox sessions;

    /**
     * Creates a {@code PersonCode} with the given {@code Person}, index to display,
     * and a flag indicating whether sessions should be shown.
     */
    public PatientCard(Person person, int displayedIndex, boolean showSessions) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        if (person instanceof Patient patient) {
            ic.setText(patient.getIC().toString());
            ward.setText(patient.getWard().toString());
            patient.getTags()
                .forEach(tag -> tags.getChildren().add(new Label(tag.toString())));

            // Display NOKs as numbered list
            int nokIndex = 1;
            for (NextOfKin nok : patient.getNextOfKinList()) {
                Label nokLabel = new Label(String.format("%d. %s (%s) - %s",
                    nokIndex++,
                    nok.getName().fullName,
                    nok.getRelationship().toString(),
                    nok.getPhone().toString()));
                nokLabel.setWrapText(true);
                nextOfKins.getChildren().add(nokLabel);
            }

            // Only build and display sessions when allowed (e.g., list size == 1)
            if (showSessions) {
                int sessionIndex = 1;
                for (CaringSession session : patient.getCaringSessionList()) {
                    Note note = session.getNote();
                    HBox sessionContainer = new HBox(0.5);
                    sessionContainer.setAlignment(Pos.TOP_LEFT);

                    // Status indicator
                    Label statusBadge = new Label(session.isComplete() ? "✓" : "✗");
                    statusBadge.setMinWidth(20);

                    // Session text
                    VBox sessionContent = new VBox(2);
                    String mainText = String.format("%d. %s - %s at %s",
                        sessionIndex++,
                        session.getCareType(),
                        session.getDate().printPretty(),
                        session.getTime());

                    Label mainLabel = new Label(mainText);
                    mainLabel.setWrapText(true);
                    sessionContent.getChildren().add(mainLabel);

                    if (!note.value.isEmpty()) {
                        Label noteLabel = new Label("   Notes: " + note);
                        noteLabel.setWrapText(true);
                        noteLabel.getStyleClass().addAll("cell_small_label", "session-note");
                        sessionContent.getChildren().add(noteLabel);
                    }

                    sessionContainer.getChildren().addAll(statusBadge, sessionContent);
                    sessions.getChildren().add(sessionContainer);
                }
            }
        }

        // Hide Next of Kin section if empty
        nextOfKins.getParent().visibleProperty().bind(
            javafx.beans.binding.Bindings.isNotEmpty(nextOfKins.getChildren())
        );
        nextOfKins.getParent().managedProperty().bind(nextOfKins.getParent().visibleProperty());

        // Hide Sessions section if empty (sessions will be empty when showSessions == false)
        sessions.getParent().visibleProperty().bind(
            javafx.beans.binding.Bindings.isNotEmpty(sessions.getChildren())
        );
        sessions.getParent().managedProperty().bind(sessions.getParent().visibleProperty());
    }
}
