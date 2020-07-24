package model;

import java.time.LocalDateTime;

public class Auction {
    private int id;
    private Good good;
    private LocalDateTime endDate;
    private long price;

    public Auction(int id, Good good, LocalDateTime endDate, long price) {
        this.id = id;
        this.good = good;
        this.endDate = endDate;
        this.price = price;
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
}
