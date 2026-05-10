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

    public SearchBarPodcastResults(ArrayList<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    public ArrayList<PodcastInput> getPodcasts() {
        return podcasts;
    }

    public ArrayList<PodcastInput> filterPodcastsByName(String name) {
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

    public ArrayList<PodcastInput> filterPodcastsByOwner(String owner) {
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

    @Override
    public ObjectNode searchResult(ObjectMapper objectMapper, Command currentCommand) {
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

    @Override
    public ArrayList<String> getLastSearchResult() {
        return lastSearchResult;
    }

    @Override
    public void setLastSearchResult(ArrayList<String> result) {
        this.lastSearchResult = result;
    }
}
