package playlist.playlistcommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import playlist.PlaylistJsonNode;

import java.util.ArrayList;

public class ShowPlaylists {
    private ArrayList<PlaylistJsonNode> playlistJsonNodes;

    /***
     * constructor for ShowPlaylists class
     * @param playlistJsonNodes value the object is initialized with
     */
    public ShowPlaylists(ArrayList<PlaylistJsonNode> playlistJsonNodes) {
        this.playlistJsonNodes = playlistJsonNodes;
    }

    /***
     * playlistJsonNodes getter
     * @return playlistJsonNodes
     */
    public ArrayList<PlaylistJsonNode> getPlaylistJsonNodes() {
        return playlistJsonNodes;
    }

    /***
     * method that stores the output for showPlaylists command in an object node
     * @param objectMapper object that helps print the output in JSON format
     * @param currentCommand currentCommand used
     * @return an object node that stores the result message for showPlaylists command
     */
    public ObjectNode showPlaylistsResult(ObjectMapper objectMapper, Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        JsonNode jsonNode = objectMapper.valueToTree(playlistJsonNodes);
        objectNode.set(CheckerConstants.RESULT_PATH, jsonNode);
        return objectNode;
    }
}
