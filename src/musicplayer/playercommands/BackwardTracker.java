package musicplayer.playercommands;

public class BackwardTracker {
    private boolean hasBeenBackwarded;

    public BackwardTracker(boolean hasBeenBackwarded) {
        this.hasBeenBackwarded = hasBeenBackwarded;
    }

    public boolean isHasBeenBackwarded() {
        return hasBeenBackwarded;
    }

    public void setHasBeenBackwarded(boolean hasBeenBackwarded) {
        this.hasBeenBackwarded = hasBeenBackwarded;
    }
}
