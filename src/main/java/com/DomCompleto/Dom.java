package com.DomCompleto;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dom {

    private Document doc;

    public Dom(String path) {

        File xml = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xml);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Dom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void questaoA() {
        NodeList books = doc.getElementsByTagName("book");
        for (int i = 0; i < books.getLength(); i++) {
            Element book = (Element) books.item(i);
            int qtd = book.getElementsByTagName("author").getLength();
            if(qtd > 1) {
                System.out.println(book.getElementsByTagName("title")
                        .item(0).getTextContent());
            }
        }
    }

    public static void main(String[] args) {

        Dom dom = new Dom("src/main/bibliography.xml");
        dom.questaoA();
    }

}
