package musicplayer.playercommands;

public class ForwardTracker {
    boolean hasBeenForwarded;

    public ForwardTracker(boolean hasBeenForwarded) {
        this.hasBeenForwarded = hasBeenForwarded;
    }

    public boolean isHasBeenForwarded() {
        return hasBeenForwarded;
    }

    public void setHasBeenForwarded(boolean hasBeenForwarded) {
        this.hasBeenForwarded = hasBeenForwarded;
    }
}
