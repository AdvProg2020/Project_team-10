package model;

import java.util.Date;

public class Auction {
    private Good good;
    private Date startDate;
    private Date endDate;

    public Auction(Good good, Date startDate, Date endDate) {
        this.good = good;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Good getGood() {
        return good;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
