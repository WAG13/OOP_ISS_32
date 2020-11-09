package model.track_types;

import model.Style;
import model.Track;
import model.exceptions.NegativeDurationException;

public class PunkTrack extends Track {
    public PunkTrack(String artist, String title, float duration) throws NegativeDurationException {
        super(artist, title, duration, Style.PUNK);
    }
}

