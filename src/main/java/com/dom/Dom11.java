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

public class Dom11 {

    public static void main(String[] args) {

        List<String> authors = new ArrayList<>();

        try {

            File xml = new File("src/main/bibliography.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            document.getDocumentElement().normalize();

            NodeList bookList = document.getElementsByTagName("book");
            System.out.println("Qual autor possui mais livros publicados? ");
            for(int i = 0; i<= bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if(bookNode != null) {
                    if(bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNode;
                        NodeList authorList = bookElement.getElementsByTagName("author");
                        authors.add(authorList.item(0).getTextContent());
                    }
                }
            }

            Map<String, Long> authorsFrequency = authors.stream()
                    .collect(Collectors.groupingBy(author -> author, Collectors.counting()));

            Long maxValue = Collections.max(authorsFrequency.values());

            List<String> topAuthors = authorsFrequency.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), maxValue))
                    .map(Map.Entry::getKey)
                    .toList();

            System.out.println(topAuthors);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
