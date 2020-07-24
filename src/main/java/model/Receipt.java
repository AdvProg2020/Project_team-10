package model;

public class Receipt {
    private String token;
    private String receiptType;
    private long money;
    private int sourceId;
    private int destId;
    private String description;
    private int receiptId;
    private boolean isPaid;



    public Receipt(String token, String receiptType, long money, int sourceId, int destId, String description) {
        this.token = token;
        this.receiptType = receiptType;
        this.money = money;
        this.sourceId = sourceId;
        this.destId = destId;
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public long getMoney() {
        return money;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getDestId() {
        return destId;
    }

    public String getDescription() {
        return description;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "receiptType:" + receiptType + "\n" +
                "money:" + money + "\n" +
                "sourceId:" + sourceId + "\n" +
                "destId:" + destId + "\n" +
                "description:" + description + "\n" +
                "receiptId:" + receiptId + "\n" +
                "isPaid:" + isPaid;
    }
}
