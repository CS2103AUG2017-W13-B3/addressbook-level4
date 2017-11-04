package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author alexanderleegs

/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteMeetingCommandParser implements Parser<DeleteMeetingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddMeetingCommand
     * and returns an AddMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMeetingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+", 3);
        Index index;
        if (parts.length < 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndex(parts[0]);
            String meetingName = parts[1];
            String meetingTime = parts[2];
            return new DeleteMeetingCommand(index, meetingName, meetingTime);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE));
        }
    }
}
