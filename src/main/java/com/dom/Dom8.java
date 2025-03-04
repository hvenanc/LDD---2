package com.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Dom8 {

    public static void main(String[] args) {

        try {

            File xml = new File("src/main/bibliography.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            document.getDocumentElement().normalize();

            NodeList bookList = document.getElementsByTagName("book");
            System.out.println("Quais os nomes dos livros em português? ");
            for(int i = 0; i<= bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if(bookNode != null) {
                    if(bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNode;
                        NodeList titleList = bookElement.getElementsByTagName("title");
                        for (int j = 0; j < titleList.getLength(); j++) {
                            Node titleNode = titleList.item(j);
                            if(titleNode != null) {
                                if(titleNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element titleElement = (Element) titleNode;
                                    if(titleElement.getAttribute("lang").equals("pt-br")) {
                                        System.out.println(titleElement.getTextContent());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
