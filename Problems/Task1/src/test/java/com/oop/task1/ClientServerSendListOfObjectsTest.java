package com.oop.task1;

import com.oop.task1.client.Client;
import com.oop.task1.server.Server;
import com.oop.task1.track.Track;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientServerSendListOfObjectsTest {

    @Test
    public void shouldSendObjects() throws InterruptedException, IOException {
        //Given
        List<Object> trackList = new ArrayList<>(Arrays.asList( new Track("Queen","Bohemian Rhapsody", 555),
                new Track("Basement","Fall", 450),
                new Track("Eminem","Fall", 55),
                new Track("Eminem","Rap God", 65)));

        FileWriter fr = new FileWriter(new File("tracksTest.txt"), false);
        fr.close();
        fr = new FileWriter("tracksTest.txt", true);
        BufferedWriter br = new BufferedWriter(fr);

        for (Object track : trackList) {
            br.write(track.toString());
            br.newLine();
        }
        br.close();
        fr.close();

        Server server = new Server();
        server.start();
        //When
        Client client = new Client(trackList);
        client.start();
        client.join();
        server.closeServer();
        //Then
        Assert.assertEquals(server.getPort(),client.getPort());
        Assert.assertEquals(client.getHost(),"localhost");
        Assert.assertTrue(FileUtils.contentEquals(  new File("tracks.txt"),
                                                    new File("tracksTest.txt")));
        new File("tracksTest.txt").delete();

    }
}
