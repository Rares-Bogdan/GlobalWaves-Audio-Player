package messages;

import checker.CheckerConstants;

public class PlayPauseMessage {
    /***
     *
     * @return a message that shows a playback has been paused successfully
     */
    public static String pauseSuccessMessage() {
        return CheckerConstants.PLAYBACK_PAUSE_MESSAGE;
    }

    /***
     *
     * @return a message that shows a playback has been resumed successfully
     */
    public static String resumeSuccessMessage() {
        return CheckerConstants.PLAYBACK_RESUME_MESSAGE;
    }

    /***
     *
     * @return a message that asks the user to load a source before attempting to pause or resume
     */
    public static String noSourcePlaybackMessage() {
        return CheckerConstants.PLAYBACK_NO_SOURCE_MESSAGE;
    }
}
