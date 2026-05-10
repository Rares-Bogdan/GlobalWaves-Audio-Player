package messages;

import checker.CheckerConstants;

public class ForwardMessage extends SkipMessage {
    public static String forwardSkipSuccessMessage() {
        return CheckerConstants.FORWARD_SKIP_SUCCESS_MESSAGE;
    }

    public static String noLoadedSourceToForwardMessage() {
        return CheckerConstants.NO_LOADED_SOURCE_TO_FORWARD_MESSAGE;
    }
}
