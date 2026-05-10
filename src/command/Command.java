package command;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import searchbar.Filter;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class Command {
    private String command;
    private String username;
    private int timestamp;
    private int itemNumber;
    private String type;
    private Filter filters;
    private String playlistName;
    private int playlistId;
    private int seed;

    public String getCommand() {
        return command;
    }

    public String getUsername() {
        return username;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public String getType() {
        return type;
    }

    public Filter getFilters() {
        return filters;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public int getSeed() {
        return seed;
    }
}
