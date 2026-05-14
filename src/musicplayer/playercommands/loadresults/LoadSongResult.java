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

    /***
     * constructor for LoadSongResult class
     * @param selectedSong checks if a song was selected
     * @param song name of the selected song
     */
    public LoadSongResult(final AtomicBoolean selectedSong, final String song) {
        this.selectedSong = selectedSong;
        this.song = song;
    }

    /***
     * selectedSong getter
     * @return an atomic boolean with true value if a song is selected or false otherwise
     */
    public AtomicBoolean getSelectedSong() {
        return selectedSong;
    }

    /***
     * song name getter
     * @return name of the selected song
     */
    public String getSong() {
        return song;
    }

    /***
     * method that helps print the output for load command used on a song
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @param musicPlayer play / pause state of the music player
     * @return an object node that stores the result message for load command used on a song
     */
    @Override
    public ObjectNode loadResult(final ObjectMapper objectMapper, final Command currentCommand,
                                 final MusicPlayer musicPlayer) {
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
