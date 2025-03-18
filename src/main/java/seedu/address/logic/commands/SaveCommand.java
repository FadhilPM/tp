package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import seedu.address.commons.util.FileUtil;
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

    /**
     * Execute save command depending on the parameter.
     * @param model {@code Model} which the command should operate on.
     * @return Either save to original file or save to a new file.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Path pathToBeSaved;
            Path oldPath = model.getAddressBookFilePath();

            if (fileName.isPresent()) {
                pathToBeSaved = Paths.get("data", fileName.get() + ".json");
                storage.saveAddressBook(model.getAddressBook(), pathToBeSaved);
                model.setAddressBookFilePath(pathToBeSaved);

                try {
                    storage.saveUserPrefs(model.getUserPrefs());
                } catch (IOException e) {
                    System.err.println("Error saving user preferences");
                }

                FileUtil.purgeOldAddressBookFile_active(oldPath, pathToBeSaved);

                return new CommandResult(String.format(SUCCESS, pathToBeSaved), false, false, true);
            } else {
                FileUtil.purgeOldAddressBookFile_passive(oldPath);
                pathToBeSaved = oldPath;
            }

            storage.saveAddressBook(model.getAddressBook(), pathToBeSaved);
            storage.saveUserPrefs(model.getUserPrefs());

            return new CommandResult(String.format(SUCCESS, pathToBeSaved), false, false, false);
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
