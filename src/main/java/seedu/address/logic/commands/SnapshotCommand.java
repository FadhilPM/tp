package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Creates snapshots of existing file.
 */
public class SnapshotCommand extends Command{
    public static final String COMMAND_WORD = "snapshot";
    public static final String SUCCESS = "Snapshot saved to %s";

    private final Storage storage;
    private final LocalDateTime currentDateTime;

    /**
     * Constructs the snapshot command with {@code storage}.
     * @param storage storage interface
     */
    public SnapshotCommand(Storage storage) {
        this.storage = requireNonNull(storage);
        this.currentDateTime = LocalDateTime.now();

        boolean snapshotDirExists = FileUtil.isSnapshotFolderExists();
        if (!snapshotDirExists) {
            FileUtil.createFolder();
        }
    }

    /**
     * Execute snapshot command.
     * @param model {@code Model} which the command should operate on.
     * @return Create snapshot of the current save file.
     * @throws CommandException Throw error for access error and I/O error.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            String custom = currentDateTime.format(DateTimeFormatter.ofPattern("dMMMuuuu_HHmmss"));
            Path pathToBeSaved = Paths.get("snapshots",custom + ".json");
            storage.saveAddressBook(model.getAddressBook(), pathToBeSaved);

            return new CommandResult(String.format(SUCCESS, pathToBeSaved), false, false, false);
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException e) {
            throw new CommandException(String.format(LogicManager.FILE_OPS_ERROR_FORMAT, e.getMessage()), e);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object.hashCode() == this.hashCode()) {
            return true;
        }

        if (!(object instanceof SnapshotCommand snapshotCommand)) {
            return false;
        }

        return currentDateTime.equals(snapshotCommand.currentDateTime);
    }
}
