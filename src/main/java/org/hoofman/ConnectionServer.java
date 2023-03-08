package org.hoofman;

import org.apache.commons.lang3.arch.Processor;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionServer { // klasa odbierająca dane
    public static final int PORT = 6666; // port przeznaczony do komunikacji
    public static final String SOMEONES_IP = "10.128.137.60"; // IP na które przesłane zostaną dane

    // rozpoczynanie nasłuchu na odpowiednim porcie
    public Archive serverConnect() {
        try{
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server opened");
            Socket socket = server.accept();
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
            // greeting(writer, reader);
            Archive archive = getData(reader);
            // sendData(writer);
            endConnection(server, writer);
            return archive;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // temp
    public void greeting(ObjectOutputStream writer, ObjectInputStream reader) throws Exception {
        String receivedGreeting = (String) reader.readObject();
        if (receivedGreeting.equals("3. Hello server, I'm client")) {
            System.out.println(receivedGreeting);
            String respond = "4. Hi client, give me data";
            writer.writeObject(respond);
        }
    }

    public Archive getData(ObjectInputStream reader) {
        try {
            Archive archive = (Archive) reader.readObject();
            System.out.println("6. Received data from client");
            //System.out.println("Data: " + receivedPlayer.getName() + " " + receivedPlayer.getSurname() + " " + receivedPlayer.getNumber());
            System.out.println("Data: " + archive.getBinaryValue() + " " + archive.getDecodingDict() + " " + archive.getLength());
            return archive;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void sendData(ObjectOutput writer) throws Exception {
        System.out.println("7. Sending data to client...");
        String message = "8. Hello client, I have your data";
        writer.writeObject(message);
    }

    public void endConnection(ServerSocket server, ObjectOutputStream writer) throws Exception {
        writer.close();
        server.close();
    }

    public static void main(String[] args) throws Exception {
        ConnectionServer server = new ConnectionServer();
        server.serverConnect();
    }
}
