package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.ShuffleMessage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Shuffle extends ShuffleMessage {
    private int seed;
    private ArrayList<Integer> songsIndexes;
    private boolean shuffleState;
    private boolean isLoadedSourcePlaylist;
    private boolean hasLoadedSource;

    /***
     *
     * @param seed number used for the random shuffling of the audio sources in a playlist
     * @param songsIndexes indexes for the songs in the playlist
     * @param shuffleState current state of the shuffling of a playlist
     * @param isLoadedSourcePlaylist checks if the loaded source is a playlist
     * @param hasLoadedSource checks if the user has a loaded source
     */
    public Shuffle(final int seed, final ArrayList<Integer> songsIndexes,
                   final boolean shuffleState,
                   final boolean isLoadedSourcePlaylist, final boolean hasLoadedSource) {
        this.seed = seed;
        this.songsIndexes = songsIndexes;
        this.shuffleState = shuffleState;
        this.isLoadedSourcePlaylist = isLoadedSourcePlaylist;
        this.hasLoadedSource = hasLoadedSource;
    }

    /***
     *
     * @return true if a playlist is shuffled or false otherwise
     */
    public boolean getShuffleState() {
        return shuffleState;
    }

    /***
     *
     * @return true if the loaded source is a playlist or false otherwise
     */
    public boolean isLoadedSourcePlaylist() {
        return isLoadedSourcePlaylist;
    }

    /***
     *
     * @return true of a source is loaded or false otherwise
     */
    public boolean isHasLoadedSource() {
        return hasLoadedSource;
    }

    /***
     *
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for the shuffle command
     */
    public ObjectNode shuffleResult(final ObjectMapper objectMapper,
                                    final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (getShuffleState()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, shuffleFunctionActivatedMessage());
        } else if (!getShuffleState()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, shuffleFunctionDeactivatedMessage());
        } else if (isHasLoadedSource() && !isLoadedSourcePlaylist()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, loadedSourceNotPlaylistMessage());
        } else if (!isHasLoadedSource()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, loadSourceBeforeShuffleMessage());
        }
        return objectNode;
    }
}
