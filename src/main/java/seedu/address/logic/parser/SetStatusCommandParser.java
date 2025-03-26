package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SetStatusCommand;
import seedu.address.logic.commands.SetStatusCommand.SetStatusDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
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
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT, PREFIX_PAYMENT, PREFIX_DEADLINE, PREFIX_PROGRESS);

        Index index;
        String projectName;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            projectName = argMultimap.getValue(PREFIX_PROJECT).get();
        } catch (NoSuchElementException | ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStatusCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROJECT, PREFIX_PAYMENT, PREFIX_DEADLINE, PREFIX_PROGRESS);

        SetStatusDescriptor setStatusDescriptor = new SetStatusDescriptor();

        if (argMultimap.getValue(PREFIX_PAYMENT).isPresent()) {
            setStatusDescriptor.setPayment(ParserUtil.parsePayment(argMultimap.getValue(PREFIX_PAYMENT).get()));
        }
        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            setStatusDescriptor.setDeadline(ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get()));
        }
        if (argMultimap.getValue(PREFIX_PROGRESS).isPresent()) {
            setStatusDescriptor.setProgress(ParserUtil.parsePayment(argMultimap.getValue(PREFIX_PROGRESS).get()));
        }

        if (!setStatusDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new SetStatusCommand(index, projectName, setStatusDescriptor);
    }
}
