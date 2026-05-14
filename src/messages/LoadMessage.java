package messages;

import checker.CheckerConstants;

public final class LoadMessage {

    /***
     * private constructor for LoadMessage class
     */
    private LoadMessage() { }

    /***
     * method that prints the successful load of a source message
     * @return a message that shows a source has been loaded successfully
     */
    public static String successMessage() {
        return CheckerConstants.SUCCESS_LOAD_MESSAGE;
    }

    /***
     * method that prints the requirement to select a source in order to load message
     * @return a message that asks the user to select a source before attempting to load
     */
    public static String selectSourceMessage() {
        return CheckerConstants.SELECT_SOURCE_MESSAGE;
    }

    /***
     * method that prints the impossibility to load an empty audio collection message
     * @return a message that shows an empty audio collection cannot be loaded
     */
    public static String emptyAudioCollectionMessage() {
        return CheckerConstants.EMPTY_AUDIO_COLLECTION_MESSAGE;
    }
}
