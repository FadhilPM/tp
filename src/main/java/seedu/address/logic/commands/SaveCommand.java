package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Write file to the local storage.
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String SUCCESS = "Saved to %s";

    private final Optional<String> fileName;
    private final Storage storage;

    /**
     * @param storage allow specification of file path
     */
    public SaveCommand(Storage storage) {
        this.fileName = Optional.empty();
        this.storage = requireNonNull(storage);
    }

    /**
     * @param fileName custom file name to save as
     * @param storage allow specification of file path
     */
    public SaveCommand(String fileName, Storage storage) {
        this.fileName = Optional.of(fileName);
        this.storage = requireNonNull(storage);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Path path = fileName.map(x -> {
            String customFileName = x + ".json";
            Path newPath = Paths.get("data", customFileName);
            return newPath;
        }).orElseGet(() -> model.getAddressBookFilePath());
        try {
            storage.saveAddressBook(model.getAddressBook(), path);
            return new CommandResult(String.format(SUCCESS, path));
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException e) {
            throw new CommandException(String.format(LogicManager.FILE_OPS_ERROR_FORMAT, e.getMessage()), e);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof SaveCommand objectSaveCommand)) {
            return false;
        }

        return fileName.equals(objectSaveCommand.fileName);
    }
}
