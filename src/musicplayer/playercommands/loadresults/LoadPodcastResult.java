package musicplayer.playercommands.loadresults;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.LoadMessage;
import musicplayer.MusicPlayer;
import musicplayer.playercommands.Load;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoadPodcastResult implements Load {
    private String podcast;
    private AtomicBoolean selectedPodcast;

    /***
     *
     * @param selectedPodcast checks if a podcast was selected
     * @param podcast name of the selected podcast
     */
    public LoadPodcastResult(final AtomicBoolean selectedPodcast, final String podcast) {
        this.selectedPodcast = selectedPodcast;
        this.podcast = podcast;
    }

    /***
     *
     * @return name of the selected podcast
     */
    public String getPodcast() {
        return podcast;
    }

    /***
     *
     * @return an atomic boolean with true value if a podcast is selected or false otherwise
     */
    public AtomicBoolean getSelectedPodcast() {
        return selectedPodcast;
    }

    /***
     *
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @param musicPlayer play / pause state of the music player
     * @return an object node that stores the result message for load command used on a podcast
     */
    @Override
    public ObjectNode loadResult(final ObjectMapper objectMapper, final Command currentCommand,
                                 final MusicPlayer musicPlayer) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!getSelectedPodcast().get()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, LoadMessage.selectSourceMessage());
        } else if (getPodcast().isEmpty()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, LoadMessage.
                    emptyAudioCollectionMessage());
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, LoadMessage.successMessage());
            musicPlayer.setPlayPause(true);
        }
        return objectNode;
    }
}
