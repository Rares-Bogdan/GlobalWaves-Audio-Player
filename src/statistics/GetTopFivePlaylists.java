package statistics;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

import java.util.ArrayList;

public class GetTopFivePlaylists {
    private ArrayList<String> top5Playlists;

    /***
     * constructor for GetTopFivePlaylists class
     * @param top5Playlists value the object is initialized with
     */
    public GetTopFivePlaylists(final ArrayList<String> top5Playlists) {
        this.top5Playlists = top5Playlists;
    }

    /***
     * top5Playlists getter
     * @return a list of names that represent the top 5 most
     */
    public ArrayList<String> getTop5Playlists() {
        return top5Playlists;
    }

    /***
     * method that stores the output for getTop5Playlists command in an object node
     * @param objectMapper object that helps print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for getTop5Playlists command
     */
    public ObjectNode topFivePlaylistsResult(final ObjectMapper objectMapper,
                                             final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        JsonNode jsonNode = objectMapper.valueToTree(getTop5Playlists());
        objectNode.set(CheckerConstants.RESULT_PATH, jsonNode);
        return objectNode;
    }
}
