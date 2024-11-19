package istvangergo.javaeloadas.Model;

import java.time.LocalDate;
import java.util.List;

public class MNB {
    private List<Valuta> currencyList;
    private LocalDate date;
    public List<Valuta> getCurrencyList() {
        return currencyList;
    }
    public MNB(List<Valuta> currencyList, LocalDate date) {
        this.currencyList = currencyList;
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }
}
