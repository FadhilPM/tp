package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

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
    public void parse_allNumericKeywords_returnsFindCommandWithPhonePredicate() throws ParseException {
        String input = "123 456 789";
        FindCommand command = parser.parse(input);
        assertEquals(new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("123", "456", "789"))), command);
    }

    @Test
    public void parse_allAlphabeticKeywords_returnsFindCommandWithNamePredicate() throws ParseException {
        String input = "Alice Bob Charlie";
        FindCommand command = parser.parse(input);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(
                "Alice", "Bob", "Charlie"))), command);
    }

    @Test
    public void parse_mixedKeywords_throwsParseException() {
        String input = "Alice 123 Bob";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), exception.getMessage());
    }
}
