module istvangergo.javaeloadas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires httpcore;
    requires httpclient;
    requires gson;
    requires java.desktop;
    requires org.hibernate.orm.core;

    opens istvangergo.javaeloadas.Oanda.v20;
    opens istvangergo.javaeloadas.Oanda.v20.account;
    opens istvangergo.javaeloadas.Oanda.v20.pricing;
    opens istvangergo.javaeloadas.Oanda.v20.pricing_common;
    opens istvangergo.javaeloadas.Oanda.v20.order;
    opens istvangergo.javaeloadas.Oanda.v20.instrument;
    opens istvangergo.javaeloadas.Oanda.v20.transaction;
    opens istvangergo.javaeloadas.Oanda.v20.trade;
    opens istvangergo.javaeloadas to javafx.fxml;
    opens istvangergo.javaeloadas.Model to javafx.fxml, javafx.base;
    opens istvangergo.javaeloadas.Controller to javafx.fxml;
    exports istvangergo.javaeloadas;
    exports istvangergo.javaeloadas.Controller;
    exports istvangergo.javaeloadas.Oanda.v20.primitives;
    exports istvangergo.javaeloadas.Oanda.v20.transaction;
    exports istvangergo.javaeloadas.Oanda.v20.pricing_common;
    exports istvangergo.javaeloadas.Oanda.v20.order;
    exports istvangergo.javaeloadas.Oanda.v20.trade;
}