package istvangergo.javaeloadas.Controller;

import istvangergo.javaeloadas.Oanda.v20.account.AccountID;
public class OandaConfig {
    private OandaConfig() {}
    public static final String URL = "https://api-fxpractice.oanda.com";
    public static final String TOKEN = "e12b99399585ef4ddefe5b578cf322d5-81b2eff58eab475599b15b2f08592a27";
    public static final AccountID ACCOUNTID = new AccountID("101-004-30388780-001");
}