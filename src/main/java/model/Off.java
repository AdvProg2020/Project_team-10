package model;

import controller.AccountManager;

import java.util.Date;
import java.util.List;

public class Off {
    private int id;
    private List<Integer> goodsId;
    private String status;
    private Date startDate;
    private Date endDate;
    private int percent;

    public Off(int id, List<Integer> goodsId, Date startDate, Date endDate, int percent) {
        this.id = id;
        this.goodsId = goodsId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        AccountManager.increaseLastOffId();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(List<Integer> goodsId) {
        this.goodsId = goodsId;
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

    public int getPercent() {
        return percent;
    }


    @Override
    public String toString() {
        return "id:" + id + "\n" +
                "goods: " + goodsToString() + "\n" +
                "status: '" + status + "\n" +
                "startDate: " + startDate + "\n" +
                "endDate: " + endDate + "\n" +
                "discount: " + percent;
    }

    private String goodsToString() {
        //        for (Good good : this.goods) {
//            goodString.append("id: ").append(good.getId()).append(" , ").append("name: ").append(good.getName()).append(" | ");
//        }
        return "| ";
    }

}
