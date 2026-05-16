package commandsworkflow;

import UserApp.UserApp;
import checker.CheckerConstants;
import command.Command;
import fileio.input.SongInput;
import playlist.Playlist;
import playlist.PlaylistJsonNode;

import java.util.ArrayList;

public class ShowPlaylistsWorkflow extends CommandWorkflow {

    /***
     * constructor for ShowPlaylistWorkflow class
     * @param currentCommand current command used
     * @param userApp object used to store all the data concerning a user
     */
    public ShowPlaylistsWorkflow(final Command currentCommand, final UserApp userApp) {
        super(currentCommand, userApp);
    }

    /***
     * method that handles the workflow for showPlaylists command
     * @return a list of objects of a customized class that contains the names of playlists, the
     * list of names of songs from the playlists, visibility status and number of followers
     */
    public ArrayList<PlaylistJsonNode> showPlaylistsCommandWorkflow() {
        ArrayList<ArrayList<String>> userPlaylistsSongNames = new ArrayList<>();
        for (int currentPlaylist = 0; currentPlaylist < getUserApp().getUserPlaylists().size();
             currentPlaylist++) {
            userPlaylistsSongNames.add(new ArrayList<>());
            for (SongInput currentSong : getUserApp().getUserPlaylists().get(currentPlaylist).
                    getSongs()) {
                userPlaylistsSongNames.get(currentPlaylist).add(currentSong.getName());
            }
        }
        ArrayList<PlaylistJsonNode> playlistJsonNodes = new ArrayList<>();
        int currentPlaylistIndex = 0;
        for (Playlist currentPlaylist : getUserApp().getUserPlaylists()) {
            String visibilityString;
            if (currentPlaylist.isVisibility()) {
                visibilityString = CheckerConstants.PUBLIC;
            } else {
                visibilityString = CheckerConstants.PRIVATE;
            }
            playlistJsonNodes.add(new PlaylistJsonNode(currentPlaylist.getName(),
                    userPlaylistsSongNames.get(currentPlaylistIndex), visibilityString,
                    currentPlaylist.getFollowers()));
            currentPlaylistIndex++;
        }

        return playlistJsonNodes;
    }
}
