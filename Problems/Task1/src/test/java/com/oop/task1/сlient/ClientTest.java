package com.oop.task1.сlient;

import com.oop.task1.client.Client;
import com.oop.task1.track.Track;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {
    @Mock
    private Socket socket;
    @Mock
    private OutputStream myOutputStream;

    @Test
    public void testSendObject() throws IOException, ClassNotFoundException, InterruptedException {
        final Track track = new Track("Queen","Bohemian Rhapsody", 555);
        doReturn(true).when(socket).isConnected();
        doReturn(myOutputStream).when(socket).getOutputStream();
        Client clientTest = Mockito.spy(new Client(track));
        doReturn("").when(clientTest).getServerMessage();
        clientTest.setClient(socket);
        assertTrue(clientTest.sendObject(track));
    }
}