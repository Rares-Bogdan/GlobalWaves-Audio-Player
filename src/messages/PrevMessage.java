package messages;

import checker.CheckerConstants;

public class PrevMessage {
    public static String prevTrackSuccessMessage(String trackName) {
        return CheckerConstants.PREV_TRACK_SUCCESS_MESSAGE + trackName + ".";
    }

    public static String loadSourceBeforePrevMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_PREV_MESSAGE;
    }
}
