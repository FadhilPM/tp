package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.model.ReadOnlyAddressBook;

/**
 * Storage for creating immutable snapshots.
 */
public interface SnapshotStorage {
    /**
     * Creates snapshot of {@link ReadOnlyAddressBook} to a path.
     * @param addressBook can't be null.
     * @param filePath where the snapshot will be saved.
     * @throws IOException throw error if any when writing to file.
     */
    void makeSnapshot(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;
}
