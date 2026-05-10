package main;

import UserApp.UserApp;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import fileio.input.EpisodeInput;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import musicplayer.LastLoadedSource;
import musicplayer.LastPlaylistSelected;
import musicplayer.MusicPlayer;
import musicplayer.LastPodcastSelected;
import musicplayer.playercommands.*;
import musicplayer.playercommands.loadresults.LoadPlaylistResult;
import musicplayer.playercommands.loadresults.LoadPodcastResult;
import musicplayer.playercommands.loadresults.LoadSongResult;
import playlist.Playlist;
import playlist.PlaylistJsonNode;
import playlist.playlistcommands.FollowPlaylist;
import playlist.playlistcommands.SwitchVisibility;
import searchbar.searchresults.SearchBarPlaylistsResult;
import searchbar.searchresults.SearchBarPodcastResults;
import searchbar.searchresults.SearchBarSongResults;
import searchbar.selectresult.SelectResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static messages.AddRemoveInPlaylistMessage.*;
import static messages.CreatePlaylistMessage.sameNamePlaylistMessage;
import static messages.CreatePlaylistMessage.successPlaylistMessage;
import static messages.LikedMessage.loadSourceBeforeLikeMessage;
import static messages.LikedMessage.loadedNotSongMessage;
import static messages.LoadMessage.selectSourceMessage;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);

        ArrayNode outputs = objectMapper.createArrayNode();

        // TODO add your implementation

        ArrayList<Command> commands = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH
                + filePathInput), objectMapper.getTypeFactory().constructCollectionType(ArrayList.
                class, Command.class));
        ArrayList<UserApp> usersData = new ArrayList<>(library.getUsers().size());
        for (int currentUser = 0; currentUser < library.getUsers().size(); currentUser++) {
            UserApp userApp = new UserApp(library.getUsers().get(currentUser), library);
            usersData.add(userApp);
        }
        ArrayList<Playlist> allPlaylists = new ArrayList<>();
        ArrayList<Integer> likesForEachSongInLibrary = new ArrayList<>(library.getUsers().size());
        for (int currentIndexOfLikes = 0; currentIndexOfLikes < library.getSongs().size();
             currentIndexOfLikes++) {
            likesForEachSongInLibrary.add(0);
        }
        int position = -1;
        int currentCommandIndex = 0;
        ForwardTracker forwardTracker = new ForwardTracker(false);
        BackwardTracker backwardTracker = new BackwardTracker(false);
        for (Command currentCommand : commands) {
            if (currentCommand.getUsername() != null) {
                position = getUserPositionByUsername(usersData, currentCommand.getUsername());
            }
            if (usersData.get(position).getMusicPlayer().isPlayPause() && usersData.get(position).
                    getLastLoadedSource() != null && currentCommandIndex > 0) {
                usersData.get(position).setCurrentRemainedTime(usersData.get(position).
                        getLastLoadedSource().getDuration() - (currentCommand.getTimestamp() -
                        commands.get(currentCommandIndex - 1).getTimestamp()));
                if (usersData.get(position).getCurrentRemainedTime() < 0 && !usersData.get(
                        position).getSelectedPodcast().get() && !usersData.get(position).
                        getSelectedPlaylist().get() && usersData.get(position).getStatus().
                        getRepeat().compareTo(CheckerConstants.NO_REPEAT) == 0) {
                    usersData.get(position).getLastLoadedSource().setDuration(0);
                    Status refreshStatus = new Status();
                    usersData.get(position).setStatus(refreshStatus);
                    usersData.get(position).setCurrentRemainedTime(0);
                } else if (usersData.get(position).getCurrentRemainedTime() < 0 && !usersData.get(
                        position).getSelectedPodcast().get() && !usersData.get(position).
                        getSelectedPlaylist().get() && usersData.get(position).getStatus().
                        getRepeat().compareTo(CheckerConstants.NO_REPEAT) != 0 && usersData.get(
                                position).getSelectedSong().get()) {
                    updateSongByRepeatState(usersData.get(position), library);
                } else if (usersData.get(position).getCurrentRemainedTime() >= 0 && !usersData.get(
                        position).getSelectedPodcast().get() && !usersData.get(position).
                        getSelectedPlaylist().get()) {
                    usersData.get(position).getStatus().setRemainedTime(usersData.get(position).
                            getCurrentRemainedTime());
                    usersData.get(position).getLastLoadedSource().setDuration(usersData.
                            get(position).getCurrentRemainedTime());
                } else if (usersData.get(position).getSelectedPodcast().get()) {
                    updatePodcast(currentCommand, commands.get(currentCommandIndex - 1), usersData.
                            get(position), library);
                } else if (usersData.get(position).getSelectedPlaylist().get()) {
                    updatePlaylist(currentCommand, commands.get(currentCommandIndex - 1),
                    usersData.get(position), getSearchablePlaylistForUser(allPlaylists,
                    usersData.get(position).getUser().getUsername()));
                }
            }
            switch(currentCommand.getCommand()) {
                case CheckerConstants.SEARCH_COMMAND -> {
                    usersData.get(position).setLastLoadedSource(new LastLoadedSource("", 0, new
                            AtomicBoolean(false)));
                    usersData.get(position).setLoadedSource(new AtomicBoolean(false));
                    usersData.get(position).setSelectedSong(new AtomicBoolean(false));
                    usersData.get(position).setSelectedPodcast(new AtomicBoolean(false));
                    usersData.get(position).setSelectedPlaylist(new AtomicBoolean(false));
                    usersData.get(position).setNoLoadedSourceAfterSearch(new AtomicBoolean(true));
                    usersData.get(position).getMusicPlayer().setPlayPause(false);
                    usersData.get(position).getStatus().setRepeat(CheckerConstants.NO_REPEAT);
                    forwardTracker.setHasBeenForwarded(false);
                    backwardTracker.setHasBeenBackwarded(false);
                    usersData.get(position).setCannotSelectYet(new AtomicBoolean(false));
                    usersData.get(position).setCurrentPlaylistSelected("");
                    ArrayList<String> lastSearchResult = searchCommand(currentCommand, library, objectMapper,
                            usersData.get(position).getLastSearchResult(), usersData.get(position).
                                    getSearchedSongs(), usersData.get(position).
                                    getSearchedPodcast(), usersData.get(position).
                                    getSearchedPlaylist(), allPlaylists, outputs, usersData.get(
                                            position));
                    usersData.get(position).setLastSearchResult(lastSearchResult);
                }
                case CheckerConstants.SELECT_COMMAND ->
                    selectCommand(currentCommand, objectMapper, usersData.get(position).
                                    getLastSearchResult(), usersData.get(position).
                                    getSearchedSongs(), usersData.get(position).getSelectedSong(),
                            usersData.get(position).getSelectedPodcast(), usersData.get(position).
                                    getSearchedPodcast(), usersData.get(position).
                                    getSearchedPlaylist(), usersData.get(position).
                                    getSelectedPlaylist(), outputs, usersData.get(position).
                                    getLastIndexSelected(), usersData.get(position));
                case CheckerConstants.LOAD_COMMAND -> {
                    LastLoadedSource lastLoadedSource = loadCommand(usersData.get(position).
                                    getSelectedSong(), usersData.get(position).
                                    getLastSearchResult(), usersData.get(position).
                                    getLastIndexSelected(), objectMapper, currentCommand,
                            outputs, usersData.get(position).getSelectedPodcast(), usersData.
                                    get(position).getSelectedPlaylist(), usersData.get(
                                            position).getNoLoadedSourceAfterSearch(),
                            getSearchablePlaylistForUser(allPlaylists, usersData.get(position).
                                    getUser().getUsername()), usersData.get(position).
                                    getLastPodcastSelected(), usersData.get(position).
                                    getLastPlaylistSelected(), usersData.get(position).
                                    getMusicPlayer(), usersData.get(position).
                                    getLoadedSource(), library, usersData.get(position).
                                    getLastLoadedSource(), usersData.get(position).
                                    getStatus(), usersData.get(position));
                    usersData.get(position).setLastLoadedSource(lastLoadedSource);
                }
                case CheckerConstants.STATUS_COMMAND -> {
                    int previousCommandTimestamp = -1;
                    if (currentCommandIndex > 0) {
                        if (commands.get(currentCommandIndex - 1).getCommand().compareTo(
                                CheckerConstants.STATUS_COMMAND) == 0 && currentCommand.
                                getCommand().compareTo(commands.get(currentCommandIndex - 1).
                                        getCommand()) == 0) {
                            previousCommandTimestamp = commands.get(currentCommandIndex - 1).
                                    getTimestamp();
                        }
                    }
                    int generalPreviousCommandTimestamp = -1;
                    if (currentCommandIndex > 0) {
                        generalPreviousCommandTimestamp = commands.get(currentCommandIndex - 1).
                                getTimestamp();
                    }
                    statusCommand(objectMapper, currentCommand, usersData.get(position).
                            getLastLoadedSource().getName(), usersData.get(position).
                            getLastLoadedSource().getDuration(), usersData.get(position).
                            getStatus(), outputs, usersData.get(position).
                            getCurrentRemainedTime(), usersData.get(position),
                            previousCommandTimestamp);
                }
                case CheckerConstants.PLAY_PAUSE_COMMAND -> {
                    forwardTracker.setHasBeenForwarded(false);
                    backwardTracker.setHasBeenBackwarded(false);
                    playPauseCommand(objectMapper, currentCommand, usersData.get(position).
                            getLoadedSource(), usersData.get(position).getMusicPlayer(), outputs,
                            usersData.get(position).getStatus());
                }
                case CheckerConstants.CREATE_PLAYLIST_COMMAND ->
                    createPlaylistCommand(objectMapper, currentCommand, usersData.get(position).
                            getUserPlaylists(), allPlaylists, outputs);
                case CheckerConstants.LIKE_COMMAND ->
                    likeCommand(objectMapper, currentCommand, usersData.get(position).
                            getLoadedSource(), usersData.get(position).getSelectedSong(),
                            usersData.get(position).getNoLoadedSourceAfterSearch(),
                            usersData.get(position).getPreferredSongs(), usersData.get(position).
                                    getLastLoadedSource(), library, outputs, likesForEachSongInLibrary);
                case CheckerConstants.ADD_REMOVE_IN_PLAYLIST_COMMAND ->
                    addRemoveInPlaylistCommand(objectMapper, currentCommand, usersData.
                            get(position).getLoadedSource(), usersData.get(position).
                            getSelectedSong(), usersData.get(position).getLastLoadedSource(),
                            usersData.get(position).getUserPlaylists(), library, outputs);
                case CheckerConstants.SHOW_PLAYLISTS_COMMAND ->
                    showPlaylistsCommand(objectMapper, currentCommand, usersData.get(position),
                            outputs);
                case CheckerConstants.SHOW_PREFERRED_SONGS_COMMAND ->
                    showPreferredSongsCommand(objectMapper, currentCommand, usersData.
                                    get(position), outputs);
                case CheckerConstants.REPEAT_COMMAND ->
                    repeatCommand(objectMapper, currentCommand, usersData.get(position), outputs);
                case CheckerConstants.SHUFFLE_COMMAND ->
                    shuffleCommand(objectMapper, currentCommand, usersData.get(position), outputs,
                            getSearchablePlaylistForUser(allPlaylists, usersData.get(position).
                                    getUser().getUsername()));
                case CheckerConstants.NEXT_COMMAND ->
                    nextCommand(objectMapper, currentCommand, usersData.get(position), outputs,
                            getSearchablePlaylistForUser(allPlaylists, usersData.get(position).
                                    getUser().getUsername()), library);
                case CheckerConstants.PREV_COMMAND ->
                    prevCommand(objectMapper, currentCommand, usersData.get(position), outputs,
                            getSearchablePlaylistForUser(allPlaylists, usersData.get(position).
                                    getUser().getUsername()), library);
                case CheckerConstants.FORWARD_COMMAND -> {
                    if (backwardTracker.isHasBeenBackwarded()) {
                        backwardTracker.setHasBeenBackwarded(false);
                    }
                    forwardCommand(objectMapper, currentCommand, usersData.get(position), outputs,
                            library, forwardTracker);
                }
                case CheckerConstants.BACKWARD_COMMAND -> {
                    if (forwardTracker.isHasBeenForwarded()) {
                        forwardTracker.setHasBeenForwarded(false);
                    }
                    backwardCommand(objectMapper, currentCommand, usersData.get(position), outputs,
                            library, backwardTracker);
                }
                case CheckerConstants.FOLLOW_COMMAND ->
                    followCommand(objectMapper, currentCommand, usersData.get(position), outputs,
                            getSearchablePlaylistForUser(allPlaylists, usersData.get(position).
                            getUser().getUsername()), library, allPlaylists);
                case CheckerConstants.SWITCH_VISIBILITY_COMMAND ->
                    switchVisibilityCommand(objectMapper, currentCommand, usersData.get(position).
                            getUserPlaylists(), outputs);
                case CheckerConstants.GET_TOP_FIVE_PLAYLISTS_COMMAND ->
                        getTop5PlaylistsCommand(objectMapper, currentCommand, allPlaylists,
                                outputs);
                case CheckerConstants.GET_TOP_FIVE_SONGS_COMMAND ->
                        getTop5SongsCommand(objectMapper, currentCommand, library,
                                likesForEachSongInLibrary, outputs);

            }
            currentCommandIndex++;
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }

    public static ArrayList<String> searchCommand(Command currentCommand, LibraryInput library,
                              ObjectMapper objectMapper, ArrayList<String> lastSearchResult,
                              AtomicBoolean searchedSongs, AtomicBoolean searchedPodcast,
                                                  AtomicBoolean searchedPlaylists,
                                                  ArrayList<Playlist> allPlaylists,
                                                  ArrayNode outputs, UserApp userApp) {
        switch(currentCommand.getType()) {
            case CheckerConstants.SEARCH_TYPE_SONG -> {
                SearchBarSongResults searchBarSongResults = new SearchBarSongResults(library.getSongs());
                ObjectNode objectNode = searchBarSongResults.searchResult(objectMapper,
                        currentCommand);
                outputs.add(objectNode);
                lastSearchResult = searchBarSongResults.getLastSearchResult();

                if (!lastSearchResult.isEmpty()) {
                    searchedSongs.set(true);
                    searchedPodcast.set(false);
                    searchedPlaylists.set(false);
                    userApp.setCannotSelectYet(new AtomicBoolean(false));
                }
            }
            case CheckerConstants.SEARCH_TYPE_PODCAST -> {
                SearchBarPodcastResults searchBarPodcastResults = new
                        SearchBarPodcastResults(library.getPodcasts());
                ObjectNode objectNode = searchBarPodcastResults.searchResult(
                        objectMapper, currentCommand);
                outputs.add(objectNode);
                lastSearchResult = searchBarPodcastResults.getLastSearchResult();
                if (!lastSearchResult.isEmpty()) {
                    searchedPodcast.set(true);
                    searchedSongs.set(false);
                    searchedPlaylists.set(false);
                    userApp.setCannotSelectYet(new AtomicBoolean(false));
                }
            }
            case CheckerConstants.SEARCH_TYPE_PLAYLIST -> {
                ArrayList<Playlist> searchablePlaylists = getSearchablePlaylistForUser(
                        allPlaylists, currentCommand.getUsername());
                SearchBarPlaylistsResult searchBarPlaylistsResult = new
                        SearchBarPlaylistsResult(searchablePlaylists);
                ObjectNode objectNode = searchBarPlaylistsResult.searchResult(objectMapper, currentCommand);
                outputs.add(objectNode);
                lastSearchResult = searchBarPlaylistsResult.getLastSearchResult();
                if (!lastSearchResult.isEmpty()) {
                    searchedPlaylists.set(true);
                    searchedSongs.set(false);
                    searchedPodcast.set(false);
                    userApp.setCannotSelectYet(new AtomicBoolean(false));
                }
            }
        }
        return lastSearchResult;
    }

    public static void selectCommand(Command currentCommand, ObjectMapper objectMapper,
                                     ArrayList<String> lastSearchResult,
                                     AtomicBoolean searchedSongs, AtomicBoolean selectedSong,
                                     AtomicBoolean selectedPodcast, AtomicBoolean searchedPodcast,
                                     AtomicBoolean searchedPlaylists, AtomicBoolean selectedPlaylist,
                                     ArrayNode outputs, AtomicInteger lastIndexSelected,
                                     UserApp userApp) {
        SelectResult selectResult = new SelectResult(currentCommand.getItemNumber());
        ObjectNode objectNode = selectResult.getObjectNodeForSelectResult(
                objectMapper, currentCommand, lastSearchResult, userApp.getCannotSelectYet().
                        get());
        boolean hasBeenSelectedSuccessfully = selectResult.hasBeenSelectedSuccessfully();
        outputs.add(objectNode);
        if (searchedSongs.get() && hasBeenSelectedSuccessfully) {
            selectedSong.set(true);
            selectedPodcast.set(false);
            selectedPlaylist.set(false);
            lastIndexSelected.set(currentCommand.getItemNumber() - 1);
        } else if (searchedPodcast.get() && hasBeenSelectedSuccessfully) {
            selectedPodcast.set(true);
            selectedSong.set(false);
            selectedPlaylist.set(false);
            lastIndexSelected.set(currentCommand.getItemNumber() - 1);
        } else if (searchedPlaylists.get() && hasBeenSelectedSuccessfully) {
            selectedPlaylist.set(true);
            selectedSong.set(true);
            selectedPodcast.set(false);
            lastIndexSelected.set(currentCommand.getItemNumber() - 1);
            userApp.setCurrentPlaylistSelected(lastSearchResult.get(lastIndexSelected.get()));
        }
    }

    public static LastLoadedSource loadCommand(AtomicBoolean selectedSong, ArrayList<String> lastSearchResult,
                                   AtomicInteger lastIndexSelected, ObjectMapper objectMapper,
                                   Command currentCommand, ArrayNode outputs,
                                   AtomicBoolean selectedPodcast, AtomicBoolean selectedPlaylist,
                                   AtomicBoolean noLoadedSourceAfterSearch,
                                   ArrayList<Playlist> searchablePlaylists,
                                   LastPodcastSelected lastPodcastSelected,
                                   LastPlaylistSelected lastPlaylistSelected,
                                   MusicPlayer musicPlayer, AtomicBoolean loadedSource,
                                   LibraryInput library, LastLoadedSource lastLoadedSource,
                                               Status status, UserApp userApp) {
        LastLoadedSource lastLoadedSourceBackup = lastLoadedSource;
        if (!lastSearchResult.isEmpty()) {
            for (int currentSong = 0; currentSong < library.getSongs().size(); currentSong++) {
                if (lastIndexSelected.get() <lastSearchResult.size()) {
                    if (library.getSongs().get(currentSong).getName().compareTo(lastSearchResult.
                            get(lastIndexSelected.get())) == 0) {
                        lastLoadedSource = new LastLoadedSource(lastSearchResult.
                                get(lastIndexSelected.get()), library.getSongs().get(currentSong).
                                getDuration(), new AtomicBoolean(false));
                        break;
                    }
                }
            }
            for (Playlist searchablePlaylist : searchablePlaylists) {
                if (lastIndexSelected.get() < lastSearchResult.size()) {
                    if (searchablePlaylist.getName().compareTo(lastSearchResult.
                            get(lastIndexSelected.get())) == 0) {
                        lastPlaylistSelected.setPlaylistName(searchablePlaylist.getName());
                        String songName = searchablePlaylist.getSongs().getFirst().
                                getName();
                        int duration = searchablePlaylist.getSongs().getFirst().
                                getDuration(), totalDuration = 0;
                        for (SongInput currentSong : searchablePlaylist.getSongs()) {
                            totalDuration += currentSong.getDuration();
                        }
                        lastPlaylistSelected.setTotalDurationOfPlaylist(totalDuration);
                        lastPlaylistSelected.setTotalNumberOfSongsInPlaylist(searchablePlaylist.
                                getSongs().size());
                        ArrayList<Integer> currentStartPositions = new ArrayList<>();
                        currentStartPositions.add(0);
                        for (int currentStart = 1; currentStart < searchablePlaylist.getSongs().size();
                             currentStart++) {
                            int currentStartPosition = 0;
                            for (int songsBeforeCurrentOne = 0; songsBeforeCurrentOne < currentStart;
                                 songsBeforeCurrentOne++) {
                                currentStartPosition += searchablePlaylist.getSongs().get(
                                        songsBeforeCurrentOne).getDuration();
                            }
                            currentStartPositions.add(currentStartPosition);
                        }
                        lastPlaylistSelected.setSongsStartsPositions(currentStartPositions);
                        lastPlaylistSelected.setCurrentSongName(songName);
                        lastPlaylistSelected.setCurrentPositionInPlaylist(new AtomicInteger(0));
                        lastLoadedSource = new LastLoadedSource(songName, duration, new AtomicBoolean(
                                false));
                        break;
                    }
                }
            }
            for (int currentPodcast = 0; currentPodcast < library.getPodcasts().size();
                 currentPodcast++) {
                if (lastIndexSelected.get() < lastSearchResult.size()) {
                    if (library.getPodcasts().get(currentPodcast).getName().compareTo(lastSearchResult.
                            get(lastIndexSelected.get())) == 0) {
                        lastPodcastSelected.setPodcastName(library.getPodcasts().get(currentPodcast).
                                getName());
                        String podcastEpisodeName;
                        int duration, totalDuration = 0;
                        for (EpisodeInput currentEpisode : library.getPodcasts().get(currentPodcast).
                                getEpisodes()) {
                            totalDuration += currentEpisode.getDuration();
                        }
                        lastPodcastSelected.setTotalDurationOfPodcast(totalDuration);
                        lastPodcastSelected.setTotalNumberOfEpisodes(library.getPodcasts().get(
                                currentPodcast).getEpisodes().size());
                        ArrayList<Integer> currentStartPositions = new ArrayList<>();
                        currentStartPositions.add(0);
                        for (int currentStart = 1; currentStart < library.getPodcasts().get(
                                currentPodcast).getEpisodes().size(); currentStart++) {
                            int currentStartPosition = 0;
                            for (int episodeBeforeCurrentOne = 0; episodeBeforeCurrentOne
                                    < currentStart; episodeBeforeCurrentOne++) {
                                currentStartPosition += library.getPodcasts().get(currentPodcast).
                                        getEpisodes().get(episodeBeforeCurrentOne).getDuration();
                            }
                            currentStartPositions.add(currentStartPosition);
                        }
                        lastPodcastSelected.setEpisodesStartsPositions(currentStartPositions);
                        if (lastPodcastSelected.getEpisodeIndex().get() == -1 && lastPodcastSelected.
                                getCurrentPositionOfPodcast().get() == -1) {
                            podcastEpisodeName = library.getPodcasts().get(currentPodcast).
                                    getEpisodes().getFirst().getName();
                            lastPodcastSelected.setCurrentEpisodeName(podcastEpisodeName);
                            duration = library.getPodcasts().get(currentPodcast).getEpisodes().
                                    getFirst().getDuration();
                            lastPodcastSelected.setCurrentPositionOfPodcast(new AtomicInteger(0));
                            lastLoadedSource = new LastLoadedSource(podcastEpisodeName, duration, new
                                    AtomicBoolean(false));
                        } else if (lastPodcastSelected.getEpisodeIndex().get() > -1
                                && lastPodcastSelected.getCurrentPositionOfPodcast().get() > -1) {
                            podcastEpisodeName = library.getPodcasts().get(currentPodcast).
                                    getEpisodes().get(lastPodcastSelected.getEpisodeIndex().get()).
                                    getName();
                            lastPodcastSelected.setCurrentEpisodeName(podcastEpisodeName);
                            duration = lastPodcastSelected.getCurrentPositionOfPodcast().get();
                            lastLoadedSource = new LastLoadedSource(podcastEpisodeName, duration, new
                                    AtomicBoolean(false));
                        }
                        break;
                    }
                }
            }
        }

        if (selectedSong.get()) {
            LoadSongResult loadSongResult = new LoadSongResult(selectedSong, lastSearchResult.get(lastIndexSelected.get()));
            if (loadedSource.get()) {
                ObjectNode objectNode = getDefaultObjectNodeParameters(objectMapper,
                        currentCommand);
                objectNode.put(CheckerConstants.MESSAGE_FIELD, selectSourceMessage());
                outputs.add(objectNode);
                lastLoadedSource = lastLoadedSourceBackup;
            } else {
                ObjectNode objectNode = loadSongResult.loadResult(objectMapper, currentCommand, musicPlayer);
                outputs.add(objectNode);
                loadedSource.set(true);
                status.setPaused(false);
                noLoadedSourceAfterSearch.set(false);
                userApp.setCannotSelectYet(new AtomicBoolean(true));
            }
        } else if (selectedPodcast.get()) {
            LoadPodcastResult loadPodcastResult = new LoadPodcastResult(selectedPodcast,
                    lastSearchResult.get(lastIndexSelected.get()));
            if (loadedSource.get()) {
                ObjectNode objectNode = getDefaultObjectNodeParameters(objectMapper,
                        currentCommand);
                objectNode.put(CheckerConstants.MESSAGE_FIELD, selectSourceMessage());
                outputs.add(objectNode);
                lastLoadedSource = lastLoadedSourceBackup;
            } else {
                ObjectNode objectNode = loadPodcastResult.loadResult(objectMapper, currentCommand, musicPlayer);
                outputs.add(objectNode);
                loadedSource.set(true);
                status.setPaused(false);
                noLoadedSourceAfterSearch.set(false);
                userApp.setCannotSelectYet(new AtomicBoolean(true));
            }
        } else if (selectedPlaylist.get()) {
            LoadPlaylistResult loadPlaylistResult = new LoadPlaylistResult(lastSearchResult.get(
                    lastIndexSelected.get()), selectedPlaylist);
            if (loadedSource.get()) {
                ObjectNode objectNode = getDefaultObjectNodeParameters(objectMapper,
                        currentCommand);
                objectNode.put(CheckerConstants.MESSAGE_FIELD, selectSourceMessage());
                outputs.add(objectNode);
                lastLoadedSource = lastLoadedSourceBackup;
            } else {
                ObjectNode objectNode = loadPlaylistResult.loadResult(objectMapper, currentCommand, musicPlayer);
                outputs.add(objectNode);
                loadedSource.set(true);
                status.setPaused(false);
                noLoadedSourceAfterSearch.set(false);
                userApp.setCannotSelectYet(new AtomicBoolean(true));
            }
        } else if (!selectedSong.get() && !selectedPlaylist.get() && !selectedPodcast.get()) {
            ObjectNode objectNode = getDefaultObjectNodeParameters(objectMapper,
                    currentCommand);
            objectNode.put(CheckerConstants.MESSAGE_FIELD, selectSourceMessage());
            outputs.add(objectNode);
            lastLoadedSource = lastLoadedSourceBackup;
        }
        return lastLoadedSource;
    }

    public static void statusCommand(ObjectMapper objectMapper, Command currentCommand,
                                     String name, int remainedTime, Status status,
                                     ArrayNode outputs, int currentRemainedTime, UserApp userApp,
                                     int previousCommandTimestamp) {
        if (currentRemainedTime <= 0 && status.getRepeat().compareTo(CheckerConstants.NO_REPEAT)
                == 0) {
            status.setName("");
            status.setRemainedTime(0);
        } else if ((!userApp.getSelectedPlaylist().get() && !userApp.getSelectedPodcast().get()
                && !userApp.getSelectedSong().get()) || !userApp.getLoadedSource().get()) {
            status.setName("");
            status.setRemainedTime(0);
            status.setPaused(true);
            userApp.getMusicPlayer().setPlayPause(false);
        } else {
            status.setName(name);
            status.setRemainedTime(currentRemainedTime);
        }

        if (userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get() > 0 && userApp.
                getSelectedPodcast().get()) {
            status.setName(userApp.getLastPodcastSelected().getCurrentEpisodeName());
            int updateRemainedTime;
            if (userApp.getLastPodcastSelected().getEpisodeIndex().get() != userApp.
                    getLastPodcastSelected().getTotalNumberOfEpisodes() - 1) {
                updateRemainedTime = userApp.getLastPodcastSelected().getEpisodesStartsPositions().
                        get(userApp.getLastPodcastSelected().getEpisodeIndex().get() + 1) - userApp.
                        getLastPodcastSelected().getCurrentPositionOfPodcast().get();
            } else {
                updateRemainedTime = userApp.getLastPodcastSelected().getTotalDurationOfPodcast()
                        - userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get();
            }
            status.setRemainedTime(updateRemainedTime);
        }

        if (userApp.getLastPlaylistSelected().getCurrentPositionInPlaylist().get() > 0 && userApp.
                getSelectedPlaylist().get()) {
            if (status.isShuffle() && userApp.getSelectedPlaylist().get() && !userApp.
                    getLastPlaylistSelected().getPlaylistName().isEmpty()) {
                for (int currentSong = userApp.getLastPlaylistSelected().
                        getTotalNumberOfSongsInPlaylist() - 1; currentSong >= 0 ; currentSong--) {
                    if (userApp.getLastPlaylistSelected().getCurrentPositionInPlaylist().get()
                            >= userApp.getLastPlaylistSelected().getSongsStartsPositions().get(
                            currentSong)) {
                        status.setName(userApp.getChangedSongsPositionsNames().get(currentSong));
                        break;
                    }
                }
            } else {
                status.setName(userApp.getLastPlaylistSelected().getCurrentSongName());
            }
            int updateRemainedTime;
            if (userApp.getLastPlaylistSelected().getSongIndex().get() != userApp.
                    getLastPlaylistSelected().getTotalNumberOfSongsInPlaylist() - 1) {
                updateRemainedTime = userApp.getLastPlaylistSelected().getSongsStartsPositions().
                        get(userApp.getLastPlaylistSelected().getSongIndex().get() + 1) - userApp.
                        getLastPlaylistSelected().getCurrentPositionInPlaylist().get();
            }
            else {
                updateRemainedTime = userApp.getLastPlaylistSelected().getTotalDurationOfPlaylist()
                        - userApp.getLastPlaylistSelected().getCurrentPositionInPlaylist().get();
            }
            status.setRemainedTime(updateRemainedTime);
            if (previousCommandTimestamp > -1) {
                if (userApp.getLastPlaylistSelected().getCurrentPositionInPlaylist().get()
                        + (currentCommand.getTimestamp() - previousCommandTimestamp) > userApp.
                        getLastPlaylistSelected().getTotalDurationOfPlaylist()
                        && status.getRepeat().compareTo(CheckerConstants.REPEAT_ALL) != 0) {
                    status.setName("");
                    status.setRemainedTime(0);
                    status.setPaused(true);
                    userApp.getMusicPlayer().setPlayPause(false);
                }
            }
        }

        ObjectNode objectNode = status.statusResult(objectMapper, currentCommand);
        outputs.add(objectNode);
    }

    public static void playPauseCommand(ObjectMapper objectMapper, Command currentCommand,
                                        AtomicBoolean loadedSource,
                                        MusicPlayer musicPlayer, ArrayNode outputs,
                                        Status status) {
        if (loadedSource.get()) {
            musicPlayer.setPlayPause(!musicPlayer.isPlayPause());
        }

        ObjectNode objectNode = musicPlayer.playPauseResult(objectMapper, currentCommand,
                loadedSource, status);
        outputs.add(objectNode);
    }

    public static void createPlaylistCommand(ObjectMapper objectMapper, Command currentCommand,
                                             ArrayList<Playlist> userPlaylists,
                                             ArrayList<Playlist> allPlaylists, ArrayNode outputs) {
        Playlist newPlaylist = new Playlist(new ArrayList<>(), true, currentCommand.
                getPlaylistName(), 0);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        boolean alreadyExists = false;
        for (Playlist currentPlaylist : userPlaylists) {
            if (currentPlaylist.getName().compareTo(newPlaylist.getName()) == 0) {
                alreadyExists = true;
                break;
            }
        }
        if (alreadyExists) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, sameNamePlaylistMessage());
        } else {
            userPlaylists.add(newPlaylist);
            userPlaylists.getLast().setOwner(currentCommand.getUsername());
            allPlaylists.add(newPlaylist);
            objectNode.put(CheckerConstants.MESSAGE_FIELD, successPlaylistMessage());
        }
        outputs.add(objectNode);
    }

    public static void likeCommand(ObjectMapper objectMapper, Command currentCommand,
                                   AtomicBoolean loadedSource, AtomicBoolean selectedSong,
                                   AtomicBoolean noLoadedSourceAfterSearch,
                                   Playlist preferredSongs, LastLoadedSource lastLoadedSource,
                                   LibraryInput library, ArrayNode outputs,
                                   ArrayList<Integer> likesForEachSongInLibrary) {
        AtomicBoolean likedOrUnliked = new AtomicBoolean(false);
        for (SongInput currentSong : preferredSongs.getSongs()) {
            if (currentSong.getName().compareTo(lastLoadedSource.getName()) == 0 && selectedSong.
                    get()) {
                likedOrUnliked.set(true);
                break;
            }
        }

        int songPosition = -1;
        for (int currentSong = 0; currentSong < library.getSongs().size(); currentSong++) {
            if (lastLoadedSource.getName().compareTo(library.getSongs().get(currentSong).
                    getName()) == 0 && selectedSong.get()) {
                songPosition = currentSong;
                break;
            }
        }
        if (songPosition == -1 && !selectedSong.get() && !noLoadedSourceAfterSearch.get()) {
            ObjectNode objectNode = getDefaultObjectNodeParameters(objectMapper, currentCommand);
            objectNode.put(CheckerConstants.MESSAGE_FIELD, loadedNotSongMessage());
            outputs.add(objectNode);
        } else if (noLoadedSourceAfterSearch.get()) {
            ObjectNode objectNode = getDefaultObjectNodeParameters(objectMapper, currentCommand);
            objectNode.put(CheckerConstants.MESSAGE_FIELD, loadSourceBeforeLikeMessage());
            outputs.add(objectNode);
        } else {
            for (int currentSong = 0; currentSong < library.getSongs().size(); currentSong++) {
                if (library.getSongs().get(currentSong).getName().compareTo(lastLoadedSource.
                        getName()) == 0) {
                    if (likedOrUnliked.get()) {
                        likesForEachSongInLibrary.set(currentSong, likesForEachSongInLibrary.get(
                                currentSong) - 1);
                    } else {
                        likesForEachSongInLibrary.set(currentSong, likesForEachSongInLibrary.get(
                                currentSong) + 1);
                    }
                    break;
                }
            }
            if (songPosition > -1) {
                Like newLike = new Like(loadedSource, selectedSong, likedOrUnliked, library.getSongs().
                        get(songPosition));
                ObjectNode objectNode = newLike.likedResult(objectMapper, currentCommand, preferredSongs);
                outputs.add(objectNode);
            }
        }

    }

    public static void addRemoveInPlaylistCommand(ObjectMapper objectMapper,
                                                  Command currentCommand,
                                                  AtomicBoolean loadedSource,
                                                  AtomicBoolean selectedSong,
                                                  LastLoadedSource lastLoadedSource,
                                                  ArrayList<Playlist> userPlaylist,
                                                  LibraryInput library, ArrayNode outputs) {
        ObjectNode objectNode = getDefaultObjectNodeParameters(objectMapper, currentCommand);
        if (currentCommand.getPlaylistId() - 1 >= userPlaylist.size()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, playlistNotExistMessage());
        } else {
            int songPosition= -1;
            for (int currentSong = 0; currentSong < library.getSongs().size(); currentSong++) {
                if (library.getSongs().get(currentSong).getName().compareTo(lastLoadedSource.getName())
                        == 0 && selectedSong.get()) {
                    songPosition = currentSong;
                    break;
                }
            }

            boolean isAlreadyInPlaylist = false;
            int positionInPlaylist = -1, currentSongPosition = 0;
            for (SongInput currentSong : userPlaylist.get(currentCommand.getPlaylistId() - 1).
                    getSongs()) {
                if (songPosition > -1) {
                    if (currentSong.getName().compareTo(library.getSongs().get(songPosition).getName())
                            == 0 && selectedSong.get()) {
                        isAlreadyInPlaylist = true;
                        positionInPlaylist = currentSongPosition;
                        break;
                    }
                }
                currentSongPosition++;
            }
            if (!loadedSource.get()) {
                objectNode.put(CheckerConstants.MESSAGE_FIELD,
                        loadSourceBeforeAddOrRemoveMessage());
            } else if (!selectedSong.get()) {
                objectNode.put(CheckerConstants.MESSAGE_FIELD, loadedSourceNotSongMessage());
            } else if (isAlreadyInPlaylist) {
                userPlaylist.get(currentCommand.getPlaylistId() - 1).getSongs().remove(
                        userPlaylist.get(currentCommand.getPlaylistId() - 1).getSongs().get(
                                positionInPlaylist));
                objectNode.put(CheckerConstants.MESSAGE_FIELD,
                        successfullyRemovedFromPlaylistMessage());
            } else {
                userPlaylist.get(currentCommand.getPlaylistId() - 1).getSongs().add(library.
                        getSongs().get(songPosition));
                objectNode.put(CheckerConstants.MESSAGE_FIELD,
                        successfullyAddedToPlaylistMessage());
            }
        }
        outputs.add(objectNode);
    }

    public static void showPlaylistsCommand(ObjectMapper objectMapper, Command currentCommand,
                                            UserApp userApp, ArrayNode outputs) {
        ObjectNode objectNode = getDefaultObjectNodeParameters(objectMapper, currentCommand);
        ArrayList<ArrayList<String>> userPlaylistsSongNames = new ArrayList<>();
        for (int currentPlaylist = 0; currentPlaylist < userApp.getUserPlaylists().size();
             currentPlaylist++) {
            userPlaylistsSongNames.add(new ArrayList<>());
            for (SongInput currentSong : userApp.getUserPlaylists().get(currentPlaylist).
                    getSongs()) {
                userPlaylistsSongNames.get(currentPlaylist).add(currentSong.getName());
            }
        }
        ArrayList<PlaylistJsonNode> playlistJsonNodes = new ArrayList<>();
        int currentPlaylistIndex = 0;
        for (Playlist currentPlaylist : userApp.getUserPlaylists()) {
            String visibilityString;
            if (currentPlaylist.isVisibility()) {
                visibilityString = CheckerConstants.PUBLIC;
            } else {
                visibilityString = CheckerConstants.PRIVATE;
            }
            playlistJsonNodes.add(new PlaylistJsonNode(currentPlaylist.getName(),
                    userPlaylistsSongNames.get(currentPlaylistIndex), visibilityString,
                    currentPlaylist.getFollowers()));
            currentPlaylistIndex++;
        }

        JsonNode jsonNode = objectMapper.valueToTree(playlistJsonNodes);
        objectNode.set(CheckerConstants.RESULT_PATH, jsonNode);
        outputs.add(objectNode);
    }

    public static void showPreferredSongsCommand(ObjectMapper objectMapper, Command currentCommand,
                                                 UserApp userApp, ArrayNode outputs) {
        ObjectNode objectNode = getDefaultObjectNodeParameters(objectMapper, currentCommand);
        ArrayList<String> preferredSongsNames = new ArrayList<>();
        for (SongInput currentSong : userApp.getPreferredSongs().getSongs()) {
            preferredSongsNames.add(currentSong.getName());
        }
        JsonNode jsonNode = objectMapper.valueToTree(preferredSongsNames);
        objectNode.set(CheckerConstants.RESULT_PATH, jsonNode);
        outputs.add(objectNode);
    }

    public static void repeatCommand(ObjectMapper objectMapper, Command currentCommand,
                                     UserApp userApp, ArrayNode outputs) {
        boolean hasLoadedSource = (userApp.getSelectedSong().get() || userApp.getSelectedPlaylist().
                get() || userApp.getSelectedPodcast().get()) && userApp.getLoadedSource().get();
        Repeat repeat = new Repeat(userApp.getStatus().getRepeat().toLowerCase(), hasLoadedSource);
        if ((userApp.getSelectedSong().get() && !userApp.getLastLoadedSource().getName().
                isEmpty()) && !userApp.getSelectedPlaylist().get() || (userApp.
                getSelectedPodcast().get() && !userApp.getLastPodcastSelected().getPodcastName().
                isEmpty())) {
            switch(repeat.getRepeatState()) {
                case CheckerConstants.NO_REPEAT_STATE -> {
                    userApp.getStatus().setRepeat(CheckerConstants.REPEAT_ONCE);
                    repeat.setRepeatState(CheckerConstants.REPEAT_ONCE_STATE);
                    userApp.getLastLoadedSource().setHasRepeatOnceState(new AtomicBoolean(true));
                }
                case CheckerConstants.REPEAT_ONCE_STATE -> {
                    userApp.getStatus().setRepeat(CheckerConstants.REPEAT_INFINITE);
                    repeat.setRepeatState(CheckerConstants.REPEAT_INFINITE_STATE);
                    userApp.getLastLoadedSource().setHasRepeatOnceState(new AtomicBoolean(false));
                }
                case CheckerConstants.REPEAT_INFINITE_STATE -> {
                    userApp.getStatus().setRepeat(CheckerConstants.NO_REPEAT);
                    repeat.setRepeatState(CheckerConstants.NO_REPEAT_STATE);
                }
            }
        } else if (userApp.getSelectedPlaylist().get() && !userApp.getLastPlaylistSelected().
                getPlaylistName().isEmpty()) {
            switch(repeat.getRepeatState()) {
                case CheckerConstants.NO_REPEAT_STATE -> {
                    userApp.getStatus().setRepeat(CheckerConstants.REPEAT_ALL);
                    repeat.setRepeatState(CheckerConstants.REPEAT_ALL_STATE);
                }
                case CheckerConstants.REPEAT_ALL_STATE -> {
                    userApp.getStatus().setRepeat(CheckerConstants.REPEAT_CURRENT_SONG);
                    repeat.setRepeatState(CheckerConstants.REPEAT_CURRENT_SONG_STATE);
                    userApp.getLastPlaylistSelected().setHasRepeatCurrentSongState(new
                            AtomicBoolean(true));
                    userApp.getLastPlaylistSelected().setCurrentSongToBeRepeatedIndex(new
                            AtomicInteger(userApp.getLastPlaylistSelected().getSongIndex().get()));
                }
                case CheckerConstants.REPEAT_CURRENT_SONG_STATE -> {
                    userApp.getStatus().setRepeat(CheckerConstants.NO_REPEAT);
                    repeat.setRepeatState(CheckerConstants.NO_REPEAT_STATE);
                    userApp.getLastPlaylistSelected().setHasRepeatCurrentSongState(new
                            AtomicBoolean(false));
                    userApp.getLastPlaylistSelected().setCurrentSongToBeRepeatedIndex(new
                            AtomicInteger(-1));
                }
            }
        }
        ObjectNode objectNode = repeat.repeatResult(objectMapper, currentCommand);
        outputs.add(objectNode);
    }

    public static void shuffleCommand(ObjectMapper objectMapper, Command currentCommand,
                                      UserApp userApp, ArrayNode outputs,
                                      ArrayList<Playlist> searchablePlaylists) {
        if (!userApp.getLastPlaylistSelected().getPlaylistName().isEmpty() && userApp.
                getSelectedPlaylist().get()) {
            ArrayList<Integer> songIndexes = new ArrayList<>();
            ArrayList<SongInput> changedPositionsSongs = new ArrayList<>();
            ArrayList<SongInput> songsInPlaylist = new ArrayList<>();
            ArrayList<String> changedPositionsSongsNames = new ArrayList<>();
            int playlistSize = -1;
            for (Playlist currentPlaylist : searchablePlaylists) {
                if (currentPlaylist.getName().compareTo(userApp.getLastPlaylistSelected().
                        getPlaylistName()) == 0) {
                    for (int currentSong = 0; currentSong < currentPlaylist.getSongs().size();
                    currentSong++) {
                        songIndexes.add(currentSong);
                    }
                    userApp.setSongsIndexesInPlaylist(songIndexes);
                    playlistSize = currentPlaylist.getSongs().size();
                    songsInPlaylist = currentPlaylist.getSongs();
                    break;
                }
            }
            userApp.getStatus().setShuffle(!userApp.getStatus().isShuffle());
            if (userApp.getStatus().isShuffle()) {
                Random rnd = new Random(currentCommand.getSeed());
                Collections.shuffle(userApp.getSongsIndexesInPlaylist(), rnd);
            } else {
                Collections.sort(userApp.getSongsIndexesInPlaylist());
            }
            for (int currentSong = 0; currentSong < playlistSize; currentSong++) {
                changedPositionsSongs.add(songsInPlaylist.get(userApp.getSongsIndexesInPlaylist().
                        get(currentSong)));
            }
            ArrayList<Integer> currentStartPositions = new ArrayList<>();
            currentStartPositions.add(0);
            for (int currentStart = 1; currentStart < changedPositionsSongs.size();
                 currentStart++) {
                int currentStartPosition = 0;
                for (int songsBeforeCurrentOne = 0; songsBeforeCurrentOne < currentStart;
                     songsBeforeCurrentOne++) {
                    currentStartPosition += changedPositionsSongs.get(
                            songsBeforeCurrentOne).getDuration();
                }
                currentStartPositions.add(currentStartPosition);
            }
            int currentSongIndex = -1;
            for (int currentSong = 0; currentSong < changedPositionsSongs.size();
            currentSong++) {
                if (userApp.getLastPlaylistSelected().getCurrentSongName().compareTo(
                changedPositionsSongs.get(currentSong).getName()) == 0) {
                    currentSongIndex = currentSong;
                    break;
                }
            }
            int remainedTimeBeforeUpdate = 0;
            if (currentSongIndex >= 1) {
                remainedTimeBeforeUpdate = userApp.getLastPlaylistSelected().
                        getCurrentPositionInPlaylist().get() - userApp.getLastPlaylistSelected().
                        getSongsStartsPositions().get(currentSongIndex);
            }
            for (SongInput changedPositionsSong : changedPositionsSongs) {
                changedPositionsSongsNames.add(changedPositionsSong.getName());
            }
            userApp.setChangedSongsPositionsNames(changedPositionsSongsNames);
            userApp.getLastPlaylistSelected().setSongsStartsPositions(currentStartPositions);
            if (currentSongIndex >= 1) {
                userApp.getLastPlaylistSelected().setCurrentPositionInPlaylist(new AtomicInteger(
                        userApp.getLastPlaylistSelected().getSongsStartsPositions().get(
                                currentSongIndex) + remainedTimeBeforeUpdate));
            }
        }
        boolean hasLoadedSource = userApp.getLoadedSource().get();
        boolean isLoadedSourcePlaylist = userApp.getSelectedPlaylist().get() && hasLoadedSource;
        Shuffle shuffle = new Shuffle(currentCommand.getSeed(), userApp.
                getSongsIndexesInPlaylist(), userApp.getStatus().isShuffle(),
                isLoadedSourcePlaylist, hasLoadedSource);
        ObjectNode objectNode = shuffle.shuffleResult(objectMapper, currentCommand);
        outputs.add(objectNode);
    }

    public static void nextCommand(ObjectMapper objectMapper, Command currentCommand,
                                   UserApp userApp, ArrayNode outputs,
                                   ArrayList<Playlist> searchablePlaylists, LibraryInput library) {
        boolean hasLoadedSource = userApp.getLoadedSource().get();
        boolean isAtEnd = false;
        String nextTrack = "";
        int updateDuration = 0;
        if (!userApp.getLastPlaylistSelected().getPlaylistName().isEmpty() && userApp.
                getSelectedPlaylist().get()) {
            if (userApp.getLastPlaylistSelected().getSongIndex().get() == userApp.
                    getLastPlaylistSelected().getTotalNumberOfSongsInPlaylist() - 1) {
                isAtEnd = true;
                userApp.setLastPlaylistSelected(new LastPlaylistSelected());
                sourceReset(userApp);
            } else {
                isAtEnd = false;
                for (Playlist currentPlaylist : searchablePlaylists) {
                    if (currentPlaylist.getName().compareTo(userApp.getLastPlaylistSelected().
                            getPlaylistName()) == 0) {
                        nextTrack = currentPlaylist.getSongs().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get() + 1).getName();
                        updateDuration = currentPlaylist.getSongs().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get() + 1).getDuration();
                        break;
                    }
                }
                userApp.getLastPlaylistSelected().setSongIndex(new AtomicInteger(userApp.
                        getLastPlaylistSelected().getSongIndex().get() + 1));
                userApp.getLastPlaylistSelected().setCurrentSongName(nextTrack);
                userApp.getLastPlaylistSelected().setCurrentPositionInPlaylist(new AtomicInteger(userApp.
                        getLastPlaylistSelected().getSongsStartsPositions().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get())));
                userApp.getStatus().setName(userApp.getLastPlaylistSelected().
                        getCurrentSongName());
                userApp.getStatus().setRemainedTime(updateDuration);
                userApp.setCurrentRemainedTime(updateDuration);

            }
        } else if (!userApp.getLastPodcastSelected().getPodcastName().isEmpty() && userApp.
                getSelectedPodcast().get()) {
            if (userApp.getLastPodcastSelected().getEpisodeIndex().get() == userApp.
                    getLastPodcastSelected().getTotalNumberOfEpisodes() - 1) {
                isAtEnd = true;
                userApp.setLastPodcastSelected(new LastPodcastSelected());
                sourceReset(userApp);
            } else {
                isAtEnd = false;
                for (PodcastInput currentPodcast : library.getPodcasts()) {
                    if (currentPodcast.getName().compareTo(userApp.getLastPodcastSelected().
                            getPodcastName()) == 0) {
                        nextTrack = currentPodcast.getEpisodes().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get() + 1).getName();
                        updateDuration = currentPodcast.getEpisodes().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get() + 1).
                                getDuration();
                        break;
                    }
                }
                userApp.getLastPodcastSelected().setEpisodeIndex(new AtomicInteger(userApp.
                        getLastPodcastSelected().getEpisodeIndex().get() + 1));
                userApp.getLastPodcastSelected().setCurrentEpisodeName(nextTrack);
                userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(userApp.
                        getLastPodcastSelected().getEpisodesStartsPositions().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get())));
                userApp.getStatus().setName(userApp.getLastPodcastSelected().
                        getCurrentEpisodeName());
                userApp.getStatus().setRemainedTime(updateDuration);
                userApp.setCurrentRemainedTime(updateDuration);
            }
        } else if (userApp.getSelectedSong().get() && !userApp.getLastLoadedSource().getName().
                isEmpty()) {
            isAtEnd = true;
            sourceReset(userApp);
        }

        if (isAtEnd) {
            hasLoadedSource = false;
        }

        Next next = new Next(isAtEnd, hasLoadedSource);
        ObjectNode objectNode = next.nextResult(objectMapper, currentCommand, nextTrack);
        outputs.add(objectNode);
    }

    public static void prevCommand(ObjectMapper objectMapper, Command currentCommand,
                                   UserApp userApp, ArrayNode outputs,
                                   ArrayList<Playlist> searchablePlaylists, LibraryInput library) {
        boolean hasLoadedSource = userApp.getLoadedSource().get();
        int secondsPassed = userApp.getLastLoadedSource().getDuration() - userApp.
                getCurrentRemainedTime();
        String prevTrack = "";
        int updateDuration = 0;
        if (!userApp.getLastPlaylistSelected().getPlaylistName().isEmpty() && userApp.
                getSelectedPlaylist().get()) {
            if (userApp.getLastPlaylistSelected().getSongIndex().get() == 0) {
                for (Playlist currentPlaylist : searchablePlaylists) {
                    if (currentPlaylist.getName().compareTo(userApp.getLastPlaylistSelected().
                            getPlaylistName()) == 0) {
                        prevTrack = currentPlaylist.getSongs().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get()).getName();
                        updateDuration = currentPlaylist.getSongs().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get()).getDuration();
                        break;
                    }
                }
                userApp.getLastPlaylistSelected().setCurrentPositionInPlaylist(new AtomicInteger(
                        0));
                userApp.getStatus().setRemainedTime(updateDuration);
                userApp.setCurrentRemainedTime(updateDuration);
            } else if (secondsPassed == 0) {
                for (Playlist currentPlaylist : searchablePlaylists) {
                    if (currentPlaylist.getName().compareTo(userApp.getLastPlaylistSelected().
                            getPlaylistName()) == 0) {
                        prevTrack = currentPlaylist.getSongs().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get() - 1).getName();
                        updateDuration = currentPlaylist.getSongs().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get() - 1).getDuration();
                        break;
                    }
                }
                userApp.getLastPlaylistSelected().setSongIndex(new AtomicInteger(userApp.
                        getLastPlaylistSelected().getSongIndex().get() - 1));
                userApp.getLastPlaylistSelected().setCurrentSongName(prevTrack);
                userApp.getLastPlaylistSelected().setCurrentPositionInPlaylist(new AtomicInteger(userApp.
                        getLastPlaylistSelected().getSongsStartsPositions().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get())));
                userApp.getStatus().setName(userApp.getLastPlaylistSelected().
                        getCurrentSongName());
                userApp.getStatus().setRemainedTime(updateDuration);
                userApp.setCurrentRemainedTime(updateDuration);

            } else {
                for (Playlist currentPlaylist : searchablePlaylists) {
                    if (currentPlaylist.getName().compareTo(userApp.getLastPlaylistSelected().
                            getPlaylistName()) == 0) {
                        prevTrack = currentPlaylist.getSongs().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get()).getName();
                        updateDuration = currentPlaylist.getSongs().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get()).getDuration();
                        break;
                    }
                }
                userApp.getLastPlaylistSelected().setCurrentPositionInPlaylist(new AtomicInteger(
                        userApp.getLastPlaylistSelected().getSongsStartsPositions().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get())));
                userApp.getStatus().setRemainedTime(updateDuration);
                userApp.setCurrentRemainedTime(updateDuration);
            }
        } else if (!userApp.getLastPodcastSelected().getPodcastName().isEmpty() && userApp.
                getSelectedPodcast().get()) {
            if (userApp.getLastPodcastSelected().getEpisodeIndex().get() == 0) {
                for (PodcastInput currentPodcast : library.getPodcasts()) {
                    if (currentPodcast.getName().compareTo(userApp.getLastPodcastSelected().
                            getPodcastName()) == 0) {
                        prevTrack = currentPodcast.getEpisodes().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get()).getName();
                        updateDuration = currentPodcast.getEpisodes().get(userApp.
                                getLastPlaylistSelected().getSongIndex().get()).getDuration();
                        break;
                    }
                }
                userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(
                        0));
                userApp.getStatus().setRemainedTime(updateDuration);
                userApp.setCurrentRemainedTime(updateDuration);
            } else if (secondsPassed == 0) {
                for (PodcastInput currentPodcast : library.getPodcasts()) {
                    if (currentPodcast.getName().compareTo(userApp.getLastPodcastSelected().
                            getPodcastName()) == 0) {
                        prevTrack = currentPodcast.getEpisodes().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get() - 1).getName();
                        updateDuration = currentPodcast.getEpisodes().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get() - 1).getDuration();
                        break;
                    }
                }
                userApp.getLastPodcastSelected().setEpisodeIndex(new AtomicInteger(userApp.
                        getLastPodcastSelected().getEpisodeIndex().get() - 1));
                userApp.getLastPodcastSelected().setCurrentEpisodeName(prevTrack);
                userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(userApp.
                        getLastPodcastSelected().getEpisodesStartsPositions().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get())));
                userApp.getStatus().setName(userApp.getLastPodcastSelected().
                        getCurrentEpisodeName());
                userApp.getStatus().setRemainedTime(updateDuration);
                userApp.setCurrentRemainedTime(updateDuration);

            } else {
                for (PodcastInput currentPodcast : library.getPodcasts()) {
                    if (currentPodcast.getName().compareTo(userApp.getLastPodcastSelected().
                            getPodcastName()) == 0) {
                        prevTrack = currentPodcast.getEpisodes().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get()).getName();
                        updateDuration = currentPodcast.getEpisodes().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get()).getDuration();
                        break;
                    }
                }
                userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(
                        userApp.getLastPodcastSelected().getEpisodesStartsPositions().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get())));
                userApp.getStatus().setRemainedTime(updateDuration);
                userApp.setCurrentRemainedTime(updateDuration);
            }
        } else if (userApp.getSelectedSong().get() && !userApp.getLastLoadedSource().getName().
                isEmpty()) {
            for (SongInput currentSong : library.getSongs()) {
                if (currentSong.getName().compareTo(userApp.getLastLoadedSource().getName())
                        == 0) {
                    updateDuration = currentSong.getDuration();
                }
            }
            userApp.getLastLoadedSource().setDuration(updateDuration);
            userApp.getStatus().setRemainedTime(updateDuration);
            userApp.setCurrentRemainedTime(updateDuration);
        }

        userApp.getMusicPlayer().setPlayPause(true);
        userApp.getStatus().setPaused(false);

        Prev prev = new Prev(secondsPassed, hasLoadedSource);
        ObjectNode objectNode = prev.prevResult(objectMapper, currentCommand, prevTrack);
        outputs.add(objectNode);
    }

    public static void forwardCommand(ObjectMapper objectMapper, Command currentCommand,
                                      UserApp userApp, ArrayNode outputs, LibraryInput library,
                                      ForwardTracker forwardTracker) {
        boolean hasLoadedSource = userApp.getLoadedSource().get();
        boolean isLoadedSourcePodcast = !userApp.getLastPodcastSelected().getPodcastName().
                isEmpty() && userApp.getSelectedPodcast().get();
        if (isLoadedSourcePodcast) {
            if (userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                    + CheckerConstants.FORWARD_BACKWARD_TIME_SKIP >= userApp.
                    getLastPodcastSelected().getTotalDurationOfPodcast()) {
                sourceReset(userApp);
                hasLoadedSource = false;
                isLoadedSourcePodcast = false;
            } else {
                String forwardEpisodeName = "";
                int updateDuration = 0;
                int currentIndex = 0;
                for (PodcastInput currentPodcast : library.getPodcasts()) {
                    if (currentPodcast.getName().compareTo(userApp.getLastPodcastSelected().
                            getPodcastName()) == 0) {
                        if (userApp.getLastPodcastSelected().getEpisodeIndex().get() !=
                                currentPodcast.getEpisodes().size() - 1) {
                            forwardEpisodeName = currentPodcast.getEpisodes().get(userApp.
                                    getLastPodcastSelected().getEpisodeIndex().get() + 1).getName();
                            updateDuration = currentPodcast.getEpisodes().get(userApp.
                                            getLastPodcastSelected().getEpisodeIndex().get() + 1).
                                    getDuration();
                        } else {
                            forwardEpisodeName = currentPodcast.getEpisodes().get(userApp.
                                    getLastPodcastSelected().getEpisodeIndex().get()).getName();
                            updateDuration = currentPodcast.getEpisodes().get(userApp.
                                            getLastPodcastSelected().getEpisodeIndex().get()).
                                    getDuration();
                        }
                        break;
                    }
                    currentIndex++;
                }
                if (userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                        + CheckerConstants.FORWARD_BACKWARD_TIME_SKIP >= userApp.
                        getLastPodcastSelected().getEpisodesStartsPositions().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get() + 1)) {
                    userApp.getStatus().setName(forwardEpisodeName);
                    userApp.getStatus().setRemainedTime(updateDuration);
                    userApp.getLastPodcastSelected().setEpisodeIndex(new AtomicInteger(userApp.
                            getLastPodcastSelected().getEpisodeIndex().get() + 1));
                    userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(
                            userApp.getLastPodcastSelected().getEpisodesStartsPositions().get(
                                    userApp.getLastPodcastSelected().getEpisodeIndex().get())));
                    userApp.setCurrentRemainedTime(updateDuration);
                    forwardTracker.setHasBeenForwarded(true);
                } else {
                    userApp.getStatus().setRemainedTime(userApp.getCurrentRemainedTime()
                            - CheckerConstants.FORWARD_BACKWARD_TIME_SKIP);
                    userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(
                            userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                                    + CheckerConstants.FORWARD_BACKWARD_TIME_SKIP));
                    userApp.setCurrentRemainedTime(userApp.getCurrentRemainedTime()
                            - CheckerConstants.FORWARD_BACKWARD_TIME_SKIP);
                    forwardTracker.setHasBeenForwarded(true);
                }
            }
        }
        Forward forward = new Forward(isLoadedSourcePodcast, hasLoadedSource);
        ObjectNode objectNode = forward.forwardResult(objectMapper, currentCommand);
        outputs.add(objectNode);
    }

    public static void backwardCommand(ObjectMapper objectMapper, Command currentCommand,
                                      UserApp userApp, ArrayNode outputs, LibraryInput library,
                                       BackwardTracker backwardTracker) {
        boolean hasLoadedSource = userApp.getLoadedSource().get();
        boolean isLoadedSourcePodcast = !userApp.getLastPodcastSelected().getPodcastName().
                isEmpty() && userApp.getSelectedPodcast().get();
        if (isLoadedSourcePodcast) {
            if (userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                    - CheckerConstants.FORWARD_BACKWARD_TIME_SKIP <= 0 && userApp.
                    getLastPodcastSelected().getEpisodeIndex().get() == 0) {
                userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(0));
                userApp.getStatus().setRemainedTime(userApp.getLastPodcastSelected().
                        getEpisodesStartsPositions().get(1));
                userApp.setCurrentRemainedTime(userApp.getLastPodcastSelected().
                        getEpisodesStartsPositions().get(1));
                backwardTracker.setHasBeenBackwarded(true);
            } else if (userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                    - CheckerConstants.FORWARD_BACKWARD_TIME_SKIP > 0 && userApp.
                    getLastPodcastSelected().getEpisodeIndex().get() > 0) {
                String backwardEpisodeName = "";
                int updateDuration = 0;
                for (PodcastInput currentPodcast : library.getPodcasts()) {
                    if (currentPodcast.getName().compareTo(userApp.getLastPodcastSelected().
                            getPodcastName()) == 0) {
                            backwardEpisodeName = currentPodcast.getEpisodes().get(userApp.
                                    getLastPodcastSelected().getEpisodeIndex().get() - 1).
                                    getName();
                            updateDuration = currentPodcast.getEpisodes().get(userApp.
                                            getLastPodcastSelected().getEpisodeIndex().get() - 1).
                                    getDuration();
                        break;
                    }
                }
                if (userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                        - CheckerConstants.FORWARD_BACKWARD_TIME_SKIP <= userApp.
                        getLastPodcastSelected().getEpisodesStartsPositions().get(userApp.
                                getLastPodcastSelected().getEpisodeIndex().get() - 1)) {
                    userApp.getStatus().setName(backwardEpisodeName);
                    userApp.getStatus().setRemainedTime(updateDuration);
                    userApp.getLastPodcastSelected().setEpisodeIndex(new AtomicInteger(userApp.
                            getLastPodcastSelected().getEpisodeIndex().get() - 1));
                    userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(
                            userApp.getLastPodcastSelected().getEpisodesStartsPositions().get(
                                    userApp.getLastPodcastSelected().getEpisodeIndex().get())));
                    userApp.setCurrentRemainedTime(updateDuration);
                    backwardTracker.setHasBeenBackwarded(true);
                } else {
                    userApp.getStatus().setRemainedTime(userApp.getCurrentRemainedTime()
                            + CheckerConstants.FORWARD_BACKWARD_TIME_SKIP);
                    userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(
                            userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                                    - CheckerConstants.FORWARD_BACKWARD_TIME_SKIP));
                    userApp.setCurrentRemainedTime(userApp.getCurrentRemainedTime()
                            + CheckerConstants.FORWARD_BACKWARD_TIME_SKIP);
                    backwardTracker.setHasBeenBackwarded(true);
                }
            } else if (userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                    - CheckerConstants.FORWARD_BACKWARD_TIME_SKIP > 0 && userApp.
                    getLastPodcastSelected().getEpisodeIndex().get() == 0) {
                userApp.getStatus().setRemainedTime(userApp.getCurrentRemainedTime()
                        + CheckerConstants.FORWARD_BACKWARD_TIME_SKIP);
                userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(
                        userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                                - CheckerConstants.FORWARD_BACKWARD_TIME_SKIP));
                userApp.setCurrentRemainedTime(userApp.getCurrentRemainedTime()
                        + CheckerConstants.FORWARD_BACKWARD_TIME_SKIP);
                backwardTracker.setHasBeenBackwarded(true);
            }
        }
        Backward backward = new Backward(isLoadedSourcePodcast, hasLoadedSource);
        ObjectNode objectNode = backward.backwardResult(objectMapper, currentCommand);
        outputs.add(objectNode);
    }

    public static void followCommand(ObjectMapper objectMapper, Command currentCommand,
                                     UserApp userApp, ArrayNode outputs,
                                     ArrayList<Playlist> searchablePlaylists,
                                     LibraryInput library, ArrayList<Playlist> allPlaylists) {
        boolean hasSelectedSource = userApp.getSelectedSong().get() || userApp.
                getSelectedPodcast().get() || userApp.getSelectedPlaylist().get();
        boolean isSelectedSourcePlaylist = !userApp.getCurrentPlaylistSelected().isEmpty()
                && userApp.getSelectedPlaylist().get();
        boolean hasBeenFollowed = false;
        int playlistToBeRemovedIndex = -1;
        boolean selfPlaylistFollowAttempt = false;
        for (int currentPlaylist = 0; currentPlaylist < userApp.getFollowedPlaylists().size();
             currentPlaylist++) {
            if (userApp.getFollowedPlaylists().get(currentPlaylist).getName().compareTo(
                    userApp.getCurrentPlaylistSelected()) == 0) {
                hasBeenFollowed = true;
                playlistToBeRemovedIndex = currentPlaylist;
                break;
            }
        }
        if (hasBeenFollowed) {
            userApp.getFollowedPlaylists().remove(userApp.getFollowedPlaylists().get(
                    playlistToBeRemovedIndex));
            for (Playlist currentPlaylist : allPlaylists) {
                if (currentPlaylist.getName().compareTo(userApp.
                        getCurrentPlaylistSelected()) == 0) {
                    currentPlaylist.setFollowers(currentPlaylist.getFollowers() - 1);
                }
            }
        } else {
            for (int currentPlaylist = 0; currentPlaylist < allPlaylists.size();
                 currentPlaylist++) {
                if (getPlaylist(allPlaylists, currentPlaylist).getName().compareTo(userApp.
                        getCurrentPlaylistSelected()) == 0) {
                    if (allPlaylists.get(currentPlaylist).getOwner().compareTo(
                            currentCommand.getUsername()) == 0) {
                        selfPlaylistFollowAttempt = true;
                    } else {
                        allPlaylists.get(currentPlaylist).setFollowers(allPlaylists.get(
                                currentPlaylist).getFollowers() + 1);
                        userApp.getFollowedPlaylists().add(getPlaylist(allPlaylists,
                                currentPlaylist));
                    }
                    break;
                }
            }
        }

        FollowPlaylist followPlaylist = new FollowPlaylist(hasBeenFollowed,
                isSelectedSourcePlaylist, hasSelectedSource, selfPlaylistFollowAttempt);
        ObjectNode objectNode = followPlaylist.followPlaylistResult(objectMapper, currentCommand);
        outputs.add(objectNode);
    }

    private static void switchVisibilityCommand(ObjectMapper objectMapper, Command currentCommand,
                                                ArrayList<Playlist> userPlaylists,
                                                ArrayNode outputs) {
        boolean tooHighIdPlaylist = currentCommand.getPlaylistId() > userPlaylists.size();
        boolean visibilityStatus = false;
        int foundPlaylistIndex = -1;
        if (!tooHighIdPlaylist) {
            for (int currentPlaylist = 0; currentPlaylist < userPlaylists.size();
                 currentPlaylist++) {
                if (currentPlaylist == currentCommand.getPlaylistId() - 1) {
                    foundPlaylistIndex = currentPlaylist;
                    userPlaylists.get(currentPlaylist).setVisibility(!userPlaylists.
                            get(currentPlaylist).isVisibility());
                    break;
                }
            }
        }

        if (foundPlaylistIndex > -1) {
            visibilityStatus = userPlaylists.get(foundPlaylistIndex).isVisibility();
        }
        SwitchVisibility switchVisibility = new SwitchVisibility(visibilityStatus,
                tooHighIdPlaylist);
        ObjectNode objectNode = switchVisibility.switchVisibilityResult(objectMapper,
                currentCommand);
        outputs.add(objectNode);
    }

    public static void getTop5SongsCommand(ObjectMapper objectMapper, Command currentCommand,
                                    LibraryInput library,
                                    ArrayList<Integer> likesForEachSongInLibrary,
                                    ArrayNode outputs) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        ArrayList<Integer> likesForEachSongInLibraryBackup = new ArrayList<>(
                likesForEachSongInLibrary);
        ArrayList<Integer> likesForEachSongInLibraryBackupIndexes = new ArrayList<>(
                likesForEachSongInLibrary.size());
        for (int currentLikesIndex = 0; currentLikesIndex < likesForEachSongInLibrary.size();
             currentLikesIndex++) {
            likesForEachSongInLibraryBackupIndexes.add(currentLikesIndex, currentLikesIndex);
        }
        for (int currentLikesIndex = 0; currentLikesIndex < likesForEachSongInLibrary.size() - 1;
             currentLikesIndex++) {
            for (int currentLikesIndexInsider = currentLikesIndex + 1;
                 currentLikesIndexInsider < likesForEachSongInLibrary.size();
                 currentLikesIndexInsider++) {
                if (likesForEachSongInLibraryBackup.get(currentLikesIndex)
                        < likesForEachSongInLibraryBackup.get(currentLikesIndexInsider)) {
                    int temporary = likesForEachSongInLibraryBackupIndexes.get(currentLikesIndex);
                    likesForEachSongInLibraryBackupIndexes.set(currentLikesIndex,
                            likesForEachSongInLibraryBackupIndexes.get(currentLikesIndexInsider));
                    likesForEachSongInLibraryBackupIndexes.set(currentLikesIndexInsider,
                            temporary);
                }
            }
        }

        ArrayList<String> top5Songs = new ArrayList<>();
        for (int currentSong = 0; currentSong < CheckerConstants.MAXIMUM_SEARCH_RESULT;
             currentSong++) {
            top5Songs.add(library.getSongs().get(likesForEachSongInLibraryBackupIndexes.get(
                    currentSong)).getName());
        }
        JsonNode jsonNode = objectMapper.valueToTree(top5Songs);
        objectNode.set(CheckerConstants.RESULT_PATH, jsonNode);
        outputs.add(objectNode);
    }

    public static void getTop5PlaylistsCommand(ObjectMapper objectMapper, Command currentCommand,
                                               ArrayList<Playlist> allPlaylists,
                                               ArrayNode outputs) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        int[] allPlaylistsIndexes = new int[allPlaylists.size()];
        for (int currentIndex = 0; currentIndex < allPlaylists.size(); currentIndex++) {
            allPlaylistsIndexes[currentIndex] = currentIndex;
        }
        for (int currentFollowsIndex = 0; currentFollowsIndex < allPlaylists.size() - 1;
             currentFollowsIndex++) {
            for (int currentFollowsIndexInsider = currentFollowsIndex + 1;
                 currentFollowsIndexInsider < allPlaylists.size(); currentFollowsIndexInsider++) {
                if (allPlaylists.get(currentFollowsIndex).getFollowers() < allPlaylists.get(
                        currentFollowsIndexInsider).getFollowers()) {
                    int temporary = allPlaylistsIndexes[currentFollowsIndex];
                    allPlaylistsIndexes[currentFollowsIndex] = allPlaylistsIndexes[
                            currentFollowsIndexInsider];
                    allPlaylistsIndexes[currentFollowsIndexInsider] = temporary;
                }
            }
        }
        ArrayList<String> top5Playlists = new ArrayList<>();
        for (int currentPlaylist = 0; currentPlaylist < Math.min(CheckerConstants.
                MAXIMUM_SEARCH_RESULT, allPlaylists.size()); currentPlaylist++) {
            top5Playlists.add(allPlaylists.get(allPlaylistsIndexes[currentPlaylist]).getName());
        }
        JsonNode jsonNode = objectMapper.valueToTree(top5Playlists);
        objectNode.set(CheckerConstants.RESULT_PATH, jsonNode);
        outputs.add(objectNode);
    }

    private static Playlist getPlaylist(ArrayList<Playlist> searchablePlaylists,
                                        int currentPlaylist) {
        return searchablePlaylists.get(currentPlaylist);
    }

    public static int getUserPositionByUsername(ArrayList<UserApp> usersData,
                                                    String username) {
        int result = -1;
        for (int currentUser = 0; currentUser < usersData.size(); currentUser++) {
            if (usersData.get(currentUser).getUser().getUsername().compareTo(username) == 0) {
                result = currentUser;
                break;
            }
        }
        return result;
    }

    public static ObjectNode getDefaultObjectNodeParameters(ObjectMapper objectMapper,
                                                            Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        return objectNode;
    }

    public static ArrayList<Playlist> getSearchablePlaylistForUser(
            ArrayList<Playlist> allPlaylists, String username) {
        ArrayList<Playlist> searchablePlaylists = new ArrayList<>();
        for (Playlist currentPlaylist : allPlaylists) {
            if (currentPlaylist.isVisibility() || currentPlaylist.getOwner().compareTo(username)
                    == 0) {
                searchablePlaylists.add(currentPlaylist);
            }
        }
        return searchablePlaylists;
    }

    public static void updatePodcast(Command currentCommand, Command previousCommand,
                                     UserApp userApp, LibraryInput library) {
        int updatePosition = currentCommand.getTimestamp() - previousCommand.getTimestamp();
        userApp.getLastPodcastSelected().setCurrentPositionOfPodcast(new AtomicInteger(userApp.
                getLastPodcastSelected().getCurrentPositionOfPodcast().get() + updatePosition));
        if (userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get() > userApp.
                getLastPodcastSelected().getTotalDurationOfPodcast()) {
            userApp.setLastPodcastSelected(new LastPodcastSelected());
            userApp.getMusicPlayer().setPlayPause(false);
            userApp.getStatus().setPaused(true);
        } else {
            int updateEpisodeIndex = -1;
            for (int currentEpisodePosition = userApp.getLastPodcastSelected().
                    getTotalNumberOfEpisodes() - 1; currentEpisodePosition >= 0;
                 currentEpisodePosition--) {
                if (userApp.getLastPodcastSelected().getCurrentPositionOfPodcast().get()
                        >= userApp.getLastPodcastSelected().getEpisodesStartsPositions().get(
                                currentEpisodePosition)) {
                    updateEpisodeIndex = currentEpisodePosition;
                    break;
                }
            }
            if (updateEpisodeIndex > -1) {
                userApp.getLastPodcastSelected().setEpisodeIndex(new AtomicInteger(
                        updateEpisodeIndex));
                for (PodcastInput currentPodcast : library.getPodcasts()) {
                    if (currentPodcast.getName().compareTo(userApp.getLastPodcastSelected().
                            getPodcastName()) == 0) {
                        userApp.getLastPodcastSelected().setCurrentEpisodeName(currentPodcast.
                                getEpisodes().get(updateEpisodeIndex).getName());
                        break;
                    }
                }
            }
        }
    }

    public static void updatePlaylist(Command currentCommand, Command previousCommand,
                                      UserApp userApp, ArrayList<Playlist> searchablePlaylists) {
        int updatePosition = currentCommand.getTimestamp() - previousCommand.getTimestamp();
        userApp.getLastPlaylistSelected().setCurrentPositionInPlaylist(new AtomicInteger(userApp.
                getLastPlaylistSelected().getCurrentPositionInPlaylist().get() + updatePosition));
        if (userApp.getLastPlaylistSelected().getCurrentPositionInPlaylist().get() > userApp.
                getLastPlaylistSelected().getTotalDurationOfPlaylist()) {
            if (userApp.getStatus().getRepeat().compareTo(CheckerConstants.REPEAT_ALL) == 0) {
                while (userApp.getLastPlaylistSelected().getCurrentPositionInPlaylist().get()
                        > userApp.getLastPlaylistSelected().getTotalDurationOfPlaylist()) {
                    userApp.getLastPlaylistSelected().setCurrentPositionInPlaylist(new
                            AtomicInteger(userApp.getLastPlaylistSelected().
                            getCurrentPositionInPlaylist().get() - userApp.
                            getLastPlaylistSelected().getTotalDurationOfPlaylist()));

                }
            } else if (userApp.getStatus().getRepeat().compareTo(CheckerConstants.
                    REPEAT_CURRENT_SONG) == 0) {
                if (userApp.getLastPlaylistSelected().getHasRepeatCurrentSongState().get()) {
                    for (Playlist currentPlaylist : searchablePlaylists) {
                        if (currentPlaylist.getName().compareTo(userApp.getLastPlaylistSelected().
                                getPlaylistName()) == 0) {
                            userApp.getLastPlaylistSelected().setCurrentSongName(currentPlaylist.
                                    getSongs().getLast().getName());
                            userApp.getLastPlaylistSelected().setCurrentPositionInPlaylist(new
                                    AtomicInteger(userApp.getLastPlaylistSelected().
                                    getCurrentPositionInPlaylist().get() - currentPlaylist.
                                    getSongs().getLast().getDuration()));
                        }
                    }
                    userApp.getLastPlaylistSelected().setHasRepeatCurrentSongState(new
                            AtomicBoolean(false));
                    userApp.getStatus().setRepeat(CheckerConstants.NO_REPEAT);
                }
            } else {
                userApp.setLastPlaylistSelected(new LastPlaylistSelected());
                userApp.getMusicPlayer().setPlayPause(false);
                userApp.getStatus().setPaused(true);
            }
        } else {
            if (userApp.getStatus().getRepeat().compareTo(CheckerConstants.
                    REPEAT_CURRENT_SONG) == 0) {
                AtomicInteger currentSongToRepeatIndex = userApp.getLastPlaylistSelected().
                        getCurrentSongToBeRepeatedIndex();
                int foundPlaylist = -1;
                for (int currentPlaylist = 0; currentPlaylist < searchablePlaylists.size();
                     currentPlaylist++) {
                    if (searchablePlaylists.get(currentPlaylist).getName().compareTo(userApp.
                            getLastPlaylistSelected().getPlaylistName()) == 0) {
                        foundPlaylist = currentPlaylist;
                        break;
                    }
                }
                if (userApp.getLastPlaylistSelected().getCurrentPositionInPlaylist().get()
                        >= userApp.getLastPlaylistSelected().getSongsStartsPositions().get(
                                currentSongToRepeatIndex.get() + 1)) {
                    userApp.getLastPlaylistSelected().setCurrentPositionInPlaylist(new
                            AtomicInteger(userApp.getLastPlaylistSelected().
                            getCurrentPositionInPlaylist().get() - searchablePlaylists.get(
                                    foundPlaylist).getSongs().get(currentSongToRepeatIndex.get()).
                            getDuration()));
                    userApp.getLastPlaylistSelected().setSongIndex(currentSongToRepeatIndex);
                    userApp.getLastPlaylistSelected().setCurrentSongName(searchablePlaylists.get(
                                    foundPlaylist).getSongs().get(currentSongToRepeatIndex.get()).
                            getName());
                }
            } else if (userApp.getStatus().getRepeat().compareTo(CheckerConstants.NO_REPEAT)
                    == 0) {
                int updateSongIndex = -1;
                for (int currentSongPosition = userApp.getLastPlaylistSelected().
                        getTotalNumberOfSongsInPlaylist() - 1; currentSongPosition >= 0;
                     currentSongPosition--) {
                    if (userApp.getLastPlaylistSelected().getCurrentPositionInPlaylist().get()
                            >= userApp.getLastPlaylistSelected().getSongsStartsPositions().get(
                            currentSongPosition)) {
                        updateSongIndex = currentSongPosition;
                        break;
                    }
                }

                if (updateSongIndex > -1) {
                    userApp.getLastPlaylistSelected().setSongIndex(new AtomicInteger(
                            updateSongIndex));
                    for (Playlist currentPlaylist : searchablePlaylists) {
                        if (currentPlaylist.getName().compareTo(userApp.getLastPlaylistSelected().
                                getPlaylistName()) == 0) {
                            userApp.getLastPlaylistSelected().setCurrentSongName(currentPlaylist.
                                    getSongs().get(updateSongIndex).getName());
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void updateSongByRepeatState(UserApp userApp, LibraryInput library) {

        int found = -1;
        for (int currentSong = 0; currentSong < library.getSongs().size(); currentSong++) {
            if (userApp.getLastLoadedSource().getName().compareTo(library.getSongs().get(
                    currentSong).getName()) == 0) {
                found = currentSong;
                break;
            }
        }

        switch (userApp.getStatus().getRepeat()) {
            case CheckerConstants.REPEAT_ONCE -> {
                if (userApp.getLastLoadedSource().getHasRepeatOnceState().get()) {
                    userApp.getStatus().setRemainedTime(userApp.getCurrentRemainedTime() + library.
                            getSongs().get(found).getDuration());
                    userApp.getStatus().setName(library.getSongs().get(found).getName());
                    userApp.getLastLoadedSource().setName(library.getSongs().get(found).getName());
                    userApp.getLastLoadedSource().setDuration(userApp.getCurrentRemainedTime()
                            + library.getSongs().get(found).getDuration());
                    userApp.setCurrentRemainedTime(userApp.getLastLoadedSource().getDuration());
                    userApp.getStatus().setRepeat(CheckerConstants.NO_REPEAT);
                    userApp.getLastLoadedSource().setHasRepeatOnceState(new AtomicBoolean(
                            false));
                }
            }
            case CheckerConstants.REPEAT_INFINITE -> {
                while (userApp.getCurrentRemainedTime() < 0) {
                    userApp.getStatus().setRemainedTime(userApp.getCurrentRemainedTime() + library.
                            getSongs().get(found).getDuration());
                    userApp.getStatus().setName(library.getSongs().get(found).getName());
                    userApp.getLastLoadedSource().setName(library.getSongs().get(found).getName());
                    userApp.getLastLoadedSource().setDuration(userApp.getCurrentRemainedTime()
                            + library.getSongs().get(found).getDuration());
                    userApp.setCurrentRemainedTime(userApp.getLastLoadedSource().getDuration());
                }
            }
        }
    }

    public static void sourceReset(UserApp userApp) {
        userApp.getStatus().setName("");
        userApp.getStatus().setRemainedTime(0);
        userApp.getStatus().setPaused(true);
        userApp.getMusicPlayer().setPlayPause(false);
        userApp.setLastLoadedSource(new LastLoadedSource());
        userApp.setLoadedSource(new AtomicBoolean(false));
        userApp.setCurrentRemainedTime(0);
    }
}
