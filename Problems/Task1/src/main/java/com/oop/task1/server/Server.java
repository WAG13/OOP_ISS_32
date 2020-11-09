package com.oop.task1.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server extends Thread{

    private static final int PORT = 1234;
    private static final Logger logger= Logger.getLogger(Server.class.getName());
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

//    public static void main(String[] args) {
//        new Server().run();
//    }

    @Override
    public void run() {
        try {
            while (!exit) {
                exit = checkConnection();
                if (!exit && client.isConnected()){
                    readData();
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

    public int getPort() {
        return PORT;
    }

    private boolean checkConnection() {
        try {
            client = serverSocket.accept();
            logger.info("Connected.");
        } catch (IOException e) {
            return true;
        }
        return false;
    }

    private void readData() {
        try {
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Object object = in.readObject();

            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject("Server received "+object.toString());

            out.close();
            in.close();
            client.close();

            saveData(object);
        } catch (IOException | ClassNotFoundException e) {
            logger.info( "Can't read data.");
        }
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
