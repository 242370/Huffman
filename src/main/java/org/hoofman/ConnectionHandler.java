package org.hoofman;

import javax.crypto.spec.PSource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConnectionHandler {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter  writer;

    //Server Part
    public void startListening(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String greeting = reader.readLine();
            if ("hello server".equals(greeting)) {
                writer.println(Files.readString(Path.of("source.txt")));
                reader.readLine();
            }
            else {
                writer.println("unrecognised greeting");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopConnectionByServer() {
        try {
            reader.close();
            writer.close();
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
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendMessageToServer(String msg) throws IOException {
        writer.println(msg);
        String resp = reader.readLine();
        return resp;
    }

    public void stopConnectionByClient() {
        try {
            reader.close();
            writer.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

