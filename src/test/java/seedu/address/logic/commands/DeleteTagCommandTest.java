package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteTagCommand}.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagOnePerson_success() throws Exception {
        String tagName = "owesMoney";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(tagName);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(newTag);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagTwoPersons_success() throws Exception {
        String tagName = "friends";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(tagName);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(newTag);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagDoesNotExist_throwsCommandException() throws Exception {
        String tagName = "robot";
        DeleteTagCommand deleteTagCommand = prepareCommand(tagName);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_TAG_DISPLAYED);
    }

    @Test
    public void execute_invalidTagFormat_throwsCommandException() throws Exception {
        String tagName = "hi there";
        try {
            prepareCommand(tagName);
            fail("Expected IllegalValueException to be thrown");
        } catch (IllegalValueException ive) {
            assertEquals(null, ive.getMessage(), "Tags names should be alphanumeric");
        }
    }

    @Test
    public void equals() throws IllegalValueException {
        DeleteTagCommand deleteFirstCommand = new DeleteTagCommand(TAG_DESC_FRIEND);
        DeleteTagCommand deleteSecondCommand = new DeleteTagCommand(TAG_DESC_HUSBAND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTagCommand deleteFirstCommandCopy = new DeleteTagCommand(TAG_DESC_FRIEND);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteTagCommand} with the parameter {@code tagName}.
     */
    private DeleteTagCommand prepareCommand(String tagName) throws Exception {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(tagName);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }
}