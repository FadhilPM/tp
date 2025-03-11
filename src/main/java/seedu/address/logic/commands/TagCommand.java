package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags a project to a contact. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "PHONE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TAG + "project-x";

    public static final String MESSAGE_SUCCESS = "Tag added to Contact: %1$s";
    private final Index index;
    private final Phone phone;
    private final Set<Tag> tags;

    public TagCommand(Index index, Phone phone, Set<Tag> tags) {
        requireNonNull(index);
        requireNonNull(phone);
        this.index = index;
        this.phone = phone;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person taggedPerson = tagProjectToPerson(personToEdit, this.tags);

        model.setPerson(personToEdit, taggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(taggedPerson)));
    }

    private static Person tagProjectToPerson(Person personToEdit, Set<Tag> newlyAddedTags) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Set<Tag> currentTags = personToEdit.getTags();

        // Add the current and newly added tags to a single Linked Hash Set
        Set<Tag> newTags = new LinkedHashSet<>();
        newTags.addAll(currentTags);
        newTags.addAll(newlyAddedTags);

        // Return new Person 
        return new Person(name, phone, newTags);
    }
}
