package seedu.address.model.person;

/**
 * Represents a person's preferred method of contact.
 * The preferred contact method is a fixed value that defaults to "Phone".
 */
public class PreferredContactMethod {

    public final String preferredContactMethod;

    /**
     * Creates a new PreferredContactMethod with the default value of "Phone".
     */
    public PreferredContactMethod() {
        this.preferredContactMethod = "Phone"; //we set the default value of preferred method to be Phone
    }

    /**
     * Creates a new PreferredContactMethod with the specified preferred contact method.
     *
     * @param preferredContactMethod The preferred contact method to be set.
     */
    public PreferredContactMethod(String preferredContactMethod) {
        this.preferredContactMethod = preferredContactMethod;
    }

    /**
     * Switches the current preferred contact method between "Phone" and "Email".
     *
     * @return A new PreferredContactMethod instance with the switched contact method.
     */
    public PreferredContactMethod switchPreferredContactMethod() {
        if (this.preferredContactMethod.equalsIgnoreCase("Phone")) {
            return new PreferredContactMethod("Email");
        }

        return new PreferredContactMethod("Phone");
    }

    /**
     * Gets the preferred contact method.
     *
     * @return The preferred contact method as a string.
     */
    public String getPreferredContactMethod() {
        return this.preferredContactMethod;
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
