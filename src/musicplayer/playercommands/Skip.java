package musicplayer.playercommands;

import messages.SkipMessage;

public abstract class Skip extends SkipMessage {
    private boolean isLoadedSourcePodcast;
    private boolean hasLoadedSource;

    /***
     *
     * @param isLoadedSourcePodcast checks if the loaded source is a podcast
     * @param hasLoadedSource checks if the user has a loaded source
     */
    public Skip(final boolean isLoadedSourcePodcast, final boolean hasLoadedSource) {
        this.isLoadedSourcePodcast = isLoadedSourcePodcast;
        this.hasLoadedSource = hasLoadedSource;
    }

    /***
     *
     * @return true if the loaded source is a podcast or false otherwise
     */
    public boolean isLoadedSourcePodcast() {
        return isLoadedSourcePodcast;
    }

    /***
     *
     * @return true if the user has a loaded source or false otherwise
     */
    public boolean isHasLoadedSource() {
        return hasLoadedSource;
    }
}
