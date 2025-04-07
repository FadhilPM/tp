package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Optional<String> optName = argMultimap.getValue(PREFIX_NAME);
        Optional<String> optPhone = argMultimap.getValue(PREFIX_PHONE);

        if (optName.isPresent() && optPhone.isPresent()) { //different types of prefixes present
            throw new ParseException(Messages.MESSAGE_MULTIPLE_PREFIXES_PROVIDED + "\n" + FindCommand.PREFIX_OPTIONS);
        } else if (optPhone.isPresent()) {

            String trimmedPhone = optPhone.get().trim();
            if (trimmedPhone.isEmpty()) {
                throw new ParseException("Phone number must not be empty.");
            }

            String[] keywords = optPhone.get().split("\\s+");
            return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (optName.isPresent()) {

            String trimmedName = optName.get().trim();
            if (trimmedName.isEmpty()) {
                throw new ParseException("Name must not be empty.");
            }

            String[] keywords = optName.get().split("\\s+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
