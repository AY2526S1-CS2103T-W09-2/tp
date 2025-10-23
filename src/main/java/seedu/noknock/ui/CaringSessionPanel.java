package seedu.noknock.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.noknock.commons.core.LogsCenter;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.PatientCaringSession;

/**
 * Panel containing the flattened list of caring sessions from all patients.
 */
public class CaringSessionPanel extends UiPart<Region> {
    private static final String FXML = "CaringSessionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CaringSessionPanel.class);
    private final ObservableList<PatientCaringSession> flattenedSessions = FXCollections.observableArrayList();
    @FXML
    private ListView<PatientCaringSession> sessionListView;

    /**
     * Creates a {@code CaringSessionPanel} with the given {@code ObservableList} of patients.
     */
    public CaringSessionPanel(ObservableList<Patient> patientList) {
        super(FXML);
        sessionListView.setItems(flattenedSessions);
        sessionListView.setCellFactory(listView -> new CaringSessionListViewCell());

        // Initial build
        rebuildFromPatients(patientList);

        // Rebuild when patient list changes
        patientList.addListener((ListChangeListener<Patient>) change ->
            rebuildFromPatients(patientList)
        );
    }

    private void rebuildFromPatients(ObservableList<Patient> patientList) {
        flattenedSessions.setAll(
            patientList.stream()
                .flatMap(p -> p.getCaringSessionList().stream()
                    .map(session -> new PatientCaringSession(p, session)))
                .collect(Collectors.toList())
        );
    }

    /**
     * Custom {@code ListCell} that displays a {@code PatientCaringSession} as text.
     */
    class CaringSessionListViewCell extends ListCell<PatientCaringSession> {
        @Override
        protected void updateItem(PatientCaringSession pcs, boolean empty) {
            super.updateItem(pcs, empty);

            if (empty || pcs == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CaringSessionCard(pcs, getIndex() + 1).getRoot());
            }
        }
    }
}
