package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteByPhoneCommand}.
 */
public class DeleteByPhoneCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPhoneUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(1);
        Phone phoneToDelete = personToDelete.getPhone();
        DeleteCommand deleteCommand = new DeleteByPhoneCommand(phoneToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                                               Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPhoneUnfilteredList_throwsCommandException() {
        Phone nonExistentPhone = new Phone("99999999");
        DeleteCommand deleteCommand = new DeleteByPhoneCommand(nonExistentPhone);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_ABSENT_PHONE_NUMBER);
    }

    @Test
    public void equals() {
        Phone firstPhone = new Phone(VALID_PHONE_AMY);
        Phone secondPhone = new Phone(VALID_PHONE_BOB);
        DeleteCommand deleteFirstCommand = new DeleteByPhoneCommand(firstPhone);
        DeleteCommand deleteSecondCommand = new DeleteByPhoneCommand(secondPhone);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteByPhoneCommand(firstPhone);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different phone -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Phone phone = new Phone(VALID_PHONE_AMY);
        DeleteCommand deleteCommand = new DeleteByPhoneCommand(phone);
        String expected = new ToStringBuilder(deleteCommand)
            .add("phone", phone)
            .toString();
        assertEquals(expected, deleteCommand.toString());
    }
}
