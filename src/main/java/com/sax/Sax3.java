package com.sax;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Sax3 extends DefaultHandler {

    //(c) Qual a média de preços dos livros da categoria SO?
    private List<Double> priceBooks = new ArrayList<>();
    private boolean isSO = false;
    private Double price = 0.0;
    private boolean isPrice = false;
    private int count = 0;


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("book")) {
            var category = attributes.getValue("category");
            if(category.equals("SO")) {
                isSO = true;
            }
        }
        if(qName.equals("price") && isSO) {
            isPrice = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(isPrice) {
            String priceStr = new String(ch, start, length);
            price += Double.parseDouble(priceStr);
            count++;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("price")) {
            isPrice = false;
        }
        if(qName.equals("book")) {
            isSO = false;
        }
    }

    public void getAvg() {
        double avg = price/count;
        System.out.println("Média: " + avg);
    }

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            Sax3 sax = new Sax3();
            saxParser.parse(new File("src/main/bibliography.xml"), sax);
            sax.getAvg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
