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

    public SearchBarPlaylistsResult(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public ArrayList<String> getLastSearchResult() {
        return lastSearchResult;
    }

    @Override
    public void setLastSearchResult(ArrayList<String> lastSearchResult) {
        this.lastSearchResult = lastSearchResult;
    }

    public ArrayList<Playlist> filterPlaylistsByName(String name) {
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

    public ArrayList<Playlist> filterPlaylistsByOwner(String owner) {
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

    @Override
    public ObjectNode searchResult(ObjectMapper objectMapper, Command currentCommand) {
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
