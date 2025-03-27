package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class SaveCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Path testAddressBookFile;
    private Storage storage;
    private Model model;

    @BeforeEach
    public void setUp() throws IOException {
        testAddressBookFile = temporaryFolder.resolve("testArtHive.json");

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(testAddressBookFile);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("preferences.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(testAddressBookFile);
        model = new ModelManager(getTypicalAddressBook(), userPrefs);
    }

    @Test
    public void execute_saveWithNoParameters_savesAndLoadsCorrectly() throws Exception {
        SaveCommand saveCommand = new SaveCommand(storage);
        CommandResult result = saveCommand.execute(model);

        String expectedMessage = String.format(SaveCommand.SUCCESS, testAddressBookFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        assertTrue(Files.exists(testAddressBookFile));

        JsonAddressBookStorage loadStorage = new JsonAddressBookStorage(testAddressBookFile);

        // Attempt read from file and confirm that save contents match current addressbook
        loadStorage.readAddressBook()
            .map(x -> x.getPersonList())
            .ifPresentOrElse(x -> assertEquals(
                model.getAddressBook().getPersonList(), x), () -> fail("Unable to read from file"));
    }
}
