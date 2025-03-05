package com.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Dom9 {

    public double getAvg(NodeList list, String attribute) {

        double price = 0;
        int count = 0;

        for(int i = 0; i<= list.getLength(); i++) {
            Node bookNode = list.item(i);
            if(bookNode != null) {
                if(bookNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) bookNode;
                    NodeList titleList = bookElement.getElementsByTagName("title");
                    for (int j = 0; j < titleList.getLength(); j++) {
                        Node titleNode = titleList.item(j);
                        if(titleNode != null) {
                            if(titleNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element titleElement = (Element) titleNode;
                                if(titleElement.getAttribute("lang").equals(attribute)) {
                                    String priceSrt = bookElement.getElementsByTagName("price").item(j).getTextContent();
                                    price += Double.parseDouble(priceSrt);
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return  price/count;
    }

    public static void main(String[] args) {

        try {

            File xml = new File("src/main/bibliography.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            document.getDocumentElement().normalize();

            NodeList bookList = document.getElementsByTagName("book");
            System.out.println("Quais os nomes dos livros em português? ");

            Dom9 dom = new Dom9();
            double pricePt = dom.getAvg(bookList, "pt-br");
            double priceEn = dom.getAvg(bookList, "en");
            if(pricePt > priceEn) {
                System.out.println("A média dos livros em Português é maior! R$" + pricePt);
            }
            else {
                System.out.println(priceEn);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
