package seedu.noknock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.noknock.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.noknock.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_IC_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.noknock.logic.commands.CommandTestUtil.VALID_WARD_BOB;

import org.junit.jupiter.api.Test;

import seedu.noknock.logic.commands.EditPatientCommand.EditPatientDescriptor;
import seedu.noknock.testutil.EditPatientDescriptorBuilder;

public class EditPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPatientDescriptor descriptorWithSameValues = new EditPatientDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPatientDescriptor editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withWard(VALID_WARD_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withIC(VALID_IC_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPatientDescriptor editPersonDescriptor = new EditPatientDescriptor();
        String expected = EditPatientDescriptor.class.getCanonicalName() + "{name="
            + editPersonDescriptor.getName().orElse(null) + ", ward="
            + editPersonDescriptor.getWard().orElse(null) + ", ic="
            + editPersonDescriptor.getIc().orElse(null) + ", tags="
            + editPersonDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }
}
