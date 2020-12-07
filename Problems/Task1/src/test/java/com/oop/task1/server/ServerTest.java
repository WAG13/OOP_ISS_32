package com.oop.task1.server;

import com.oop.task1.track.Track;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServerTest {
    private Server testServer;
    private final Track track = new Track("Queen","Bohemian Rhapsody", 555);
    private Socket client;


    @Test
    public void testReadingObject() throws IOException, ClassNotFoundException, InterruptedException {
        ServerSocket serverSocket = Mockito.spy(new ServerSocket(1234));
        doReturn(client).when(serverSocket).accept();
        this.testServer = Mockito.spy(Server.class);
        doNothing().when(testServer).sendMessage(isA(String.class));
        testServer.setServerSocket(serverSocket);

        FileOutputStream file = new FileOutputStream("file.txt");
        ObjectOutputStream output = new ObjectOutputStream(file);
        output.writeObject(track);

        FileInputStream fileStream = new FileInputStream("file.txt");
        ObjectInputStream objStream = new ObjectInputStream(fileStream);

        Object trackReceived = testServer.readData(objStream);
        assertEquals(trackReceived.toString(), track.toString());
        testServer.closeServer();
    }

}
