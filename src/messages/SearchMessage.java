package messages;

public class SearchMessage {
    public static String successSearchMessage(int numberOfFoundResults) {
        return "Search returned " + numberOfFoundResults + " results";
    }
}
