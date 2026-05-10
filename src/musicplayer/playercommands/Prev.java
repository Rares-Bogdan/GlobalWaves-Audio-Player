package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.PrevMessage;

public class Prev extends PrevMessage {
    private int secondsPassed;
    private boolean hasLoadedSource;

    public Prev(int secondsPassed, boolean hasLoadedSource) {
        this.secondsPassed = secondsPassed;
        this.hasLoadedSource = hasLoadedSource;
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }

    public boolean isHasLoadedSource() {
        return hasLoadedSource;
    }

    public ObjectNode prevResult(ObjectMapper objectMapper, Command currentCommand,
                                 String prevTrack) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!isHasLoadedSource()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, loadSourceBeforePrevMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, prevTrackSuccessMessage(prevTrack));
        }
        return objectNode;
    }
}
