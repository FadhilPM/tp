package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class FileUtilTest {
    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    void test_getFile_oneJsonFile() throws IOException {
        Path temp = Files.createTempDirectory("temp");

        Path jsonFile = Files.createFile(temp.resolve("temp.json"));

        Path result = FileUtil.getFile(temp);

        assertEquals(jsonFile, result);

        Files.deleteIfExists(jsonFile);
        Files.deleteIfExists(temp);
    }

    @Test
    void test_getFile_multipleJsonFiles() throws IOException {
        Path temp = Files.createTempDirectory("temp");

        Files.createFile(temp.resolve("temp1.json"));
        Files.createFile(temp.resolve("temp2.json"));

        assertThrows(IllegalStateException.class, () -> FileUtil.getFile(temp));

        Files.walk(temp)
                .map(Path::toFile)
                .forEach(file -> file.delete());
    }

    @Test
    void test_getFile_emptyDirectory() throws IOException {
        Path temp = Files.createTempDirectory("temp");

        assertThrows(IllegalStateException.class, () -> FileUtil.getFile(temp));

        Files.delete(temp);
    }

    @Test
    void test_getFile_invalidDirectory() {
        Path invalid = Paths.get("invalid");

        assertThrows(IOException.class, () -> FileUtil.getFile(invalid));
    }

    @Test
    void test_purgeOldAddressBook_sameDirectory() {
        Path oldFile = Paths.get("first.txt");
        Path newFile = Paths.get("first.txt");

        try {
            Files.deleteIfExists(oldFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileUtil.purgeOldAddressBookFile(oldFile, newFile);

        assertFalse(Files.exists(oldFile));
    }

    @Test
    void test_purgeOldAddressBook_fileDoesNotExist() {
        Path oldFile = Paths.get("doesntexist.txt");
        Path newFile = Paths.get("savefile.txt");

        assertFalse(Files.exists(oldFile), "Old file should not exist.");

        FileUtil.purgeOldAddressBookFile(oldFile, newFile);

        assertFalse(Files.exists(oldFile), "Old file does not exist.");
    }
}
