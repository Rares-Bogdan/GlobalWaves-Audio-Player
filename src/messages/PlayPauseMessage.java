package messages;

import checker.CheckerConstants;

public class PlayPauseMessage {
    /***
     * method that prints an audio source was paused successfully
     * @return a message that shows a playback has been paused successfully
     */
    public static String pauseSuccessMessage() {
        return CheckerConstants.PLAYBACK_PAUSE_MESSAGE;
    }

    /***
     * method that prints an audio source was resumed successfully
     * @return a message that shows a playback has been resumed successfully
     */
    public static String resumeSuccessMessage() {
        return CheckerConstants.PLAYBACK_RESUME_MESSAGE;
    }

    /***
     * method that prints the requirement to load a source in order to pause or resume
     * @return a message that asks the user to load a source before attempting to pause or resume
     */
    public static String noSourcePlaybackMessage() {
        return CheckerConstants.PLAYBACK_NO_SOURCE_MESSAGE;
    }
}
