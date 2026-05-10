package messages;

import checker.CheckerConstants;

public class SelectMessage {
    public static String successMessage(String name) {
        return "Successfully selected " + name + ".";
    }

    public static String noSearchExecutedMessage() {
        return CheckerConstants.NO_SEARCH_EXECUTED_MESSAGE;
    }

    public static String tooHighIdMessage() {
        return CheckerConstants.TOO_HIGH_ID_MESSAGE;
    }
}
