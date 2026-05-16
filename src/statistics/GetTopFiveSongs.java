package statistics;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

import java.util.ArrayList;

public class GetTopFiveSongs {
    private ArrayList<String> top5Songs;

    /***
     * constructor for GetTopFiveSongs class
     * @param top5Songs value the object is initialized with
     */
    public GetTopFiveSongs(ArrayList<String> top5Songs) {
        this.top5Songs = top5Songs;
    }

    /***
     * getTop5Songs getter
     * @return a list of top 5 most liked songs in the library
     */
    public ArrayList<String> getTop5Songs() {
        return top5Songs;
    }

    /***
     * method that stores the output for getTop5Songs command in an object node
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for getTop5Songs command
     */
    public ObjectNode getTop5SongsResult(final ObjectMapper objectMapper,
                                         final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        JsonNode jsonNode = objectMapper.valueToTree(getTop5Songs());
        objectNode.set(CheckerConstants.RESULT_PATH, jsonNode);
        return objectNode;
    }
}
