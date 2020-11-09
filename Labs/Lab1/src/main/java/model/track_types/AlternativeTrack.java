package model.track_types;

import model.Style;
import model.Track;
import model.exceptions.NegativeDurationException;

public class AlternativeTrack extends Track {
    public AlternativeTrack(String artist, String title, float duration) throws NegativeDurationException {
        super(artist, title, duration, Style.ALTERNATIVE);
    }
}

