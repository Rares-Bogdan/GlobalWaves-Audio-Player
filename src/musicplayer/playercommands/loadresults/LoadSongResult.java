package musicplayer.playercommands.loadresults;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.LoadMessage;
import musicplayer.MusicPlayer;
import musicplayer.playercommands.Load;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoadSongResult implements Load {
    private AtomicBoolean selectedSong;
    private String song;
    public LoadSongResult(AtomicBoolean selectedSong, String song) {
        this.selectedSong = selectedSong;
        this.song = song;
    }

    public AtomicBoolean getSelectedSong() {
        return selectedSong;
    }

    public String getSong() {
        return song;
    }

    @Override
    public ObjectNode loadResult(ObjectMapper objectMapper, Command currentCommand,
                                 MusicPlayer musicPlayer) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!getSelectedSong().get()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, LoadMessage.selectSourceMessage());
        } else if (getSong().isEmpty()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, LoadMessage.
                    emptyAudioCollectionMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, LoadMessage.successMessage());
            musicPlayer.setPlayPause(true);
        }
        return objectNode;
    }
}
