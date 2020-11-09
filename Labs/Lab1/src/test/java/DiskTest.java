import model.StyleComparator;
import model.Track;
import model.exceptions.NegativeDurationException;
import model.track_types.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import collection.Disk;

public class DiskTest {
    private Disk disk;
    private List<Track> trackList = new ArrayList<>(Arrays.asList(  new RockTrack("Queen","Bohemian Rhapsody", 555),
                                                                    new PunkTrack("Basement","Fall", 450),
                                                                    new RapTrack("Eminem","Fall", 55),
                                                                    new RapTrack("Eminem","Rap God", 65)));

    public DiskTest() throws NegativeDurationException {}



    void createNewDisk() {
        this.disk = new Disk("My Disk", trackList);
    }

    @Test
    void shouldCreateEmptyDiskWithoutName() {
        //Given
        //When
        disk = new Disk();
        //Then
        assertEquals("", disk.getName());
        assertTrue(disk.getTracks().isEmpty());
    }

    @Test
    void shouldCreateDiskWithoutName() {
        //Given
        //When
        disk = new Disk(trackList);
        //Then
        assertEquals("", disk.getName());
    }

    @Test
    void shouldCreateEmptyDiskWithName() {
        //Given
        //When
        disk = new Disk("My Disk");
        //Then
        assertEquals("My Disk", disk.getName());
        assertTrue(disk.getTracks().isEmpty());
    }

    @Test
    void shouldCreateDiskWithTracksAndWithName() {
        //Given
        //When
        createNewDisk();
        //Then
        assertEquals(trackList, disk.getTracks());
        assertEquals("My Disk", disk.getName());
    }

    @Test
    void shouldRenameDisk() {
        //Given
        createNewDisk();
        //When
        disk.setName("My Music");
        //Then
        assertEquals("My Music", disk.getName());
    }

    @Test
    void shouldAddEDMTrackToDisk() throws NegativeDurationException {
        //Given
        Track edmTrack = new EDMTrack("Avicii","Wake me up", 123);
        createNewDisk();
        //When
        disk.addTrack(edmTrack);
        //Then
        assertTrue(disk.getTracks().contains(edmTrack));
        assertEquals(5, disk.getTracks().size());
    }

    @Test
    void shouldRemoveAllTracksOfGivenAuthorFromDisk() throws NegativeDurationException {
        //Given
        createNewDisk();
        //When
        disk.removeTracksByAuthor("Eminem");
        //Then
        assertFalse(disk.getTracks().contains(new RapTrack("Eminem","Fall", 55)));
        assertFalse(disk.getTracks().contains(new RapTrack("Eminem","Rap God", 65)));
        assertEquals(2, disk.getTracks().size());
    }

    @Test
    void shouldRemoveOneTracksOfGivenAuthorFromDisk() throws NegativeDurationException {
        //Given
        createNewDisk();
        //When
        disk.removeTracksByAuthor("Eminem", 1);
        //Then
        assertFalse(disk.getTracks().contains(new RapTrack("Eminem","Fall", 55))
                    && disk.getTracks().contains(new RapTrack("Eminem","Rap God", 65)));
        assertTrue(disk.getTracks().contains(new RapTrack("Eminem","Fall", 55))
                    || disk.getTracks().contains(new RapTrack("Eminem","Rap God", 65)));
        assertEquals(3, disk.getTracks().size());
    }

    @Test
    void shouldRemoveAllTracksOfGivenTitleFromDisk() throws NegativeDurationException {
        //Given
        createNewDisk();
        //When
        disk.removeTracksByTitle("Fall");
        //Then
        System.out.println(disk.getInfo());
        assertFalse(disk.getTracks().contains(new RapTrack("Eminem","Fall", 55)));
        assertFalse(disk.getTracks().contains(new PunkTrack("Basement","Fall", 450)));
        assertEquals(2, disk.getTracks().size());
    }

    @Test
    void shouldRemoveOneTracksOfGivenTitleFromDisk() throws NegativeDurationException {
        //Given
        createNewDisk();
        //When
        disk.removeTracksByTitle("Fall", 1);
        //Then
        assertFalse(disk.getTracks().contains(new RapTrack("Eminem","Fall", 55))
                    && disk.getTracks().contains(new PunkTrack("Basement","Fall", 450)));
        assertTrue(disk.getTracks().contains(new RapTrack("Eminem","Fall", 55))
                    || disk.getTracks().contains(new PunkTrack("Basement","Fall", 450)));
        assertEquals(3, disk.getTracks().size());
    }

    @Test
    void shouldRemoveTracksOfGivenTitleAndAuthorFromDisk() throws NegativeDurationException {
        //Given
        createNewDisk();
        //When
        disk.removeTracks("Eminem","Fall");
        //Then
        assertFalse(disk.getTracks().contains(new RapTrack("Eminem","Fall", 55)));
        assertEquals(3, disk.getTracks().size());
    }

    @Test
    void shouldCalculateTotalDuration() {
        //Given
        createNewDisk();
        //When
        float totalCalories = disk.calculateDuration();
        //Then
        assertEquals(1125, totalCalories);
    }

    @Test
    void shouldSortTracksByStyle() throws NegativeDurationException {
        //Given
        createNewDisk();
        //When
        disk.sortTracks(new StyleComparator());
        //Then
        assertEquals(disk.getTracks(), Arrays.asList( new PunkTrack("Basement","Fall", 450),
                                    new RapTrack("Eminem","Fall", 55),
                                    new RapTrack("Eminem","Rap God", 65),
                                    new RockTrack("Queen","Bohemian Rhapsody", 555)));
    }

    @Test
    void shouldGetInfoAboutDisk() {
        //Given
        createNewDisk();
        //When
        //Then
        assertEquals(disk.getInfo(),"Disk: My Disk\n" +
                                    "Tracks amount: 4\n" +
                                    "Tracklist:\n" +
                                    "Track: Queen - Bohemian Rhapsody\n" +
                                    "Duration: 555.0\n" +
                                    "Style: ROCK\n" +
                                    "Track: Basement - Fall\n" +
                                    "Duration: 450.0\n" +
                                    "Style: PUNK\n" +
                                    "Track: Eminem - Fall\n" +
                                    "Duration: 55.0\n" +
                                    "Style: RAP\n" +
                                    "Track: Eminem - Rap God\n" +
                                    "Duration: 65.0\n" +
                                    "Style: RAP");
    }

    @Test
    void shouldDeleteAllTracksFromDisk() {
        //Given
        createNewDisk();
        //When
        disk.deleteAll();
        //Then
        assertTrue(disk.getTracks().isEmpty());
    }

    @Test
    void shouldFindTwoTracksInInterval() throws NegativeDurationException {
        //Given
        createNewDisk();
        //When
        List<Track> tracksInInterval = disk.findTracksInDurationInterval(440, 560);
        //Then
        assertEquals(Arrays.asList( new RockTrack("Queen","Bohemian Rhapsody", 555),
                                    new PunkTrack("Basement","Fall", 450)),
                                    tracksInInterval);
    }
}
