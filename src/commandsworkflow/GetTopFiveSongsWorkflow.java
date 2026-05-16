package commandsworkflow;

import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.Command;
import fileio.input.LibraryInput;

import java.util.ArrayList;

public class GetTopFiveSongsWorkflow extends CommandWorkflow {
    private LibraryInput library;
    private ArrayList<Integer> likesForEachSongInLibrary;

    /***
     * constructor for CommandWorkflow abstract class
     * @param objectMapper object used to print the output in JSON format
     * @param currentCommand current command used
     */
    public GetTopFiveSongsWorkflow(final ObjectMapper objectMapper, final Command currentCommand,
                                   final LibraryInput library,
                                   final ArrayList<Integer> likesForEachSongInLibrary) {
        super(objectMapper, currentCommand);
        this.library = library;
        this.likesForEachSongInLibrary = likesForEachSongInLibrary;
    }

    /***
     * library getter
     * @return library
     */
    public LibraryInput getLibrary() {
        return library;
    }

    /***
     * likesForEachSongInLibrary getter
     * @return a list containing the number of likes for each song in the library
     */
    public ArrayList<Integer> getLikesForEachSongInLibrary() {
        return likesForEachSongInLibrary;
    }

    /***
     * method that handles the workflow for getTop5Songs command
     * @return a list of the 5 most liked songs in the library
     */
    public ArrayList<String> getTopFiveSongsCommandWorkflow() {
        ArrayList<Integer> likesForEachSongInLibraryBackup = new ArrayList<>(
                getLikesForEachSongInLibrary());
        ArrayList<Integer> likesForEachSongInLibraryBackupIndexes = new ArrayList<>(
                getLikesForEachSongInLibrary().size());
        for (int currentLikesIndex = 0; currentLikesIndex < getLikesForEachSongInLibrary().size();
             currentLikesIndex++) {
            likesForEachSongInLibraryBackupIndexes.add(currentLikesIndex, currentLikesIndex);
        }
        for (int currentLikesIndex = 0; currentLikesIndex < getLikesForEachSongInLibrary().size()
                - 1; currentLikesIndex++) {
            for (int currentLikesIndexInsider = currentLikesIndex + 1;
                 currentLikesIndexInsider < getLikesForEachSongInLibrary().size();
                 currentLikesIndexInsider++) {
                if (likesForEachSongInLibraryBackup.get(currentLikesIndex)
                        < likesForEachSongInLibraryBackup.get(currentLikesIndexInsider)) {
                    int temporary = likesForEachSongInLibraryBackupIndexes.get(currentLikesIndex);
                    likesForEachSongInLibraryBackupIndexes.set(currentLikesIndex,
                            likesForEachSongInLibraryBackupIndexes.get(currentLikesIndexInsider));
                    likesForEachSongInLibraryBackupIndexes.set(currentLikesIndexInsider,
                            temporary);
                }
            }
        }

        ArrayList<String> top5Songs = new ArrayList<>();
        for (int currentSong = 0; currentSong < CheckerConstants.MAXIMUM_SEARCH_RESULT;
             currentSong++) {
            top5Songs.add(getLibrary().getSongs().get(likesForEachSongInLibraryBackupIndexes.get(
                    currentSong)).getName());
        }

        return top5Songs;
    }
}
