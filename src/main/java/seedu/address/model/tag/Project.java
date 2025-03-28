package seedu.address.model.tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Project in the address book.
 * Project is a type of Tag (Extends Tag).
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Project extends Tag {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline should be in the format 'dd MMM yyyy HHmm'  with the first letter of the month capitalised "
                    + "(e.g 01 Apr 2026 2359)";
    public static final String MESSAGE_PROGRESS_CONSTRAINTS =
            "Progress should be either be 'Complete' or 'Incomplete'";
    public static final String MESSAGE_PAYMENT_CONSTRAINTS =
            "Payment should be either be 'Paid' or 'Unpaid'";

    private final boolean isComplete;
    private final boolean isPaid;
    private final LocalDateTime deadline;

    private Project(String tagName, boolean isComplete, boolean isPaid, LocalDateTime deadline) {
        super(tagName);
        this.isComplete = isComplete;
        this.isPaid = isPaid;
        this.deadline = deadline;
    }

    /**
     * Constructs a {@code Project}.
     *
     * @param tagName A valid tag name.
     * @param isComplete Complete or Incomplete.
     * @param isPaid Paid or Unpaid.
     * @param deadline deadline in dd MMM yyyy HHmm format.
     */
    public Project(String tagName, String isComplete, String isPaid, String deadline) {
        this(tagName,
                isComplete.equals("Complete"),
                isPaid.equals("Paid"),
                LocalDateTime.parse(deadline.trim(), DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")));
    }

    /**
     * Constructs a {@code Project}.
     *
     * @param tagName A valid tag name.
     */
    public Project(String tagName) {
        // Set the deadline to 1 day from creation.
        this(tagName, false, false, LocalDateTime.now().plusDays(1));
    }

    /**
     * Creates and returns a new {@code Project} with updated details.
     */
    public Project createEditedProject(SetStatusDescriptor setStatusDescriptor) {
        boolean newIsComplete = setStatusDescriptor.isComplete().orElse(this.isComplete);
        boolean newIsPaid = setStatusDescriptor.isPaid().orElse(this.isPaid);
        LocalDateTime newDeadline = setStatusDescriptor.deadline().orElse(this.deadline);
        return new Project(this.tagName, newIsComplete, newIsPaid, newDeadline);
    }

    /**
     * Get the completion status of the project, complete or incomplete.
     */
    public String checkIfComplete() {
        return this.isComplete ? "Complete" : "Incomplete";
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
     * Stores the details to set the project with. Each non-empty field value will replace the
     * corresponding field value of the project.
     */
    public record SetStatusDescriptor(Optional<Boolean> isComplete,
                                      Optional<Boolean> isPaid,
                                      Optional<LocalDateTime> deadline) {

        // No-argument constructor creating an "empty" descriptor.
        public SetStatusDescriptor() {
            this(Optional.empty(), Optional.empty(), Optional.empty());
        }

        // Copy constructor.
        public SetStatusDescriptor(SetStatusDescriptor toCopy) {
            this(toCopy.isComplete, toCopy.isPaid, toCopy.deadline);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("progress", isComplete)
                    .add("payment", isPaid)
                    .add("deadline", deadline)
                    .toString();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Project otherProject) {
            return this.tagName.equals(otherProject.tagName);
        }
        return false;
    }

    @Override
    public String toString() {
        return '[' + tagName + " | " + checkIfPaid() + " | " + checkIfComplete()
                + " | Deadline: " + getDeadlineString() + ']';
    }
}
