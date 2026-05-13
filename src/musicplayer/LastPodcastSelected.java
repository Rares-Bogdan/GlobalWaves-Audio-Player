package musicplayer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LastPodcastSelected {
    private AtomicInteger episodeIndex;
    private AtomicInteger currentPositionOfPodcast;
    private int totalDurationOfPodcast;
    private int totalNumberOfEpisodes;
    private ArrayList<Integer> episodesStartsPositions;
    private String currentEpisodeName;
    private String podcastName;

    /***
     * default constructor for last podcast selected by a user
     */
    public LastPodcastSelected() {
        this.episodeIndex = new AtomicInteger(-1);
        this.currentPositionOfPodcast = new AtomicInteger(-1);
        this.totalDurationOfPodcast = 0;
        this.totalNumberOfEpisodes = 0;
        this.episodesStartsPositions = new ArrayList<Integer>();
        this.currentEpisodeName = "";
        this.podcastName = "";
    }

    /***
     *
     * @return the current episode the podcast is positioned to
     */
    public AtomicInteger getEpisodeIndex() {
        return episodeIndex;
    }

    /***
     *
     * @param episodeIndex sets the episodeIndex for the last podcast selected by a user
     */
    public void setEpisodeIndex(final AtomicInteger episodeIndex) {
        this.episodeIndex = episodeIndex;
    }

    /***
     *
     * @return the current position in the podcast, relative to the total time
     */
    public AtomicInteger getCurrentPositionOfPodcast() {
        return currentPositionOfPodcast;
    }

    /***
     *
     * @param currentPositionOfPodcast sets the current position in the podcast, relative to the
     *                                 total time
     */
    public void setCurrentPositionOfPodcast(final AtomicInteger currentPositionOfPodcast) {
        this.currentPositionOfPodcast = currentPositionOfPodcast;
    }

    /***
     *
     * @return total time of the last podcast selected by a user
     */
    public int getTotalDurationOfPodcast() {
        return totalDurationOfPodcast;
    }

    /***
     *
     * @param totalDurationOfPodcast sets the total time of the last podcast selected by a user
     */
    public void setTotalDurationOfPodcast(final int totalDurationOfPodcast) {
        this.totalDurationOfPodcast = totalDurationOfPodcast;
    }

    /***
     *
     * @return total number of episodes from the last podcast selected by a user
     */
    public int getTotalNumberOfEpisodes() {
        return totalNumberOfEpisodes;
    }

    /***
     *
     * @param totalNumberOfEpisodes sets the total number of episodes from the last podcast
     *                              selected by a user
     */
    public void setTotalNumberOfEpisodes(final int totalNumberOfEpisodes) {
        this.totalNumberOfEpisodes = totalNumberOfEpisodes;
    }

    /***
     *
     * @return the start position for each episode in the last podcast selected by a user
     */
    public ArrayList<Integer> getEpisodesStartsPositions() {
        return episodesStartsPositions;
    }

    /***
     *
     * @param episodesStartsPositions sets the episodes start positions for the last podcast
     *                                selected by a user
     */
    public void setEpisodesStartsPositions(final ArrayList<Integer> episodesStartsPositions) {
        this.episodesStartsPositions = episodesStartsPositions;
    }

    /***
     *
     * @return the name for the current episode the podcast is positioned at
     */
    public String getCurrentEpisodeName() {
        return currentEpisodeName;
    }

    /***
     *
     * @param currentEpisodeName sets the name of the current episode the podcast is positioned at
     */
    public void setCurrentEpisodeName(final String currentEpisodeName) {
        this.currentEpisodeName = currentEpisodeName;
    }

    /***
     *
     * @return the name for the last podcast selected by a user
     */
    public String getPodcastName() {
        return podcastName;
    }

    /***
     *
     * @param podcastName sets the name for the last podcast selected by a user
     */
    public void setPodcastName(final String podcastName) {
        this.podcastName = podcastName;
    }
}
