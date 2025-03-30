package seedu.address.commons.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Writes and reads files
 */
public class FileUtil {
    public static final String SNAPSHOTS_STRING = "snapshots";
    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String)},
     * otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Check if there are more than 1 save file.
     */
    public static Path checkExistingJsonFiles(Path dir) throws IOException {
        try (Stream<Path> files = Files.list(dir)) {
            List<Path> jsonFiles = files
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".json"))
                    .toList();

            if (jsonFiles.size() != 1) {
                throw new IllegalStateException("There should only be 1 json file. Please check the directory: " + dir);
            }

            return jsonFiles.get(0);
        }
    }

    /**
     * This version should be used for passive saving
     * features only.
     * Deletes all .json files except the new savefile
     * and preferences.json
     * @param oldPath
     */
    public static void purgeOldAddressBookFile_passive(Path oldPath) {
        try {
            Path dir = oldPath.getParent();
            if (dir == null || !Files.exists(dir)) {
                return;
            }

            try (Stream<Path> files = Files.list(dir)) {
                files.filter(Files::isRegularFile)
                        .filter(file -> file.toString().endsWith(".json"))
                        .filter(file -> !file.getFileName().toString().equals("preferences.json"))
                        .filter(file -> !file.getFileName().equals(oldPath.getFileName()))
                        .forEach(file -> {
                            try {
                                file.toFile().setWritable(true);
                                Files.deleteIfExists(file);
                                System.err.println("Deleted old savefile: " + file);
                            } catch (IOException e) {
                                System.err.println("Error deleting old savefile: " + e.getMessage());
                            }
                        });
            }
        } catch (IOException e) {
            System.err.println("Can't list files in: " + e.getMessage());
        }
    }

    /**
     * This version should be used for active saving
     * features only.
     * Deletes all .json files except the new savefile
     * and preferences.json
     * @param oldPath Old savefile name
     * @param newPath New savefile name
     */
    public static void purgeOldAddressBookFile_active(Path oldPath, Path newPath) {
        if (oldPath.equals(newPath)) {
            return;
        }

        try {
            Path dir = newPath.getParent();
            if (dir == null || !Files.exists(dir)) {
                return;
            }

            try (Stream<Path> files = Files.list(dir)) {
                files.filter(Files::isRegularFile)
                        .filter(file -> file.toString().endsWith(".json"))
                        .filter(file -> !file.getFileName().equals(newPath.getFileName()))
                        .filter(file -> !file.getFileName().toString().equals("preferences.json"))
                        .forEach(file -> {
                            try {
                                file.toFile().setWritable(true);
                                Files.deleteIfExists(file);
                                System.err.println("Deleted old savefile: " + file);
                            } catch (IOException e) {
                                System.err.println("Error deleting old savefile: " + e.getMessage());
                            }
                        });
            }
        } catch (IOException e) {
            System.err.println("Can't list files in: " + e.getMessage());
        }
    }

    /**
     * Create a folder at specific folderPath.
     * @throws IOException throw I/O related error
     */
    public static boolean createFolder() {
        String directoryName = SNAPSHOTS_STRING;
        String currDirectory = System.getProperty("user.dir");
        String dirPath = currDirectory + File.separator + directoryName;
        File directory = new File(dirPath);

        if (directory.exists() && directory.isDirectory()) {
            return true;
        }

        return directory.mkdir();
    }

    /**
     * Get the boolean value of snapshot folder's existence.
     * @return True/False
     */
    public static boolean isSnapshotFolderExists() {
        String directoryName = SNAPSHOTS_STRING;
        String currDirectory = System.getProperty("user.dir");
        String dirPath = currDirectory + File.separator + directoryName;
        File directory = new File(dirPath);

        return directory.exists() && directory.isDirectory();
    }
}
