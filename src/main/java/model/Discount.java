package model;

import java.util.Date;
import java.util.List;

public class Discount {
    private String code;
    private Date startDate;
    private Date endDate;
    private int percent;
    private long maxAmountOfDiscount;
    private int repeatDiscount;
    private List<Buyer> users;
}
