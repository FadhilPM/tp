package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UnTagCommand.unTagProjectFromPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnTagCommand.
 */
public class UnTagCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unTag_success() {
        Person personToTag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Phone phone = personToTag.getPhone();
        Tag newTag = new Tag("T_3st-x");
        Set<Tag> tags = new LinkedHashSet<>();
        tags.add(newTag);
        UnTagCommand untagComd = new UnTagCommand(phone, tags);
        Person unTaggedPerson = unTagProjectFromPerson(personToTag, tags);

        String expectedMessage = String.format(UnTagCommand.MESSAGE_SUCCESS);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToTag, unTaggedPerson);

        assertCommandSuccess(untagComd, model, expectedMessage, expectedModel);

    }

    @Test
    public void equals() {
        Person p = new PersonBuilder().withTags("test_Equal5").build();
        Phone phone = p.getPhone();
        Tag newTag = new Tag("test_Equal5");
        Set<Tag> tags = new LinkedHashSet<>();
        tags.add(newTag);
        UnTagCommand removeTagCommand = new UnTagCommand(phone, tags);

        // same object -> returns true
        assertTrue(removeTagCommand.equals(removeTagCommand));
    }
}
