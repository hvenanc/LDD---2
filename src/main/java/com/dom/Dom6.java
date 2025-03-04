package com.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Dom6 {

    public static void main(String[] args) {

        Set<String> authors = new HashSet<>();

        try {

            File xml = new File("src/main/bibliography.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            document.getDocumentElement().normalize();

            NodeList bookList = document.getElementsByTagName("book");
            System.out.println("Quantos autores começam com a letra ‘A’? ");
            for(int i = 0; i<= bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if(bookNode != null) {
                    if(bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNode;
                        NodeList authorList = bookElement.getElementsByTagName("author");
                        String author = authorList.item(0).getTextContent();
                        if(author.startsWith("A")) {
                            authors.add(author);

                        }
                    }
                }
            }
            System.out.println(authors.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
