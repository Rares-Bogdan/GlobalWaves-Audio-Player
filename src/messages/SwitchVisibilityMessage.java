package messages;

import checker.CheckerConstants;

public class SwitchVisibilityMessage {
    public static String visibilityStatusUpdateMessage(boolean visibilityStatus) {
        if (visibilityStatus) {
            return CheckerConstants.VISIBILITY_STATUS_UPDATE_MESSAGE + CheckerConstants.PUBLIC
                    + ".";
        } else {
            return CheckerConstants.VISIBILITY_STATUS_UPDATE_MESSAGE + CheckerConstants.PRIVATE
                    + ".";
        }
    }

    public static String tooHighIdPlaylistMessage() {
        return CheckerConstants.TOO_HIGH_ID_PLAYLIST_MESSAGE;
    }
}
