package seedu.address.logic.parser;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.Storage;

/**
 * Validates and parse input(s) to SaveCommand.
 */
public class SaveCommandParser implements Parser<SaveCommand> {
    private final Storage storage;

    public SaveCommandParser(Storage storage) {
        this.storage = storage;
    }

    /**
     * @param args file name
     * @return execute writing file to local storage.
     * @throws ParseException ensure that no user argument is invalid.
     */
    public SaveCommand parse(String args) throws ParseException {
        String trimArgs = args.trim();
        if (trimArgs.isEmpty()) {
            return new SaveCommand(storage);
        }

        if (!trimArgs.matches("[a-zA-Z0-9-_]+")) {
            throw new ParseException("Input contains invalid characters. Only alphanumeric characters are allowed.");
        }

        return new SaveCommand(trimArgs, storage);
    }
}
