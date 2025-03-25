package seedu.address.logic.commands;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

/**
 * Adds a person to the address book.
 */
public class SetStatusCommand extends Command {

    public static final String COMMAND_WORD = "setstatus";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the status of specified project."
            + "Parameters: "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_PROJECT + "PROJECT "
            + "[" + PREFIX_PAYMENT + "PAYMENT] "
            + "[" + PREFIX_DEADLINE + "DEADLINE] "
            + "[" + PREFIX_PROGRESS + "PROGRESS] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_PROJECT + "PROJECT "
            + PREFIX_PAYMENT + "paid "
            + PREFIX_DEADLINE + "07-09-2025 "
            + PREFIX_PROGRESS + "complete ";

    public static final String MESSAGE_SUCCESS = "Project status is updated";
    public static final String MESSAGE_NUMBER_NOT_PROVIDED = "Phone number of person to edit must be provided.";
    public static final String MESSAGE_PROJECT_NOT_PROVIDED = "Name of project to edit must be provided.";

    //private final Project toSet;

    /**
     * Creates an SetStatusCommand to update the specified {@code Project}
     */
    public SetStatusCommand(SetStatusDescriptor setStatusDescriptor) {
        requireNonNull(setStatusDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /**
     * Stores the details to set the project with. Each non-empty field value will replace the
     * corresponding field value of the project.
     */
    public static class SetStatusDescriptor {
        private boolean isComplete;
        private boolean isPaid;
        private LocalDateTime deadline;

        public SetStatusDescriptor() {}

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(isComplete, isPaid, deadline);
        }

        public void setProgress(boolean isComplete) {
            this.isComplete = isComplete;
        }

        public Optional<Boolean> getProgress() {
            return Optional.of(isComplete);
        }

        public void setPayment(boolean isPaid) {
            this.isPaid = isPaid;
        }

        public Optional<Boolean> getPayment() {
            return Optional.of(isPaid);
        }

        public void setDeadline(LocalDateTime deadline) {
            this.deadline = deadline;
        }

        public Optional<LocalDateTime> getDeadline() {
            return Optional.ofNullable(deadline);
        }


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof SetStatusDescriptor)) {
                return false;
            }

            SetStatusDescriptor otherSetStatusDescriptor = (SetStatusDescriptor) other;
            return Objects.equals(isComplete, otherSetStatusDescriptor.isComplete)
                    && Objects.equals(isPaid, otherSetStatusDescriptor.isPaid)
                    && Objects.equals(deadline, otherSetStatusDescriptor.deadline);
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
}
