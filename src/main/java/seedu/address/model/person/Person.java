package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Optional<Email> optionalEmail;
    private final PreferredContactMethod preferredContactMethod;

    // Data fields
    private final Set<Tag> tags;
    private final Set<Project> projects;

    /**
     * Constructs a {@code Person} with a specified preferred contact method.
     *
     * @param name The name of the person.
     * @param phone The phone number of the person.
     * @param optionalEmail The email address of the person.
     * @param tags The set of tags associated with the person.
     * @param projects The set of projects associated with the person
     */
    public Person(Name name, Phone phone, Optional<Email> optionalEmail,
                  Set<Tag> tags, Set<Project> projects) {
        this(name, phone, optionalEmail, tags, projects, new PreferredContactMethod("Phone"));
    }

    /**
     * Constructs a {@code Person} with a specified preferred contact method.
     *
     * @param name The name of the person.
     * @param phone The phone number of the person.
     * @param optionalEmail The email address of the person.
     * @param tags The set of tags associated with the person.
     * @param projects The set of projects associated with the person.
     * @param preferredContactMethod The preferred method of contact (Phone or Email).
     */
    public Person(Name name, Phone phone, Optional<Email> optionalEmail,
                   Set<Tag> tags, Set<Project> projects, PreferredContactMethod preferredContactMethod) {
        requireAllNonNull(name, phone, optionalEmail, tags, projects, preferredContactMethod);
        this.name = name;
        this.phone = phone;
        this.optionalEmail = optionalEmail;
        this.tags = Collections.unmodifiableSet(tags);
        this.projects = Collections.unmodifiableSet(projects);
        this.preferredContactMethod = preferredContactMethod;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Optional<Email> getEmail() {
        return optionalEmail;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Returns an immutable project set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Project> getProjects() {
        return projects;
    }

    public PreferredContactMethod getPreferredContactMethod() {
        return preferredContactMethod;
    }

    /**
     * Returns a {@code List} of {@code Tag}s from the given {@code tagSet} that are not associated with this person.
     *
     * @param tagSet the set of tags to compare against this person's tags; must not be null
     * @return a list of tags from {@code tagSet} that this person does not have
     * @throws NullPointerException if {@code tagSet} or any of its elements are null
     */
    public List<Tag> tagsNotInTagSet(Set<Tag> tagSet) {
        requireAllNonNull(tagSet);
        return tagSet.stream().filter(x -> !this.tags.contains(x)).toList();
    }

    /**
     * Returns a {@code List} of {@code Project}s from the given {@code projectSet}
     * that are not associated with this person.
     * @param projectSet the set of projects to compare against this person's projects; must not be null
     * @return a list of projects from {@code projectSet} that this person does not have
     * @throws NullPointerException if {@code projectSet} or any of its elements are null
     */
    public List<Project> projectsNotInProjectSet(Set<Project> projectSet) {
        requireAllNonNull(projectSet);
        return projectSet.stream().filter(x -> !this.projects.contains(x)).toList();
    }

    /**
     * Checks if this person has the same phone number as the specified {@code Phone} object.
     *
     * @param toCompare the phone number to compare with this person's phone; must not be null
     * @return {@code true} if the phone numbers are equal, {@code false} otherwise
     * @throws NullPointerException if {@code toCompare} is null
     */
    public boolean hasSamePhone(Phone toCompare) {
        requireAllNonNull(toCompare);
        return this.phone.equals(toCompare);
    }

    /**
     * Creates and returns a new {@code Person} with updated details based on the given {@code EditPersonDescriptor}.
     * Fields not specified in the descriptor will retain their original values.
     *
     * @param epd the descriptor containing the fields to update; must not be null
     * @return a new {@code Person} instance with the updated details
     * @throws NullPointerException if {@code epd} is null
     */
    public Person createEditedPerson(EditPersonDescriptor epd) {
        requireAllNonNull(epd);
        Name updatedName = epd.name().orElse(this.name);
        Phone updatedPhone = epd.phone().orElse(this.phone);
        Optional<Email> updatedEmail = epd.email().or(() -> this.optionalEmail);

        return new Person(updatedName, updatedPhone, updatedEmail, tags, projects, preferredContactMethod);
    }

    /**
     * Creates and returns a new {@code Person} with the specified tags and projects added to the existing ones.
     *
     * @param newlyAddedTags the tags to add; must not be null
     * @param newlyAddedProjects the projects to add; must not be null
     * @return a new {@code Person} instance with the additional tags and projects
     * @throws NullPointerException if {@code newlyAddedTags} or {@code newlyAddedProjects} is null
     */
    public Person tagPerson(Set<Tag> newlyAddedTags, Set<Project> newlyAddedProjects) {
        requireAllNonNull(newlyAddedTags, newlyAddedProjects);
        Set<Tag> newTags = new LinkedHashSet<>(this.tags);
        Set<Project> newProjects = new LinkedHashSet<>(this.projects);

        newTags.addAll(newlyAddedTags);
        newProjects.addAll(newlyAddedProjects);

        return new Person(name, phone, optionalEmail, newTags, newProjects, preferredContactMethod);
    }

    /**
     * Creates and returns a new {@code Person} with the specified tags and projects removed from the existing ones.
     *
     * @param tagsToRemove the tags to remove; must not be null
     * @param projectsToRemove the projects to remove; must not be null
     * @return a new {@code Person} instance with the specified tags and projects removed
     * @throws NullPointerException if {@code tagsToRemove} or {@code projectsToRemove} is null
     */
    public Person unTagPerson(Set<Tag> tagsToRemove, Set<Project> projectsToRemove) {
        requireAllNonNull(tagsToRemove, projectsToRemove);
        Set<Tag> newTags = new LinkedHashSet<>(this.tags);
        Set<Project> newProjects = new LinkedHashSet<>(this.projects);

        newTags.removeAll(tagsToRemove);
        newProjects.removeAll(projectsToRemove);

        return new Person(name, phone, optionalEmail, newTags, newProjects, preferredContactMethod);
    }

    /**
     * Creates and returns a new {@code Person} with the specified project replaced.
     * If the project exists in the current set,
     * it will be removed and then re-added (e.g., to reflect updated details).
     * @param project the project to replace; must not be null
     * @return a new {@code Person} instance with the updated project
     * @throws NullPointerException if {@code project} is null
     */
    public Person replaceProject(Project project) {
        requireAllNonNull(project);
        LinkedHashSet<Project> newProjectSet = new LinkedHashSet<>(this.projects);

        if (newProjectSet.remove(project)) {
            newProjectSet.add(project);
        }
        return new Person(name, phone, optionalEmail, tags, newProjectSet, preferredContactMethod);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null && otherPerson.hasSamePhone(this.phone);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Person otherPerson) {
            return name.equals(otherPerson.name)
                    && phone.equals(otherPerson.phone)
                    && optionalEmail.equals(otherPerson.optionalEmail)
                    && tags.equals(otherPerson.tags)
                    && projects.equals(otherPerson.projects);
        }
        return false;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, optionalEmail, tags, projects);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", optionalEmail)
                .add("tags", tags)
                .add("projects", projects)
                .toString();
    }
}
