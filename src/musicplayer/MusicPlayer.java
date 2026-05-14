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

    /***
     * constructor for MusicPlayer class
     * @param playPause play state of the music player
     */
    public MusicPlayer(final boolean playPause) {
        this.playPause = playPause;
    }

    /***
     * playPause getter
     * @return the play state of the music player
     */
    public boolean isPlayPause() {
        return playPause;
    }

    /***
     * playPause setter
     * @param playPause sets the play state of the music player
     */
    public void setPlayPause(final boolean playPause) {
        this.playPause = playPause;
    }

    /***
     * method that helps print the output for playPause command
     * @param objectMapper object used to print output in JSON format
     * @param currentCommand current command used
     * @param loadedSource checks if there exists a loaded source to be played or paused
     * @param status current status of the user's loaded source
     * @return an object node containing the result of the playPause command
     */
    public ObjectNode playPauseResult(final ObjectMapper objectMapper, final Command currentCommand,
                                      final AtomicBoolean loadedSource, final Status status) {
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
