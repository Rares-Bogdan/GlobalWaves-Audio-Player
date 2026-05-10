package musicplayer.playercommands;

import messages.SkipMessage;

public abstract class Skip extends SkipMessage {
    private boolean isLoadedSourcePodcast;
    private boolean hasLoadedSource;

    public Skip(boolean isLoadedSourcePodcast, boolean hasLoadedSource) {
        this.isLoadedSourcePodcast = isLoadedSourcePodcast;
        this.hasLoadedSource = hasLoadedSource;
    }

    public boolean isLoadedSourcePodcast() {
        return isLoadedSourcePodcast;
    }

    public boolean isHasLoadedSource() {
        return hasLoadedSource;
    }
}
