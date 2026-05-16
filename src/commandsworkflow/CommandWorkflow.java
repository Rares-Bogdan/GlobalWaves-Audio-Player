package commandsworkflow;

import UserApp.UserApp;
import command.Command;

public abstract class CommandWorkflow {

    private Command currentCommand;
    private UserApp userApp;

    /***
     * constructor for CommandWorkflow abstract class
     * @param currentCommand current command used
     * @param userApp object used to store all the data concerning a user
     */
    public CommandWorkflow(final Command currentCommand, final UserApp userApp) {
        this.currentCommand = currentCommand;
        this.userApp = userApp;
    }

    /***
     * currentCommand getter
     * @return current command used
     */
    public Command getCurrentCommand() {
        return currentCommand;
    }

    /***
     * userApp getter
     * @return userApp
     */
    public UserApp getUserApp() {
        return userApp;
    }
}
