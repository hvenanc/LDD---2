package com.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Dom4 {

    public static void main(String[] args) {

        int livroCount = 0;

        try {

            File xml = new File("src/main/bibliography.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            document.getDocumentElement().normalize();

            NodeList bookList = document.getElementsByTagName("book");
            System.out.println("Quantos livros a partir de 2010 possuem preço maior que 150? ");
            for(int i = 0; i<= bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if(bookNode != null) {
                    if(bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNode;
                        String year = bookElement.getElementsByTagName("year").item(0).getTextContent();
                        int yearInt = Integer.parseInt(year);
                        String price = bookElement.getElementsByTagName("price").item(0).getTextContent();
                        Double priceDouble = Double.parseDouble(price);

                        if(yearInt >= 2010 && priceDouble >= 150) {
                            livroCount++;
                        }
                    }
                }
            }
            System.out.println(livroCount);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
