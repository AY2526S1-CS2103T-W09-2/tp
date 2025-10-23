package seedu.noknock.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.noknock.commons.core.LogsCenter;
import seedu.noknock.model.date.Date;
import seedu.noknock.model.person.Patient;
import seedu.noknock.model.session.PatientCaringSession;

/**
 * Panel containing the flattened list of caring sessions from all patients,
 * grouped by date with a date header row.
 */
public class CaringSessionPanel extends UiPart<Region> {
    private static final String FXML = "CaringSessionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CaringSessionPanel.class);

    private final ObservableList<Object> flattenedItems = FXCollections.observableArrayList();

    @FXML
    private ListView<Object> sessionListView;

    /**
     * Creates a {@code CaringSessionPanel} with the given {@code ObservableList<Patient>}.
     */
    public CaringSessionPanel(ObservableList<Patient> patientList) {
        super(FXML);
        sessionListView.setItems(flattenedItems);
        sessionListView.setCellFactory(listView -> new GroupedCaringSessionCell());

        rebuildFromPatients(patientList);

        patientList.addListener((ListChangeListener<Patient>) change ->
            rebuildFromPatients(patientList)
        );
    }

    /**
     * Rebuilds the flattened, grouped list.
     */
    private void rebuildFromPatients(ObservableList<Patient> patientList) {
        // Collect and sort all sessions in ascending order (least recent first)
        List<PatientCaringSession> sorted = patientList.stream()
            .flatMap(p -> p.getCaringSessionList().stream().map(s -> new PatientCaringSession(p, s)))
            .sorted(Comparator.comparing((PatientCaringSession pcs) -> pcs.getCaringSession().getDate().value)
                .thenComparing(pcs -> pcs.getCaringSession().getTime().value))
            .toList();

        // Group by date preserving order
        Map<Date, List<PatientCaringSession>> groupedMap = sorted.stream()
            .collect(Collectors.groupingBy(
                pcs -> pcs.getCaringSession().getDate(),
                LinkedHashMap::new,
                Collectors.toList()
            ));

        // Build flattened list with date headers
        List<Object> grouped = new ArrayList<>();
        for (Map.Entry<Date, List<PatientCaringSession>> entry : groupedMap.entrySet()) {
            grouped.add(new DateHeader(entry.getKey()));
            grouped.addAll(entry.getValue());
        }

        flattenedItems.setAll(grouped);
    }

    /**
     * Simple header object used to render date rows.
     */
    private record DateHeader(Date date) {
    }

    /**
     * Custom ListCell that renders DateHeader or PatientCaringSession.
     */
    static class GroupedCaringSessionCell extends ListCell<Object> {
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                return;
            }

            if (item instanceof DateHeader header) {
                Label label = new Label(header.date.printPretty());
                label.getStyleClass().add("session-date-header");
                setGraphic(label);
                setText(null);
            } else if (item instanceof PatientCaringSession pcs) {
                int idx = (int) getListView().getItems().subList(0, getIndex()).stream()
                    .filter(o -> o instanceof PatientCaringSession)
                    .count();
                int displayIndex = idx + 1;

                setGraphic(new CaringSessionCard(pcs, displayIndex).getRoot());
                setText(null);
            } else {
                setText(item.toString());
                setGraphic(null);
            }
        }
    }
}
