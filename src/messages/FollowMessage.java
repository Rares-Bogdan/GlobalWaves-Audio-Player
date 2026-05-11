package messages;

import checker.CheckerConstants;

public class FollowMessage {
    /***
     *
     * @return a message that shows a playlist has been successfully followed
     */
    public static String successFollowMessage() {
        return CheckerConstants.SUCCESS_FOLLOW_MESSAGE;
    }

    /***
     *
     * @return a message that shows a playlist has been successfully unfollowed
     */
    public static String successUnfollowMessage() {
        return CheckerConstants.SUCCESS_UNFOLLOW_MESSAGE;
    }

    /***
     *
     * @return a message that shows the selected source is not a playlist
     */
    public static String selectedSourceNotPlaylistMessage() {
        return CheckerConstants.SELECTED_SOURCE_NOT_PLAYLIST_MESSAGE;
    }

    /***
     *
     * @return a message that asks the user to select a source before attempting to follow or
     * unfollow
     */
    public static String noSourceFollowMessage() {
        return CheckerConstants.NO_SOURCE_FOLLOW_MESSAGE;
    }

    /***
     *
     * @return a message that shows a user cannot follow or unfollow said user's own playlist
     */
    public static String noFollowYourOwnPlaylistMessage() {
        return CheckerConstants.NO_FOLLOW_OWN_PLAYLIST_MESSAGE;
    }
}
