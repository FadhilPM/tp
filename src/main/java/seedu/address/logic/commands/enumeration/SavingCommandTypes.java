package seedu.address.logic.commands.enumeration;

/**
 * Enum list of all command types that requires
 * saving features
 */
public enum SavingCommandTypes {
    ADD("add"),
    CLEAR("clear"),
    DELETE("delete"),
    EDIT("edit"),
    SNAPSHOT("snapshot"),
    SWITCHCONTACT("switchContact"),
    TAG("tag"),
    UNTAG("untag");

    private final String command;

    SavingCommandTypes(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    /**
     * Check if user command input matches one that requires
     * saving feature
     * @param command The command string to verify
     * @return true if command matches saving need
     */
    public static boolean isRequireSaving(String command) {
        for (SavingCommandTypes savingCommandTypes : SavingCommandTypes.values()) {
            if (savingCommandTypes.getCommand().equals(command)) {
                return true;
            }
        }
        return false;
    }
}
