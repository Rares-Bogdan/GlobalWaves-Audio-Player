package messages;

import checker.CheckerConstants;

public class CreatePlaylistMessage {
    public static String successPlaylistMessage() {
        return CheckerConstants.SUCCESSFUL_PLAYLIST_MESSAGE;
    }

    public static String sameNamePlaylistMessage() {
        return CheckerConstants.SAME_NAME_PLAYLIST_MESSAGE;
    }
}
