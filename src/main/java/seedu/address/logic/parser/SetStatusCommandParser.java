package seedu.address.logic.parser;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SetStatusCommand;
import seedu.address.logic.commands.SetStatusCommand.SetStatusDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class SetStatusCommandParser implements Parser<SetStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetStatusCommand
     * and returns an SetStatusCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SetStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_PROJECT, PREFIX_PAYMENT, PREFIX_DEADLINE, PREFIX_PROGRESS);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE, PREFIX_PROJECT, PREFIX_PAYMENT, PREFIX_DEADLINE, PREFIX_PROGRESS);

        SetStatusDescriptor setStatusDescriptor = new SetStatusDescriptor();

        if (argMultimap.getValue(PREFIX_PAYMENT).isPresent()) {
            setStatusDescriptor.setPayment(argMultimap.getValue(PREFIX_PAYMENT).get());
        }
        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            setStatusDescriptor.setDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
        }
        if (argMultimap.getValue(PREFIX_PROGRESS).isPresent()) {
            setStatusDescriptor.setProgress(argMultimap.getValue(PREFIX_PAYMENT).get());
        }

        if (!setStatusDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new SetStatusCommand(setStatusDescriptor);
    }

}
