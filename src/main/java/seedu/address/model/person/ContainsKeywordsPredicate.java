package seedu.address.model.person;

public abstract class ContainsKeywordsPredicate{

    /**
     * Checks if a field in Person matches the keyword.
     *
     * @param person {@code Person} which the check is based on.
     * @return true if the field in Person contains the keyword.
     */
    public abstract boolean test(Person person);
}
