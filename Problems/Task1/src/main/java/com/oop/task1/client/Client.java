package com.oop.task1.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Client extends Thread{

    private static final String HOST = "localhost";
    private static final int PORT = 1234;
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private List<Object> objects = new ArrayList<>();
    private Socket client = null;

    public Client(List<Object> objects) {
        this.objects = objects;
    }

    public Client(Object object) {
        if (object != null) this.objects.add(object);
    }

//    public static void main(String[] args) {
//        List<Object> trackList = new ArrayList<>(Arrays.asList( new Track("Queen","Bohemian Rhapsody", 555),
//                                                                new Track("Basement","Fall", 450),
//                                                                new Track("Eminem","Fall", 55),
//                                                                new Track("Eminem","Rap God", 65)));
//        new Client(trackList).run();
//    }

    @Override
    public void run() {
        for (Object object:objects){
            sendObject(object);
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return HOST;
    }

    public int getPort() {
        return PORT;
    }

    public void connect() {
        try {
            client = new Socket(HOST,  PORT);
            logger.info(String.format("Connected to the %s", client.getInetAddress().getHostAddress()));
        } catch (IOException e) {
            logger.info("Exception handled: " + e.getMessage());
        }
    }

    public void sendObject(Object object) {
        connect();
        if (client.isConnected()) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                out.writeObject(object);
                logger.info("Send object "+object.toString());

                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                String message = (String)in.readObject();
                logger.info(message);

                in.close();
                out.close();
            } catch (IOException | ClassNotFoundException e) {
                logger.info("Object wasn't send. Exception handled: " + e.getMessage());
            }
        } else {
            logger.info("Not connected to server.");
        }
    }
}