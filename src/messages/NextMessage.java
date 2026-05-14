package messages;

import checker.CheckerConstants;

public class NextMessage {
    /***
     * method that prints the successful skip to the next track message
     * @param trackName the name of the track we skipped to
     * @return a message that shows we skipped successfully to the next track
     */
    public static String nextTrackSuccessMessage(final String trackName) {
        return CheckerConstants.NEXT_TRACK_SUCCESS_MESSAGE + trackName + ".";
    }

    /***
     * method that prints the requirement to load a source in order to skip message
     * @return a message that asks the user to load a source before attempting to skip to the next
     * track
     */
    public static String loadSourceBeforeNextMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_NEXT_MESSAGE;
    }
}
