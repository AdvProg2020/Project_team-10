package model;

public class Comment {
    private Account account;
    private Good good;
    private String text;
    private String status;
    private boolean isBought;

    public Comment(Account account, Good good, String text, String status) {
        this.account = account;
        this.good = good;
        this.text = text;
        this.status = status;
    }
}
