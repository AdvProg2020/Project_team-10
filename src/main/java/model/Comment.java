package model;

import controller.AccountManager;

public class Comment {
    private String username;
    private int goodId;
    private String text;
    private String status;
    private boolean isBought;

    public Comment(String username, int goodId, String text) {
        this.username = username;
        this.goodId = goodId;
        this.text = text;
        AccountManager.increaseLastCommentId();
    }

    @Override
    public String toString() {
        return text + '\n' +
                "isBought: " + isBought;
    }
}
