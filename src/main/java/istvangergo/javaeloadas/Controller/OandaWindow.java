package istvangergo.javaeloadas.Controller;

import istvangergo.javaeloadas.Model.HistoricRate;
import istvangergo.javaeloadas.Oanda.v20.ContextBuilder;
import istvangergo.javaeloadas.Oanda.v20.instrument.Candlestick;
import istvangergo.javaeloadas.Oanda.v20.instrument.InstrumentCandlesRequest;
import istvangergo.javaeloadas.Oanda.v20.instrument.InstrumentCandlesResponse;
import istvangergo.javaeloadas.Oanda.v20.order.MarketOrderRequest;
import istvangergo.javaeloadas.Oanda.v20.order.OrderCreateRequest;
import istvangergo.javaeloadas.Oanda.v20.order.OrderCreateResponse;
import istvangergo.javaeloadas.Oanda.v20.pricing.ClientPrice;
import istvangergo.javaeloadas.Oanda.v20.pricing.PricingGetRequest;
import istvangergo.javaeloadas.Oanda.v20.pricing.PricingGetResponse;
import istvangergo.javaeloadas.Oanda.v20.pricing_common.PriceValue;
import istvangergo.javaeloadas.Oanda.v20.primitives.AccountUnits;
import istvangergo.javaeloadas.Oanda.v20.primitives.DateTime;
import istvangergo.javaeloadas.Oanda.v20.primitives.DecimalNumber;
import istvangergo.javaeloadas.Oanda.v20.primitives.InstrumentName;
import istvangergo.javaeloadas.Oanda.v20.trade.Trade;
import istvangergo.javaeloadas.Oanda.v20.trade.TradeCloseRequest;
import istvangergo.javaeloadas.Oanda.v20.trade.TradeID;
import istvangergo.javaeloadas.Oanda.v20.trade.TradeSpecifier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import istvangergo.javaeloadas.Oanda.v20.Context;
import istvangergo.javaeloadas.Oanda.v20.account.AccountID;
import istvangergo.javaeloadas.Oanda.v20.account.AccountSummary;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static istvangergo.javaeloadas.Oanda.v20.instrument.CandlestickGranularity.H1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class OandaWindow {
    @FXML
    private TableView<Trade> openPositions;
    @FXML
    private TableColumn<Trade, TradeID> openTradeID;
    @FXML
    private TableColumn<Trade, InstrumentName> openInstrument;
    @FXML
    private TableColumn<Trade, DateTime> openDate;
    @FXML
    private TableColumn<Trade, DecimalNumber> openUnit;
    @FXML
    private TableColumn<Trade, PriceValue> openPrice;
    @FXML
    private TableColumn<Trade, AccountUnits> openUnrealizedpl;
    /* Pozíciózárás */
    @FXML
    private TextField positionID;
    /* Pozíciónyitás */
    @FXML
    private TextArea openTextArea;
    @FXML
    private ComboBox<String> currencyPairsOpen;
    @FXML
    private ComboBox<String> sellOrBuy;
    @FXML
    private ComboBox<Integer> amount;
    /* Historikus adatok */
    @FXML
    private TableView<HistoricRate> historicTable;
    @FXML
    private TableColumn<HistoricRate, DateTime> historicDate;
    @FXML
    private TableColumn<HistoricRate, String> historicRate;
    @FXML
    private DatePicker historicFrom;
    @FXML
    private DatePicker historicTo;
    @FXML
    private ComboBox<String> currencyPairsHistoric;

    /* Számlaadatok */
    @FXML
    private TextArea accountDetails;
    @FXML
    private TextArea currentPrices;
    @FXML
    private ComboBox<String> currencyPairsActual;


    public void populateComboBoxes(){
        List<String> instruments = new ArrayList<>( Arrays.asList("EUR_USD", "USD_JPY",
                "GBP_USD", "USD_CHF"));
        currencyPairsActual.getItems().addAll(instruments);
        currencyPairsHistoric.getItems().addAll(instruments);
        currencyPairsOpen.getItems().addAll(instruments);
        sellOrBuy.getItems().addAll("Long","Short");
        for(int i = 25; i <= 1000; i+= 25){
            amount.getItems().add(i);
        }
    }
    public void initialize(){
        populateComboBoxes();
        historicDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        historicRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        openTradeID.setCellValueFactory(new PropertyValueFactory<>("id"));
        openInstrument.setCellValueFactory(new PropertyValueFactory<>("instrument"));
        openDate.setCellValueFactory(new PropertyValueFactory<>("openTime"));
        openUnit.setCellValueFactory(new PropertyValueFactory<>("currentUnits"));
        openPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        openUnrealizedpl.setCellValueFactory(new PropertyValueFactory<>("unrealizedPL"));

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
            instruments.add( currencyPairsActual.getValue());
            PricingGetRequest request = new PricingGetRequest(accountId, instruments);
            PricingGetResponse resp = ctx.pricing.get(request);
            for (ClientPrice price : resp.getPrices())
                currentPrices.setText(currentPrices.getText() + "\n" + price.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHistoricData(ActionEvent actionEvent) {
        ObservableList<HistoricRate> rates = FXCollections.observableArrayList();
        rates.clear();
        try {
            Context ctx = new
                    ContextBuilder(OandaConfig.URL).setToken(OandaConfig.TOKEN).setApplication("HistoricPrices").build();
            AccountID accountId = OandaConfig.ACCOUNTID;
            String instrument = currencyPairsHistoric.getValue();
            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new
                    InstrumentName( instrument ));
            request.setGranularity(H1);
            request.setFrom(String.valueOf(historicFrom.getValue()));
            request.setTo(String.valueOf(historicTo.getValue()));
            InstrumentCandlesResponse resp = ctx.instrument.candles(request);
            for (Candlestick candle : resp.getCandles())
            {
                rates.add(new HistoricRate(candle.getTime(),candle.getMid().getC().toString()));
            }
            historicTable.setItems(rates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onOpenPosition(ActionEvent actionEvent) {
        try {
            Context ctx = new
                    ContextBuilder(OandaConfig.URL).setToken(OandaConfig.TOKEN).setApplication("openPosition").build();
            AccountID accountId = OandaConfig.ACCOUNTID;
            openPosition(accountId, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openPosition(AccountID _accountId, Context _ctx) {
        try {
            OrderCreateRequest request = new OrderCreateRequest(_accountId);
            MarketOrderRequest marketorderrequest = new MarketOrderRequest();
            marketorderrequest.setInstrument(currencyPairsOpen.getValue());
            if(Objects.equals(sellOrBuy.getValue(), "Short")) {
                marketorderrequest.setUnits(-amount.getValue());
            }
            else {
            marketorderrequest.setUnits(amount.getValue());
            }
            request.setOrder(marketorderrequest);
            OrderCreateResponse response = _ctx.order.create(request);
            openTextArea.setText(openTextArea.getText() + "\n" +"tradeId:" + response.getOrderFillTransaction().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closePosition(ActionEvent actionEvent) {
        try{
        Context ctx = new
                ContextBuilder(OandaConfig.URL).setToken(OandaConfig.TOKEN).setApplication("openPosition").build();
        AccountID accountId = OandaConfig.ACCOUNTID;
        ctx.trade.close(new TradeCloseRequest(accountId, new TradeSpecifier(positionID.getText())));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOpenPositions(ActionEvent actionEvent) {
        ObservableList<Trade> trades = FXCollections.observableArrayList();
        trades.clear();
        try {
            Context ctx = new
                    ContextBuilder(OandaConfig.URL).setToken(OandaConfig.TOKEN).setApplication("openPosition").build();
            AccountID accountId = OandaConfig.ACCOUNTID;
            for (Trade trade : ctx.trade.listOpen(accountId).getTrades()){
                trades.add(trade);
            }
            openPositions.setItems(trades);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

