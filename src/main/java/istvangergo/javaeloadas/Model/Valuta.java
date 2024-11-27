package istvangergo.javaeloadas.Model;

public class Valuta {
    private Float value;
    private String currency;
    private Integer rate;

    public Valuta(Float value, String currency, Integer rate) {
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
    public String getCurrency() {
        return currency;
    }

}
