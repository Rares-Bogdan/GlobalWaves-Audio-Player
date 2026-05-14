package musicplayer.playercommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import musicplayer.MusicPlayer;

public interface Load {
    /***
     * interface method that helps print the output for load command that will be updated in
     * classes that implement Load
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     * @param musicPlayer play / pause state of the music player
     * @return an object node that stores the result message for the load command
     */
    ObjectNode loadResult(ObjectMapper objectMapper, Command currentCommand,
                          MusicPlayer musicPlayer);
}
