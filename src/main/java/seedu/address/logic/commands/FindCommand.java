package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String PREFIX_OPTIONS = "Accepted Prefixes:\n" + " - n/ (Search by name)\n"
            + "  p/ (Search by phone number)\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all contacts whose names or phone numbers, "
            + "contain any of the keywords (case-insensitive). "
            + "Parameters: n/NAME OR p/PHONE \n"
            + "Example 1: " + COMMAND_WORD + " n/alice bob charlie \n"
            + "Example 2: " + COMMAND_WORD + " p/87438807 88888888";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof FindCommand otherFindCommand) {
            return predicate.equals(otherFindCommand.predicate);
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
