package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.PrevMessage;

public class Prev extends PrevMessage {
    private int secondsPassed;
    private boolean hasLoadedSource;

    /***
     *
     * @param secondsPassed how many seconds passed since the start of the source
     * @param hasLoadedSource checks if the user has a loaded source he can use prev on
     */
    public Prev(final int secondsPassed, final boolean hasLoadedSource) {
        this.secondsPassed = secondsPassed;
        this.hasLoadedSource = hasLoadedSource;
    }

    /***
     *
     * @return true if the user has a loaded source he can use prev on or false otherwise
     */
    public boolean isHasLoadedSource() {
        return hasLoadedSource;
    }

    /***
     *
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @param prevTrack the name of the previous track
     * @return an object node that stores the result message for the prev command
     */
    public ObjectNode prevResult(final ObjectMapper objectMapper, final Command currentCommand,
                                 final String prevTrack) {
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
