package istvangergo.javaeloadas.Controller;

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
    public static Map<LocalDate, Map<String, Float>> parseAndTransform(File xmlFile) throws Exception {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        NodeList Dates = document.getElementsByTagName("day");
        return getTableData(Dates);
    }

    private static Map<LocalDate, Map<String, Float>> getTableData(NodeList nodes){
        Map<LocalDate, Map<String, Float>> tableData = new LinkedHashMap<>();
        for(int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            Element dateElement = (Element) node;
            LocalDate localDate = LocalDate.parse(dateElement.getAttribute("date"));
            NodeList currencyList = dateElement.getElementsByTagName("Rate");
            Map<String, Float> currencyMap = new HashMap<>();
            for( int j = 0; j < currencyList.getLength(); j++ ) {
                Node currencyNode = currencyList.item(j);
                Element currencyElement = (Element) currencyNode;
                Integer rate = Integer.parseInt(currencyElement.getAttribute("unit"));
                String curr = currencyElement.getAttribute("curr");
                Float value = Float.parseFloat(currencyElement.getTextContent().trim().replace(',','.'));
                currencyMap.put(curr, value * rate);
            }
            tableData.put(localDate, currencyMap);
        }
        return tableData;
    }
}
