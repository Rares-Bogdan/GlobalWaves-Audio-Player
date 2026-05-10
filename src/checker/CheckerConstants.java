package checker;

/**
 * The class contains the minimum of constants needed.
 *
 * You can define your own constants here or create separate files.
 */
public final class CheckerConstants {
    private CheckerConstants() {
    }

    // checker constants
    public static final Integer BIG_TEST_POINTS = 3;
    public static final Integer MAXIMUM_ERROR_CHECKSTYLE = 30;
    public static final Integer CHECKSTYLE_POINTS = 10;
    public static final String TESTS_PATH = "input/";
    public static final String OUT_PATH = "result/out_";
    public static final String REF_PATH = "ref/ref_";
    public static final String RESULT_PATH = "result";
    public static final String OUT_FILE = "out.txt";
    public static final String ERROR_TEST = "error";
    public static final String COMPLEX_TEST = "complex";
    public static final Integer SIMPLE_TEST_POINTS = 4;
    public static final Integer ERROR_TEST_POINTS = 4;
    public static final Integer COMPLEX_TEST_POINTS = 10;
    public static final int MAX_POINTS = 100;
    public static final int FIVE_POINTS = 5;
    public static final int ZERO_POINTS = 0;
    public static final int LEN_LONGEST_TEST_NAME = 45;
    public static final String SEARCH_TYPE_SONG = "song";
    public static final String SEARCH_TYPE_PODCAST = "podcast";
    public static final String SEARCH_TYPE_PLAYLIST = "playlist";
    public static final int MAXIMUM_SEARCH_RESULT = 5;
    public static final int FORWARD_BACKWARD_TIME_SKIP = 90;
    public static final String SEARCH_COMMAND = "search";
    public static final String SELECT_COMMAND = "select";
    public static final String LOAD_COMMAND = "load";
    public static final String STATUS_COMMAND = "status";
    public static final String PLAY_PAUSE_COMMAND = "playPause";
    public static final String CREATE_PLAYLIST_COMMAND = "createPlaylist";
    public static final String LIKE_COMMAND = "like";
    public static final String ADD_REMOVE_IN_PLAYLIST_COMMAND = "addRemoveInPlaylist";
    public static final String SHOW_PLAYLISTS_COMMAND = "showPlaylists";
    public static final String SHOW_PREFERRED_SONGS_COMMAND = "showPreferredSongs";
    public static final String REPEAT_COMMAND = "repeat";
    public static final String SHUFFLE_COMMAND = "shuffle";
    public static final String NEXT_COMMAND = "next";
    public static final String PREV_COMMAND = "prev";
    public static final String FORWARD_COMMAND = "forward";
    public static final String BACKWARD_COMMAND = "backward";
    public static final String FOLLOW_COMMAND = "follow";
    public static final String SWITCH_VISIBILITY_COMMAND = "switchVisibility";
    public static final String GET_TOP_FIVE_PLAYLISTS_COMMAND = "getTop5Playlists";
    public static final String GET_TOP_FIVE_SONGS_COMMAND = "getTop5Songs";
    public static final String COMMAND_FIELD = "command";
    public static final String USER_FIELD = "user";
    public static final String TIMESTAMP_FIELD = "timestamp";
    public static final String MESSAGE_FIELD = "message";
    public static final String RESULTS_FIELD = "results";
    public static final String STATS_FIELD = "stats";
    public static final String NO_SEARCH_EXECUTED_MESSAGE =
            "Please conduct a search before making a selection.";
    public static final String TOO_HIGH_ID_MESSAGE = "The selected ID is too high.";
    public static final String SUCCESS_LOAD_MESSAGE = "Playback loaded successfully.";
    public static final String SELECT_SOURCE_MESSAGE =
            "Please select a source before attempting to load.";
    public static final String EMPTY_AUDIO_COLLECTION_MESSAGE =
            "You can't load an empty audio collection!";
    public static final String PLAYBACK_PAUSE_MESSAGE = "Playback paused successfully.";
    public static final String PLAYBACK_RESUME_MESSAGE = "Playback resumed successfully.";
    public static final String PLAYBACK_NO_SOURCE_MESSAGE =
            "Please load a source before attempting to pause or resume playback.";
    public static final String SUCCESSFUL_PLAYLIST_MESSAGE = "Playlist created successfully.";
    public static final String SAME_NAME_PLAYLIST_MESSAGE =
            "A playlist with the same name already exists.";
    public static final String LIKED_SUCCESSFULLY_MESSAGE = "Like registered successfully.";
    public static final String UNLIKED_SUCCESSFULLY_MESSAGE = "Unlike registered successfully.";
    public static final String LOAD_SOURCE_BEFORE_LIKE_MESSAGE =
            "Please load a source before liking or unliking.";
    public static final String LOADED_SOURCE_NOT_SONG_MESSAGE = "The loaded source is not a song.";
    public static final String LOAD_SOURCE_BEFORE_ADD_PLAYLIST_MESSAGE =
            "Please load a source before adding to or removing from the playlist.";
    public static final String PLAYLIST_NOT_EXIST_MESSAGE =
            "The specified playlist does not exist.";
    public static final String SUCCESSFULLY_ADD_PLAYLIST_MESSAGE =
            "Successfully added to playlist.";
    public static final String SUCCESSFULLY_REMOVE_PLAYLIST_MESSAGE =
            "Successfully removed from playlist.";
    public static final String LOAD_SOURCE_BEFORE_CHANGE_REPEAT_STATUS_MESSAGE =
            "Please load a source before setting the repeat status.";
    public static final String SHUFFLE_FUNCTION_ACTIVATED_MESSAGE =
            "Shuffle function activated successfully.";
    public static final String SHUFFLE_FUNCTION_DEACTIVATED_MESSAGE =
            "Shuffle function deactivated successfully.";
    public static final String LOADED_SOURCE_NOT_PLAYLIST_MESSAGE =
            "The loaded source is not a playlist.";
    public static final String LOAD_SOURCE_BEFORE_SHUFFLE_MESSAGE =
            "Please load a source before using the shuffle function.";
    public static final String NEXT_TRACK_SUCCESS_MESSAGE =
            "Skipped to next track successfully. The current track is ";
    public static final String LOAD_SOURCE_BEFORE_NEXT_MESSAGE =
            "Please load a source before skipping to the next track.";
    public static final String PREV_TRACK_SUCCESS_MESSAGE =
            "Returned to previous track successfully. The current track is ";
    public static final String LOAD_SOURCE_BEFORE_PREV_MESSAGE =
            "Please load a source before returning to the previous track.";
    public static final String FORWARD_SKIP_SUCCESS_MESSAGE = "Skipped forward successfully.";
    public static final String LOADED_SOURCE_NOT_PODCAST_MESSAGE =
            "The loaded source is not a podcast.";
    public static final String NO_LOADED_SOURCE_TO_FORWARD_MESSAGE =
            "Please load a source before attempting to forward.";
    public static final String BACKWARD_SKIP_SUCCESS_MESSAGE = "Rewound successfully.";
    public static final String NO_LOADED_SOURCE_TO_BACKWARD_MESSAGE =
            "Please select a source before rewinding.";
    public static final String SUCCESS_FOLLOW_MESSAGE = "Playlist followed successfully.";
    public static final String SUCCESS_UNFOLLOW_MESSAGE = "Playlist unfollowed successfully.";
    public static final String SELECTED_SOURCE_NOT_PLAYLIST_MESSAGE =
            "The selected source is not a playlist.";
    public static final String NO_SOURCE_FOLLOW_MESSAGE =
            "Please select a source before following or unfollowing.";
    public static final String NO_FOLLOW_OWN_PLAYLIST_MESSAGE =
            "You cannot follow or unfollow your own playlist.";
    public static final String VISIBILITY_STATUS_UPDATE_MESSAGE =
            "Visibility status updated successfully to ";
    public static final String TOO_HIGH_ID_PLAYLIST_MESSAGE =
            "The specified playlist ID is too high.";
    public static final String NO_REPEAT = "No Repeat";
    public static final String REPEAT_ONCE = "Repeat Once";
    public static final String REPEAT_INFINITE = "Repeat Infinite";
    public static final String REPEAT_ALL = "Repeat All";
    public static final String REPEAT_CURRENT_SONG = "Repeat Current Song";
    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";
    public static final String NO_REPEAT_STATE = "no repeat";
    public static final String REPEAT_ONCE_STATE = "repeat once";
    public static final String REPEAT_INFINITE_STATE = "repeat infinite";
    public static final String REPEAT_ALL_STATE = "repeat all";
    public static final String REPEAT_CURRENT_SONG_STATE = "repeat current song";
}
