package model;

import java.util.Date;
import java.util.List;

public class SellerLog {
    private int id;
    private Date date;
    private long receivedAmount;
    private long discount;
    private List<Good> goods;
    private String usernameOfBuyer;
    private String status;

    public SellerLog(int id, Date date, long receivedAmount, long discount, List<Good> goods, String usernameOfBuyer, String status) {
        this.id = id;
        this.date = date;
        this.receivedAmount = receivedAmount;
        this.discount = discount;
        this.goods = goods;
        this.usernameOfBuyer = usernameOfBuyer;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "date: " + date + "\n" +
                "receivedAmount: " + receivedAmount + "\n" +
                "discount: " + discount + "\n" +
                "goods: " + goodsToString() + "\n" +
                "buyer: " + usernameOfBuyer + "\n" +
                "status: " + status + "\n--------------------------------";
    }

    private String goodsToString() {
        StringBuilder goodString = new StringBuilder("| ");
        for (Good good : this.goods) {
            goodString.append("id: ").append(good.getId()).append(" , ").append("name: ").append(good.getName()).append(" | ");
        }
        return goodString.toString();
    }

}
