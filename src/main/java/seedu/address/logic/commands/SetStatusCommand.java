package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Project;

/**
 * Adds a person to the address book.
 */
public class SetStatusCommand extends Command {

    public static final String COMMAND_WORD = "setstatus";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the status of specified project of the "
            + "person identified by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PROJECT + "PROJECT "
            + "[" + PREFIX_PAYMENT + "PAYMENT] "
            + "[" + PREFIX_DEADLINE + "DEADLINE] "
            + "[" + PREFIX_PROGRESS + "PROGRESS] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PROJECT + "PROJECT "
            + PREFIX_PAYMENT + "paid "
            + PREFIX_DEADLINE + "07 Mar 2025 1800"
            + PREFIX_PROGRESS + "complete ";

    public static final String MESSAGE_SUCCESS = "Project status is updated: %1$s";

    private final Index index;
    private final String projectName;
    private final SetStatusDescriptor setStatusDescriptor;

    /**
     * Creates an SetStatusCommand to update the specified {@code Project}
     */
    public SetStatusCommand(Index index, String projectName, SetStatusDescriptor setStatusDescriptor) {
        requireNonNull(index);
        requireNonNull(projectName);
        requireNonNull(setStatusDescriptor);

        this.index = index;
        this.projectName = projectName;
        this.setStatusDescriptor = new SetStatusDescriptor(setStatusDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Project projectToEdit = personToEdit.getProjects()
                .stream()
                .filter(x -> x.getTagName().equals(projectName))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_ABSENT_PROJECT));

        projectToEdit.setPayment(setStatusDescriptor.getPayment().orElse(projectToEdit.getPayment()));
        projectToEdit.setProgress(setStatusDescriptor.getProgress().orElse(projectToEdit.getProgress()));
        projectToEdit.setDeadline(setStatusDescriptor.getDeadline().orElse(projectToEdit.getDeadline()));

        // Create new person with updated project, to replace old person with old project
        // This will ensure a new objectID and allow JavaFX to detect the change and update UI
        Person editedPerson = personToEdit.replaceProject(projectToEdit);
        model.setPerson(personToEdit, editedPerson);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, projectToEdit));
    }

    /**
     * Stores the details to set the project with. Each non-empty field value will replace the
     * corresponding field value of the project.
     */
    public static class SetStatusDescriptor {
        private Boolean isComplete;
        private Boolean isPaid;
        private LocalDateTime deadline;

        public SetStatusDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public SetStatusDescriptor(SetStatusDescriptor toCopy) {
            setProgress(toCopy.isComplete);
            setPayment(toCopy.isPaid);
            setDeadline(toCopy.deadline);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(isComplete, isPaid, deadline);
        }

        public void setProgress(Boolean isComplete) {
            this.isComplete = isComplete;
        }

        public Optional<Boolean> getProgress() {
            return Optional.ofNullable(isComplete);
        }

        public void setPayment(Boolean isPaid) {
            this.isPaid = isPaid;
        }

        public Optional<Boolean> getPayment() {
            return Optional.ofNullable(isPaid);
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
