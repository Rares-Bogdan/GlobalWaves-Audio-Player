package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

import static messages.BackwardMessage.backwardSkipSuccessMessage;
import static messages.BackwardMessage.noLoadedSourceToBackwardMessage;

public class Backward extends Skip {

    /***
     * constructor for Backward class
     * @param isLoadedSourcePodcast checks if the loaded source is a podcast
     * @param hasLoadedSource checks if the user has a loaded source he can rewind
     */
    public Backward(final boolean isLoadedSourcePodcast, final boolean hasLoadedSource) {
        super(isLoadedSourcePodcast, hasLoadedSource);
    }

    /***
     * method that helps print the output for backward command
     * @param objectMapper object used to print output in JSON format
     * @param currentCommand current command used
     * @return an object node that prints the result message of the backward command
     */
    public ObjectNode backwardResult(final ObjectMapper objectMapper,
                                     final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!isHasLoadedSource()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, noLoadedSourceToBackwardMessage());
        } else if (isLoadedSourcePodcast()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, backwardSkipSuccessMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, loadedSourceNotPodcastMessage());
        }
        return objectNode;
    }
}
