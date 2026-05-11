package messages;

import checker.CheckerConstants;

public final class CreatePlaylistMessage {

    private CreatePlaylistMessage() { }

    /***
     *
     * @return a message that shows a playlist has been successfully created
     */
    public static String successPlaylistMessage() {
        return CheckerConstants.SUCCESSFUL_PLAYLIST_MESSAGE;
    }

    /***
     *
     * @return a message that shows a playlist with the same name already exists
     */
    public static String sameNamePlaylistMessage() {
        return CheckerConstants.SAME_NAME_PLAYLIST_MESSAGE;
    }
}
