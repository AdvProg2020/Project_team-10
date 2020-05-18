package model;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BuyerLog {
    private int id;
    private Date date;
    private long paidAmount;
    private long discount;
    private Map<Seller, List<Good>> sellersToHisGoods;
    private String status;

    public BuyerLog(int id, Date date, long paidAmount, long discount, Map<Seller, List<Good>> sellersToHisGoods, String status) {
        this.id = id;
        this.date = date;
        this.paidAmount = paidAmount;
        this.discount = discount;
        this.status = status;
        this.sellersToHisGoods = sellersToHisGoods;
    }


    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                " date: " + date + "\n" +
                " paidAmount: " + paidAmount + "\n" +
                " discount: " + discount +  "\n" +
                mapToString() + "\n" +
                " status: '" + status + "\n-------------------------------";
    }

    private String mapToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Seller seller : sellersToHisGoods.keySet()) {
            stringBuilder.append("seller: ").append(seller.getUsername());
            for (Good good : sellersToHisGoods.get(seller)) {
                stringBuilder.append(" | ").append(good.goodMenuToString());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
