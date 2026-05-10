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

    public Shuffle(int seed, ArrayList<Integer> songsIndexes, boolean shuffleState,
                   boolean isLoadedSourcePlaylist, boolean hasLoadedSource) {
        this.seed = seed;
        this.songsIndexes = songsIndexes;
        this.shuffleState = shuffleState;
        this.isLoadedSourcePlaylist = isLoadedSourcePlaylist;
        this.hasLoadedSource = hasLoadedSource;
    }

    public int getSeed() {
        return seed;
    }

    public ArrayList<Integer> getSongsIndexes() {
        return songsIndexes;
    }

    public void setSongsIndexes(ArrayList<Integer> songsIndexes) {
        this.songsIndexes = songsIndexes;
    }

    public boolean getShuffleState() {
        return shuffleState;
    }

    public void setShuffleState(boolean shuffleState) {
        this.shuffleState = shuffleState;
    }

    public boolean isLoadedSourcePlaylist() {
        return isLoadedSourcePlaylist;
    }

    public boolean isHasLoadedSource() {
        return hasLoadedSource;
    }

    public ObjectNode shuffleResult(ObjectMapper objectMapper, Command currentCommand) {
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
