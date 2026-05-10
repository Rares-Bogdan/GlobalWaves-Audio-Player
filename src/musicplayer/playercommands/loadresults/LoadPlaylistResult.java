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
    AtomicBoolean selectedPlaylist;

    public LoadPlaylistResult(String playlist, AtomicBoolean selectedPlaylist) {
        this.playlist = playlist;
        this.selectedPlaylist = selectedPlaylist;
    }

    public String getPlaylist() {
        return playlist;
    }

    public AtomicBoolean getSelectedPlaylist() {
        return selectedPlaylist;
    }

    @Override
    public ObjectNode loadResult(ObjectMapper objectMapper, Command currentCommand,
                                 MusicPlayer musicPlayer) {
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
