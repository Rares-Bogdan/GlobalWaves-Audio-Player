package playlist.playlistcommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.FollowMessage;

public class FollowPlaylist extends FollowMessage {
    private boolean hasBeenFollowed;
    private boolean isSelectedSourcePlaylist;
    private boolean isSelectedSource;
    private boolean ownPlaylistFollowAttempt;

    public FollowPlaylist(boolean hasBeenFollowed, boolean isSelectedSourcePlaylist,
                          boolean isSelectedSource, boolean ownPlaylistFollowAttempt) {
        this.hasBeenFollowed = hasBeenFollowed;
        this.isSelectedSourcePlaylist = isSelectedSourcePlaylist;
        this.isSelectedSource = isSelectedSource;
        this.ownPlaylistFollowAttempt = ownPlaylistFollowAttempt;
    }

    public boolean isHasBeenFollowed() {
        return hasBeenFollowed;
    }

    public boolean isSelectedSourcePlaylist() {
        return isSelectedSourcePlaylist;
    }

    public boolean isSelectedSource() {
        return isSelectedSource;
    }

    public boolean isOwnPlaylistFollowAttempt() {
        return ownPlaylistFollowAttempt;
    }

    public ObjectNode followPlaylistResult(ObjectMapper objectMapper, Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!isSelectedSource()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, noSourceFollowMessage());
        } else if (!isSelectedSourcePlaylist()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, selectedSourceNotPlaylistMessage());
        } else if (isOwnPlaylistFollowAttempt()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, noFollowYourOwnPlaylistMessage());
        } else if (!isHasBeenFollowed()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, successFollowMessage());
        } else if (isHasBeenFollowed()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, successUnfollowMessage());
        }
        return objectNode;
    }
}
