package searchbar;

import java.util.ArrayList;

public class Filter {
    private String name;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private String owner;

    /***
     * name getter
     * @return name
     */
    public String getName() {
        return name;
    }

    /***
     * album getter
     * @return album
     */
    public String getAlbum() {
        return album;
    }

    /***
     * lyrics getter
     * @return lyrics
     */
    public String getLyrics() {
        return lyrics;
    }

    /***
     * tags getter
     * @return tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /***
     * releaseYear getter
     * @return releaseYear
     */
    public String getReleaseYear() {
        return releaseYear;
    }

    /***
     * genre getter
     * @return genre
     */
    public String getGenre() {
        return genre;
    }

    /***
     * artist getter
     * @return artist
     */
    public String getArtist() {
        return artist;
    }

    /***
     * owner getter
     * @return owner
     */
    public String getOwner() {
        return owner;
    }
}
