package com.oop.task1.track;

import java.io.Serializable;
import java.util.Objects;

public class Track implements Serializable {
    private String author;
    private String title;
    private int duration;

    public Track(String author, String title, int duration) {
        this.author = author;
        this.title = title;
        this.duration = duration;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Track: " )
                .append(this.author)
                .append(" - ")
                .append(this.title)
                .append("\t\tDuration: ")
                .append(this.duration);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Float.compare(track.duration, duration) == 0 &&
                title.equals(track.title) &&
                author.equals(track.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, duration);
    }
}

