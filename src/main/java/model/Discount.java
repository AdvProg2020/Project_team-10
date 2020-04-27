package model;

import java.util.Date;
import java.util.List;

public class Discount {
    private int code;
    private Date startDate;
    private Date endDate;
    private int percent;
    private long maxAmountOfDiscount;
    private int repeatDiscount;
    private List<Buyer> users;

    public Discount(int code, Date startDate, Date endDate, int percent, long maxAmountOfDiscount, int repeatDiscount, List<Buyer> users) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.maxAmountOfDiscount = maxAmountOfDiscount;
        this.repeatDiscount = repeatDiscount;
        this.users = users;
    }

    public int getCode() {
        return code;
    }
}
