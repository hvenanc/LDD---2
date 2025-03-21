package com.prova22;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Dom {

    private Document doc;
    private List<String> codesList = new ArrayList<>();
    private Map<String, Long> codeCount = new HashMap<>();

    public Dom(String path) {

        File xml = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xml);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(com.DomCompleto.Dom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getCodes() {

        NodeList codes = doc.getElementsByTagName("productCode");
        for (int i = 0; i < codes.getLength(); i++) {
            Element code = (Element) codes.item(i);
            codesList.add(code.getTextContent());
        }
    }

    public Map<String, Long> groupData() {

        codeCount = codesList.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return codeCount;
    }

    public void writeHtml(String filename) {

        Map<String, Long> data = groupData();
        List<Map.Entry<String, Long>> listOrderd = new ArrayList<>(data.entrySet());
        listOrderd.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            Element olElement = document.createElement("ol");
            document.appendChild(olElement);

            for(Map.Entry<String, Long> entry : listOrderd) {
                Element liElement = document.createElement("li");
                liElement.setTextContent(entry.getKey() + " (" + entry.getValue() + ")");
                olElement.appendChild(liElement);
            }

            // 4. Serializar o DOM em arquivo (ou console)
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Definir origem e destino
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filename));

            // Gravar no arquivo
            transformer.transform(source, result);

            System.out.println("Arquivo HTML (DOM) gerado com sucesso!");


        } catch (ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Dom dom = new Dom("src/main/orderdetails.xml");
        dom.getCodes();
        dom.groupData();
        dom.writeHtml("Lista.html");
    }

}
