package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
    private Label email;
    @FXML
    private Label preferredContactMethod;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane projects;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().map(email -> email.value).orElseGet(() -> "No email provided."));
        preferredContactMethod.setText(
                "Preferred method of contact: " + person.getPreferredContactMethod().preferredContactMethod);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getProjects().stream()
                .sorted(Comparator.comparing(project -> project.tagName))
                //.forEach(project -> projects.getChildren().add(new Label(project.tagName)));
                .forEach(project -> {
                    HBox projectBox = new HBox(5);
                    projectBox.setStyle("-fx-background-color:#cccccc; -fx-padding:5; -fx-background-radius:3;");

                    Label nameLabel = new Label(project.tagName);
                    nameLabel.setStyle("-fx-font-weight:bold;");

                    Label deadlineLabel = new Label(project.getDeadline());

                    Label completionLabel = new Label(project.checkIfComplete());
                    if (project.checkIfComplete().equalsIgnoreCase("Complete")) {
                        completionLabel.getStyleClass().add("project-complete");
                    } else {
                        completionLabel.getStyleClass().add("project-incomplete");
                    }

                    Label paidLabel = new Label(project.checkIfPaid());
                    if (project.checkIfPaid().equalsIgnoreCase("Paid")) {
                        paidLabel.getStyleClass().add("project-paid");
                    } else {
                        paidLabel.getStyleClass().add("project-unpaid");
                    }

                    projectBox.getChildren().addAll(nameLabel, deadlineLabel,  completionLabel, paidLabel);
                    projects.getChildren().add(projectBox);
                });

    }
}
