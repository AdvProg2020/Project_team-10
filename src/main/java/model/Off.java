package model;

import java.util.Date;
import java.util.List;

public class Off {
    private int id;
    private List<Good> goods;
    private String status;
    private Date startDate;
    private Date endDate;
    private int discount;

    public Off(int id, List<Good> goods, String status, Date startDate, Date endDate, int discount) {
        this.id = id;
        this.goods = goods;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
    }
}
