package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SwitchContactCommand.switchPreferredContactFromPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code SwitchContactCommand}.
 */
public class SwitchContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPhoneWithEmail_success() {
        Person personWithEmail = new PersonBuilder().build();

        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        testModel.addPerson(personWithEmail);
        Phone phoneToSwitch = personWithEmail.getPhone();

        SwitchContactCommand switchCommand = new SwitchContactCommand(phoneToSwitch);

        Person switchedPerson = switchPreferredContactFromPerson(personWithEmail);
        String expectedMessage = SwitchContactCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(testModel.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithEmail, switchedPerson);

        assertCommandSuccess(switchCommand, testModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPhone_throwsCommandException() {
        Phone nonExistentPhone = new Phone("99999999");
        SwitchContactCommand switchCommand = new SwitchContactCommand(nonExistentPhone);

        assertCommandFailure(switchCommand, model, Messages.MESSAGE_ABSENT_PHONE_NUMBER);
    }

    @Test
    public void execute_personWithoutEmail_throwsCommandException() {
        // Create a person without email
        Person personWithoutEmail = new PersonBuilder().withEmail(null).build();

        // Add the person to the model
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        testModel.addPerson(personWithoutEmail);

        SwitchContactCommand switchCommand = new SwitchContactCommand(personWithoutEmail.getPhone());

        assertCommandFailure(switchCommand, testModel, SwitchContactCommand.NO_EMAIL_FAILURE);
    }

    @Test
    public void switchPreferredContactMethod_success() {
        // Create a person with default preferred contact method (Phone)
        Person person = new PersonBuilder().withEmail("test@example.com").build();
        assertEquals("Phone", person.getPreferredContactMethod().toString());

        // Switch the preferred contact method
        Person switchedPerson = switchPreferredContactFromPerson(person);
        assertEquals("Email", switchedPerson.getPreferredContactMethod().toString());

        // Switch back
        Person switchedBackPerson = switchPreferredContactFromPerson(switchedPerson);
        assertEquals("Phone", switchedBackPerson.getPreferredContactMethod().toString());
    }

    @Test
    public void equals() {
        Phone firstPhone = new Phone(VALID_PHONE_AMY);
        Phone secondPhone = new Phone(VALID_PHONE_BOB);
        SwitchContactCommand switchFirstCommand = new SwitchContactCommand(firstPhone);
        SwitchContactCommand switchSecondCommand = new SwitchContactCommand(secondPhone);

        // same object -> returns true
        assertTrue(switchFirstCommand.equals(switchFirstCommand));

        // same values -> returns true
        SwitchContactCommand switchFirstCommandCopy = new SwitchContactCommand(firstPhone);
        assertTrue(switchFirstCommand.equals(switchFirstCommandCopy));

        // different types -> returns false
        assertFalse(switchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(switchFirstCommand.equals(null));

        // different phone -> returns false
        assertFalse(switchFirstCommand.equals(switchSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Phone phone = new Phone(VALID_PHONE_AMY);
        SwitchContactCommand switchCommand = new SwitchContactCommand(phone);

        String expected = new ToStringBuilder(switchCommand)
            .add("phone", phone)
            .toString();

        assertEquals(expected, switchCommand.toString());
    }

}
