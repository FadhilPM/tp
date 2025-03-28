package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TagTest {

    private Tag valid_tag;
    @BeforeEach
    public void setUp(){
        valid_tag = new Tag("valid_tag");
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
        assertEquals(valid_tag.getTagName(), "valid_tag");
        assertNotEquals(valid_tag.getTagName(), "valid-tag");
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
        assertTrue(valid_tag.equals(valid_tag));

        // Null -> false
        assertFalse(valid_tag.equals(null));

        // case insensitive
        assertTrue(valid_tag.equals(new Tag("Valid_tag")));

        assertTrue(valid_tag.equals(new Tag("valid_tag")));

        // hyphen instead of underscore
        assertFalse(valid_tag.equals(new Tag("valid-tag")));
    }

    @Test
    public void test_hashCode() {
        int hashCode = valid_tag.hashCode();
        assertEquals(hashCode, valid_tag.hashCode());
    }

    @Test
    public void test_toString() {
        assertEquals(valid_tag.toString(), "[valid_tag]");
        assertNotEquals(valid_tag.toString(), "[valid-tag]");
    }


}
