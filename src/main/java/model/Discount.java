package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Discount {
    private int code;
    private Date startDate;
    private Date endDate;
    private int percent;
    private long maxAmountOfDiscount;
    private int repeatDiscount;
    private List<Account> users;

    public Discount(int code, Date startDate, Date endDate, int percent, long maxAmountOfDiscount, int repeatDiscount, List<Account> users) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.maxAmountOfDiscount = maxAmountOfDiscount;
        this.repeatDiscount = repeatDiscount;
        this.users = users;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setMaxAmountOfDiscount(long maxAmountOfDiscount) {
        this.maxAmountOfDiscount = maxAmountOfDiscount;
    }

    public void setRepeatDiscount(int repeatDiscount) {
        this.repeatDiscount = repeatDiscount;
    }

    public void setUsers(List<Account> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "code: " + code + "\n" +
                "startDate: " + startDate + "\n" +
                "endDate: " + endDate + "\n" +
                "percent: " + percent + "\n" +
                "maxAmountOfDiscount: " + maxAmountOfDiscount + "\n" +
                "repeatDiscount: " + repeatDiscount + "\n" +
                "users: " ;
    }

    public int getCode() {
        return code;
    }

    public List<Account> getUsers() {
        return users;
    }

}
