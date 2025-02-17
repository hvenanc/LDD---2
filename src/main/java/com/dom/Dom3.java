package com.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Dom3 {

    public static void main(String[] args) {

        int livroCount = 0;
        double prices = 0;

        try {

            File xml = new File("src/main/bibliography.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            document.getDocumentElement().normalize();

            NodeList bookList = document.getElementsByTagName("book");
            System.out.println("Qual a média de preços dos livros da categoria SO? ");
            for(int i = 0; i<= bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if(bookNode != null) {
                    if(bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNode;
                        if(bookElement.getAttribute("category").equals("SO")) {
                            String price = bookElement.getElementsByTagName("price").item(0).getTextContent();
                            prices += Double.parseDouble(price);
                            livroCount++;
                        }
                    }
                }
            }
            System.out.println(prices/livroCount);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
