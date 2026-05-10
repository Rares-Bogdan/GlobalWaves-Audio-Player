package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.RepeatMessage;

public class Repeat extends RepeatMessage {
    private String repeatState;
    private boolean hasLoadedSource;

    public Repeat(String repeatState, boolean hasLoadedSource) {
        this.repeatState = repeatState;
        this.hasLoadedSource = hasLoadedSource;
    }

    public String getRepeatState() {
        return repeatState;
    }

    public void setRepeatState(String repeatState) {
        this.repeatState = repeatState;
    }

    public boolean hasLoadedSource() {
        return hasLoadedSource;
    }

    public void setHasLoadedSource(boolean hasLoadedSource) {
        this.hasLoadedSource = hasLoadedSource;
    }

    public ObjectNode repeatResult(ObjectMapper objectMapper, Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!hasLoadedSource()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD,
                    loadSourceBeforeChangeRepeatStatusMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, repeatModeChangedMessage(getRepeatState()));
        }
        return objectNode;
    }
}
