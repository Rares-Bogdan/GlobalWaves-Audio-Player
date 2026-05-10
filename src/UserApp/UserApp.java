package UserApp;

import fileio.input.LibraryInput;
import fileio.input.UserInput;
import musicplayer.LastLoadedSource;
import musicplayer.LastPlaylistSelected;
import musicplayer.LastPodcastSelected;
import musicplayer.MusicPlayer;
import musicplayer.playercommands.Status;
import playlist.Playlist;
import searchbar.searchresults.SearchBarPodcastResults;
import searchbar.searchresults.SearchBarSongResults;
import searchbar.selectresult.SelectResult;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class UserApp {
    private final UserInput user;
    private ArrayList<String> lastSearchResult;
    private AtomicBoolean searchedSongs;
    private AtomicBoolean selectedSong;
    private AtomicBoolean searchedPlaylist;
    private AtomicBoolean selectedPlaylist;
    private AtomicBoolean searchedPodcast;
    private AtomicBoolean selectedPodcast;
    private AtomicInteger lastIndexSelected;
    private MusicPlayer musicPlayer;
    private SearchBarSongResults searchBarSongResults;
    private SearchBarPodcastResults searchBarPodcastResults;
    private SelectResult selectResult;
    private AtomicBoolean loadedSource;
    private LastLoadedSource lastLoadedSource;
    private AtomicInteger lastPlayPauseTimestamp;
    private int currentRemainedTime;
    private ArrayList<Playlist> userPlaylists;
    private Playlist preferredSongs;
    private AtomicBoolean noLoadedSourceAfterSearch;
    private LastPodcastSelected lastPodcastSelected;
    private LastPlaylistSelected lastPlaylistSelected;
    private ArrayList<Integer> songsIndexesInPlaylist;
    private ArrayList<String> changedSongsPositionsNames;
    private ArrayList<Playlist> followedPlaylists;
    private AtomicBoolean cannotSelectYet;
    private String currentPlaylistSelected;
    private Status status;

    public UserApp(UserInput user, LibraryInput library) {
        this.user = user;
        this.lastSearchResult = new ArrayList<>();
        this.searchedSongs = new AtomicBoolean(false);
        this.selectedSong = new AtomicBoolean(false);
        this.searchedPlaylist = new AtomicBoolean(false);
        this.selectedPlaylist = new AtomicBoolean(false);
        this.searchedPodcast = new AtomicBoolean(false);
        this.selectedPodcast = new AtomicBoolean(false);
        this.lastIndexSelected = new AtomicInteger(-1);
        this.musicPlayer = new MusicPlayer(false);
        this.searchBarSongResults = new SearchBarSongResults(library.getSongs());
        this.searchBarPodcastResults = new SearchBarPodcastResults(library.getPodcasts());
        this.selectResult = new SelectResult(-1);
        this.loadedSource = new AtomicBoolean(false);
        this.lastLoadedSource = null;
        this.lastPlayPauseTimestamp = new AtomicInteger(0);
        this.currentRemainedTime = 0;
        this.userPlaylists = new ArrayList<>();
        this.preferredSongs = new Playlist();
        this.noLoadedSourceAfterSearch = new AtomicBoolean(false);
        this.lastPodcastSelected = new LastPodcastSelected();
        this.lastPlaylistSelected = new LastPlaylistSelected();
        this.songsIndexesInPlaylist = new ArrayList<>();
        this.changedSongsPositionsNames = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
        this.cannotSelectYet = new AtomicBoolean(false);
        this.currentPlaylistSelected = "";
        this.status = new Status();
    }

    public UserInput getUser() {
        return user;
    }

    public ArrayList<String> getLastSearchResult() {
        return lastSearchResult;
    }

    public void setLastSearchResult(ArrayList<String> lastSearchResult) {
        this.lastSearchResult = lastSearchResult;
    }

    public AtomicBoolean getSearchedSongs() {
        return searchedSongs;
    }

    public void setSearchedSongs(AtomicBoolean searchedSongs) {
        this.searchedSongs = searchedSongs;
    }

    public AtomicBoolean getSelectedSong() {
        return selectedSong;
    }

    public void setSelectedSong(AtomicBoolean selectedSong) {
        this.selectedSong = selectedSong;
    }

    public AtomicBoolean getSearchedPlaylist() {
        return searchedPlaylist;
    }

    public void setSearchedPlaylist(AtomicBoolean searchedPlaylist) {
        this.searchedPlaylist = searchedPlaylist;
    }

    public AtomicBoolean getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public void setSelectedPlaylist(AtomicBoolean selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }

    public AtomicBoolean getSearchedPodcast() {
        return searchedPodcast;
    }

    public void setSearchedPodcast(AtomicBoolean searchedPodcast) {
        this.searchedPodcast = searchedPodcast;
    }

    public AtomicBoolean getSelectedPodcast() {
        return selectedPodcast;
    }

    public void setSelectedPodcast(AtomicBoolean selectedPodcast) {
        this.selectedPodcast = selectedPodcast;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public void setMusicPlayer(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    public AtomicInteger getLastIndexSelected() {
        return lastIndexSelected;
    }

    public void setLastIndexSelected(AtomicInteger lastIndexSelected) {
        this.lastIndexSelected = lastIndexSelected;
    }

    public SearchBarSongResults getSearchBarSongResults() {
        return searchBarSongResults;
    }

    public void setSearchBarSongResults(SearchBarSongResults searchBarSongResults) {
        this.searchBarSongResults = searchBarSongResults;
    }

    public SearchBarPodcastResults getSearchBarPodcastResults() {
        return searchBarPodcastResults;
    }

    public void setSearchBarPodcastResults(SearchBarPodcastResults searchBarPodcastResults) {
        this.searchBarPodcastResults = searchBarPodcastResults;
    }

    public SelectResult getSelectResult() {
        return selectResult;
    }

    public void setSelectResult(SelectResult selectResult) {
        this.selectResult = selectResult;
    }

    public AtomicBoolean getLoadedSource() {
        return loadedSource;
    }

    public void setLoadedSource(AtomicBoolean loadedSource) {
        this.loadedSource = loadedSource;
    }

    public LastLoadedSource getLastLoadedSource() {
        return lastLoadedSource;
    }

    public void setLastLoadedSource(LastLoadedSource lastLoadedSource) {
        this.lastLoadedSource = lastLoadedSource;
    }

    public AtomicInteger getLastPlayPauseTimestamp() {
        return lastPlayPauseTimestamp;
    }

    public void setLastPlayPauseTimestamp(AtomicInteger lastPlayPauseTimestamp) {
        this.lastPlayPauseTimestamp = lastPlayPauseTimestamp;
    }

    public int getCurrentRemainedTime() {
        return currentRemainedTime;
    }

    public void setCurrentRemainedTime(int currentRemainedTime) {
        this.currentRemainedTime = currentRemainedTime;
    }

    public ArrayList<Playlist> getUserPlaylists() {
        return userPlaylists;
    }

    public void setUserPlaylists(ArrayList<Playlist> userPlaylists) {
        this.userPlaylists = userPlaylists;
    }

    public Playlist getPreferredSongs() {
        return preferredSongs;
    }

    public void setPreferredSongs(Playlist preferredSongs) {
        this.preferredSongs = preferredSongs;
    }

    public AtomicBoolean getNoLoadedSourceAfterSearch() {
        return noLoadedSourceAfterSearch;
    }

    public void setNoLoadedSourceAfterSearch(AtomicBoolean noLoadedSourceAfterSearch) {
        this.noLoadedSourceAfterSearch = noLoadedSourceAfterSearch;
    }

    public LastPodcastSelected getLastPodcastSelected() {
        return lastPodcastSelected;
    }

    public void setLastPodcastSelected(LastPodcastSelected lastPodcastSelected) {
        this.lastPodcastSelected = lastPodcastSelected;
    }

    public LastPlaylistSelected getLastPlaylistSelected() {
        return lastPlaylistSelected;
    }

    public void setLastPlaylistSelected(LastPlaylistSelected lastPlaylistSelected) {
        this.lastPlaylistSelected = lastPlaylistSelected;
    }

    public ArrayList<Integer> getSongsIndexesInPlaylist() {
        return songsIndexesInPlaylist;
    }

    public void setSongsIndexesInPlaylist(ArrayList<Integer> songsIndexesInPlaylist) {
        this.songsIndexesInPlaylist = songsIndexesInPlaylist;
    }

    public ArrayList<String> getChangedSongsPositionsNames() {
        return changedSongsPositionsNames;
    }

    public void setChangedSongsPositionsNames(ArrayList<String> changedSongsPositionsNames) {
        this.changedSongsPositionsNames = changedSongsPositionsNames;
    }

    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    public void setFollowedPlaylists(ArrayList<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    public AtomicBoolean getCannotSelectYet() {
        return cannotSelectYet;
    }

    public void setCannotSelectYet(AtomicBoolean cannotSelectYet) {
        this.cannotSelectYet = cannotSelectYet;
    }

    public String getCurrentPlaylistSelected() {
        return currentPlaylistSelected;
    }

    public void setCurrentPlaylistSelected(String currentPlaylistSelected) {
        this.currentPlaylistSelected = currentPlaylistSelected;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatusPlayPause(boolean value) {
        this.status.setPaused(value);
    }
}
