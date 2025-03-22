package seedu.address.model.tag;

import java.time.LocalDateTime;

public class Project extends Tag {


    private CompletionStatus completionStatus;
    private boolean paymentStatus;
    private LocalDateTime deadline;
    public enum CompletionStatus {
        COMPLETE,
        IN_PROGRESS,
        NOT_STARTED
    }
    public Project(String tagName, LocalDateTime deadline) {
        super(tagName);
        this.paymentStatus = false;
        this.deadline = deadline;
        this.completionStatus = CompletionStatus.NOT_STARTED;
    }

    /**
     * Returns true if tag has tagPaid attribute of true, returns false otherwise
     */
    public boolean isPaid() {
        return this.paymentStatus;
    }

    /**
     * Set the tagPaid attribute to true, to reflect successful payment
     */
    public void pay() {
        this.paymentStatus = true;
    }

    /**
     * Set the completion status of the project to COMPLETE
     */
    public void setComplete() {
        this.completionStatus = CompletionStatus.COMPLETE;
    }
}
