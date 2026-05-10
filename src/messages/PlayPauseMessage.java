package messages;

import checker.CheckerConstants;

public class PlayPauseMessage {
    public static String pauseSuccessMessage() {
        return CheckerConstants.PLAYBACK_PAUSE_MESSAGE;
    }

    public static String resumeSuccessMessage() {
        return CheckerConstants.PLAYBACK_RESUME_MESSAGE;
    }

    public static String noSourcePlaybackMessage() {
        return CheckerConstants.PLAYBACK_NO_SOURCE_MESSAGE;
    }
}
