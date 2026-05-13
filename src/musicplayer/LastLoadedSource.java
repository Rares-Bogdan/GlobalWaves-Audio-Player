package musicplayer;

import java.util.concurrent.atomic.AtomicBoolean;

public class LastLoadedSource {
    private String name;
    private int duration;
    private AtomicBoolean hasRepeatOnceState;

    /***
     *
     * @param name the name of the source
     * @param duration current duration of the source
     * @param hasRepeatOnceState checks if the source is set to be repeated once
     */
    public LastLoadedSource(final String name, final int duration,
                            final AtomicBoolean hasRepeatOnceState) {
        this.name = name;
        this.duration = duration;
        this.hasRepeatOnceState = hasRepeatOnceState;
    }

    /***
     * default constructor for last loaded source
     */
    public LastLoadedSource() {
        this.name = "";
        this.duration = 0;
        this.hasRepeatOnceState = new AtomicBoolean(false);
    }

    /***
     *
     * @return source name
     */
    public String getName() {
        return name;
    }

    /***
     *
     * @param name sets the source name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /***
     *
     * @return source current duration
     */
    public int getDuration() {
        return duration;
    }

    /***
     *
     * @param duration sets the source current duration
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /***
     *
     * @return an atomic boolean that checks if the source is set to be repeated once
     */
    public AtomicBoolean getHasRepeatOnceState() {
        return hasRepeatOnceState;
    }

    /***
     *
     * @param hasRepeatOnceState sets hasRepeatOnceState for the source
     */
    public void setHasRepeatOnceState(final AtomicBoolean hasRepeatOnceState) {
        this.hasRepeatOnceState = hasRepeatOnceState;
    }
}
