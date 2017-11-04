package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author alexanderleegs
/**
 * Deletes a meeting from an existing person in the address book.
 */
public class DeleteMeetingCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletemeeting";
    public static final String COMMAND_ALIAS = "dm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a meeting from the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "MEETING NAME (one word only)"
            + "MEETING TIME (YYYY-MM-DD HH:MM)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "business " + "2017-12-20 10:00";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Deleted Meeting: %1$s";
    public static final String MESSAGE_NO_MEETING = "This person does not have this meeting.";

    private final Index index;
    private final String meetingName;
    private final String meetingTime;
    private Meeting targetMeeting;

    /**
     * @param index of the person in the filtered person list to edit
     * @param meetingName to be deleted from the person
     * @param meetingTime to be deleted from the person
     */
    public DeleteMeetingCommand(Index index, String meetingName, String meetingTime) {
        requireNonNull(index);

        this.index = index;
        this.meetingName = meetingName;
        this.meetingTime = meetingTime;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        targetMeeting = new Meeting(personToEdit, meetingName, meetingTime);
        Set<Meeting> oldMeetings = new HashSet<Meeting>(personToEdit.getMeetings());
        if (!oldMeetings.contains(targetMeeting)) {
            throw new CommandException(MESSAGE_NO_MEETING);
        }
        Person editedPerson = new Person(personToEdit);
        oldMeetings.remove(targetMeeting);
        editedPerson.setMeetings(oldMeetings);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Not creating a new person");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, targetMeeting.value));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMeetingCommand)) {
            return false;
        }

        // state check
        DeleteMeetingCommand toCompare = (DeleteMeetingCommand) other;
        return index.equals(toCompare.index)
                && meetingName.equals(toCompare.meetingName)
                && meetingTime.equals(toCompare.meetingTime);
    }
}
