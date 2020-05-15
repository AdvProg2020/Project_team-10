package model.requests;

import model.*;

import java.util.Date;
import java.util.List;

public class EditOffRequest extends Request {

    private int offId;
    private List<Good> goods;
    private Date startDate;
    private Date endDate;
    private int discount;

    public EditOffRequest(Account account, int id, int offId, List<Good> goods, Date startDate, Date endDate, int discount) {
        super(account, id);
        this.offId = offId;
        this.goods = goods;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
        this.requestName = "edit off request";
        this.acceptMessage = "off with id " + id + " edited";
        this.declineMessage = "request of edit of off with id " + id + " was declined";
    }

    @Override
    public void accept() {
        ((Seller) account).getOffWithId(offId).setGoods(goods);
        ((Seller) account).getOffWithId(offId).setStartDate(startDate);
        ((Seller) account).getOffWithId(offId).setEndDate(endDate);
        ((Seller) account).getOffWithId(offId).setDiscount(discount);
    }
}
