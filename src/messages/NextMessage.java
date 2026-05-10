package messages;

import checker.CheckerConstants;

public class NextMessage {
    public static String nextTrackSuccessMessage(String trackName) {
        return CheckerConstants.NEXT_TRACK_SUCCESS_MESSAGE + trackName + ".";
    }

    public static String loadSourceBeforeNextMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_NEXT_MESSAGE;
    }
}
