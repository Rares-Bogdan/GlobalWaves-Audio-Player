package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import fileio.input.SongInput;
import messages.LikedMessage;
import playlist.Playlist;

import java.util.concurrent.atomic.AtomicBoolean;

public class Like extends LikedMessage {
    private AtomicBoolean loadedSource;
    private AtomicBoolean selectedSong;
    private AtomicBoolean likedOrUnliked;
    private SongInput songInput;

    /***
     * constructor for Like class
     * @param loadedSource checks if the user has a loaded source he can like or unlike
     * @param selectedSong checks if the user's loaded source is a song
     * @param likedOrUnliked current state of the feedback on the current loaded source
     * @param songInput current song data
     */
    public Like(final AtomicBoolean loadedSource, final AtomicBoolean selectedSong,
                final AtomicBoolean likedOrUnliked, final SongInput songInput) {
        this.loadedSource = loadedSource;
        this.selectedSong = selectedSong;
        this.likedOrUnliked = likedOrUnliked;
        this.songInput = songInput;
    }

    /***
     * loadedSource getter
     * @return an atomic boolean with true value if the user has a loaded source or one with false
     * value otherwise
     */
    public AtomicBoolean getLoadedSource() {
        return loadedSource;
    }

    /***
     * likedOrUnliked getter
     * @return an atomic boolean with true value if the song has been liked or one with false value
     * otherwise
     */
    public AtomicBoolean getLikedOrUnliked() {
        return likedOrUnliked;
    }

    /***
     * likedOrUnliked setter
     * @param likedOrUnliked sets the current state of the feedback on the current loaded song for
     *                       a user
     */
    public void setLikedOrUnliked(final AtomicBoolean likedOrUnliked) {
        this.likedOrUnliked = likedOrUnliked;
    }

    /***
     * songInput getter
     * @return the current song loaded data
     */
    public SongInput getSongInput() {
        return songInput;
    }

    /***
     * method that helps print the output for like command
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @param playlist a list of the liked songs by a user
     * @return an object node that stores the result message of the like command
     */
    public ObjectNode likedResult(final ObjectMapper objectMapper, final Command currentCommand,
                                  final Playlist playlist) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (!getLoadedSource().get()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, loadSourceBeforeLikeMessage());
        } else if (getLikedOrUnliked().get()) {
            setLikedOrUnliked(new AtomicBoolean(false));
            objectNode.put(CheckerConstants.MESSAGE_FIELD, unlikedSuccessfullyMessage());
            playlist.getSongs().remove(getSongInput());
        } else {
            setLikedOrUnliked(new AtomicBoolean(true));
            objectNode.put(CheckerConstants.MESSAGE_FIELD, likedSuccessfullyMessage());
            playlist.getSongs().add(getSongInput());
        }
        return objectNode;
    }
}
