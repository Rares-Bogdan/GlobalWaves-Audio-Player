package messages;

import checker.CheckerConstants;

public class LoadMessage {
    public static String successMessage() {
        return CheckerConstants.SUCCESS_LOAD_MESSAGE;
    }

    public static String selectSourceMessage() {
        return CheckerConstants.SELECT_SOURCE_MESSAGE;
    }

    public static String emptyAudioCollectionMessage() {
        return CheckerConstants.EMPTY_AUDIO_COLLECTION_MESSAGE;
    }
}
