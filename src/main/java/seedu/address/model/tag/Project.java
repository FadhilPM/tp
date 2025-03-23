package seedu.address.model.tag;

import java.time.LocalDateTime;

/**
 * Represents a Project in the address book.
 * Project is a type of Tag (Extends Tag).
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Project extends Tag {

    private boolean isComplete;
    private boolean isPaid;
    private LocalDateTime deadline;

    /**
     * Constructs a {@code Project}.
     *
     * @param tagName A valid tag name.
     */
    public Project(String tagName) {
        super(tagName);
        this.isComplete = false;
        this.isPaid = false;
        this.deadline = LocalDateTime.now().plusDays(1); // Set the deadline to 1 day from creation.
    }


    /**
     * Get the completion status of the project, complete or incomplete.
     */
    public String checkIfComplete() {
        return this.isComplete ? "Complete" : "Incomplete";
    }

    /**
     * Set isComplete to true, denoting that the project is complete.
     */
    public void setComplete() {
        this.isComplete = true;
    }

    /**
     * Set isComplete to false, denoting that the project is not complete.
     */
    public void setNotComplete() {
        this.isComplete = false;
    }

    /**
     * Returns Paid if project has isPaid attribute of true, returns Unpaid otherwise.
     */
    public String checkIfPaid() {
        return this.isPaid ? "Paid" : "Unpaid";
    }

    /**
     * Set the isPaid attribute to true, to reflect successful payment.
     */
    public void pay() {
        this.isPaid = true;
    }

    /**
     * Get the deadline as LocalDateTime
     */
    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    /**
     * Set deadline attribute.
     * @param deadline LocalDateTime
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return '[' + tagName + " | " + checkIfPaid() + " | " + checkIfComplete()
                + " | Deadline: " + getDeadline() + ']';
    }
}
