package org.hoofman;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionServer {
    public static final int PORT = 6667;
    public static final String MY_IP = "10.128.110.154";
    public static final String SOMEONES_IP = "10.128.137.60";

    public void serverConnect() throws Exception {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("1. Server opened");
        Socket socket = server.accept();
        ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
        greeting(writer, reader);
        getData(reader);
        sendData(writer);
        endConnection(server, writer);
    }

    public void greeting(ObjectOutputStream writer, ObjectInputStream reader) throws Exception{
        String receivedGreeting = (String) reader.readObject();
        if(receivedGreeting.equals("3. Hello server, I'm client")){
            System.out.println(receivedGreeting);
            String respond = "4. Hi client, give me data";
            writer.writeObject(respond);
        }
    }

    public void getData(ObjectInputStream reader) throws Exception {
        //FootballPlayer receivedPlayer = (FootballPlayer) reader.readObject();
        Archive archive = (Archive) reader.readObject();
        System.out.println("6. Received data from client");
        //System.out.println("Data: " + receivedPlayer.getName() + " " + receivedPlayer.getSurname() + " " + receivedPlayer.getNumber());
        System.out.println("Data: " + archive.getBinaryValue() + " " + archive.getDecodingDict() + " " + archive.getLength());
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

    public static void main(String[] args) throws Exception{
        ConnectionServer server = new ConnectionServer();
        server.serverConnect();
    }
}
