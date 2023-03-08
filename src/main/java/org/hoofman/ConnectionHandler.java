package org.hoofman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConnectionHandler {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader messageReader;
    private PrintWriter messageWriter;

    private ObjectOutputStream objectWriter;
    private ObjectInputStream objectReader;

    //Server Part
    public void startListening(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            System.out.println("Server: client connected successfully");

            messageWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            messageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // objectWriter = new ObjectOutputStream(clientSocket.getOutputStream());
            objectReader = new ObjectInputStream(clientSocket.getInputStream());

            Archive archive = (Archive) objectReader.readObject(); //read object from user
            String greeting = messageReader.readLine(); //read message from user
            if ("hello server".equals(greeting)) {
                System.out.println("Message from client: " + greeting);
                System.out.println("Data from client (System.out.println): " + archive);
                System.out.println("Sending data to client...");
                messageWriter.println("hello client, i'm server; your data ->" + Files.readString(Path.of("source.txt")));//send data to client
                System.out.println("Sent successfully");
            }
            else {
                messageWriter.println("unrecognised greeting");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void stopConnectionByServer() {
        try {
            messageReader.close();
            messageWriter.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    //Client part
    public void startConnectionByClient(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            messageWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            messageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //objectReader = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String sendMessageToServer(String msg, Archive archive) throws IOException {
        messageWriter.println(msg); //send message to server
        objectWriter = new ObjectOutputStream(clientSocket.getOutputStream());
        objectWriter.writeObject(archive); //send object to server
        String resp = messageReader.readLine();
        return resp;
    }

    public void stopConnectionByClient() {
        try {
            messageReader.close();
            messageWriter.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

