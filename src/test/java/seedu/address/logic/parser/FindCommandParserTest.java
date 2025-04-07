package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_phoneKeywords_returnsFindCommandWithPhonePredicate() throws ParseException {
        String input = " p/88888888 66666666 88886666";
        FindCommand command = parser.parse(input);
        assertEquals(new FindCommand(new PhoneContainsKeywordsPredicate(
                Arrays.asList("88888888", "66666666", "88886666"))), command);
    }

    @Test
    public void parse_nameKeywords_returnsFindCommandWithNamePredicate() throws ParseException {
        String input = " n/Alice Bob Charlie";
        FindCommand command = parser.parse(input);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(
                "Alice", "Bob", "Charlie"))), command);
    }

    @Test
    public void parse_mixedPrefixes_throwsParseException() {
        String input = " n/Alice p/88888888";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(Messages.MESSAGE_MULTIPLE_PREFIXES_PROVIDED + "\n" + FindCommand.PREFIX_OPTIONS,
                exception.getMessage());
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" x/Alice"));
        assertThrows(ParseException.class, () -> parser.parse(" e/12345"));
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        // Test with text before any prefix
        String userInput = "someText " + "n/Alice";
        assertThrows(ParseException.class, () -> parser.parse(userInput));

        // Test with just text and no prefix
        String userInput2 = "someText";
        assertThrows(ParseException.class, () -> parser.parse(userInput2));

        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), exception.getMessage());
    }

}
