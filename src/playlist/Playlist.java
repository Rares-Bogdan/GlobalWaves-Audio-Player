package playlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.input.SongInput;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<SongInput> songs;
    private boolean visibility;
    private int followers;
    @JsonIgnore
    private String owner;

    /***
     * constructor for Playlist class
     * @param songs list of the songs that belong to a playlist
     * @param visibility visibility state of the playlist (public or private)
     * @param name name of the playlist
     * @param followers number of followers a playlist has
     */
    public Playlist(final ArrayList<SongInput> songs, final boolean visibility, final String name,
                    final int followers) {
        this.songs = songs;
        this.visibility = visibility;
        this.name = name;
        this.followers = followers;
    }

    /***
     * default constructor for a playlist
     */
    public Playlist() {
        this.songs = new ArrayList<>();
        this.visibility = true;
        this.name = "";
        this.followers = 0;
    }

    /***
     * songs getter
     * @return the song list from the playlist
     */
    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    /***
     * songs setter
     * @param songs sets the song list of the playlist
     */
    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    /***
     * visibility getter
     * @return true if the visibility is public or false if it is private
     */
    public boolean isVisibility() {
        return visibility;
    }

    /***
     * visibility setter
     * @param visibility sets the visibility status of the playlist
     */
    public void setVisibility(final boolean visibility) {
        this.visibility = visibility;
    }

    /***
     * name getter
     * @return name of the playlist
     */
    public String getName() {
        return name;
    }

    /***
     * name setter
     * @param name sets the name of the playlist
     */
    public void setName(final String name) {
        this.name = name;
    }

    /***
     * followers getter
     * @return number of followers of a playlist
     */
    public int getFollowers() {
        return followers;
    }

    /***
     * followers setter
     * @param followers sets the number of followers of a playlist
     */
    public void setFollowers(final int followers) {
        this.followers = followers;
    }

    /***
     * owner getter
     * @return name of the owner of a playlist
     */
    public String getOwner() {
        return owner;
    }

    /***
     * owner setter
     * @param owner sets the name of the owner of a playlist
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }
}
