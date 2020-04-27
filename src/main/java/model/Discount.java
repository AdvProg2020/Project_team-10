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

    @Override
    public String toString() {
        return "code: " + code +"\n"+
                "startDate: " + startDate +"\n"+
                "endDate: " + endDate +"\n"+
                "percent: " + percent +"\n"+
                "maxAmountOfDiscount: " + maxAmountOfDiscount +"\n"+
                "repeatDiscount: " + repeatDiscount + "\n"+
                "users: " + users + "\n";
    }

    public int getCode() {
        return code;
    }

}
