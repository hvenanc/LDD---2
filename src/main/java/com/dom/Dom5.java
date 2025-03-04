package com.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Dom5 {

    public static void main(String[] args) {

        int livroCount = 0;

        try {

            File xml = new File("src/main/bibliography.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            document.getDocumentElement().normalize();

            NodeList bookList = document.getElementsByTagName("book");
            System.out.println("Quantos livros da categoria LP estão em inglês? ");
            for(int i = 0; i<= bookList.getLength(); i++) {
                Node bookNode = bookList.item(i);
                if(bookNode != null) {
                    if(bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) bookNode;
                        if(bookElement.getAttribute("category").equals("LP")) {
                            NodeList titleList = bookElement.getElementsByTagName("title");
                            for(int j = 0; j<= titleList.getLength(); j++) {
                                Node titleNode = titleList.item(j);
                                if(titleNode != null) {
                                    if(titleNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element titleElement = (Element) titleNode;
                                        if(titleElement.getAttribute("lang").equals("en")) {
                                            livroCount++;
                                        }
                                    }
                                }
                            }
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

