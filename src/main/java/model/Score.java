package model;

public class Score {
    private Buyer buyer;
    private float score;
    private Good good;

    public Score(Buyer buyer, float score, Good good) {
        this.buyer = buyer;
        this.score = score;
        this.good = good;
    }
}
