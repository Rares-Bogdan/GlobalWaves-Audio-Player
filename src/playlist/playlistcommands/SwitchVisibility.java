package playlist.playlistcommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.SwitchVisibilityMessage;

public class SwitchVisibility extends SwitchVisibilityMessage {
    private boolean visilibityStatus;
    private boolean tooHighIdPlaylist;

    /***
     * constructor for SwitchVisibility class
     * @param visilibityStatus visibility state of the playlist
     * @param tooHighIdPlaylist checks if the id of the playlist is too high to change its
     *                          visibility
     */
    public SwitchVisibility(final boolean visilibityStatus, final boolean tooHighIdPlaylist) {
        this.visilibityStatus = visilibityStatus;
        this.tooHighIdPlaylist = tooHighIdPlaylist;
    }

    /***
     * visibilityStatus getter
     * @return visibility state of the playlist
     */
    public boolean isVisilibityStatus() {
        return visilibityStatus;
    }

    /***
     * tooHighIdPlaylist getter
     * @return true if the id of the playlist is too high to change its visibility or false
     *         otherwise
     */
    public boolean isTooHighIdPlaylist() {
        return tooHighIdPlaylist;
    }

    /***
     * method that helps print the output for switchVisibility command
     * @param objectMapper object that helps print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for the switchVisibility command
     */
    public ObjectNode switchVisibilityResult(final ObjectMapper objectMapper,
                                             final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (isTooHighIdPlaylist()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, tooHighIdPlaylistMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, visibilityStatusUpdateMessage(
                    isVisilibityStatus()));
        }
        return objectNode;
    }
}
