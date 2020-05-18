package model;

import java.util.Date;
import java.util.List;

public class Off {
    private int id;
    private List<Good> goods;
    private String status;
    private Date startDate;
    private Date endDate;
    private int percent;

    public Off(int id, List<Good> goods, Date startDate, Date endDate, int percent) {
        this.id = id;
        this.goods = goods;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
    }

    public int getId() {
        return id;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
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

    public List<Good> getGoods() {
        return goods;
    }

    @Override
    public String toString() {
        return "id:" + id + "\n" +
                "goods: " + goods + "\n" +
                "status: '" + status + "\n" +
                "startDate: " + startDate + "\n" +
                "endDate: " + endDate + "\n" +
                "discount: " + percent;
    }
}
