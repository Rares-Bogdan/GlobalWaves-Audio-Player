package messages;

import checker.CheckerConstants;

public final class LoadMessage {

    private LoadMessage() { }

    /***
     *
     * @return a message that shows a source has been loaded successfully
     */
    public static String successMessage() {
        return CheckerConstants.SUCCESS_LOAD_MESSAGE;
    }

    /***
     *
     * @return a message that asks the user to select a source before attempting to load
     */
    public static String selectSourceMessage() {
        return CheckerConstants.SELECT_SOURCE_MESSAGE;
    }

    /***
     *
     * @return a message that shows an empty audio collection cannot be loaded
     */
    public static String emptyAudioCollectionMessage() {
        return CheckerConstants.EMPTY_AUDIO_COLLECTION_MESSAGE;
    }
}
