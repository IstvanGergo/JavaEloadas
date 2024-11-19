package istvangergo.javaeloadas.Controller;

import javafx.fxml.FXML;
import istvangergo.javaeloadas.Oanda.v20.Context;
import istvangergo.javaeloadas.Oanda.v20.account.AccountID;
import istvangergo.javaeloadas.Oanda.v20.account.AccountSummary;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;


public class OandaWindow {
    @FXML
    private TextArea accountDetails;
    public void getAccountDetails(ActionEvent actionEvent) {
        try {
            Context ctx = new Context("https://api-fxpractice.oanda.com","e12b99399585ef4ddefe5b578cf322d5-81b2eff58eab475599b15b2f08592a27");
            AccountSummary summary = ctx.account.summary(new
                    AccountID("101-004-30388780-001")).getAccount();
            accountDetails.setText(summary.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
