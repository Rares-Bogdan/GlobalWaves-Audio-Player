package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.RepeatMessage;

public class Repeat extends RepeatMessage {
    private String repeatState;
    private boolean hasLoadedSource;

    /***
     * constructor for Repeat class
     * @param repeatState current repeat state of the source
     *                    constructor will initialize with no repeat
     * @param hasLoadedSource checks if the user has a source he can use repeat on
     */
    public Repeat(final String repeatState, final boolean hasLoadedSource) {
        this.repeatState = repeatState;
        this.hasLoadedSource = hasLoadedSource;
    }

    /***
     * repeatState getter
     * @return current repeat state of the source
     */
    public String getRepeatState() {
        return repeatState;
    }

    /***
     * repeatState setter
     * @param repeatState sets the current repeat state of the source
     */
    public void setRepeatState(final String repeatState) {
        this.repeatState = repeatState;
    }

    /***
     * hasLoadedSource getter
     * @return true if the user has a loaded source or false otherwise
     */
    public boolean hasLoadedSource() {
        return hasLoadedSource;
    }

    /***
     * method that helps print the output for repeat command
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for the repeat command
     */
    public ObjectNode repeatResult(final ObjectMapper objectMapper, final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!hasLoadedSource()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD,
                    loadSourceBeforeChangeRepeatStatusMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, repeatModeChangedMessage(
                    getRepeatState()));
        }
        return objectNode;
    }
}
