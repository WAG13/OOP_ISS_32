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

    @Override
    public void run() {
        for (Object object:objects){
            connect();
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

    public void setClient(Socket client) {
        this.client = client;
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

    public boolean sendObject(Object object) {

        if (client.isConnected()) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                out.writeObject(object);
                getServerMessage();
                out.close();
            } catch (IOException | ClassNotFoundException e) {
                logger.info("Object wasn't send. Exception handled: " + e.getMessage());
                return false;
            }
        } else {
            logger.info("Not connected to server.");
            return false;
        }
        return true;
    }

    public String getServerMessage() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
        String message = (String)in.readObject();
        logger.info(message);
        in.close();
        return message;
    }
}