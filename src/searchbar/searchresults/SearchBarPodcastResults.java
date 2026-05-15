package searchbar.searchresults;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import fileio.input.PodcastInput;
import messages.SearchMessage;
import searchbar.SearchBar;

import java.util.ArrayList;

public class SearchBarPodcastResults implements SearchBar {
    private ArrayList<PodcastInput> podcasts;
    private ArrayList<String> lastSearchResult;

    /***
     * constructor for SearchBarPodcastsResult class
     * @param podcasts the list of podcasts the object is initialized with
     */
    public SearchBarPodcastResults(final ArrayList<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    /***
     * podcasts getter
     * @return the list of podcasts
     */
    public ArrayList<PodcastInput> getPodcasts() {
        return podcasts;
    }

    /***
     * method that filters the search of the podcasts by a string the name of a podcast starts
     * with
     * @param name string that is used to check if a podcast's name starts with it
     * @return a list of podcasts that start with the string name
     */
    public ArrayList<PodcastInput> filterPodcastsByName(final String name) {
        ArrayList<PodcastInput> filteredPodcasts = new ArrayList<>();
        for (PodcastInput currentPodcast : getPodcasts()) {
            if (currentPodcast.getName().startsWith(name)) {
                filteredPodcasts.add(currentPodcast);
            }

            if (filteredPodcasts.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredPodcasts;
    }

    /***
     * method that filters the search of podcasts by the owner's name
     * @param owner the name of the owner
     * @return a list of podcasts that are owned by the user with the name from the string owner
     */
    public ArrayList<PodcastInput> filterPodcastsByOwner(final String owner) {
        ArrayList<PodcastInput> filteredPodcasts = new ArrayList<>();
        for (PodcastInput currentPodcast : getPodcasts()) {
            if (currentPodcast.getOwner().compareTo(owner) == 0) {
                filteredPodcasts.add(currentPodcast);
            }

            if (filteredPodcasts.size() == CheckerConstants.MAXIMUM_SEARCH_RESULT) {
                break;
            }
        }
        return filteredPodcasts;
    }

    /***
     * method that helps print the output for search command
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for the search command used on
     * podcasts
     */
    @Override
    public ObjectNode searchResult(final ObjectMapper objectMapper, final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        ArrayList<PodcastInput> filteredPodcasts = new ArrayList<>();
        if (currentCommand.getFilters().getName() != null) {
            filteredPodcasts = filterPodcastsByName(currentCommand.getFilters().getName());
        }
        if (currentCommand.getFilters().getOwner() != null) {
            filteredPodcasts = filterPodcastsByOwner(currentCommand.getFilters().getOwner());
        }
        ArrayList<String> filteredPodcastsNames = new ArrayList<>();
        for (PodcastInput currentFilteredPodcast : filteredPodcasts) {
            filteredPodcastsNames.add(currentFilteredPodcast.getName());
        }
        objectNode.put(CheckerConstants.MESSAGE_FIELD, SearchMessage.successSearchMessage(
                filteredPodcasts.size()));
        JsonNode jsonNode = objectMapper.valueToTree(filteredPodcastsNames);
        objectNode.set(CheckerConstants.RESULTS_FIELD, jsonNode);
        setLastSearchResult(filteredPodcastsNames);
        return objectNode;
    }

    /***
     * lastSearchResult getter
     * @return the list of names of songs from the last search result used on podcasts
     */
    @Override
    public ArrayList<String> getLastSearchResult() {
        return lastSearchResult;
    }

    /***
     * lastSearchResult setter
     * @param result sets the names of songs for the last search result
     */
    @Override
    public void setLastSearchResult(final ArrayList<String> result) {
        this.lastSearchResult = result;
    }
}
