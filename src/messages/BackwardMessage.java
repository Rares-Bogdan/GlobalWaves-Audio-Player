package messages;

import checker.CheckerConstants;

public final class BackwardMessage extends SkipMessage {

    private BackwardMessage() { }

    /***
     *
     * @return a message that shows a source has been rewound successfully
     */
    public static String backwardSkipSuccessMessage() {
        return CheckerConstants.BACKWARD_SKIP_SUCCESS_MESSAGE;
    }

    /***
     *
     * @return a message that asks the user to load a source before attempting to rewind
     */
    public static String noLoadedSourceToBackwardMessage() {
        return CheckerConstants.NO_LOADED_SOURCE_TO_BACKWARD_MESSAGE;
    }
}
