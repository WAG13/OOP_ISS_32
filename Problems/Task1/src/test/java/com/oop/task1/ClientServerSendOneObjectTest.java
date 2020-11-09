package com.oop.task1;

import com.oop.task1.server.Server;
import com.oop.task1.client.Client;
import com.oop.task1.track.Track;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ClientServerSendOneObjectTest {

    @Test
    public void shouldSendObject() throws InterruptedException, IOException {
        //Given
        Track track = new Track("Queen","Bohemian Rhapsody", 555);

        FileWriter fr = new FileWriter(new File("tracksTest.txt"), false);
        fr.close();
        fr = new FileWriter("tracksTest.txt", true);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(track.toString());
        br.newLine();
        br.close();
        fr.close();

        Server server = new Server();
        server.start();
        //When
        Client client = new Client(track);
        client.start();
        client.join();
        server.closeServer();
        //Then
        Assert.assertTrue(FileUtils.contentEquals(  new File("tracks.txt"),
                                                    new File("tracksTest.txt")));
        new File("tracksTest.txt").delete();
    }

}
