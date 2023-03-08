package org.hoofman;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionClient {
    public void clientConnect(Archive archive) throws Exception{
        Socket socket = new Socket(ConnectionServer.MY_IP, ConnectionServer.PORT);

        System.out.println("2. Client ready to connect");

        ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());

        greeting(writer);
        sendData(writer, reader, archive);
        getData(reader);
        endConnection(writer, socket);
    }

    public void greeting(ObjectOutputStream writer) throws Exception {
        String greeting = "3. Hello server, I'm client";
        writer.writeObject(greeting);
    }

    public void sendData(ObjectOutputStream writer, ObjectInputStream reader, Archive archive) throws Exception {
        String respondFromServer = (String) reader.readObject();
        if(respondFromServer.equals("4. Hi client, give me data")){
            System.out.println(respondFromServer);
            //FootballPlayer player = new FootballPlayer("Cristiano", "Ronaldo", 7);
            System.out.println("5. Sending data to server...");
            writer.writeObject(archive);
        }
    }

    public void getData(ObjectInputStream reader) throws Exception {
        String receivedMessage = (String) reader.readObject();
        System.out.println("9. Yeah! I have data from server:\n" + receivedMessage);
    }

    public void endConnection(ObjectOutputStream writer, Socket socket) throws Exception{
        writer.close();
        socket.close();
    }

//    public static void main(String[] args) throws Exception{
//
//        ConnectionClient client = new ConnectionClient();
//        client.clientConnect();
//    }
}
