package musicplayer.playercommands;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;

public class Status {
    private String name;
    private int remainedTime;
    private String repeat;
    private boolean shuffle;
    private boolean paused;

    /***
     *
     * @param name current played / paused source name
     * @param remainedTime current played / paused source remained time until the end
     * @param repeat repeat state for the current played / paused source
     * @param shuffle shuffle state for the current played / paused source
     * @param paused play / pause state for the current source
     */
    public Status(final String name, final int remainedTime, final String repeat,
                  final boolean shuffle, final boolean paused) {
        this.name = name;
        this.remainedTime = remainedTime;
        this.repeat = repeat;
        this.shuffle = shuffle;
        this.paused = paused;
    }

    /***
     * default constructor for the user status
     */
    public Status() {
        this.name = "";
        this.remainedTime = 0;
        this.repeat = CheckerConstants.NO_REPEAT;
        this.shuffle = false;
        this.paused = true;
    }

    /***
     *
     * @return current played / paused source name
     */
    public String getName() {
        return name;
    }

    /***
     *
     * @param name sets the current played / paused source name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /***
     *
     * @return current played / paused source remained time until the end
     */
    public int getRemainedTime() {
        return remainedTime;
    }

    /***
     *
     * @param remainedTime sets the current played / paused source remained time until the end
     */
    public void setRemainedTime(final int remainedTime) {
        this.remainedTime = remainedTime;
    }

    /***
     *
     * @return repeat state for the current played / paused source
     */
    public String getRepeat() {
        return repeat;
    }

    /***
     *
     * @param repeat sets the repeat state for the current played / paused source
     */
    public void setRepeat(final String repeat) {
        this.repeat = repeat;
    }

    /***
     *
     * @return true if the current source is shuffled or false otherwise
     */
    public boolean isShuffle() {
        return shuffle;
    }

    /***
     *
     * @param shuffle sets the shuffle state for the current played / paused source
     */
    public void setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
    }

    /***
     *
     * @return true if the current source is paused or false otherwise
     */
    public boolean isPaused() {
        return paused;
    }

    /***
     *
     * @param paused sets the play / pause state for the current source
     */
    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    /***
     *
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @return an object node that stores the result message for status command
     */
    public ObjectNode statusResult(final ObjectMapper objectMapper, final Command currentCommand) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(CheckerConstants.COMMAND_FIELD, currentCommand.getCommand());
        objectNode.put(CheckerConstants.USER_FIELD, currentCommand.getUsername());
        objectNode.put(CheckerConstants.TIMESTAMP_FIELD, currentCommand.getTimestamp());
        Status currentStatus = new Status(getName(), getRemainedTime(), getRepeat(), isShuffle(),
                isPaused());
        JsonNode jsonNode = objectMapper.valueToTree(currentStatus);
        objectNode.set(CheckerConstants.STATS_FIELD, jsonNode);
        return objectNode;
    }
}
