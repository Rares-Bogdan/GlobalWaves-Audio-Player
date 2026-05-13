package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.NextMessage;

public class Next extends NextMessage {
    private boolean isAtEnd;
    private boolean hasLoadedSource;

    /***
     *
     * @param isAtEnd checks if the source is at the last song / episode from the
     *                playlist / podcast
     * @param hasLoadedSource checks if the user has a loaded source
     */
    public Next(final boolean isAtEnd, final boolean hasLoadedSource) {
        this.isAtEnd = isAtEnd;
        this.hasLoadedSource = hasLoadedSource;
    }

    /***
     *
     * @return true if the source is at the last song / episode or false otherwise
     */
    public boolean isAtEnd() {
        return isAtEnd;
    }

    /***
     *
     * @return true if the user has a loaded source he can use next on or false otherwise
     */
    public boolean isHasLoadedSource() {
        return hasLoadedSource;
    }

    /***
     *
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @param nextTrack name of the next track
     * @return an object node that stores the result message of the next command
     */
    public ObjectNode nextResult(final ObjectMapper objectMapper, final Command currentCommand,
                                 final String nextTrack) {
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
