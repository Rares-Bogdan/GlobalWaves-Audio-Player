package messages;

import checker.CheckerConstants;

public class ShuffleMessage {
    public static String shuffleFunctionActivatedMessage() {
        return CheckerConstants.SHUFFLE_FUNCTION_ACTIVATED_MESSAGE;
    }

    public static String shuffleFunctionDeactivatedMessage() {
        return CheckerConstants.SHUFFLE_FUNCTION_DEACTIVATED_MESSAGE;
    }

    public static String loadedSourceNotPlaylistMessage() {
        return CheckerConstants.LOADED_SOURCE_NOT_PLAYLIST_MESSAGE;
    }

    public static String loadSourceBeforeShuffleMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_SHUFFLE_MESSAGE;
    }
}
