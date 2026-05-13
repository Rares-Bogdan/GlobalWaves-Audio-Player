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

    /***
     * constructor for FollowPlaylist class
     * @param hasBeenFollowed checks if a playlist has been followed
     * @param isSelectedSourcePlaylist checks if the selected source is a playlist
     * @param isSelectedSource checks if the user has selected a source
     * @param ownPlaylistFollowAttempt checks if a user attempted to follow their own playlist
     */
    public FollowPlaylist(final boolean hasBeenFollowed, final boolean isSelectedSourcePlaylist,
                          final boolean isSelectedSource, final boolean ownPlaylistFollowAttempt) {
        this.hasBeenFollowed = hasBeenFollowed;
        this.isSelectedSourcePlaylist = isSelectedSourcePlaylist;
        this.isSelectedSource = isSelectedSource;
        this.ownPlaylistFollowAttempt = ownPlaylistFollowAttempt;
    }

    /***
     * hasBeenFollowed getter
     * @return true if a playlist has been followed or false otherwise
     */
    public boolean isHasBeenFollowed() {
        return hasBeenFollowed;
    }

    /***
     * isSelectedSourcePlaylist getter
     * @return true if the selected source is a playlist or false otherwise
     */
    public boolean isSelectedSourcePlaylist() {
        return isSelectedSourcePlaylist;
    }

    /***
     * isSelectedSource getter
     * @return true if the user has selected a source or false otherwise
     */
    public boolean isSelectedSource() {
        return isSelectedSource;
    }

    /***
     * ownPlaylistFollowAttemp getter
     * @return true if the user attempted to follow their own playlist or false otherwise
     */
    public boolean isOwnPlaylistFollowAttempt() {
        return ownPlaylistFollowAttempt;
    }

    /***
     * method that helps print the output for followPlaylist command
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for followPlaylist command
     */
    public ObjectNode followPlaylistResult(final ObjectMapper objectMapper,
                                           final Command currentCommand) {
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
