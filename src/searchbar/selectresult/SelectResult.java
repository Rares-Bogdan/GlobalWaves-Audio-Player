package searchbar.selectresult;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import messages.SelectMessage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class SelectResult extends SelectMessage {
    private int itemNumber;
    private boolean hasBeenSelectedSuccessfully;

    public SelectResult(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public boolean hasBeenSelectedSuccessfully() {
        return hasBeenSelectedSuccessfully;
    }

    public void setHasBeenSelectedSuccessfully(boolean hasBeenSelectedSuccessfully) {
        this.hasBeenSelectedSuccessfully = hasBeenSelectedSuccessfully;
    }

    public ObjectNode getObjectNodeForSelectResult(ObjectMapper objectMapper,
                                                   Command currentCommand,
                                                   ArrayList<String> lastSearchResult,
                                                   boolean cannotSelectYet) {
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
