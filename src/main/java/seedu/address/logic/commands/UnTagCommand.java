package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Removes Tag(s) from a person in the address book.
 */
public class UnTagCommand extends Command {
    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove tags or projects from a contact. "
            + "Parameters: "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_PROJECT + "PROJECT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TAG + "project-x";

    public static final String MESSAGE_SUCCESS = "Tags and/or projects removed from %1$s";
    private final Phone phone;
    private final Set<Tag> tags;

    /**
     * @param phone number of the person in the filtered person list to edit
     * @param tags to remove
     */
    public UnTagCommand(Phone phone, Set<Tag> tags) {
        requireNonNull(phone);
        this.phone = phone;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToUnTag = model.getFilteredPersonList()
                .stream()
                .filter(x -> x.hasSamePhone(phone))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_ABSENT_PHONE_NUMBER));

        Set<Tag> currentTags = new LinkedHashSet<>(personToUnTag.getTags());
        currentTags.addAll(personToUnTag.getProjects());
        String check = checkForTagInExistingTags(currentTags, this.tags);
        if (!check.equals("")) {
            throw new CommandException(String.format(Messages.MESSAGE_ABSENT_TAG_PROJECT,
                    check, personToUnTag.getName(), personToUnTag.getPhone()));
        }

        Person taggedPerson = personToUnTag.unTagPerson(this.tags);

        model.setPerson(personToUnTag, taggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, taggedPerson.getName()));
    }

    /**
     * For each Tag in tagsToRemove, check if Tag currently exists within the existingTags.
     * Returns tagName if not found within existingTags, "" otherwise.
     * @param existingTags set of tags that currently exist
     * @param tagsToRemove set of tags that should be untagged/removed
     */
    public static String checkForTagInExistingTags(Set<Tag> existingTags, Set<Tag> tagsToRemove) {
        assert existingTags != null;
        assert tagsToRemove != null;

        for (Tag tagToRemove : tagsToRemove) {
            if (!(existingTags.contains(tagToRemove))) {
                return tagToRemove.getTagName();
            }
        }
        return "";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof UnTagCommand otherCommand) {
            return phone.equals(otherCommand.phone)
                    && tags.equals(otherCommand.tags);
        }
        return false;
    }
}
