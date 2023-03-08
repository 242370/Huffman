package org.hoofman;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionClient { // Klasa przesyłająca dane

    // obsługa całego połączenia po stronie klienta
    public void clientConnect(Archive archive) {
        try {
            Socket socket = new Socket(ConnectionServer.SOMEONES_IP, ConnectionServer.PORT);
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            sendData(writer, reader, archive);
            System.out.println("Data successfully sent");
            endConnection(writer, socket);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Connection with server is somehow corrupted");
        }
    }

    // przesył obiektu Archiwum
    public void sendData(ObjectOutputStream writer, ObjectInputStream reader, Archive archive) {
        try {
            System.out.println("Sending data to server...");
            writer.writeObject(archive);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // zamykanie połączenia po stronie klienta
    public void endConnection(ObjectOutputStream writer, Socket socket) throws Exception {
        writer.close();
        socket.close();
    }

}
