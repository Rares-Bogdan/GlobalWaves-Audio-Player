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

    public Like(AtomicBoolean loadedSource, AtomicBoolean selectedSong,
                AtomicBoolean likedOrUnliked, SongInput songInput) {
        this.loadedSource = loadedSource;
        this.selectedSong = selectedSong;
        this.likedOrUnliked = likedOrUnliked;
        this.songInput = songInput;
    }

    public AtomicBoolean getLoadedSource() {
        return loadedSource;
    }

    public void setLoadedSource(AtomicBoolean loadedSource) {
        this.loadedSource = loadedSource;
    }

    public AtomicBoolean getSelectedSong() {
        return selectedSong;
    }

    public void setSelectedSong(AtomicBoolean selectedSong) {
        this.selectedSong = selectedSong;
    }

    public AtomicBoolean getLikedOrUnliked() {
        return likedOrUnliked;
    }

    public void setLikedOrUnliked(AtomicBoolean likedOrUnliked) {
        this.likedOrUnliked = likedOrUnliked;
    }

    public SongInput getSongInput() {
        return songInput;
    }

    public void setSongInput(SongInput songInput) {
        this.songInput = songInput;
    }

    public ObjectNode likedResult(ObjectMapper objectMapper, Command currentCommand, Playlist playlist) {
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
