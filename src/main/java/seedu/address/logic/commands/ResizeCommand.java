package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ResizeMainWindowEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Resize the main window.
 */
public class ResizeCommand extends Command {

    public static final int MAX_WIDTH = 1280;
    public static final int MAX_HEIGHT = 720;
    public static final String COMMAND_WORD = "resize";
    public static final String COMMAND_ALIAS = "rs";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resize the MainWindows to "
            + "the specified WIDTH(<=1280) and HEIGHT(<=720) \n"
            + "Parameters: WIDTH HEIGHT\n"
            + String.format("Example: " + COMMAND_WORD + " %d %d", MAX_WIDTH, MAX_HEIGHT);
    public static final String MESSAGE_SUCCESS = "Resize successfully to %d*%d";


    private int width;
    private int height;

    public ResizeCommand(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public CommandResult execute() throws CommandException{
        EventsCenter.getInstance().post(new ResizeMainWindowEvent(width, height));
        return new CommandResult(String.format(MESSAGE_SUCCESS, width, height));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResizeCommand // instanceof handles nulls
                && this.width == ((ResizeCommand) other).width
                && this.height == ((ResizeCommand) other).height); // state check
    }
}
