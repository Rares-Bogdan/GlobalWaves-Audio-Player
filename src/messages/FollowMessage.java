package messages;

import checker.CheckerConstants;

public class FollowMessage {
    public static String successFollowMessage() {
        return CheckerConstants.SUCCESS_FOLLOW_MESSAGE;
    }

    public static String successUnfollowMessage() {
        return CheckerConstants.SUCCESS_UNFOLLOW_MESSAGE;
    }

    public static String selectedSourceNotPlaylistMessage() {
        return CheckerConstants.SELECTED_SOURCE_NOT_PLAYLIST_MESSAGE;
    }

    public static String noSourceFollowMessage() {
        return CheckerConstants.NO_SOURCE_FOLLOW_MESSAGE;
    }

    public static String noFollowYourOwnPlaylistMessage() {
        return CheckerConstants.NO_FOLLOW_OWN_PLAYLIST_MESSAGE;
    }
}
