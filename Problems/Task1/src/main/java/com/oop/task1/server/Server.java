package com.oop.task1.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server extends Thread {

    private static final int PORT = 1234;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private File file = new File("tracks.txt");

    private boolean exit = false;
    private ServerSocket serverSocket;
    private Socket client;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            new FileWriter(file, false).close();
        } catch (IOException e) {
            logger.info("Exception handled: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (!exit) {
                exit = checkConnection();
                if (!exit && client.isConnected()) {
                    saveData(readData(new ObjectInputStream(client.getInputStream())));
                    client.close();
                }
            }
        } catch (Exception e) {
            logger.severe("Exception handled: " + e.getMessage());
            try {
                serverSocket.close();
            } catch (IOException ex) {
                logger.severe("Cannot close server. " + ex.getMessage());
            }
        }
    }

    public void closeServer() throws IOException, InterruptedException {
        serverSocket.close();
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public int getPort() {
        return PORT;
    }

    public boolean checkConnection() {
        try {
            client = serverSocket.accept();
            logger.info("Connected.");
        } catch (IOException e) {
            return true;
        }
        return false;
    }

    public Object readData(ObjectInputStream in) throws IOException {
        Object object = new Object();
        try {
            object = in.readObject();
            sendMessage("Server received " + object.toString());
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            sendMessage("Can't read data.");
            logger.info("Can't read data.");
        }
        return object;
    }

    public void sendMessage(String text) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(text);
        out.close();
    }

    private void saveData(Object object) throws IOException {
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(object.toString());
        br.newLine();
        br.close();
        fr.close();
        logger.info("Saved object: " + object.toString());
    }

}
