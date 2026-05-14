package musicplayer.playercommands;

public class BackwardTracker {
    private boolean hasBeenBackwarded;

    /***
     * constructor for BackwardTracker class
     * @param hasBeenBackwarded backward tracker state
     *                          constructor will be initialized with false
     */
    public BackwardTracker(final boolean hasBeenBackwarded) {
        this.hasBeenBackwarded = hasBeenBackwarded;
    }

    /***
     * hasBeenBackwarded getter
     * @return true if a rewind happened to the podcast and false otherwise
     */
    public boolean isHasBeenBackwarded() {
        return hasBeenBackwarded;
    }

    /***
     * hasBeenBackwarded setter
     * @param hasBeenBackwarded sets hasBeenBackwarded for a backward tracker
     *                          is set on true if the rewind occurred or false if other
     *                          operation happened
     */
    public void setHasBeenBackwarded(final boolean hasBeenBackwarded) {
        this.hasBeenBackwarded = hasBeenBackwarded;
    }
}
