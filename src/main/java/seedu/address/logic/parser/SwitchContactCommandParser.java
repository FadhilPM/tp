package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.address.logic.commands.SwitchContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new {@code SwitchContactCommand} object.
 */
public class SwitchContactCommandParser implements Parser<SwitchContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code SwitchContactCommand}
     * and returns a {@code SwitchContactCommand} object for execution.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public SwitchContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE);
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());

        return new SwitchContactCommand(phone);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
