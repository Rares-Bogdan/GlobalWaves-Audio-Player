package searchbar.searchresults;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.SearchMessage;
import playlist.Playlist;
import searchbar.SearchBar;

import java.util.ArrayList;

public class SearchBarPlaylistsResult implements SearchBar {
    private ArrayList<Playlist> playlists;
    private ArrayList<String> lastSearchResult;

    /***
     * constructor for SearchBarPlaylistsResult class
     * @param playlists the list of playlists the object is initialized with
     */
    public SearchBarPlaylistsResult(final ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    /***
     * playlists getter
     * @return the list of playlists
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /***
     * lastSearchResult getter
     * @return the list of names of the playlists
     */
    @Override
    public ArrayList<String> getLastSearchResult() {
        return lastSearchResult;
    }

    /***
     * lastSearchResult setter
     * @param lastSearchResult sets the value for lastSearchResult
     */
    @Override
    public void setLastSearchResult(final ArrayList<String> lastSearchResult) {
        this.lastSearchResult = lastSearchResult;
    }

    /***
     * method that filters the search of the playlists by a string the name of a playlist starts
     * with
     * @param name string that is used to check if a playlist's name starts with it
     * @return a list of playlists that start with the string name
     */
    public ArrayList<Playlist> filterPlaylistsByName(final String name) {
        ArrayList<Playlist> filteredPlaylists = new ArrayList<>();
        for (Playlist currentPlaylist : getPlaylists()) {
            if (currentPlaylist.getName().startsWith(name)) {
                filteredPlaylists.add(currentPlaylist);
            }

            if (filteredPlaylists.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredPlaylists;
    }

    /***
     * method that filters the search of playlists by the owner's name
     * @param owner the name of the owner
     * @return a list of playlists that are owned by the user with the name from the string owner
     */
    public ArrayList<Playlist> filterPlaylistsByOwner(final String owner) {
        ArrayList<Playlist> filteredPlaylists = new ArrayList<>();
        for (Playlist currentPlaylist : getPlaylists()) {
            if (currentPlaylist.getOwner().compareTo(owner) == 0) {
                filteredPlaylists.add(currentPlaylist);
            }

            if (filteredPlaylists.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredPlaylists;
    }

    /***
     * method that helps print the output for search command
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for the search command used on
     * playlists
     */
    @Override
    public ObjectNode searchResult(final ObjectMapper objectMapper, final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        ArrayList<Playlist> filteredPlaylists = new ArrayList<>();
        if (currentCommand.getFilters().getName() != null) {
            filteredPlaylists = filterPlaylistsByName(currentCommand.getFilters().getName());
        }
        if (currentCommand.getFilters().getOwner() != null) {
            filteredPlaylists = filterPlaylistsByOwner(currentCommand.getFilters().getOwner());
        }
        objectNode.put(CheckerConstants.MESSAGE_FIELD, SearchMessage.successSearchMessage(
                filteredPlaylists.size()));
        ArrayList<String> filteredPlaylistsNames = new ArrayList<>();
        for (Playlist currentFilteredPlaylist : filteredPlaylists) {
            filteredPlaylistsNames.add(currentFilteredPlaylist.getName());
        }
        JsonNode jsonNode = objectMapper.valueToTree(filteredPlaylistsNames);
        objectNode.set(CheckerConstants.RESULTS_FIELD, jsonNode);
        setLastSearchResult(filteredPlaylistsNames);
        return objectNode;
    }
}
