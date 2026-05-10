package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

import static messages.BackwardMessage.backwardSkipSuccessMessage;
import static messages.BackwardMessage.noLoadedSourceToBackwardMessage;

public class Backward extends Skip {

    public Backward(boolean isLoadedSourcePodcast, boolean hasLoadedSource) {
        super(isLoadedSourcePodcast, hasLoadedSource);
    }

    public ObjectNode backwardResult(ObjectMapper objectMapper, Command currentCommand) {
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
