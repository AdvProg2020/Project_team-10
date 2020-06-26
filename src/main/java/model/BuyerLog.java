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
    private Map<String, List<Good>> sellersToHisGoods;
    private String status;

    public BuyerLog(int id, Date date, long paidAmount, long discount, Map<String, List<Good>> sellersToHisGoods, String status) {
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

    public String mapToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String sellerUsername : sellersToHisGoods.keySet()) {
            stringBuilder.append("seller: ").append(sellerUsername);
            for (Good good : sellersToHisGoods.get(sellerUsername)) {
                stringBuilder.append(" | ").append(good.goodMenuToString());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Date getDate() {
        return date;
    }

    public long getPaidAmount() {
        return paidAmount;
    }

    public String getStatus() {
        return status;
    }

    public long getDiscount() {
        return discount;
    }

    public Map<String, List<Good>> getSellersToHisGoods() {
        return sellersToHisGoods;
    }
}
