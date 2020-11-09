package model.track_types;

import model.Style;
import model.Track;
import model.exceptions.NegativeDurationException;

public class PopTrack extends Track {
    public PopTrack(String artist, String title, float duration) throws NegativeDurationException {
        super(artist, title, duration, Style.POP);
    }
}

