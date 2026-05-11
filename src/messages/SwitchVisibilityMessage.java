package messages;

import checker.CheckerConstants;

public class SwitchVisibilityMessage {
    /***
     *
     * @param visibilityStatus the state of visibility the user switches the source to
     * @return a message that shows the visibility status was updated successfully
     */
    public static String visibilityStatusUpdateMessage(final boolean visibilityStatus) {
        if (visibilityStatus) {
            return CheckerConstants.VISIBILITY_STATUS_UPDATE_MESSAGE + CheckerConstants.PUBLIC
                    + ".";
        } else {
            return CheckerConstants.VISIBILITY_STATUS_UPDATE_MESSAGE + CheckerConstants.PRIVATE
                    + ".";
        }
    }

    /***
     *
     * @return a message that shows the id of the playlist the user attempts to select is too high
     */
    public static String tooHighIdPlaylistMessage() {
        return CheckerConstants.TOO_HIGH_ID_PLAYLIST_MESSAGE;
    }
}
