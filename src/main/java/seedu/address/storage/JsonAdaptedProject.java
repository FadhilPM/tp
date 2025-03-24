package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedProject {

    private final String tagName;

    private String isComplete;
    private String isPaid;
    private String deadline;


    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
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

    @JsonValue
    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Project toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        return new Project(tagName);
    }
}
