package searchbar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

import java.util.ArrayList;

public interface SearchBar {
    ObjectNode searchResult(ObjectMapper objectMapper, Command currentCommand);
    ArrayList<String> getLastSearchResult();
    void setLastSearchResult(ArrayList<String> result);
}
