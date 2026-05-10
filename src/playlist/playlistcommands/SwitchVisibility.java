package playlist.playlistcommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.SwitchVisibilityMessage;

public class SwitchVisibility extends SwitchVisibilityMessage {
    private boolean visilibityStatus;
    private boolean tooHighIdPlaylist;

    public SwitchVisibility(boolean visilibityStatus, boolean tooHighIdPlaylist) {
        this.visilibityStatus = visilibityStatus;
        this.tooHighIdPlaylist = tooHighIdPlaylist;
    }

    public boolean isVisilibityStatus() {
        return visilibityStatus;
    }

    public boolean isTooHighIdPlaylist() {
        return tooHighIdPlaylist;
    }

    public ObjectNode switchVisibilityResult(ObjectMapper objectMapper, Command currentCommand) {
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
