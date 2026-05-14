package musicplayer;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class LastPlaylistSelected {
    private AtomicInteger songIndex;
    private AtomicInteger currentPositionInPlaylist;
    private int totalDurationOfPlaylist;
    private int totalNumberOfSongsInPlaylist;
    private ArrayList<Integer> songsStartsPositions;
    private String currentSongName;
    private String playlistName;
    private AtomicBoolean hasRepeatCurrentSongState;
    private AtomicInteger currentSongToBeRepeatedIndex;

    /***
     * default constructor for the last playlist selected by the user
     */
    public LastPlaylistSelected() {
        this.songIndex = new AtomicInteger(-1);
        this.currentPositionInPlaylist = new AtomicInteger(-1);
        this.totalDurationOfPlaylist = 0;
        this.totalNumberOfSongsInPlaylist = 0;
        this.songsStartsPositions = new ArrayList<>();
        this.currentSongName = "";
        this.playlistName = "";
        this.hasRepeatCurrentSongState = new AtomicBoolean(false);
        this.currentSongToBeRepeatedIndex = new AtomicInteger(-1);
    }

    /***
     * songIndex getter
     * @return the current song the playlist is positioned to
     */
    public AtomicInteger getSongIndex() {
        return songIndex;
    }

    /***
     * songIndex setter
     * @param songIndex sets the song index for the playlist
     */
    public void setSongIndex(final AtomicInteger songIndex) {
        this.songIndex = songIndex;
    }

    /***
     * currentPositionInPlaylist getter
     * @return the current position in the playlist, relative to the total time of the playlist
     */
    public AtomicInteger getCurrentPositionInPlaylist() {
        return currentPositionInPlaylist;
    }

    /***
     * currentPositionInPlaylist setter
     * @param currentPositionInPlaylist sets the current position in playlist, relative to the
     *                                  total time of the playlist
     */
    public void setCurrentPositionInPlaylist(final AtomicInteger currentPositionInPlaylist) {
        this.currentPositionInPlaylist = currentPositionInPlaylist;
    }

    /***
     * totalDurationOfPlaylist getter
     * @return the total time of the playlist
     */
    public int getTotalDurationOfPlaylist() {
        return totalDurationOfPlaylist;
    }

    /***
     * totalDurationOfPlaylist setter
     * @param totalDurationOfPlaylist sets the total time of the playlist
     */
    public void setTotalDurationOfPlaylist(final int totalDurationOfPlaylist) {
        this.totalDurationOfPlaylist = totalDurationOfPlaylist;
    }

    /***
     * totalNumberOfSongsInPlaylist getter
     * @return total number of songs in the last playlist selected by a user
     */
    public int getTotalNumberOfSongsInPlaylist() {
        return totalNumberOfSongsInPlaylist;
    }

    /***
     * totalNumberOfSongsInPlaylist setter
     * @param totalNumberOfSongsInPlaylist sets the total number of songs in the last playlist
     *                                     selected by a user
     */
    public void setTotalNumberOfSongsInPlaylist(final int totalNumberOfSongsInPlaylist) {
        this.totalNumberOfSongsInPlaylist = totalNumberOfSongsInPlaylist;
    }

    /***
     * getSongsStartsPositions getter
     * @return the start position of every song in the last playlist selected by a user
     */
    public ArrayList<Integer> getSongsStartsPositions() {
        return songsStartsPositions;
    }

    /***
     * getSongsStartsPositions setter
     * @param songsStartsPositions sets the start position of every song in the last playlist
     *                             selected by a user
     */
    public void setSongsStartsPositions(final ArrayList<Integer> songsStartsPositions) {
        this.songsStartsPositions = songsStartsPositions;
    }

    /***
     * currentSongName getter
     * @return the name of the current song the playlist is positioned at
     */
    public String getCurrentSongName() {
        return currentSongName;
    }

    /***
     * currentSongName setter
     * @param currentSongName sets currentSongName for the last playlist selected by a user
     */
    public void setCurrentSongName(final String currentSongName) {
        this.currentSongName = currentSongName;
    }

    /***
     * playlistName getter
     * @return the name of the last playlist selected by a user
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /***
     * playlistName setter
     * @param playlistName sets the name of the last playlist selected by a user
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /***
     * hasRepeatCurrentSongState getter
     * @return an atomic boolean that checks if the playlist has the repeat once state for the
     * current song
     */
    public AtomicBoolean getHasRepeatCurrentSongState() {
        return hasRepeatCurrentSongState;
    }

    /***
     * hasRepeatCurrentSongState setter
     * @param hasRepeatCurrentSongState sets hasRepeatCurrentSongState for the last playlist
     *                                  selected by a user
     */
    public void setHasRepeatCurrentSongState(final AtomicBoolean hasRepeatCurrentSongState) {
        this.hasRepeatCurrentSongState = hasRepeatCurrentSongState;
    }

    /***
     * currentSongToBeRepeatedIndex getter
     * @return the index for the song that has repeat once status
     */
    public AtomicInteger getCurrentSongToBeRepeatedIndex() {
        return currentSongToBeRepeatedIndex;
    }

    /***
     * currentSongToBeRepeatedIndex setter
     * @param currentSongToBeRepeatedIndex sets currentSongToBeRepeatedIndex for the last playlist
     *                                     selected by a user
     */
    public void setCurrentSongToBeRepeatedIndex(final AtomicInteger currentSongToBeRepeatedIndex) {
        this.currentSongToBeRepeatedIndex = currentSongToBeRepeatedIndex;
    }
}
