package com.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Dom10 {

    public static void main(String[] args) {

       int count = 0;

        try {

            File xml = new File("src/main/bibliography.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            document.getDocumentElement().normalize();

            NodeList bookList = document.getElementsByTagName("book");
            System.out.println("Quantos livros ‘Abraham Silberschatz’ publicou em 2012? ");
            for(int i = 0; i<= bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if(bookNode != null) {
                    if(bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNode;
                        NodeList authorList = bookElement.getElementsByTagName("author");
                        NodeList yearList = bookElement.getElementsByTagName("year");
                        String year = yearList.item(0).getTextContent();
                        String author = authorList.item(0).getTextContent();
                        if(author.equalsIgnoreCase("Abraham Silberschatz")
                                && year.equalsIgnoreCase("2012"))
                        {
                            count++;
                        }
                    }
                }
            }
            System.out.println(count);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
