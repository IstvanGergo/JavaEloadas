package istvangergo.javaeloadas.Controller;

import istvangergo.javaeloadas.Oanda.v20.ContextBuilder;
import istvangergo.javaeloadas.Oanda.v20.pricing.ClientPrice;
import istvangergo.javaeloadas.Oanda.v20.pricing.PricingGetRequest;
import istvangergo.javaeloadas.Oanda.v20.pricing.PricingGetResponse;
import javafx.fxml.FXML;
import istvangergo.javaeloadas.Oanda.v20.Context;
import istvangergo.javaeloadas.Oanda.v20.account.AccountID;
import istvangergo.javaeloadas.Oanda.v20.account.AccountSummary;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OandaWindow {
    @FXML
    private TextArea accountDetails;
    @FXML
    private TextArea currentPrices;
    @FXML
    private ComboBox currencyPairs;

    public void populateCurrencyPairs(){

    }
    public void Initialize(){
        List<String> instruments = new ArrayList<>( Arrays.asList("EUR_USD", "USD_JPY",
                "GBP_USD", "USD_CHF"));
        currencyPairs.getItems().addAll(instruments);
    }
    public void getAccountDetails(ActionEvent actionEvent) {
        try {
            Context ctx = new Context(OandaConfig.URL,OandaConfig.TOKEN);
            AccountSummary summary = ctx.account.summary(new
                    AccountID(OandaConfig.ACCOUNTID)).getAccount();
            accountDetails.setText(summary.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getCurrentPrices(ActionEvent actionEvent) {
        try {
            Context ctx = new
                    ContextBuilder(OandaConfig.URL).setToken(OandaConfig.TOKEN).setApplication("PricePolling").build();
            AccountID accountId = OandaConfig.ACCOUNTID;
            List<String> instruments = new ArrayList<>();
            instruments.add((String) currencyPairs.getValue());
            PricingGetRequest request = new PricingGetRequest(accountId, instruments);
            PricingGetResponse resp = ctx.pricing.get(request);
            for (ClientPrice price : resp.getPrices())
                currentPrices.setText(currentPrices.getText() + "\n" + price.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

