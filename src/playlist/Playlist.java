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

    public Playlist(ArrayList<SongInput> songs, boolean visibility, String name, int followers) {
        this.songs = songs;
        this.visibility = visibility;
        this.name = name;
        this.followers = followers;
    }

    public Playlist() {
        this.songs = new ArrayList<>();
        this.visibility = true;
        this.name = "";
        this.followers = 0;
    }

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
