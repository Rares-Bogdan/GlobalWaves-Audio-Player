package playlist;

import java.util.ArrayList;

public class PlaylistJsonNode {
    private String name;
    private ArrayList<String> songs;
    private String visibility;
    private int followers;

    /***
     *
     * @param name name of the playlist
     * @param songs list of names of the songs that belong to a playlist
     * @param visibility visibility status of the playlist
     * @param followers number of followers of a playlist
     */
    public PlaylistJsonNode(final String name, final ArrayList<String> songs,
                            final String visibility, final int followers) {
        this.name = name;
        this.songs = songs;
        this.visibility = visibility;
        this.followers = followers;
    }

    /***
     *
     * @return name of the playlist
     */
    public String getName() {
        return name;
    }

    /***
     *
     * @param name sets the name of the playlist
     */
    public void setName(final String name) {
        this.name = name;
    }

    /***
     *
     * @return list of names of the songs that belong to the playlist
     */
    public ArrayList<String> getSongs() {
        return songs;
    }

    /***
     *
     * @param songs sets the list of names of the songs that belong to the playlist
     */
    public void setSongs(final ArrayList<String> songs) {
        this.songs = songs;
    }

    /***
     *
     * @return visibility status of the playlist
     */
    public String getVisibility() {
        return visibility;
    }

    /***
     *
     * @param visibility sets the visibility status of the playlist
     */
    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    /***
     *
     * @return number of followers of the playlist
     */
    public int getFollowers() {
        return followers;
    }

    /***
     *
     * @param followers sets the number of followers of the playlist
     */
    public void setFollowers(final int followers) {
        this.followers = followers;
    }
}
