package messages;

import checker.CheckerConstants;

public class RepeatMessage {
    /***
     * method that prints the successful change of the repeat mode
     * @param repeatState the repeat mode the user changes the source to
     * @return a message that shows the change of the repeat mode was successful
     */
    public static String repeatModeChangedMessage(final String repeatState) {
        return "Repeat mode changed to " + repeatState + ".";
    }

    /***
     * method that prints the requirement to load a source in order to change the repeat mode
     * @return a message that asks the user to load a source before attempting to change the repeat
     * mode
     */
    public static String loadSourceBeforeChangeRepeatStatusMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_CHANGE_REPEAT_STATUS_MESSAGE;
    }
}
