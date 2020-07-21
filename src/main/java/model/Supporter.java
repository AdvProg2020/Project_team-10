package model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Supporter extends Account {
    private ServerSocket serverSocket;

    public Supporter(String username, String firstName, String lastName, String email, String phoneNumber
            , String password, String imagePath) {
        super(username, firstName, lastName, email, phoneNumber, password, imagePath);
    }

    public void chat() throws IOException {
        serverSocket = new ServerSocket(9090);
        while (true) {
            System.out.println("Waiting for client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("a client connected");

        }
    }
}

class handleInput extends Thread {
    Socket socket;
    DataInputStream dataInputStream;
    Account account;


    public handleInput(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                show(dataInputStream.readUTF());

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void show(String message) {

    }
}

class handleOutput extends Thread {
    Socket socket;
    DataOutputStream dataOutputStream;
    Account account;


    public handleOutput(Socket socket) throws IOException {
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    @Override
    public void run() {
//        try {
//            while (true) {
//                dataOutputStream.writeUTF();
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }


}

class clientInput extends Thread {
    Socket socket;
    DataInputStream dataInputStream;

    public clientInput(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    @Override
    public void run() {
        while (true) {

        }
    }
}