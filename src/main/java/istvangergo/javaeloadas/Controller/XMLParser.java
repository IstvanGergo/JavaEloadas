package istvangergo.javaeloadas.Controller;

import istvangergo.javaeloadas.Model.MNB;
import istvangergo.javaeloadas.Model.Valuta;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class XMLParser {

    public static List<MNB> parseAll(String input) throws Exception {
        List<MNB> list = new ArrayList<MNB>();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(input));
        Document document = builder.parse(is);

        NodeList Dates = document.getElementsByTagName("Date");
        for (int i = 0; i < Dates.getLength(); i++) {
            Node dateNode = Dates.item(i);
            Element dateElement = (Element) dateNode;
            LocalDate date = LocalDate.parse(dateElement.getAttribute("Date"));
            NodeList currencyList = dateElement.getElementsByTagName("Rate");
            List<Valuta> valuták = new ArrayList<>();
            for(int j = 0; j < currencyList.getLength(); j++) {
                Node currencyNode = currencyList.item(j);
                Element currencyElement = (Element) currencyNode;
                Currency curr = Currency.getInstance(currencyElement.getAttribute("curr"));
                Integer rate = Integer.parseInt(currencyElement.getAttribute("unit"));
                Float value = Float.parseFloat(currencyElement.getTextContent());
                Valuta valuta = new Valuta(value, curr,rate);
                valuták.add(valuta);
            }
            MNB data = new MNB(valuták, date);
            list.add(data);
        }
        return list;
    }


}
