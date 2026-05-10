package messages;

import checker.CheckerConstants;

public class BackwardMessage extends SkipMessage {
    public static String backwardSkipSuccessMessage() {
        return CheckerConstants.BACKWARD_SKIP_SUCCESS_MESSAGE;
    }

    public static String noLoadedSourceToBackwardMessage() {
        return CheckerConstants.NO_LOADED_SOURCE_TO_BACKWARD_MESSAGE;
    }
}
