package messages;

import checker.CheckerConstants;

public class PrevMessage {
    /***
     * method that prints the successful return to the previous track
     * @param trackName the name of the track the user returns to
     * @return a message that shows the return to the previous track was successful
     */
    public static String prevTrackSuccessMessage(final String trackName) {
        return CheckerConstants.PREV_TRACK_SUCCESS_MESSAGE + trackName + ".";
    }

    /***
     * method that prints the requirement to load a source in order to return to the previous track
     * @return a message that asks the user to load a source before attempting to return to the
     * previous track
     */
    public static String loadSourceBeforePrevMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_PREV_MESSAGE;
    }
}
