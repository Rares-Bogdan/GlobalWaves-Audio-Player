package messages;

public final class SearchMessage {

    /***
     * private constructor for SearchMessage class
     */
    private SearchMessage() { }

    /***
     * method that prints the number of results the search returned
     * @param numberOfFoundResults how many sources have been found after a search
     * @return a message that shows how many results the search returned
     */
    public static String successSearchMessage(final int numberOfFoundResults) {
        return "Search returned " + numberOfFoundResults + " results";
    }
}
