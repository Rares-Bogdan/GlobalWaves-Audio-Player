package messages;

import checker.CheckerConstants;

public final class AddRemoveInPlaylistMessage {
    private AddRemoveInPlaylistMessage() { }

    /***
     * method that prints the successfully added to playlist message
     * @return a message that shows a song has been successfully added in a playlist
     */
    public static String successfullyAddedToPlaylistMessage() {
        return CheckerConstants.SUCCESSFULLY_ADD_PLAYLIST_MESSAGE;
    }

    /***
     * method that prints the successfully removed from the playlist message
     * @return a message that shows a song has been successfully removed from a playlist
     */
    public static String successfullyRemovedFromPlaylistMessage() {
        return CheckerConstants.SUCCESSFULLY_REMOVE_PLAYLIST_MESSAGE;
    }

    /***
     * method that prints the loaded source is not a song message
     * @return a message that show the loaded source is not a song
     */
    public static String loadedSourceNotSongMessage() {
        return CheckerConstants.LOADED_SOURCE_NOT_SONG_MESSAGE;
    }

    /***
     * method that prints the playlist does not exist message
     * @return a message that shows the playlist does not exist
     */
    public static String playlistNotExistMessage() {
        return CheckerConstants.PLAYLIST_NOT_EXIST_MESSAGE;
    }

    /***
     * method that prints the requirement to load a source before other operations message
     * @return a message that asks the user to load a source before trying to add or remove a
     * source
     */
    public static String loadSourceBeforeAddOrRemoveMessage() {
        return CheckerConstants.LOAD_SOURCE_BEFORE_ADD_PLAYLIST_MESSAGE;
    }
}
