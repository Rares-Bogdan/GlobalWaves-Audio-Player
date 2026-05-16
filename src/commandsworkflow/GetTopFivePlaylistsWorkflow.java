package commandsworkflow;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import playlist.Playlist;

import java.util.ArrayList;

public class GetTopFivePlaylistsWorkflow {
    private ArrayList<Playlist> allPlaylists;

    /***
     * constructor for GetTopFivePlaylistsWorkflow class
     * @param allPlaylists all existing playlists
     */
    public GetTopFivePlaylistsWorkflow(final ArrayList<Playlist> allPlaylists) {
        this.allPlaylists = allPlaylists;
    }

    /***
     * allPlaylists getter
     * @return allPlaylists
     */
    public ArrayList<Playlist> getAllPlaylists() {
        return allPlaylists;
    }

    /***
     * method that handles the workflow for getTop5Playlists command
     * @return a list of strings that represent the names of the top 5 most followed public
     * playlists
     */
    public ArrayList<String> getTop5PlaylistsCommandWorkflow() {
        ArrayList<Playlist> publicPlaylists = new ArrayList<>();
        for (Playlist currentPlaylist : getAllPlaylists()) {
            if (currentPlaylist.isVisibility()) {
                publicPlaylists.add(currentPlaylist);
            }
        }
        int[] publicPlaylistsIndexes = new int[publicPlaylists.size()];
        for (int currentIndex = 0; currentIndex < publicPlaylists.size(); currentIndex++) {
            publicPlaylistsIndexes[currentIndex] = currentIndex;
        }
        for (int currentFollowsIndex = 0; currentFollowsIndex < publicPlaylists.size() - 1;
             currentFollowsIndex++) {
            for (int currentFollowsIndexInsider = currentFollowsIndex + 1;
                 currentFollowsIndexInsider < publicPlaylists.size(); currentFollowsIndexInsider++) {
                if (publicPlaylists.get(currentFollowsIndex).getFollowers() < publicPlaylists.get(
                        currentFollowsIndexInsider).getFollowers()) {
                    int temporary = publicPlaylistsIndexes[currentFollowsIndex];
                    publicPlaylistsIndexes[currentFollowsIndex] = publicPlaylistsIndexes[
                            currentFollowsIndexInsider];
                    publicPlaylistsIndexes[currentFollowsIndexInsider] = temporary;
                }
            }
        }
        ArrayList<String> top5Playlists = new ArrayList<>();
        for (int currentPlaylist = 0; currentPlaylist < Math.min(CheckerConstants.
                MAXIMUM_SEARCH_RESULT, publicPlaylists.size()); currentPlaylist++) {
            top5Playlists.add(publicPlaylists.get(publicPlaylistsIndexes[currentPlaylist]).getName());
        }

        return top5Playlists;
    }
}
