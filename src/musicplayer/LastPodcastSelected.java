package musicplayer;

import fileio.input.PodcastInput;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class LastPodcastSelected {
    private AtomicInteger episodeIndex;
    private AtomicInteger currentPositionOfPodcast;
    private int totalDurationOfPodcast;
    private int totalNumberOfEpisodes;
    private ArrayList<Integer> episodesStartsPositions;
    private String currentEpisodeName;
    private String podcastName;
    private AtomicBoolean hasRepeatOnceState;

    public LastPodcastSelected() {
        this.episodeIndex = new AtomicInteger(-1);
        this.currentPositionOfPodcast = new AtomicInteger(-1);
        this.totalDurationOfPodcast = 0;
        this.totalNumberOfEpisodes = 0;
        this.episodesStartsPositions = new ArrayList<Integer>();
        this.currentEpisodeName = "";
        this.podcastName = "";
        this.hasRepeatOnceState = new AtomicBoolean(false);
    }

    public AtomicInteger getEpisodeIndex() {
        return episodeIndex;
    }

    public void setEpisodeIndex(AtomicInteger episodeIndex) {
        this.episodeIndex = episodeIndex;
    }

    public AtomicInteger getCurrentPositionOfPodcast() {
        return currentPositionOfPodcast;
    }

    public void setCurrentPositionOfPodcast(AtomicInteger currentPositionOfPodcast) {
        this.currentPositionOfPodcast = currentPositionOfPodcast;
    }

    public int getTotalDurationOfPodcast() {
        return totalDurationOfPodcast;
    }

    public void setTotalDurationOfPodcast(int totalDurationOfPodcast) {
        this.totalDurationOfPodcast = totalDurationOfPodcast;
    }

    public int getTotalNumberOfEpisodes() {
        return totalNumberOfEpisodes;
    }

    public void setTotalNumberOfEpisodes(int totalNumberOfEpisodes) {
        this.totalNumberOfEpisodes = totalNumberOfEpisodes;
    }

    public ArrayList<Integer> getEpisodesStartsPositions() {
        return episodesStartsPositions;
    }

    public void setEpisodesStartsPositions(ArrayList<Integer> episodesStartsPositions) {
        this.episodesStartsPositions = episodesStartsPositions;
    }

    public String getCurrentEpisodeName() {
        return currentEpisodeName;
    }

    public void setCurrentEpisodeName(String currentEpisodeName) {
        this.currentEpisodeName = currentEpisodeName;
    }

    public String getPodcastName() {
        return podcastName;
    }

    public void setPodcastName(String podcastName) {
        this.podcastName = podcastName;
    }

    public AtomicBoolean getHasRepeatOnceState() {
        return hasRepeatOnceState;
    }

    public void setHasRepeatOnceState(AtomicBoolean hasRepeatOnceState) {
        this.hasRepeatOnceState = hasRepeatOnceState;
    }
}
