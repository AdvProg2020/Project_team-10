package Bank;

import controller.AccountManager;
import model.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

public class BankServer {
    private SecureRandom secureRandom = new SecureRandom();
    private Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static HashMap<String, String> onlineAccounts = new HashMap<>();

    public static void main(String[] args) throws IOException {
        FileHandler.updateDatabase();
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            System.out.println("Waiting for client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("a client connected");
            new ClientHandlerForBank(clientSocket).start();
        }
    }


    public String generateTokenForUser(String username) {
        while (true) {
            byte[] randomBytes = new byte[24];
            secureRandom.nextBytes(randomBytes);
            String token = base64Encoder.encodeToString(randomBytes);
            if (!onlineAccounts.containsKey(token)) {
                onlineAccounts.put(token, username);
                return token;
            }
        }
    }
}

class ClientHandlerForBank extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Account account;


    public ClientHandlerForBank(Socket socket) throws IOException {
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = dataInputStream.readUTF().trim();
                String[] info = request.split("\\s");
                if (request.startsWith("create_account")) {
                    if (BankManager.canRegister(info[3], info[4], info[5]).equals("true")) {
                        dataOutputStream.writeUTF(String.valueOf(AccountManager.getLastAccountNumber()));
                        Bank.getBank().getAllBankAccounts().add(new BankAccount(info[1], info[2], info[3], info[4], AccountManager.getLastAccountNumber()));
                    } else {
                        dataOutputStream.writeUTF(BankManager.canRegister(info[3], info[4], info[5]));
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("get_token")) {
                    BankAccount bankAccount = BankManager.canGetToken(info[1], info[2]);
                    if (bankAccount != null) {
                        String token = new BankServer().generateTokenForUser(bankAccount.getUsername());
                        dataOutputStream.writeUTF(token);
                        BankManager.onlineToken.put(token, BankManager.getAccountIdByUsername(info[1]));
                        BankManager.tokenToDate.put(token, new Date());
                    } else {
                        dataOutputStream.writeUTF("invalid username or password");
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("create_receipt")) {
                    if (!(info.length == 7)) {
                        dataOutputStream.writeUTF("invalid parameters passed");
                    } else if (Long.parseLong(info[3]) < 1) {
                        dataOutputStream.writeUTF("invalid money");
                    } else if (BankManager.checkExpiration(info[1])) {
                        dataOutputStream.writeUTF("token expired");
                    } else {
                        if (info[2].equals("deposit")) {
                            if (info[4].equals("-1")) {
                                if (BankManager.isExistBankAccount(Integer.parseInt(info[5]))) {
                                    if (BankManager.canDeposit(info[1], Integer.parseInt(info[5]))) {
                                        if (info[6].contains("*") || info[6].contains("@") || info[6].contains("$")) {
                                            dataOutputStream.writeUTF("your input contains invalid characters");
                                        } else {
                                            dataOutputStream.writeUTF(String.valueOf(AccountManager.getLastReceiptId()));
                                            Bank.getBank().getAllReceipts().add(new Receipt(AccountManager.getLastReceiptId(),
                                                    info[1], info[2], Long.parseLong(info[3]), Integer.parseInt(info[4]),
                                                    Integer.parseInt(info[5]), info[6]));
                                        }
                                    } else {
                                        dataOutputStream.writeUTF("token is invalid");
                                    }
                                } else {
                                    dataOutputStream.writeUTF("dest account id is invalid");
                                }
                            } else {
                                dataOutputStream.writeUTF("source account id is invalid");
                            }
                        } else if (info[2].equals("withdraw")) {
                            if (info[5].equals("-1")) {
                                if (BankManager.isExistBankAccount(Integer.parseInt(info[4]))) {
                                    if (BankManager.canDeposit(info[1], Integer.parseInt(info[4]))) {
                                        if (info[6].contains("*") || info[6].contains("@") || info[6].contains("$")) {
                                            dataOutputStream.writeUTF("your input contains invalid characters");
                                        } else {
                                            dataOutputStream.writeUTF(String.valueOf(AccountManager.getLastReceiptId()));
                                            Bank.getBank().getAllReceipts().add(new Receipt(AccountManager.getLastReceiptId(),
                                                    info[1], info[2], Long.parseLong(info[3]), Integer.parseInt(info[4]),
                                                    Integer.parseInt(info[5]), info[6]));
                                        }
                                    } else {
                                        dataOutputStream.writeUTF("token is invalid");
                                    }
                                } else {
                                    dataOutputStream.writeUTF("source account id is invalid");
                                }
                            } else {
                                dataOutputStream.writeUTF("dest account id is invalid");
                            }
                        } else if (info[2].equals("move")) {
                            if (BankManager.isExistBankAccount(Integer.parseInt(info[4]))) {
                                if (BankManager.isExistBankAccount(Integer.parseInt(info[5]))) {
                                    if (info[4].equals(info[5])) {
                                        dataOutputStream.writeUTF("equal source and dest account");
                                    } else {
                                        if (BankManager.canDeposit(info[1], Integer.parseInt(info[4]))) {
                                            if (info[6].contains("*") || info[6].contains("@") || info[6].contains("$")) {
                                                dataOutputStream.writeUTF("your input contains invalid characters");
                                            } else {
                                                dataOutputStream.writeUTF(String.valueOf(AccountManager.getLastReceiptId()));
                                                Bank.getBank().getAllReceipts().add(new Receipt(AccountManager.getLastReceiptId(),
                                                        info[1], info[2], Long.parseLong(info[3]), Integer.parseInt(info[4]),
                                                        Integer.parseInt(info[5]), info[6]));
                                            }
                                        } else {
                                            dataOutputStream.writeUTF("token is invalid");
                                        }
                                    }
                                } else {
                                    dataOutputStream.writeUTF("dest account id is invalid");
                                }
                            } else {
                                dataOutputStream.writeUTF("source account id is invalid");
                            }

                        } else {
                            dataOutputStream.writeUTF("invalid receipt type");
                        }
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("get_transactions")) {
                    int id = BankManager.getAccountIdByToken(info[1]);
                    if (id == -1) {
                        dataOutputStream.writeUTF("token is invalid");
                    } else if (BankManager.checkExpiration(info[1])) {
                        dataOutputStream.writeUTF("token expired");
                    } else {
                        if (info[2].equals("+")) {
                            dataOutputStream.writeUTF(BankManager.getDestinationById(id));
                        } else if (info[2].equals("-")) {
                            dataOutputStream.writeUTF(BankManager.getSourceById(id));
                        } else if (info[2].equals("*")) {
                            dataOutputStream.writeUTF(BankManager.getAllTransactionsById(id));
                        } else {
                            if (BankManager.isExistReceipt(Integer.parseInt(info[2])) && BankManager.checkReceiptAndBankAccount(Integer.parseInt(info[2]), id)) {
                                dataOutputStream.writeUTF(BankManager.getReceiptById(Integer.parseInt(info[2])).toString());
                            } else {
                                dataOutputStream.writeUTF("invalid receipt id");
                            }
                        }
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("pay")) {
                    Receipt receipt = BankManager.getReceiptById(Integer.parseInt(info[1]));
                    if (receipt != null) {
                        if (!receipt.isPaid()) {
                            if (receipt.getReceiptType().equals("deposit")) {
                                BankAccount bankAccount = BankManager.getBankAccountById(receipt.getDestId());
                                bankAccount.setMoney(bankAccount.getMoney() + receipt.getMoney());
                                receipt.setPaid(true);
                                dataOutputStream.writeUTF("done successfully");
                            } else if (receipt.getReceiptType().equals("withdraw")) {
                                BankAccount bankAccount = BankManager.getBankAccountById(receipt.getSourceId());
                                if (receipt.getMoney() <= bankAccount.getMoney()) {
                                    bankAccount.setMoney(bankAccount.getMoney() - receipt.getMoney());
                                    receipt.setPaid(true);
                                    dataOutputStream.writeUTF("done successfully");
                                } else {
                                    dataOutputStream.writeUTF("source account does not have enough money");
                                }
                            } else {
                                BankAccount bankAccount1 = BankManager.getBankAccountById(receipt.getSourceId());
                                BankAccount bankAccount2 = BankManager.getBankAccountById(receipt.getDestId());
                                if (receipt.getMoney() <= bankAccount1.getMoney()) {
                                    bankAccount1.setMoney(bankAccount1.getMoney() - receipt.getMoney());
                                    bankAccount2.setMoney(bankAccount2.getMoney() + receipt.getMoney());
                                    receipt.setPaid(true);
                                    dataOutputStream.writeUTF("done successfully");
                                } else {
                                    dataOutputStream.writeUTF("source account does not have enough money");
                                }
                            }
                        } else {
                            dataOutputStream.writeUTF("receipt is paid before");
                        }
                    } else {
                        dataOutputStream.writeUTF("invalid receipt id");
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("get_balance")) {
                    if (BankManager.getAccountIdByToken(info[1]) == -1) {
                        dataOutputStream.writeUTF("token is invalid");
                    } else if (BankManager.checkExpiration(info[1])) {
                        dataOutputStream.writeUTF("token expired");
                    } else {
                        dataOutputStream.writeUTF(String.valueOf(BankManager.getBankAccountById(BankManager.getAccountIdByToken(info[1])).getMoney()));
                    }
                    dataOutputStream.flush();
                } else if (request.startsWith("exit")) {
                    FileHandler.write();
                    dataOutputStream.writeUTF("ok");
                    dataOutputStream.flush();
                    socket.close();
                    break;
                } else {
                    dataOutputStream.writeUTF("invalid input");
                    dataOutputStream.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


class InputHandler extends Thread {
    private Socket socket;
    private DataInputStream dataInputStream;

    public InputHandler(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(dataInputStream.readUTF());

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

