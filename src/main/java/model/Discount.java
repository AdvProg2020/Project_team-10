package model;

import java.util.ArrayList;
import controller.AccountManager;

import java.util.Date;
import java.util.List;

public class Discount {
    private int code;
    private Date startDate;
    private Date endDate;
    private int percent;
    private long maxAmountOfDiscount;
    private int repeatDiscount;
    private List<String> userNames;

    public Discount(int code, Date startDate, Date endDate, int percent, long maxAmountOfDiscount, int repeatDiscount, List<String> userNames) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.maxAmountOfDiscount = maxAmountOfDiscount;
        this.repeatDiscount = repeatDiscount;
        this.userNames = userNames;
        AccountManager.increaseLastDiscountId();
    }

    public int getRepeatDiscount() {
        return repeatDiscount;
    }

    public long getMaxAmountOfDiscount() {
        return maxAmountOfDiscount;
    }

    public int getPercent() {
        return percent;
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

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
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

    public String toStringForBuyer() {
        return "code: " + code + "\n" +
                "startDate: " + startDate + "\n" +
                "endDate: " + endDate + "\n" +
                "percent: " + percent + "\n" +
                "maxAmountOfDiscount: " + maxAmountOfDiscount + "\n" +
                "repeatDiscount: " + repeatDiscount + "\n-----------------------------------";
    }

    public int getCode() {
        return code;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
