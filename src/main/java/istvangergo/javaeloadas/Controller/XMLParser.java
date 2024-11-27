package istvangergo.javaeloadas.Controller;

import istvangergo.javaeloadas.Model.MNB;
import istvangergo.javaeloadas.Model.Valuta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

public class XMLParser {

    public static ObservableList<MNB> parseAll(File xmlFile) throws Exception {
        ObservableList<MNB> list = FXCollections.observableArrayList();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        NodeList Dates = document.getElementsByTagName("day");
        for (int i = 0; i < Dates.getLength(); i++) {
            Node dateNode = Dates.item(i);
            Element dateElement = (Element) dateNode;
            MNB data = getMnb(dateElement);
            list.add(data);
        }
        return list;
    }

    public static Map<LocalDate, Map<String, Float>> parseAndTransform(File xmlFile) throws Exception {
        ObservableList<MNB> mnbData = parseAll(xmlFile);
        Map<LocalDate, Map<String, Float>> tableData = new LinkedHashMap<>();
        for (MNB mnb : mnbData) {
            LocalDate date = mnb.getDate();
            Map<String, Float> currencyMap = new HashMap<>();
            for (Valuta valuta : mnb.getCurrencyList()) {
                currencyMap.put(valuta.getCurrency(), valuta.getValue() * valuta.getRate());
            }
            tableData.put(date, currencyMap);
        }
        return tableData;
    }

    private static MNB getMnb(Element dateElement) {
        LocalDate date = LocalDate.parse(dateElement.getAttribute("date"));
        NodeList currencyList = dateElement.getElementsByTagName("Rate");
        List<Valuta> valuták = new ArrayList<>();
        for(int j = 0; j < currencyList.getLength(); j++) {
            Node currencyNode = currencyList.item(j);
            Element currencyElement = (Element) currencyNode;
            Integer rate = Integer.parseInt(currencyElement.getAttribute("unit"));
            String curr = currencyElement.getAttribute("curr");
            Float value = Float.parseFloat(currencyElement.getTextContent().trim().replace(',', '.'));
            Valuta valuta = new Valuta(value, curr,rate);
            valuták.add(valuta);
        }
        MNB data = new MNB(valuták, date);
        return data;
    }


}
