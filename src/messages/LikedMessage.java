package messages;

import checker.CheckerConstants;

public class LikedMessage {
    /***
     *
     * @return a message that shows a like has been successfully registered
     */
    public static String likedSuccessfullyMessage() {
        return CheckerConstants.LIKED_SUCCESSFULLY_MESSAGE;
    }

    /***
     *
     * @return a message that shows an unlike has been successfully registered
     */
    public static String unlikedSuccessfullyMessage() {
        return CheckerConstants.UNLIKED_SUCCESSFULLY_MESSAGE;
    }

    /***
     *
     * @return a message that asks the user to load a source before attempting to like or unlike
     */
    public static String loadSourceBeforeLikeMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_LIKE_MESSAGE;
    }

    /***
     *
     * @return a message that shows the loaded source is not a song
     */
    public static String loadedNotSongMessage() {
        return CheckerConstants.LOADED_SOURCE_NOT_SONG_MESSAGE;
    }
}
