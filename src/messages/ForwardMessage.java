package messages;

import checker.CheckerConstants;

public final class ForwardMessage extends SkipMessage {
    /***
     *
     * @return a message that shows a source has been forwarded successfully
     */
    public static String forwardSkipSuccessMessage() {
        return CheckerConstants.FORWARD_SKIP_SUCCESS_MESSAGE;
    }

    /***
     *
     * @return a message that asks the user to load a source before attempting to forward
     */
    public static String noLoadedSourceToForwardMessage() {
        return CheckerConstants.NO_LOADED_SOURCE_TO_FORWARD_MESSAGE;
    }
}
