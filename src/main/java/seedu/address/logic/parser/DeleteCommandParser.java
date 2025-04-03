package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INDEX_OR_PHONE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteByIndexCommand;
import seedu.address.logic.commands.DeleteByPhoneCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE);
        String preamble = argMultimap.getPreamble().trim();
        Optional<String> phoneNumber = argMultimap.getValue(PREFIX_PHONE);

        boolean preambleIsPresent = !preamble.isEmpty();
        boolean phoneNumberIsPresent = phoneNumber.isPresent();

        if (preambleIsPresent && phoneNumberIsPresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_INDEX_OR_PHONE + "\n" + DeleteCommand.MESSAGE_USAGE));
        }

        try {
            if (phoneNumber.isPresent()) {
                Phone phone = ParserUtil.parsePhone(phoneNumber.get());
                return new DeleteByPhoneCommand(phone);
            } else {
                Index index = ParserUtil.parseIndex(preamble);
                return new DeleteByIndexCommand(index);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, pe.getMessage() + "\n" + DeleteCommand.MESSAGE_USAGE),
                    pe
            );
        }
    }
}
