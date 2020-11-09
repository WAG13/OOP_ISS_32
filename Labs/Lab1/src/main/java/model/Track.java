package model;

import model.exceptions.NegativeDurationException;

import java.util.Objects;

public abstract class Track {
    private float duration;
    private String title;
    private String author;
    private Style style;

    public Track(String author, String title, float duration, Style style) throws NegativeDurationException {
        this.author = author;
        this.title = title;
        this.style = style;
        setDuration(duration);
    }

    public void setDuration(float duration) throws NegativeDurationException {
        if (duration <= 0) {
            throw new NegativeDurationException("Attempt to change duration to negative number.");
        }
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }

    public Style getStyle() {
        return style;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() { return author; }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Track: " )
                .append(this.author)
                .append(" - ")
                .append(this.title)
                .append("\nDuration: ")
                .append(this.duration)
                .append("\nStyle: ")
                .append(this.style);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Float.compare(track.duration, duration) == 0 &&
                title.equals(track.title) &&
                author.equals(track.author) &&
                style == track.style;
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, duration);
    }
}
