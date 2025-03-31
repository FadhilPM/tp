package seedu.address.model.tag;

import seedu.address.commons.util.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Stores the details to set the project with. Each non-empty field value will replace the
 *
 * @param deadline LocalDateTime.
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

    public SetStatusDescriptor setIsComplete(boolean newIsComplete) {
        return new SetStatusDescriptor(Optional.of(newIsComplete), isPaid, deadline);
    }

    public SetStatusDescriptor setIsPaid(boolean newIsPaid) {
        return new SetStatusDescriptor(isComplete, Optional.of(newIsPaid), deadline);
    }

    public SetStatusDescriptor setDeadline(LocalDateTime newDeadline) {
        return new SetStatusDescriptor(isComplete, isPaid, Optional.of(newDeadline));
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
