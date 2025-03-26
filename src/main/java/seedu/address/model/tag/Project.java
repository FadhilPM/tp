package seedu.address.model.tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Project in the address book.
 * Project is a type of Tag (Extends Tag).
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Project extends Tag {

    private boolean isComplete;
    private boolean isPaid;
    private LocalDateTime deadline;

    public static final String MESSAGE_CONSTRAINTS =
            "Deadline should be in the format dd MMM yyyy HHmm";

    /**
     * Constructs a {@code Project}.
     *
     * @param tagName A valid tag name.
     * @param isComplete Complete or Incomplete.
     * @param isPaid Paid or Unpaid.
     * @param deadline deadline in dd MMM yyyy HHmm format.
     */
    public Project(String tagName, String isComplete, String isPaid, String deadline) {
        super(tagName);
        this.isComplete = (isComplete.equals("Complete"));
        this.isPaid = (isPaid.equals("Paid"));
        this.deadline = LocalDateTime.parse(deadline.trim(), DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
    }

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

    public void setProgress(boolean progress) {
        this.isComplete = progress;
    }

    public boolean getProgress() {
        return this.isComplete;
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

    public void setPayment(boolean payment) {
        this.isPaid = payment;
    }

    public boolean getPayment() {
        return this.isPaid;
    }

    /**
     * Set the isPaid attribute to true, to reflect successful payment.
     */
    public void setPaid() {
        this.isPaid = true;
    }

    public void setUnpaid() {
        this.isPaid = false;
    }

    /**
     * Get the deadline as String
     */
    public String getDeadlineString() {
        return this.deadline.format(DateTimeFormatter.ofPattern("d MMM uuuu HHmm"));
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
                + " | Deadline: " + getDeadlineString() + ']';
    }
}
