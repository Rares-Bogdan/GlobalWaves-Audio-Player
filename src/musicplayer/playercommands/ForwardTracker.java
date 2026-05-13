package musicplayer.playercommands;

public class ForwardTracker {
    private boolean hasBeenForwarded;

    /***
     *
     * @param hasBeenForwarded forward tracker state
     *                         constructor will be initialized with false
     */
    public ForwardTracker(final boolean hasBeenForwarded) {
        this.hasBeenForwarded = hasBeenForwarded;
    }

    /***
     *
     * @return rue if a forward happened to the podcast and false otherwise
     */
    public boolean isHasBeenForwarded() {
        return hasBeenForwarded;
    }

    /***
     *
     * @param hasBeenForwarded sets hasBeenForwarded for a backward tracker
     *                         is set on true if the rewind occurred or false if other
     *                         operation happened
     */
    public void setHasBeenForwarded(final boolean hasBeenForwarded) {
        this.hasBeenForwarded = hasBeenForwarded;
    }
}
