package com.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Sax1 extends DefaultHandler {

    //Qual o nome dos livros que possuem mais de um autor?

    private boolean isTitle = false;
    private String currentTitle;
    private int authorCount = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("book")) {
            authorCount = 0;
        } else if (qName.equals("title")) {
            isTitle = true;
        } else if (qName.equals("author")) {
            authorCount++;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isTitle) {
            currentTitle = new String(ch, start, length);
            isTitle = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("book") && authorCount > 1) {
            System.out.println(currentTitle);
        }
    }

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            Sax1 sax = new Sax1();
            saxParser.parse(new File("src/main/bibliography.xml"), sax);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
