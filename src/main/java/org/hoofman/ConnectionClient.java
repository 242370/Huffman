package org.hoofman;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionClient { // Klasa przesyłająca dane

    // obsługa całego połączenia po stronie klienta
    public void clientConnect(Archive archive) {
        try {
            Socket socket = new Socket(ConnectionServer.SOMEONES_IP, ConnectionServer.PORT);

            //System.out.println("2. Client ready to connect");

            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());

            // greeting(writer);
            sendData(writer, reader, archive);
            // getData(reader);
            System.out.println("Data successfully sent");
            endConnection(writer, socket);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Connection with server is somehow corrupted");
        }
    }

    // temp
    public void greeting(ObjectOutputStream writer) throws Exception {
        String greeting = "3. Hello server, I'm client";
        writer.writeObject(greeting);
    }

    // przesył obiektu Archiwum
    public void sendData(ObjectOutputStream writer, ObjectInputStream reader, Archive archive) {
        try {
            // String respondFromServer = (String) reader.readObject();
//            if (respondFromServer.equals("4. Hi client, give me data")) {
//                System.out.println(respondFromServer);
//                System.out.println("5. Sending data to server...");
//                writer.writeObject(archive);
//            }
            System.out.println("Sending data to server...");
            writer.writeObject(archive);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getData(ObjectInputStream reader) throws Exception {
        String receivedMessage = (String) reader.readObject();
        System.out.println("9. Yeah! I have data from server:\n" + receivedMessage);
    }

    // zamykanie połączenia po stronie klienta
    public void endConnection(ObjectOutputStream writer, Socket socket) throws Exception {
        writer.close();
        socket.close();
    }

//    public static void main(String[] args) throws Exception{
//
//        ConnectionClient client = new ConnectionClient();
//        client.clientConnect();
//    }
}
