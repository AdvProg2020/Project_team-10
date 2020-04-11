package model;

import java.util.Date;
import java.util.List;

public class Log {
    private String id;
    private Date date;
    private long paidAmount;
    private long discount;
    private List<Good> goods;
    private String nameOfBuyerOrSeller;
    private String status;

    public Log(String id, Date date, long paidAmount, long discount, List<Good> goods, String name, String status) {
        this.id = id;
        this.date = date;
        this.paidAmount = paidAmount;
        this.discount = discount;
        this.goods = goods;
        this.nameOfBuyerOrSeller = name;
        this.status = status;
    }
}
