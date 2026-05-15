package searchbar.selectresult;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.SelectMessage;

import java.util.ArrayList;

public class SelectResult extends SelectMessage {
    private int itemNumber;
    private boolean hasBeenSelectedSuccessfully;

    /***
     * constructor for SelectResult class
     * @param itemNumber index of source that is selected the object is initialized with
     */
    public SelectResult(final int itemNumber) {
        this.itemNumber = itemNumber;
    }

    /***
     * itemNumber getter
     * @return index of source that is selected
     */
    public int getItemNumber() {
        return itemNumber;
    }

    /***
     * hasBeenSelectedSuccessfully getter
     * @return true if the source has been selected successfully or false otherwise
     */
    public boolean hasBeenSelectedSuccessfully() {
        return hasBeenSelectedSuccessfully;
    }

    /***
     * hasBeenSelectedSuccessfully setter
     * @param hasBeenSelectedSuccessfully sets the value for hasBeenSelectedSuccessfully
     */
    public void setHasBeenSelectedSuccessfully(final boolean hasBeenSelectedSuccessfully) {
        this.hasBeenSelectedSuccessfully = hasBeenSelectedSuccessfully;
    }

    /***
     * method that helps print the output for select command
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @param lastSearchResult list of strings obtained after the last search
     * @param cannotSelectYet checks if the source can be selected or not
     * @return an object node that stores the result message for the select command
     */
    public ObjectNode getObjectNodeForSelectResult(final ObjectMapper objectMapper,
                                                   final Command currentCommand,
                                                   final ArrayList<String> lastSearchResult,
                                                   final boolean cannotSelectYet) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        if (getItemNumber() > lastSearchResult.size()) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, tooHighIdMessage());
            setHasBeenSelectedSuccessfully(false);
        } else if (lastSearchResult.isEmpty() && !cannotSelectYet) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, noSearchExecutedMessage());
            setHasBeenSelectedSuccessfully(false);
        } else if (!lastSearchResult.isEmpty() && cannotSelectYet) {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, noSearchExecutedMessage());
            setHasBeenSelectedSuccessfully(false);
        } else {
            objectNode.put(CheckerConstants.MESSAGE_FIELD, successMessage(lastSearchResult.get(
                    getItemNumber() - 1)));
            setHasBeenSelectedSuccessfully(true);
        }
        return objectNode;
    }
}
