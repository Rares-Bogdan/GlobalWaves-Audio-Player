package musicplayer;

import java.util.concurrent.atomic.AtomicBoolean;

public class LastLoadedSource {
    private String name;
    private int duration;
    private AtomicBoolean hasRepeatOnceState;

    public LastLoadedSource(String name, int duration, AtomicBoolean hasRepeatOnceState) {
        this.name = name;
        this.duration = duration;
        this.hasRepeatOnceState = hasRepeatOnceState;
    }

    public LastLoadedSource() {
        this.name = "";
        this.duration = 0;
        this.hasRepeatOnceState = new AtomicBoolean(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public AtomicBoolean getHasRepeatOnceState() {
        return hasRepeatOnceState;
    }

    public void setHasRepeatOnceState(AtomicBoolean hasRepeatOnceState) {
        this.hasRepeatOnceState = hasRepeatOnceState;
    }
}
