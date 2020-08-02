package model;

import controller.AccountManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Auction {
    private int id;
    private Good good;
    private LocalDateTime endDate;
    private long price;
    private List<String> buyersInAuction;
    private String buyerUsername;

    public Auction(int id, Good good, LocalDateTime endDate, long price) {
        this.id = id;
        this.good = good;
        this.endDate = endDate;
        this.price = price;
        this.buyersInAuction = new ArrayList<>();
        AccountManager.increaseLastAuctionId();
    }

    public int getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Good getGood() {
        return good;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public List<String> getBuyersInAuction() {
        return buyersInAuction;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }
}
