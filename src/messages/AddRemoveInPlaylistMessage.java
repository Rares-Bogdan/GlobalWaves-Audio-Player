package messages;

import checker.CheckerConstants;

public class AddRemoveInPlaylistMessage {
    public static String successfullyAddedToPlaylistMessage() {
        return CheckerConstants.SUCCESSFULLY_ADD_PLAYLIST_MESSAGE;
    }

    public static String successfullyRemovedFromPlaylistMessage() {
        return CheckerConstants.SUCCESSFULLY_REMOVE_PLAYLIST_MESSAGE;
    }

    public static String loadedSourceNotSongMessage() {
        return CheckerConstants.LOADED_SOURCE_NOT_SONG_MESSAGE;
    }

    public static String playlistNotExistMessage() {
        return CheckerConstants.PLAYLIST_NOT_EXIST_MESSAGE;
    }

    public static String loadSourceBeforeAddOrRemoveMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_ADD_PLAYLIST_MESSAGE;
    }
}
