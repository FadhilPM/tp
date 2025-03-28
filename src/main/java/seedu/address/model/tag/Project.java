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
            "Deadline should be in the format 'dd MMM uuuu HHmm'  with the first letter of the month capitalised "
                    + "(e.g 01 Apr 2026 2359)";
    public static final String MESSAGE_PROGRESS_CONSTRAINTS =
            "Progress should be either be 'Complete' or 'Incomplete'";
    public static final String MESSAGE_PAYMENT_CONSTRAINTS =
            "Payment should be either be 'Paid' or 'Unpaid'";

    public static final String DATETIME_FORMAT = "dd MMM uuuu HHmm";

    private boolean isComplete;
    private boolean isPaid;
    private LocalDateTime deadline;

    /**
     * Constructs a {@code Project}.
     *
     * @param tagName A valid tag name.
     * @param isComplete Complete or Incomplete.
     * @param isPaid Paid or Unpaid.
     * @param deadline deadline in dd MMM uuuu HHmm format.
     */
    public Project(String tagName, String isComplete, String isPaid, String deadline) {
        super(tagName);
        this.isComplete = (isComplete.equalsIgnoreCase("complete"));
        this.isPaid = (isPaid.equalsIgnoreCase("paid"));
        this.deadline = dateTimeStringtoLDT(deadline);
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
     * Converts String to LocalDateTime based on DATETIME_FORMAT
     * @param dateTime String representation of datetime
     */
    public static LocalDateTime dateTimeStringtoLDT(String dateTime) {
        return LocalDateTime.parse(dateTime.trim(), DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    }

    /**
     * Get the progress status as a String
     * 'Complete' if true, 'Incomplete' if false
     */
    public String getProgressString() {
        return this.isComplete ? "Complete" : "Incomplete";
    }

    /**
     * Get the progress status as boolean value
     */
    public boolean getProgress() {
        return this.isComplete;
    }

    /**
     * Set the progress status isComplete as true or false.
     * @param progress progress status.
     */
    public void setProgress(boolean progress) {
        this.isComplete = progress;
    }

    /**
     * Get the payment status as a String.
     * Returns 'Paid' if true, 'Unpaid' if false.
     */
    public String getPaymentString() {
        return this.isPaid ? "Paid" : "Unpaid";
    }

    /**
     * Get the payment status as a boolean value.
     */
    public boolean getPayment() {
        return this.isPaid;
    }

    /**
     * Set the payment status isPaid as true or false.
     * @param payment payment status.
     */
    public void setPayment(boolean payment) {
        this.isPaid = payment;
    }

    /**
     * Get the deadline as String.
     */
    public String getDeadlineString() {
        String deadline = this.deadline.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        return String.format(deadline + "H");
    }

    /**
     * Get the deadline as LocalDateTime.
     */
    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    /**
     * Set deadline attribute.
     * @param deadline LocalDateTime.
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns a String representation of the Project
     */
    @Override
    public String toString() {
        return '[' + getTagName() + " | Deadline: " + getDeadlineString() + " | " + getProgressString()
                + " | " + getPaymentString() + ']';
    }
}
