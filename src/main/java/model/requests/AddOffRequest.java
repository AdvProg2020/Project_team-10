package model.requests;

import model.*;

import java.util.Date;
import java.util.List;

public class AddOffRequest extends Request {

    private int offId;
    private List<Good> goods;
    private Date startDate;
    private Date endDate;
    private int discount;

    public AddOffRequest(Account account, int id, int offId, List<Good> goods, Date startDate, Date endDate, int discount) {
        super(account, id);
        this.offId = offId;
        this.goods = goods;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
        this.requestName = "add off request";
        this.acceptMessage = "off with id " + id + " added";
        this.declineMessage = "request of add of off with id " + id + " was declined";
    }

    @Override
    public void accept() {
        Off off = new Off(offId, goods, startDate, endDate, discount);
        Shop.getShop().getAllOffs().add(off);
        ((Seller) account).getOffs().add(off);
        for (Good good : goods) {
            good.setOffId(offId);
        }
    }
}
