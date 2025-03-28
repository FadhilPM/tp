package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SetStatusCommand;
import seedu.address.logic.commands.SetStatusCommand.SetStatusDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

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
        } catch (NoSuchElementException | ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStatusCommand.MESSAGE_USAGE), e);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROJECT, PREFIX_PAYMENT, PREFIX_DEADLINE, PREFIX_PROGRESS);

        Optional<String> optionalProgressString = argMultimap.getValue(PREFIX_PROGRESS);
        Optional<String> optionalPaymentString = argMultimap.getValue(PREFIX_PAYMENT);
        Optional<String> optionalDeadlineString = argMultimap.getValue(PREFIX_DEADLINE);

        Optional<Boolean> optionalProgress = Optional.empty();
        Optional<Boolean> optionalPayment = Optional.empty();
        Optional<LocalDateTime> optionalDeadline = Optional.empty();


        if (optionalProgressString.isPresent()) {
            optionalProgress = Optional.of(ParserUtil.parseProgress(optionalProgressString.get()));
        }
        if (optionalPaymentString.isPresent()) {
            optionalPayment = Optional.of(ParserUtil.parsePayment(optionalPaymentString.get()));
        }
        if (optionalDeadlineString.isPresent()) {
            optionalDeadline = Optional.of(ParserUtil.parseDeadline(optionalDeadlineString.get()));
        }


        boolean isAnyFieldEdited = optionalProgress.isPresent() || optionalPayment.isPresent()
                || optionalDeadline.isPresent();

        if (!isAnyFieldEdited) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        SetStatusDescriptor setStatusDescriptor = new SetStatusDescriptor(
                optionalProgress,
                optionalPayment,
                optionalDeadline);

        return new SetStatusCommand(index, projectName, setStatusDescriptor);
    }
}
