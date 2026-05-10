package playlist;

import fileio.input.SongInput;

import java.util.ArrayList;

public class PlaylistJsonNode {
    private String name;
    private ArrayList<String> songs;
    private String visibility;
    private int followers;

    public PlaylistJsonNode(String name, ArrayList<String> songs, String visibility, int followers) {
        this.name = name;
        this.songs = songs;
        this.visibility = visibility;
        this.followers = followers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<String> songs) {
        this.songs = songs;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
