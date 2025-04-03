package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Project;
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
    private final Set<Project> projects;

    /**
     * @param phone number of the person in the filtered person list to edit
     * @param tags to remove
     */
    public UnTagCommand(Phone phone, Set<Tag> tags, Set<Project> projects) {
        requireNonNull(phone);
        this.phone = phone;
        this.tags = tags;
        this.projects = projects;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToUnTag = model.getFilteredPersonList()
                .stream()
                .filter(x -> x.hasSamePhone(phone))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_ABSENT_PHONE_NUMBER));

        Optional<String> optionalMissingTagsProjects = findMissingTagsProjects(personToUnTag);

        if (optionalMissingTagsProjects.isPresent()) {
            String missingTagsProjects = optionalMissingTagsProjects.get();
            throw new CommandException(String.format(Messages.MESSAGE_ABSENT_TAG_PROJECT,
                    missingTagsProjects, personToUnTag.getName(), personToUnTag.getPhone()));
        }

        Person untaggedPerson = personToUnTag.unTagPerson(this.tags, this.projects);

        model.setPerson(personToUnTag, untaggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, untaggedPerson.getName()));
    }

    private Optional<String> findMissingTagsProjects(Person personToUnTag) {
        List<Tag> missingTags = personToUnTag.tagsNotInTagSet(tags);
        List<Project> missingProjects = personToUnTag.projectsNotInProjectSet(projects);

        Function<List<? extends Tag>, Optional<String>> listToString = x -> x.stream()
                .map(Tag::getTagName)
                .reduce((a, b) -> a + ", " + b);

        Optional<String> optMissingTagString = listToString.apply(missingTags)
                .map(x -> "Tag(s): " + x);

        Optional<String> optMissingProjectString = listToString.apply(missingProjects)
                .map(x -> "Project(s): " + x);

        return Stream.of(optMissingTagString, optMissingProjectString)
                .flatMap(Optional::stream)
                .reduce((a, b) -> a + "\n" + b);
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
