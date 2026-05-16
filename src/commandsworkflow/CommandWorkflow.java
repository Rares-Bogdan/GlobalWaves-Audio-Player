package commandsworkflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;

public abstract class CommandWorkflow {
    private ObjectMapper objectMapper;
    private Command currentCommand;

    /***
     * constructor for CommandWorkflow abstract class
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     */
    public CommandWorkflow(final ObjectMapper objectMapper, final Command currentCommand) {
        this.objectMapper = objectMapper;
        this.currentCommand = currentCommand;
    }

    /***
     * objectMapper getter
     * @return objectMapper
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /***
     * objectMapper setter
     * @param objectMapper sets the value for objectMapper
     */
    public void setObjectMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /***
     * currentCommand getter
     * @return currentCommand
     */
    public Command getCurrentCommand() {
        return currentCommand;
    }

    /***
     * currentCommand setter
     * @param currentCommand sets the value for currentCommand
     */
    public void setCurrentCommand(final Command currentCommand) {
        this.currentCommand = currentCommand;
    }
}
