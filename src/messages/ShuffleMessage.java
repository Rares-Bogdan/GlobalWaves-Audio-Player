package messages;

import checker.CheckerConstants;

public class ShuffleMessage {
    /***
     * method that prints the successful activation of shuffling of a playlist
     * @return a message that shows the shuffle function has been activated successfully
     */
    public static String shuffleFunctionActivatedMessage() {
        return CheckerConstants.SHUFFLE_FUNCTION_ACTIVATED_MESSAGE;
    }

    /***
     * method that prints the successful deactivation of shuffling of a playlist
     * @return a message that shows the shuffle function has been deactivated successfully
     */
    public static String shuffleFunctionDeactivatedMessage() {
        return CheckerConstants.SHUFFLE_FUNCTION_DEACTIVATED_MESSAGE;
    }

    /***
     * method that prints the loaded source is not a playlist
     * @return a message that shows the loaded source is not a playlist
     */
    public static String loadedSourceNotPlaylistMessage() {
        return CheckerConstants.LOADED_SOURCE_NOT_PLAYLIST_MESSAGE;
    }

    /***
     * method that prints the requirement to load a source before shuffling
     * @return a message that asks the user to load a source before attempting to shuffle
     */
    public static String loadSourceBeforeShuffleMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_SHUFFLE_MESSAGE;
    }
}
