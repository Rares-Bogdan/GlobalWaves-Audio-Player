package statistics;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

import java.util.ArrayList;

public class ShowPreferredSongs {
    private ArrayList<String> preferredSongsNames;

    /***
     * constructor for ShowPreferredSongs class
     * @param preferredSongsNames value the object is initialized with
     */
    public ShowPreferredSongs(final ArrayList<String> preferredSongsNames) {
        this.preferredSongsNames = preferredSongsNames;
    }

    /***
     * preferredSongsNames getter
     * @return preferredSongsNames
     */
    public ArrayList<String> getPreferredSongsNames() {
        return preferredSongsNames;
    }

    /***
     * method that prints the output for showPreferredSongs command
     * @param objectMapper object that helps print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for showPreferredSongs command
     */
    public ObjectNode showPreferredSongsResult(ObjectMapper objectMapper, Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        JsonNode jsonNode = objectMapper.valueToTree(getPreferredSongsNames());
        objectNode.set(CheckerConstants.RESULT_PATH, jsonNode);
        return objectNode;
    }
}
