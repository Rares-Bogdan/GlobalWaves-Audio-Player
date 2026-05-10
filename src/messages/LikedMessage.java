package messages;

import checker.CheckerConstants;

public class LikedMessage {
    public static String likedSuccessfullyMessage() {
        return CheckerConstants.LIKED_SUCCESSFULLY_MESSAGE;
    }

    public static String unlikedSuccessfullyMessage() {
        return CheckerConstants.UNLIKED_SUCCESSFULLY_MESSAGE;
    }

    public static String loadSourceBeforeLikeMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_LIKE_MESSAGE;
    }

    public static String loadedNotSongMessage() {
        return CheckerConstants.LOADED_SOURCE_NOT_SONG_MESSAGE;
    }
}
