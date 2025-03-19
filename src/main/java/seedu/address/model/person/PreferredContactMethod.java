package seedu.address.model.person;

/**
 * Represents a person's preferred method of contact.
 * The preferred contact method is a fixed value that defaults to "Phone".
 */
public class PreferredContactMethod {

    public final String preferredContactMethod;

    public PreferredContactMethod() {
        preferredContactMethod = "Phone";
    }

    /**
     * Returns the preferred contact method as a string.
     *
     * @return The preferred contact method.
     */
    @Override
    public String toString() {
        return preferredContactMethod;
    }


}
