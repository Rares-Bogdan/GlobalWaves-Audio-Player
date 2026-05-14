package messages;

import checker.CheckerConstants;

public class FollowMessage {
    /***
     * method that prints the successful follow of a playlist message
     * @return a message that shows a playlist has been successfully followed
     */
    public static String successFollowMessage() {
        return CheckerConstants.SUCCESS_FOLLOW_MESSAGE;
    }

    /***
     * methord that prints the successful unfollow of a playlist message
     * @return a message that shows a playlist has been successfully unfollowed
     */
    public static String successUnfollowMessage() {
        return CheckerConstants.SUCCESS_UNFOLLOW_MESSAGE;
    }

    /***
     * method that prints the selected source is not a playlist
     * @return a message that shows the selected source is not a playlist
     */
    public static String selectedSourceNotPlaylistMessage() {
        return CheckerConstants.SELECTED_SOURCE_NOT_PLAYLIST_MESSAGE;
    }

    /***
     * method that prints the requirement to load a source in order to follow message
     * @return a message that asks the user to select a source before attempting to follow or
     * unfollow
     */
    public static String noSourceFollowMessage() {
        return CheckerConstants.NO_SOURCE_FOLLOW_MESSAGE;
    }

    /***
     * method that prints the impossibility to follow or unfollow own playlist message
     * @return a message that shows a user cannot follow or unfollow said user's own playlist
     */
    public static String noFollowYourOwnPlaylistMessage() {
        return CheckerConstants.NO_FOLLOW_OWN_PLAYLIST_MESSAGE;
    }
}
