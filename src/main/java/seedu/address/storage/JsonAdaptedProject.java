package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    private final String tagName;

    private String isComplete;
    private String isPaid;
    private String deadline;


    /**
     * Constructs a {@code JsonAdaptedProject} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("tagName") String tagName,
                              @JsonProperty("isComplete") String isComplete,
                              @JsonProperty("isPaid") String isPaid,
                              @JsonProperty("deadline") String deadline) {
        this.tagName = tagName;
        this.isComplete = isComplete;
        this.isPaid = isPaid;
        this.deadline = deadline;
    }

    /**
     * Constructs a {@code JsonAdaptedProject} with the given {@code tagName}.
     */
    public JsonAdaptedProject(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Check given source and label tag type.
     */
    public JsonAdaptedProject(Project source) {
        tagName = source.tagName;
        isComplete = source.checkIfComplete();
        isPaid = source.checkIfPaid();
        deadline = source.getDeadline();

    }


    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public Project toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }


        return new Project(tagName, isComplete, isPaid, deadline);
    }
}
