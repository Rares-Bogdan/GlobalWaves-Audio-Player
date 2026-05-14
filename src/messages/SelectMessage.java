package messages;

import checker.CheckerConstants;

public class SelectMessage {
    /***
     * method that prints the successful selection of a source
     * @param name the name of the selected source
     * @return a message that shows a source has been successfully selected
     */
    public static String successMessage(final String name) {
        return "Successfully selected " + name + ".";
    }

    /***
     * method that prints the requirement to make a search in order to select a source
     * @return a message that asks the user to make a search before attempting to select a source
     */
    public static String noSearchExecutedMessage() {
        return CheckerConstants.NO_SEARCH_EXECUTED_MESSAGE;
    }

    /***
     * method that prints the unsuccessful selection of a source, because of a too high id
     * @return a message that shows the position of the source the user attempted to select is too
     * high
     */
    public static String tooHighIdMessage() {
        return CheckerConstants.TOO_HIGH_ID_MESSAGE;
    }
}
