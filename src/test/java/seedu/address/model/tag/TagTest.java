package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TagTest {

    private Tag validTag;
    @BeforeEach
    public void setUp() {
        validTag = new Tag("valid_tag");
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void test_getTagName() {
        assertEquals(validTag.getTagName(), "valid_tag");
        assertNotEquals(validTag.getTagName(), "valid-tag");
    }

    @Test
    public void test_isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        //more than 20 characters
        assertFalse(Tag.isValidTagName("aaaaaaaaaaaaaaaaaaaaa"));

        //less than 1 character
        assertFalse(Tag.isValidTagName(""));

        //hyphens and underscores allowed
        assertTrue(Tag.isValidTagName("_-"));

        //other special characters not allowed
        assertFalse(Tag.isValidTagName("*!@#$%^&*"));
    }

    @Test
    public void test_equals() {

        // Same object -> true
        assertTrue(validTag.equals(validTag));

        // Null -> false
        assertFalse(validTag.equals(null));

        // case insensitive
        assertTrue(validTag.equals(new Tag("Valid_tag")));

        assertTrue(validTag.equals(new Tag("valid_tag")));

        // hyphen instead of underscore
        assertFalse(validTag.equals(new Tag("valid-tag")));
    }

    @Test
    public void test_hashCode() {
        int hashCode = validTag.hashCode();
        assertEquals(hashCode, validTag.hashCode());
    }

    @Test
    public void test_toString() {
        assertEquals(validTag.toString(), "[valid_tag]");
        assertNotEquals(validTag.toString(), "[valid-tag]");
    }


}
