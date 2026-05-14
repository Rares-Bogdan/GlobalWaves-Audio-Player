package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

import static messages.ForwardMessage.forwardSkipSuccessMessage;
import static messages.ForwardMessage.noLoadedSourceToForwardMessage;

public class Forward extends Skip {

    /***
     * constructor for Forward class
     * @param isLoadedSourcePodcast checks if the loaded source is a podcast
     * @param hasLoadedSource checks if the user has a loaded source he can use forward on
     */
    public Forward(final boolean isLoadedSourcePodcast, final boolean hasLoadedSource) {
        super(isLoadedSourcePodcast, hasLoadedSource);
    }

    /***
     * method that helps print the output for forward command
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that prints the result message of the forward command
     */
    public ObjectNode forwardResult(final ObjectMapper objectMapper,
                                    final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!isHasLoadedSource()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, noLoadedSourceToForwardMessage());
        } else if (isLoadedSourcePodcast()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, forwardSkipSuccessMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, loadedSourceNotPodcastMessage());
        }
        return objectNode;
    }
}
