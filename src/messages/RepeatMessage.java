package messages;

import checker.CheckerConstants;

public class RepeatMessage {
    public static String repeatModeChangedMessage(String repeatState) {
        return "Repeat mode changed to " + repeatState + ".";
    }

    public static String loadSourceBeforeChangeRepeatStatusMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_CHANGE_REPEAT_STATUS_MESSAGE;
    }
}
