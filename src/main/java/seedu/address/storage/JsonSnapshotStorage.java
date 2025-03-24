package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Replicates JsonAddressBookStorage to create immutable snapshots in Json output.
 */
public class JsonSnapshotStorage implements SnapshotStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonSnapshotStorage.class);

    /**
     * Create snapshot of {@link ReadOnlyAddressBook} to sppecified path.
     * @param addressBook can't be null.
     * @param filePath where the snapshot will be saved.
     * @throws IOException
     */
    @Override
    public void makeSnapshot(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        logger.fine("Creating Json snapshot at: " + filePath);
        FileUtil.createParentDirsOfFile(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }
}
