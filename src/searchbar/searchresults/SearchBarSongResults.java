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

    /***
     * constructor for SearchBarSongResult class
     * @param songs the list of songs
     */
    public SearchBarSongResults(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    /***
     * songs getter
     * @return the list of names of songs
     */
    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    /***
     * method that filters the search result by the album the songs belong to
     * @param album string that represents the album the songs belong to
     * @return a list of songs that belong to the album found in the string album
     */
    public ArrayList<SongInput> filterSongsByAlbum(final String album) {
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

    /***
     * method that filters the search result by the artist that composed the songs
     * @param artist string that represents the artist that composed the songs
     * @return a list of songs that belong to the artist found in the string artist
     */
    public ArrayList<SongInput> filterSongsByArtist(final String artist) {
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

    /***
     * method that filters the search result by the genre the songs belong to
     * @param genre string that represents the genre the songs belong to
     * @return a list of songs that belong to the genre found in the string genre
     */
    public ArrayList<SongInput> filterSongsByGenre(final String genre) {
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

    /***
     * method that filters the search result by the lyrics found in the song
     * @param lyrics string that represents the lyrics found in the song
     * @return a list of songs that contain the lyrics found in the string lyrics
     */
    public ArrayList<SongInput> filterSongsByLyrics(final String lyrics) {
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

    /***
     * method that filters the search of the songs by a string the name of a song starts
     * with
     * @param name string that is used to check if a song's name starts with it
     * @return a list of songs that start with the string name
     */
    public ArrayList<SongInput> filterSongsByName(final String name) {
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

    /***
     * method that filters the search result by the condition found in the string releaseYear
     * (it can be lower or higher than the release year)
     * @param releaseYear string that contains a comparer and a year
     * @return a list of songs that are lower or higher than the release year from the condition
     */
    public ArrayList<SongInput> filterSongsByReleaseYear(final String releaseYear) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        int comparingYearToInt = Integer.parseInt(releaseYear.substring(1));
        for (SongInput currentSong : getSongs()) {
            if (releaseYear.charAt(0) == '<') {
                if (currentSong.getReleaseYear() < comparingYearToInt) {
                    filteredSongs.add(currentSong);
                }
            } else {
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

    /***
     * method that filters the search result by the tags found in the songs
     * @param tags list of strings that contain the tags in the songs
     * @return a list of songs that contain the tags in the parameter tags
     */
    public ArrayList<SongInput> filterSongsByTags(final ArrayList<String> tags) {
        ArrayList<SongInput> filteredSongs = new ArrayList<>();
        for (SongInput currentSong : getSongs()) {
            boolean doesNotHaveAllTags = false;
            for (String currentTag : tags) {
                if (!currentSong.getTags().contains(currentTag)) {
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

    /***
     * method that helps print the output for search command
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for the search command used on
     * songs
     */
    @Override
    public ObjectNode searchResult(final ObjectMapper objectMapper, final Command currentCommand) {
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

    /***
     * lastSearchResult getter
     * @return the list of names of songs from the last search result
     */
    @Override
    public ArrayList<String> getLastSearchResult() {
        return lastSearchResult;
    }

    /***
     * lastSearchResult setter
     * @param result sets the names of songs from the last search result
     */
    @Override
    public void setLastSearchResult(final ArrayList<String> result) {
        this.lastSearchResult = result;
    }
}
