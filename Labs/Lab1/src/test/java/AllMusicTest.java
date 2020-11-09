import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import model.Style;
import model.Track;
import model.exceptions.NegativeDurationException;
import model.track_types.*;

public class AllMusicTest {
    @Test
    void shouldCreateAlternativeTrack() throws NegativeDurationException {
        //Given
        //When
        Track alternativeTrack = new AlternativeTrack("Radiohead","Creep", 180);
        //Then
        assertEquals("Radiohead", alternativeTrack.getAuthor());
        assertEquals("Creep", alternativeTrack.getTitle());
        assertEquals(180, alternativeTrack.getDuration());
        assertEquals(Style.ALTERNATIVE, alternativeTrack.getStyle());
    }

    @Test
    void shouldCreateClassicTrack() throws NegativeDurationException {
        //Given
        //When
        Track classicTrack = new ClassicTrack("Mozart","Eine kleine Nachtmusik", 7560);
        //Then
        assertEquals("Mozart", classicTrack.getAuthor());
        assertEquals("Eine kleine Nachtmusik", classicTrack.getTitle());
        assertEquals(7560, classicTrack.getDuration());
        assertEquals(Style.CLASSIC, classicTrack.getStyle());
    }

    @Test
    void shouldCreateEDMTrack() throws NegativeDurationException {
        //Given
        //When
        Track edmTrack = new EDMTrack("Avicii","Wake me up", 123);
        //Then
        assertEquals("Avicii", edmTrack.getAuthor());
        assertEquals("Wake me up", edmTrack.getTitle());
        assertEquals(123, edmTrack.getDuration());
        assertEquals(Style.EDM, edmTrack.getStyle());
    }

    @Test
    void shouldCreateJazzTrack() throws NegativeDurationException {
        //Given
        //When
        Track jazzTrack = new JazzTrack("Miles Davis","So What", 245);
        //Then
        assertEquals("Miles Davis", jazzTrack.getAuthor());
        assertEquals("So What", jazzTrack.getTitle());
        assertEquals(245, jazzTrack.getDuration());
        assertEquals(Style.JAZZ, jazzTrack.getStyle());
    }

    @Test
    void shouldCreatePopTrack() throws NegativeDurationException {
        //Given
        //When
        Track popTrack = new PopTrack("Katy Perry","Roar", 124);
        //Then
        assertEquals("Katy Perry", popTrack.getAuthor());
        assertEquals("Roar", popTrack.getTitle());
        assertEquals(124, popTrack.getDuration());
        assertEquals(Style.POP, popTrack.getStyle());
    }

    @Test
    void shouldCreatePunkTrack() throws NegativeDurationException {
        //Given
        //When
        Track punkTrack = new PunkTrack("Ramones","Blitzkrieg Bop", 656);
        //Then
        assertEquals("Ramones", punkTrack.getAuthor());
        assertEquals("Blitzkrieg Bop", punkTrack.getTitle());
        assertEquals(656, punkTrack.getDuration());
        assertEquals(Style.PUNK, punkTrack.getStyle());
    }

    @Test
    void shouldCreateRapTrack() throws NegativeDurationException {
        //Given
        //When
        Track rapTrack = new RapTrack("Eminem","Fall", 55);
        //Then
        assertEquals("Eminem", rapTrack.getAuthor());
        assertEquals("Fall", rapTrack.getTitle());
        assertEquals(55, rapTrack.getDuration());
        assertEquals(Style.RAP, rapTrack.getStyle());
    }

    @Test
    void shouldCreateRockTrack() throws NegativeDurationException {
        //Given
        //When
        Track rockTrack = new RockTrack("Queen","Bohemian Rhapsody", 555);
        //Then
        assertEquals("Queen", rockTrack.getAuthor());
        assertEquals("Bohemian Rhapsody", rockTrack.getTitle());
        assertEquals(555, rockTrack.getDuration());
        assertEquals(Style.ROCK, rockTrack.getStyle());
    }

    @Test
    void checksToStringTrack() throws NegativeDurationException {
        //Given
        Track rockTrack = new RockTrack("Queen","Bohemian Rhapsody", 555);
        //When
        StringBuilder sb = new StringBuilder();
        sb.append("Track: " )
                .append(rockTrack.getAuthor())
                .append(" - ")
                .append(rockTrack.getTitle())
                .append("\nDuration: ")
                .append(rockTrack.getDuration())
                .append("\nStyle: ")
                .append(rockTrack.getStyle());
        //Then
        assertEquals(sb.toString(), rockTrack.toString());
    }

    @Test
    void checksEqualTracks() throws NegativeDurationException {
        //Given
        //When
        Track rockTrack1 = new RockTrack("Queen","Bohemian Rhapsody", 555);
        Track rockTrack2 = new RockTrack("Queen","Bohemian Rhapsody", 555);
        //Then
        assertEquals(true, rockTrack1.equals(rockTrack2));
        assertEquals(true, rockTrack2.equals(rockTrack1));
        assertEquals(rockTrack1.hashCode(), rockTrack2.hashCode());
        assertEquals(rockTrack1, rockTrack2);
    }

    @Test
    void changeTrackTitle() throws NegativeDurationException {
        //Given
        Track rockTrack = new RockTrack("Queen","Bohemian Rhapsody", 555);
        //When
        rockTrack.setTitle("Bohemian");
        //Then
        assertEquals("Bohemian", rockTrack.getTitle());
    }

    @Test
    void changeTrackAuthor() throws NegativeDurationException {
        //Given
        Track rockTrack = new RockTrack("Queen","Bohemian Rhapsody", 555);
        //When
        rockTrack.setAuthor("Queen1");
        //Then
        assertEquals("Queen1", rockTrack.getAuthor());
    }

    @Test
    void changeTrackDuration() throws NegativeDurationException {
        //Given
        Track rockTrack = new RockTrack("Queen","Bohemian Rhapsody", 555);
        //When
        rockTrack.setDuration(100);
        //Then
        assertEquals(100, rockTrack.getDuration());
    }

    @Test
    void changeTrackDurationToNegativeValue () throws NegativeDurationException {
        //Given
        Track rockTrack = new RockTrack("Queen","Bohemian Rhapsody", 555);
        //When
        Throwable exception = assertThrows(NegativeDurationException.class, () -> rockTrack.setDuration(-0.f));
        //Then
        assertEquals("Attempt to change duration to negative number." ,exception.getMessage());
    }
}
