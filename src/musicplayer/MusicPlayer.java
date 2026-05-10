package musicplayer;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.PlayPauseMessage;
import musicplayer.playercommands.Status;

import java.util.concurrent.atomic.AtomicBoolean;

public class MusicPlayer extends PlayPauseMessage {
    private boolean playPause;

    public MusicPlayer(boolean playPause) {
        this.playPause = playPause;
    }

    public boolean isPlayPause() {
        return playPause;
    }

    public void setPlayPause(boolean playPause) {
        this.playPause = playPause;
    }

    public ObjectNode playPauseResult(ObjectMapper objectMapper, Command currentCommand,
                                      AtomicBoolean loadedSource, Status status) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!loadedSource.get()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, noSourcePlaybackMessage());
        } else if (isPlayPause()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, resumeSuccessMessage());
            status.setPaused(false);
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, pauseSuccessMessage());
            status.setPaused(true);
        }
        return objectNode;
    }
}
