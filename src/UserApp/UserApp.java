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

    /***
     * constructor for UserApp class
     * @param user the user
     * @param library the library of audio sources
     */
    public UserApp(final UserInput user, final LibraryInput library) {
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

    /***
     * user getter
     * @return user
     */
    public UserInput getUser() {
        return user;
    }

    /***
     * lastSearchResult getter
     * @return lastSearchResult
     */
    public ArrayList<String> getLastSearchResult() {
        return lastSearchResult;
    }

    /***
     * lastSearchResult setter
     * @param lastSearchResult sets the value for lastSearchResult
     */
    public void setLastSearchResult(final ArrayList<String> lastSearchResult) {
        this.lastSearchResult = lastSearchResult;
    }

    /***
     * searchedSongs getter
     * @return searchedSongs
     */
    public AtomicBoolean getSearchedSongs() {
        return searchedSongs;
    }

    /***
     * selectedSong getter
     * @return selectedSong
     */
    public AtomicBoolean getSelectedSong() {
        return selectedSong;
    }

    /***
     * selectedSong setter
     * @param selectedSong sets the value for selectedSong
     */
    public void setSelectedSong(final AtomicBoolean selectedSong) {
        this.selectedSong = selectedSong;
    }

    /***
     * searchedPlaylist getter
     * @return searchedPlaylist
     */
    public AtomicBoolean getSearchedPlaylist() {
        return searchedPlaylist;
    }

    /***
     * selectedPlaylist getter
     * @return selectedPlaylist
     */
    public AtomicBoolean getSelectedPlaylist() {
        return selectedPlaylist;
    }

    /***
     * selectedPlaylist setter
     * @param selectedPlaylist sets the value for selectedPlaylist
     */
    public void setSelectedPlaylist(final AtomicBoolean selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }

    /***
     * searchedPodcast getter
     * @return searchedPodcast
     */
    public AtomicBoolean getSearchedPodcast() {
        return searchedPodcast;
    }

    /***
     * selectedPodcast getter
     * @return selectedPodcast
     */
    public AtomicBoolean getSelectedPodcast() {
        return selectedPodcast;
    }

    /***
     * selectedPodcast setter
     * @param selectedPodcast sets the value for selectedPodcast
     */
    public void setSelectedPodcast(final AtomicBoolean selectedPodcast) {
        this.selectedPodcast = selectedPodcast;
    }

    /***
     * musicPlayer getter
     * @return musicPlayer
     */
    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    /***
     * lastIndexSelected getter
     * @return lastIndexSelected
     */
    public AtomicInteger getLastIndexSelected() {
        return lastIndexSelected;
    }

    /***
     * loadedSource getter
     * @return loadedSource
     */
    public AtomicBoolean getLoadedSource() {
        return loadedSource;
    }

    /***
     * loadedSource setter
     * @param loadedSource sets the value for loadedSource
     */
    public void setLoadedSource(final AtomicBoolean loadedSource) {
        this.loadedSource = loadedSource;
    }

    /***
     * lastLoadedSource getter
     * @return lastLoadedSource
     */
    public LastLoadedSource getLastLoadedSource() {
        return lastLoadedSource;
    }

    /***
     * lastLoadedSource setter
     * @param lastLoadedSource sets the value for lastLoadedSource
     */
    public void setLastLoadedSource(final LastLoadedSource lastLoadedSource) {
        this.lastLoadedSource = lastLoadedSource;
    }

    /***
     * currentRemainedTime getter
     * @return currentRemainedTime
     */
    public int getCurrentRemainedTime() {
        return currentRemainedTime;
    }

    /***
     * currentRemainedTime setter
     * @param currentRemainedTime sets the value for currentRemainedTime
     */
    public void setCurrentRemainedTime(final int currentRemainedTime) {
        this.currentRemainedTime = currentRemainedTime;
    }

    /***
     * userPlaylists getter
     * @return userPlaylists
     */
    public ArrayList<Playlist> getUserPlaylists() {
        return userPlaylists;
    }

    /***
     * preferredSongs getter
     * @return preferredSongs
     */
    public Playlist getPreferredSongs() {
        return preferredSongs;
    }

    /***
     * noLoadedSourceAfterSearch getter
     * @return noLoadedSourceAfterSearch
     */
    public AtomicBoolean getNoLoadedSourceAfterSearch() {
        return noLoadedSourceAfterSearch;
    }

    /***
     * noLoadedSourceAfterSearch setter
     * @param noLoadedSourceAfterSearch sets the value for noLoadedSourceAfterSearch
     */
    public void setNoLoadedSourceAfterSearch(final AtomicBoolean noLoadedSourceAfterSearch) {
        this.noLoadedSourceAfterSearch = noLoadedSourceAfterSearch;
    }

    /***
     * lastPodcastSelected getter
     * @return lastPodcastSelected
     */
    public LastPodcastSelected getLastPodcastSelected() {
        return lastPodcastSelected;
    }

    /***
     * lastPodcastSelected setter
     * @param lastPodcastSelected sets the value for lastPodcastSelected
     */
    public void setLastPodcastSelected(final LastPodcastSelected lastPodcastSelected) {
        this.lastPodcastSelected = lastPodcastSelected;
    }

    /***
     * lastPlaylistSelected getter
     * @return lastPlaylistSelected
     */
    public LastPlaylistSelected getLastPlaylistSelected() {
        return lastPlaylistSelected;
    }

    /***
     * lastPlaylistSelected setter
     * @param lastPlaylistSelected sets the value for lastPlaylistSelected
     */
    public void setLastPlaylistSelected(final LastPlaylistSelected lastPlaylistSelected) {
        this.lastPlaylistSelected = lastPlaylistSelected;
    }

    /***
     * songsIndexesInPlaylist getter
     * @return songsIndexesInPlaylist
     */
    public ArrayList<Integer> getSongsIndexesInPlaylist() {
        return songsIndexesInPlaylist;
    }

    /***
     * songsIndexesInPlaylist setter
     * @param songsIndexesInPlaylist sets the value for songsIndexesInPlaylist
     */
    public void setSongsIndexesInPlaylist(final ArrayList<Integer> songsIndexesInPlaylist) {
        this.songsIndexesInPlaylist = songsIndexesInPlaylist;
    }

    /***
     * changedSongsPositionsNames getter
     * @return changedSongsPositionsNames
     */
    public ArrayList<String> getChangedSongsPositionsNames() {
        return changedSongsPositionsNames;
    }

    /***
     * changedSongsPositionsNames setter
     * @param changedSongsPositionsNames sets the value for changesSongsPositionsNames
     */
    public void setChangedSongsPositionsNames(final ArrayList<String> changedSongsPositionsNames) {
        this.changedSongsPositionsNames = changedSongsPositionsNames;
    }

    /***
     * followedPlaylists getter
     * @return followedPlaylists
     */
    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    /***
     * cannotSelectYet getter
     * @return cannotSelectYet
     */
    public AtomicBoolean getCannotSelectYet() {
        return cannotSelectYet;
    }

    /***
     * cannotSelectYet setter
     * @param cannotSelectYet sets the value for cannotSelectYet
     */
    public void setCannotSelectYet(final AtomicBoolean cannotSelectYet) {
        this.cannotSelectYet = cannotSelectYet;
    }

    /***
     * currentPlaylistSelected getter
     * @return currentPlaylistSelected
     */
    public String getCurrentPlaylistSelected() {
        return currentPlaylistSelected;
    }

    /***
     * currentPlaylistSelected setter
     * @param currentPlaylistSelected sets the value for currentPlaylistSelected
     */
    public void setCurrentPlaylistSelected(final String currentPlaylistSelected) {
        this.currentPlaylistSelected = currentPlaylistSelected;
    }

    /***
     * status getter
     * @return status
     */
    public Status getStatus() {
        return status;
    }

    /***
     * status setter
     * @param status sets the value for status
     */
    public void setStatus(final Status status) {
        this.status = status;
    }
}
