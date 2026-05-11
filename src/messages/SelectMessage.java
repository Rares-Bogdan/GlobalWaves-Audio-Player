package messages;

import checker.CheckerConstants;

public class SelectMessage {
    /***
     *
     * @param name the name of the selected source
     * @return a message that shows a source has been successfully selected
     */
    public static String successMessage(final String name) {
        return "Successfully selected " + name + ".";
    }

    /***
     *
     * @return a message that asks the user to make a search before attempting to select a source
     */
    public static String noSearchExecutedMessage() {
        return CheckerConstants.NO_SEARCH_EXECUTED_MESSAGE;
    }

    /***
     *
     * @return a message that shows the position of the source the user attempted to select is too
     * high
     */
    public static String tooHighIdMessage() {
        return CheckerConstants.TOO_HIGH_ID_MESSAGE;
    }
}
