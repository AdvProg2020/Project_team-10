package model;

import controller.AccountManager;

public class Comment {
    private String username;
    private int goodId;
    private String text;
    private String title;
    private String status;
    private boolean isBought;

    public Comment(String username, int goodId, String text, String title) {
        this.username = username;
        this.goodId = goodId;
        this.text = text;
        this.title = title;
        AccountManager.increaseLastCommentId();
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

}
