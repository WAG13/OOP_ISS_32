package collection;

import model.Track;

import java.util.*;

public class Disk implements MusicCollection {
    private String name = "";
    private List<Track> tracks;

    public Disk() {
        this.tracks = new ArrayList<>();
    }

    public Disk(String name) {
        this.name = name;
        this.tracks = new ArrayList<>();
    }

    public Disk(List<Track> tracks) {
        this.tracks = new ArrayList<>(tracks.size());
        this.tracks.addAll(tracks);
    }

    public Disk(String name, List<Track> tracks) {
        this.name = name;
        this.tracks = new ArrayList<>(tracks.size());
        this.tracks.addAll(tracks);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public int removeTracks(String trackAuthor, String trackTitle, int amountToDelete) {
        Iterator<Track> iterator = tracks.iterator();
        int removedAmount = 0;
        while (iterator.hasNext()) {
            Track track = iterator.next();
            if (track.getAuthor().equals(trackAuthor) && track.getTitle().equals(trackTitle)) {
                if (removedAmount < amountToDelete) {
                    ++removedAmount;
                    iterator.remove();
                }
            }
        }
        return removedAmount;
    }

    public int removeTracks(String trackAuthor, String trackTitle) {
        return removeTracks(trackAuthor, trackTitle, tracks.size());
    }

    public int removeTracksByTitle(String trackTitle, int amountToDelete) {
        Iterator<Track> iterator = tracks.iterator();
        int removedAmount = 0;
        while (iterator.hasNext()) {
            Track track = iterator.next();
            if (track.getTitle().equals(trackTitle)) {
                if (removedAmount < amountToDelete) {
                    ++removedAmount;
                    iterator.remove();
                }
            }
        }
        return removedAmount;
    }

    public int removeTracksByTitle(String trackTitle) {
        return removeTracksByTitle(trackTitle, tracks.size());
    }

    public int removeTracksByAuthor(String trackAuthor, int amountToDelete) {
        Iterator<Track> iterator = tracks.iterator();
        int removedAmount = 0;
        while (iterator.hasNext()) {
            Track track = iterator.next();
            if (track.getAuthor().equals(trackAuthor)) {
                if (removedAmount < amountToDelete) {
                    ++removedAmount;
                    iterator.remove();
                }
            }
        }
        return removedAmount;
    }

    public int removeTracksByAuthor(String trackAuthor) {
        return removeTracksByAuthor(trackAuthor, tracks.size());
    }

    public float calculateDuration() {
        float totalDuration = 0;
        for (Track track : tracks) {
            totalDuration += track.getDuration();
        }
        return totalDuration;
    }

    public void sortTracks(Comparator<Track> comparator) {
        tracks.sort(comparator);
    }

    public List<Track> findTracksInDurationInterval(float low, float high) {
        List<Track> result = new ArrayList<>();
        for (Track track : tracks) {
            if (track.getDuration() >= low && track.getDuration() <= high) {
                result.add(track);
            }
        }
        return result;
    }

    @Override
    public void deleteAll() {
        tracks.clear();
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Disk: " )
                .append(this.name)
                .append("\nTracks amount: ")
                .append(this.tracks.size())
                .append("\nTracklist:");
        for (Track track : tracks) {
            sb.append("\n"+track.toString());
        }
        return sb.toString();
    }
}
