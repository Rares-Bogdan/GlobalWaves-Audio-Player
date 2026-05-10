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

    public AtomicInteger getSongIndex() {
        return songIndex;
    }

    public void setSongIndex(AtomicInteger songIndex) {
        this.songIndex = songIndex;
    }

    public AtomicInteger getCurrentPositionInPlaylist() {
        return currentPositionInPlaylist;
    }

    public void setCurrentPositionInPlaylist(AtomicInteger currentPositionInPlaylist) {
        this.currentPositionInPlaylist = currentPositionInPlaylist;
    }

    public int getTotalDurationOfPlaylist() {
        return totalDurationOfPlaylist;
    }

    public void setTotalDurationOfPlaylist(int totalDurationOfPlaylist) {
        this.totalDurationOfPlaylist = totalDurationOfPlaylist;
    }

    public int getTotalNumberOfSongsInPlaylist() {
        return totalNumberOfSongsInPlaylist;
    }

    public void setTotalNumberOfSongsInPlaylist(int totalNumberOfSongsInPlaylist) {
        this.totalNumberOfSongsInPlaylist = totalNumberOfSongsInPlaylist;
    }

    public ArrayList<Integer> getSongsStartsPositions() {
        return songsStartsPositions;
    }

    public void setSongsStartsPositions(ArrayList<Integer> songsStartsPositions) {
        this.songsStartsPositions = songsStartsPositions;
    }

    public String getCurrentSongName() {
        return currentSongName;
    }

    public void setCurrentSongName(String currentSongName) {
        this.currentSongName = currentSongName;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public AtomicBoolean getHasRepeatCurrentSongState() {
        return hasRepeatCurrentSongState;
    }

    public void setHasRepeatCurrentSongState(AtomicBoolean hasRepeatCurrentSongState) {
        this.hasRepeatCurrentSongState = hasRepeatCurrentSongState;
    }

    public AtomicInteger getCurrentSongToBeRepeatedIndex() {
        return currentSongToBeRepeatedIndex;
    }

    public void setCurrentSongToBeRepeatedIndex(AtomicInteger currentSongToBeRepeatedIndex) {
        this.currentSongToBeRepeatedIndex = currentSongToBeRepeatedIndex;
    }
}
