package com.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Dom14 {

    public static void main(String[] args) {

        List<String> years = new ArrayList<>();

        try {

            File xml = new File("src/main/bibliography.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            document.getDocumentElement().normalize();

            NodeList bookList = document.getElementsByTagName("book");
            System.out.println("Quantos e quais são os livros publicados, agrupados por ano? ");
            for(int i = 0; i<= bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if(bookNode != null) {
                    if(bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNode;
                        NodeList yearList = bookElement.getElementsByTagName("year");
                        years.add(yearList.item(0).getTextContent());
                    }
                }
            }

            Map<String, Long> yearsGroup = years.stream()
                    .collect(Collectors.groupingBy(year -> year, Collectors.counting()));


            System.out.println(yearsGroup);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
