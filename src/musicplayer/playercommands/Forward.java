package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

import static messages.ForwardMessage.forwardSkipSuccessMessage;
import static messages.ForwardMessage.noLoadedSourceToForwardMessage;

public class Forward extends Skip {

    public Forward(boolean isLoadedSourcePodcast, boolean hasLoadedSource) {
        super(isLoadedSourcePodcast, hasLoadedSource);
    }

    public ObjectNode forwardResult(ObjectMapper objectMapper, Command currentCommand) {
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
