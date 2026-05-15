package searchbar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

import java.util.ArrayList;

public interface SearchBar {
    /***
     * method that helps print the output for search command
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for the search command
     */
    ObjectNode searchResult(ObjectMapper objectMapper, Command currentCommand);

    /***
     * lastSearchResult getter
     * @return the list of names of songs from the last search result
     */
    ArrayList<String> getLastSearchResult();

    /***
     * lastSearchResult setter
     * @param result sets the names of songs for the last search result
     */
    void setLastSearchResult(ArrayList<String> result);
}
