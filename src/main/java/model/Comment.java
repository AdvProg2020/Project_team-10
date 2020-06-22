package model;

public class Comment {
    private Account account;
    private int goodId;
    private String text;
    private String status;
    private boolean isBought;

    public Comment(Account account, int goodId, String text) {
        this.account = account;
        this.goodId = goodId;
        this.text = text;
    }

    @Override
    public String toString() {
        return text + '\n' +
                "isBought: " + isBought;
    }
}
