package istvangergo.javaeloadas.Model;

import istvangergo.javaeloadas.Oanda.v20.primitives.DateTime;

public class HistoricRate {
    DateTime date;
    String rate;
    public HistoricRate(DateTime date, String rate) {
        this.date = date;
        this.rate = rate;
    }
    public DateTime getDate() {
        return date;
    }
    public String getRate() {
        return rate;
    }
}
