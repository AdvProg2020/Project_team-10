package Bank;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class BankClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        Scanner scanner = new Scanner(System.in);
        new InputHandler(socket).start();
        while (true) {
            String request = scanner.nextLine();
            dataOutputStream.writeUTF(request);
            dataOutputStream.flush();
        }
    }
}
