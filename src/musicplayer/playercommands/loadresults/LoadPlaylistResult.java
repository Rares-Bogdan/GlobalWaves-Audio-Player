package musicplayer.playercommands.loadresults;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.LoadMessage;
import musicplayer.MusicPlayer;
import musicplayer.playercommands.Load;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoadPlaylistResult implements Load {
    private String playlist;
    private AtomicBoolean selectedPlaylist;

    /***
     *
     * @param playlist name of the selected playlist
     * @param selectedPlaylist checks if a playlist was selected
     */
    public LoadPlaylistResult(final String playlist, final AtomicBoolean selectedPlaylist) {
        this.playlist = playlist;
        this.selectedPlaylist = selectedPlaylist;
    }

    /***
     *
     * @return name of the selected playlist
     */
    public String getPlaylist() {
        return playlist;
    }

    /***
     *
     * @return an atomic boolean with true value if a playlist was selected or false otherwise
     */
    public AtomicBoolean getSelectedPlaylist() {
        return selectedPlaylist;
    }

    /***
     *
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @param musicPlayer play / pause state of the music player
     * @return an object node that stores the result message for load command used on a playlist
     */
    @Override
    public ObjectNode loadResult(final ObjectMapper objectMapper, final Command currentCommand,
                                 final MusicPlayer musicPlayer) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!getSelectedPlaylist().get()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, LoadMessage.selectSourceMessage());
        } else if (getPlaylist().isEmpty()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, LoadMessage.
                    emptyAudioCollectionMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, LoadMessage.successMessage());
            musicPlayer.setPlayPause(true);
        }
        return objectNode;
    }
}
