package seedu.address.model.tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Project in the address book.
 * Project is a type of Tag (Extends Tag).
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Project extends Tag {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline should be in the format dd MMM yyyy HHmm";
    public static final String MESSAGE_PROGRESS_CONSTRAINTS =
            "Progress should be either be 'Complete' or 'Incomplete'";
    public static final String MESSAGE_PAYMENT_CONSTRAINTS =
            "Payment should be either be 'Paid' or 'Unpaid'";

    private boolean isComplete;
    private boolean isPaid;
    private LocalDateTime deadline;

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
        System.out.println(this.deadline);
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
     * Get the deadline as String
     */
    public String getDeadlineString() {
        return this.deadline.format(DateTimeFormatter.ofPattern("dd MMM uuuu HHmm"));
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
