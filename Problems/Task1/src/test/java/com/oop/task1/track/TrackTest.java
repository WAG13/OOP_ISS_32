package com.oop.task1.track;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrackTest {
    @Test
    void shouldCreateTrack() {
        //Given
        //When
        Track track = new Track("Radiohead","Creep", 180);
        //Then
        assertEquals("Radiohead", track.getAuthor());
        assertEquals("Creep", track.getTitle());
        assertEquals(180, track.getDuration());
    }

    @Test
    void checksToStringTrack() {
        //Given
        Track track = new Track("Queen","Bohemian Rhapsody", 555);
        //When
        StringBuilder sb = new StringBuilder();
        sb.append("Track: " )
                .append(track.getAuthor())
                .append(" - ")
                .append(track.getTitle())
                .append("\t\tDuration: ")
                .append(track.getDuration());
        //Then
        assertEquals(sb.toString(), track.toString());
    }

    @Test
    void checksEqualTracks() {
        //Given
        //When
        Track track1 = new Track("Queen","Bohemian Rhapsody", 555);
        Track track2 = new Track("Queen","Bohemian Rhapsody", 555);
        //Then
        assertTrue(track1.equals(track2));
        assertTrue(track2.equals(track1));
        assertEquals(track1.hashCode(), track2.hashCode());
        assertEquals(track1, track2);
    }

    @Test
    void checksNotEqualTitlesTracks() {
        //Given
        //When
        Track track1 = new Track("Queen","Bohemian Rhapsody", 555);
        Track track2 = new Track("Queen","Mohemian Rhapsody", 555);
        //Then
        assertNotEquals(track1.getTitle(), track2.getTitle());
        assertNotEquals(track1, track2);
    }

    @Test
    void checksNotEqualAuthorsTracks() {
        //Given
        //When
        Track track1 = new Track("Queen","Bohemian Rhapsody", 555);
        Track track2 = new Track("Harry","Bohemian Rhapsody", 555);
        //Then
        assertNotEquals(track1.getAuthor(), track2.getAuthor());
        assertNotEquals(track1, track2);
    }

    @Test
    void checksNotEqualDurationTracks() {
        //Given
        //When
        Track track1 = new Track("Queen","Bohemian Rhapsody", 555);
        Track track2 = new Track("Queen","Bohemian Rhapsody", 666);
        //Then
        assertNotEquals(track1.getDuration(), track2.getDuration());
        assertNotEquals(track1, track2);
    }

}
