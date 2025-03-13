package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes a person from the address book.\n"
        + "Option 1: Delete by index number\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1\n"
        + "Option 2: Delete by phone number\n"
        + "Parameters: " + PREFIX_PHONE + "PHONE\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_PHONE + "98765432";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
