package istvangergo.javaeloadas.Model;

import java.util.Currency;

public class Valuta {
    private Float value;
    private Currency currency;
    private Integer rate;
    public Valuta(Float value, Currency currency, Integer rate) {
        this.value = value;
        this.currency = currency;
        this.rate = rate;
    }
    public Float getValue() {
        return value;
    }
    public Integer getRate() {
        return rate;
    }
    public Currency getCurrency() {
        return currency;
    }
}
