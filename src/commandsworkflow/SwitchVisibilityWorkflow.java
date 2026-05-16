package commandsworkflow;

import UserApp.UserApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import playlist.Playlist;

import java.util.ArrayList;

public class SwitchVisibilityWorkflow extends CommandWorkflow {
    private boolean tooHighIdPlaylist;
    private boolean visibilityStatus;

    /***
     * constructor for SwitchVisibilityWorkflow class
     * @param currentCommand current command used
     * @param userApp object used to store all the data concerning a user
     * @param tooHighIdPlaylist checks if the playlist the user tries to select has a too high id
     * @param visibilityStatus checks the visibility status of the playlist
     */
    public SwitchVisibilityWorkflow(final Command currentCommand,
                                    final UserApp userApp,
                                    final boolean tooHighIdPlaylist,
                                    final boolean visibilityStatus) {
        super(currentCommand, userApp);
        this.tooHighIdPlaylist = tooHighIdPlaylist;
        this.visibilityStatus = visibilityStatus;
    }

    /***
     * tooHighIdPlaylist getter
     * @return tooHighIdPlaylist
     */
    public boolean isTooHighIdPlaylist() {
        return tooHighIdPlaylist;
    }

    /***
     * visibilityStatus getter
     * @return visibilityStatus
     */
    public boolean isVisibilityStatus() {
        return visibilityStatus;
    }

    /***
     * visibilityStatus setter
     * @param visibilityStatus sets the value for visibilityStatus
     */
    public void setVisibilityStatus(final boolean visibilityStatus) {
        this.visibilityStatus = visibilityStatus;
    }

    /***
     * method that handles the workflow for switchVisibility command
     * @return the list of playlists that belong to a user after a visibility change occured
     */
    public ArrayList<Playlist> switchVisibilityCommandWorkflow() {
        int foundPlaylistIndex = -1;
        if (!isTooHighIdPlaylist()) {
            for (int currentPlaylist = 0; currentPlaylist < getUserApp().getUserPlaylists().size();
                 currentPlaylist++) {
                if (currentPlaylist == getCurrentCommand().getPlaylistId() - 1) {
                    foundPlaylistIndex = currentPlaylist;
                    getUserApp().getUserPlaylists().get(currentPlaylist).setVisibility(
                            !getUserApp().getUserPlaylists().get(currentPlaylist).isVisibility());
                    break;
                }
            }
        }

        if (foundPlaylistIndex > -1) {
            setVisibilityStatus(getUserApp().getUserPlaylists().get(foundPlaylistIndex).
                    isVisibility());
        }

        return getUserApp().getUserPlaylists();
    }
}
