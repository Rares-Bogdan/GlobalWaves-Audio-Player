package messages;

import checker.CheckerConstants;

public abstract class SkipMessage {
    /***
     *
     * @return a message that shows the loaded source is not a podcast
     */
    public static String loadedSourceNotPodcastMessage() {
        return CheckerConstants.LOADED_SOURCE_NOT_PODCAST_MESSAGE;
    }
}
