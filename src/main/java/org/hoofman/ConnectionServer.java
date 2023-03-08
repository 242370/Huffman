package org.hoofman;

import org.apache.commons.lang3.arch.Processor;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionServer { // klasa odbierająca dane
    public static final int PORT = 6666; // port przeznaczony do komunikacji
    public static final String SOMEONES_IP = "10.128.110.154"; // IP na które przesłane zostaną dane

    // rozpoczynanie nasłuchu na odpowiednim porcie
    public Archive serverConnect() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server opened");
            Socket socket = server.accept();
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            Archive archive = getData(reader);
            endConnection(server, writer);
            return archive;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public Archive getData(ObjectInputStream reader) {
        try {
            Archive archive = (Archive) reader.readObject();
            System.out.println("Received data");
            // System.out.println("Data: " + archive.getBinaryValue() + " " + archive.getDecodingDict() + " " + archive.getLength());
            System.out.println("Received data was decoded and written in destination.txt file");
            return archive;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    // zamknięcie połączenia po stronie serwera
    public void endConnection(ServerSocket server, ObjectOutputStream writer) throws Exception {
        writer.close();
        server.close();
    }

}
