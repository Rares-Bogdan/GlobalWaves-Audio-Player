package commandsworkflow;

import UserApp.UserApp;
import command.Command;
import playlist.Playlist;

import java.util.ArrayList;

public class FollowWorkflow extends CommandWorkflow {
    private ArrayList<Playlist> followedPlaylists;
    private ArrayList<Playlist> allPlaylists;
    private String currentPlaylistSelected;
    private boolean hasBeenFollowed;
    private boolean selfPlaylistFollowAttempt;

    /***
     * constructor for FollowWorkflow class
     * @param currentCommand current command used
     * @param userApp object used to store all the data concerning a user
     * @param allPlaylists all existing playlists
     * @param hasBeenFollowed checks if a playlist has been followed or not
     * @param selfPlaylistFollowAttempt checks if a user tried to follow said user's own playlist
     */
    public FollowWorkflow(final Command currentCommand,
                          final UserApp userApp,
                          final ArrayList<Playlist> allPlaylists,
                          final boolean hasBeenFollowed,
                          final boolean selfPlaylistFollowAttempt) {
        super(currentCommand, userApp);
        this.allPlaylists = allPlaylists;
        this.hasBeenFollowed = hasBeenFollowed;
        this.selfPlaylistFollowAttempt = selfPlaylistFollowAttempt;
    }

    /***
     * allPlaylists getter
     * @return allPlaylists
     */
    public ArrayList<Playlist> getAllPlaylists() {
        return allPlaylists;
    }

    /***
     * hasBeenFollowed getter
     * @return hasBeenFollowed
     */
    public boolean isHasBeenFollowed() {
        return hasBeenFollowed;
    }

    /***
     * hasBeenFollowed setter
     * @param hasBeenFollowed sets the value for hasBeenFollowed
     */
    public void setHasBeenFollowed(final boolean hasBeenFollowed) {
        this.hasBeenFollowed = hasBeenFollowed;
    }

    /***
     * selfPlaylistFollowAttempt getter
     * @return selfPlaylistFollowAttempt
     */
    public boolean isSelfPlaylistFollowAttempt() {
        return selfPlaylistFollowAttempt;
    }

    /***
     * selfPlaylistFollowAttempt setter
     * @param selfPlaylistFollowAttempt sets the value for selfPlaylistFollowAttempt
     */
    public void setSelfPlaylistFollowAttempt(final boolean selfPlaylistFollowAttempt) {
        this.selfPlaylistFollowAttempt = selfPlaylistFollowAttempt;
    }

    public ArrayList<Playlist> followCommandWorkflow() {
        int playlistToBeRemovedIndex = -1;
        for (int currentPlaylist = 0; currentPlaylist < getUserApp().getFollowedPlaylists().size();
             currentPlaylist++) {
            if (getUserApp().getFollowedPlaylists().get(currentPlaylist).getName().compareTo(
                    getUserApp().getCurrentPlaylistSelected()) == 0) {
                setHasBeenFollowed(true);
                playlistToBeRemovedIndex = currentPlaylist;
                break;
            }
        }
        if (isHasBeenFollowed()) {
            getUserApp().getFollowedPlaylists().remove(getUserApp().getFollowedPlaylists().get(
                    playlistToBeRemovedIndex));
            for (Playlist currentPlaylist : getAllPlaylists()) {
                if (currentPlaylist.getName().compareTo(getUserApp().getCurrentPlaylistSelected())
                        == 0) {
                    currentPlaylist.setFollowers(currentPlaylist.getFollowers() - 1);
                }
            }
        } else {
            for (int currentPlaylist = 0; currentPlaylist < getAllPlaylists().size();
                 currentPlaylist++) {
                if (getAllPlaylists().get(currentPlaylist).getName().compareTo(
                        getUserApp().getCurrentPlaylistSelected()) == 0) {
                    if (getAllPlaylists().get(currentPlaylist).getOwner().compareTo(
                            getCurrentCommand().getUsername()) == 0) {
                        setSelfPlaylistFollowAttempt(true);
                    } else {
                        getAllPlaylists().get(currentPlaylist).setFollowers(getAllPlaylists().get(
                                currentPlaylist).getFollowers() + 1);
                        getUserApp().getFollowedPlaylists().add(getAllPlaylists().get(currentPlaylist));
                    }
                    break;
                }
            }
        }
        return getUserApp().getFollowedPlaylists();
    }
}
