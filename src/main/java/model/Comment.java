package model;

public class Comment {
    private Account account;
    private Good good;
    private String text;
    private String status;
    private boolean isBought;

    public Comment(Account account, Good good, String text) {
        this.account = account;
        this.good = good;
        this.text = text;
    }

    @Override
    public String toString() {
        return "account : " + account.getUsername() + "\n" +
                "text : " + text + '\n' +
                "isBought : " + isBought;
    }
}
