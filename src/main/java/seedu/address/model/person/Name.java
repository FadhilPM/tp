package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.enumeration.PrefixEnum;
import seedu.address.storage.JsonSnapshotStorage;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String INVALID_NAME_CHARACTERS_MESSAGE = "Name contains invalid characters. Only letters,"
            + " numbers, spaces, '-', '_', '.', ',', apostrophe (') and '/' are allowed.";
    public static final String MESSAGE_NAME_LENGTH_ERROR = "Name must not exceed 40 characters.";
    public static final String MESSAGE_EMPTY_NAME_MSG = "Name field cannot be empty.";
    public static final String MESSAGE_NAME_CONTAINS_PREFIX = "Name contains command prefix.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[-a-zA-Z0-9_'.,\\/ ]*";
    private static final Logger logger = LogsCenter.getLogger(JsonSnapshotStorage.class);
    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name));
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static Optional<String> invaildNameCheck(String test) {

        //Check if there is blank
        if (test.isBlank()) {
            return Optional.of(MESSAGE_EMPTY_NAME_MSG);
        }

        if (PrefixEnum.containsPrefix(test)) {
            return Optional.of(MESSAGE_NAME_CONTAINS_PREFIX);
        }

        logger.fine("Remaining String length: " + test.length() );
        logger.fine("Name input: " + test);

        if (!test.matches(VALIDATION_REGEX)) {
            return Optional.of(INVALID_NAME_CHARACTERS_MESSAGE);
        } else if (test.length() > 40) {
            return Optional.of(MESSAGE_NAME_LENGTH_ERROR );
        }

        return Optional.empty();

    }

    /**
     * Checks whether a given name string is valid
     *
     * @param test The name string to validate
     * @return {@code true} if the name is valid, {@code false} otherwise.
     */
    public static boolean isValidName(String test) {
        Optional<String> errorMessage = invaildNameCheck(test);

        //Checks if name contains invalid characters
        return errorMessage.isEmpty();
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
