package musicplayer.playercommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import musicplayer.MusicPlayer;

public interface Load {
    public ObjectNode loadResult(ObjectMapper objectMapper, Command currentCommand,
                                 MusicPlayer musicPlayer);
}
