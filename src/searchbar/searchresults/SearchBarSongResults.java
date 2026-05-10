package searchbar.searchresults;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import fileio.input.SongInput;
import messages.SearchMessage;
import searchbar.SearchBar;

import java.util.ArrayList;

public class SearchBarSongResults implements SearchBar {
    private ArrayList<SongInput> songs;
    private ArrayList<String> lastSearchResult;

    public SearchBarSongResults(ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public ArrayList<SongInput> filterSongsByAlbum(String album) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput currentSong : getSongs()) {
            if (currentSong.getAlbum().compareTo(album) == 0) {
                filteredSongs.add(currentSong);
            }

            if (filteredSongs.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredSongs;
    }

    public ArrayList<SongInput> filterSongsByArtist(String artist) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput currentSong : getSongs()) {
            if (currentSong.getArtist().compareTo(artist) == 0) {
                filteredSongs.add(currentSong);
            }

            if (filteredSongs.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredSongs;
    }

    public ArrayList<SongInput> filterSongsByGenre(String genre) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput currentSong : getSongs()) {
            if (currentSong.getGenre().compareToIgnoreCase(genre) == 0) {
                filteredSongs.add(currentSong);
            }

            if (filteredSongs.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredSongs;
    }

    public ArrayList<SongInput> filterSongsByLyrics(String lyrics) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput currentSong : getSongs()) {
            if (currentSong.getLyrics().toLowerCase().contains(lyrics.toLowerCase())) {
                filteredSongs.add(currentSong);
            }

            if (filteredSongs.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredSongs;
    }

    public ArrayList<SongInput> filterSongsByName(String name) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput currentSong : getSongs()) {
            if (currentSong.getName().startsWith(name)) {
                filteredSongs.add(currentSong);
            }

            if (filteredSongs.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredSongs;
    }

    public ArrayList<SongInput> filterSongsByReleaseYear(String releaseYear) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        int comparingYearToInt = Integer.parseInt(releaseYear.substring(1));
        for (SongInput currentSong : getSongs()) {
            if (releaseYear.charAt(0) == '<') {
                if (currentSong.getReleaseYear() < comparingYearToInt)
                {
                    filteredSongs.add(currentSong);
                }
            }
            else {
                if (currentSong.getReleaseYear() > comparingYearToInt) {
                    filteredSongs.add(currentSong);
                }
            }

            if (filteredSongs.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredSongs;
    }

    public ArrayList<SongInput> filterSongsByTags(ArrayList<String> tags) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput currentSong : getSongs()) {
            boolean doesNotHaveAllTags = false;
            for (String currentTag : tags) {
                if (!currentSong.getTags().contains(currentTag))
                {
                    doesNotHaveAllTags = true;
                    break;
                }
            }
            if (!doesNotHaveAllTags) {
                filteredSongs.add(currentSong);
            }

            if (filteredSongs.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredSongs;
    }

    @Override
    public ObjectNode searchResult(ObjectMapper objectMapper, Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        if (currentCommand.getFilters().getName() != null) {
            filteredSongs = filterSongsByName(currentCommand.getFilters().getName());
        }
        if (currentCommand.getFilters().getAlbum() != null) {
            filteredSongs = filterSongsByAlbum(currentCommand.getFilters().getAlbum());
        }
        if (currentCommand.getFilters().getTags() != null) {
            filteredSongs = filterSongsByTags(currentCommand.getFilters().getTags());
        }
        if (currentCommand.getFilters().getLyrics() != null) {
            filteredSongs = filterSongsByLyrics(currentCommand.getFilters().getLyrics());
        }
        if (currentCommand.getFilters().getGenre() != null) {
            filteredSongs = filterSongsByGenre(currentCommand.getFilters().getGenre());
        }
        if (currentCommand.getFilters().getReleaseYear() != null) {
            filteredSongs = filterSongsByReleaseYear(currentCommand.getFilters().getReleaseYear());
        }
        if (currentCommand.getFilters().getArtist() != null) {
            filteredSongs = filterSongsByArtist(currentCommand.getFilters().getArtist());
        }
        ArrayList<String> filteredSongsNames = new ArrayList<>();
        for (SongInput currentFilteredSong : filteredSongs) {
            filteredSongsNames.add(currentFilteredSong.getName());
        }
        objectNode.put(CheckerConstants.MESSAGE_FIELD, SearchMessage.successSearchMessage(
                filteredSongs.size()));
        JsonNode jsonNode = objectMapper.valueToTree(filteredSongsNames);
        objectNode.set(CheckerConstants.RESULTS_FIELD, jsonNode);
        setLastSearchResult(filteredSongsNames);
        return objectNode;
    }

    @Override
    public ArrayList<String> getLastSearchResult() {
        return lastSearchResult;
    }

    @Override
    public void setLastSearchResult(ArrayList<String> result) {
        this.lastSearchResult = result;
    }
}
