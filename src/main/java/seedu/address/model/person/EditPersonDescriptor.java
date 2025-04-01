package seedu.address.model.person;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public record EditPersonDescriptor(Optional<Name> name, Optional<Phone> phone, Optional<Email> email) {

    // No-arg constructor, defaults all fields to Optional.empty.
    public EditPersonDescriptor() {
        this(Optional.empty(), Optional.empty(), Optional.empty());
    }

    // Copy constructor.
    public EditPersonDescriptor(EditPersonDescriptor toCopy) {
        this(toCopy.name, toCopy.phone, toCopy.email);
    }

    public EditPersonDescriptor setName(Name newName) {
        return new EditPersonDescriptor(Optional.ofNullable(newName), this.phone, this.email);
    }

    public EditPersonDescriptor setPhone(Phone newPhone) {
        return new EditPersonDescriptor(this.name, Optional.ofNullable(newPhone), this.email);
    }

    public EditPersonDescriptor setEmail(Email newEmail) {
        return new EditPersonDescriptor(this.name, this.phone, Optional.ofNullable(newEmail));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .toString();
    }
}
