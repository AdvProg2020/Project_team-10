package model;

import java.util.Date;
import java.util.List;

public class Log {
    private int id;
    private Date date;
    private long paidAmount;
    private long discount;
    private List<Good> goods;
    private String nameOfBuyerOrSeller;
    private String status;

    public Log(int id, Date date, long paidAmount, long discount, List<Good> goods, String name, String status) {
        this.id = id;
        this.date = date;
        this.paidAmount = paidAmount;
        this.discount = discount;
        this.goods = goods;
        this.nameOfBuyerOrSeller = name;
        this.status = status;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "date: " + date + "\n" +
                "paidAmount: " + paidAmount + "\n" +
                "discount: " + discount + "\n" +
                "goods: " + goods + "\n" +
                "nameOfBuyerOrSeller: " + nameOfBuyerOrSeller + "\n" +
                "status: " + status;
    }

    public int getId() {
        return id;
    }
}
