package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.NextMessage;

public class Next extends NextMessage {
    private boolean isAtEnd;
    private boolean hasLoadedSource;

    public Next(boolean isAtEnd, boolean hasLoadedSource) {
        this.isAtEnd = isAtEnd;
        this.hasLoadedSource = hasLoadedSource;
    }

    public boolean isAtEnd() {
        return isAtEnd;
    }

    public boolean isHasLoadedSource() {
        return hasLoadedSource;
    }

    public ObjectNode nextResult(ObjectMapper objectMapper, Command currentCommand,
                                 String nextTrack) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!isHasLoadedSource() || isAtEnd()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, loadSourceBeforeNextMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, nextTrackSuccessMessage(nextTrack));
        }
        return objectNode;
    }
}
