package com.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Sax4 extends DefaultHandler {

//    Quantos livros a partir de 2010 possuem preço maior que 150?
    private boolean isBook = false;
    private boolean isYear = false;
    private boolean isPrice = false;
    private boolean validYear = false;
    private int year = 0;
    private double price = 0.0;
    private int count = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("book")) {
            year = 0;
            price = 0.0;
        }
        if(qName.equals("year")) {
            isYear = true;
        }
        if(qName.equals("price")) {
            isPrice = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(isYear) {
            year = Integer.parseInt(new String(ch, start, length));
            validYear = year >= 2010;
        }

        if(isPrice) {
            price = Double.parseDouble(new String(ch, start, length));
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if(qName.equals("book")) {
            if(year >= 2010 && price >= 150) {
                count++;
                System.out.println("Livro encontrado -> Ano: " + year + ", Preço: " + price);
            }
        }
        if(qName.equals("year")) {
            isYear = false;
        }
        if(qName.equals("price")) {
            isPrice = false;
        }
    }

    public void getBooks() {
        System.out.println("Livros: " + count);
    }

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            Sax4 sax = new Sax4();
            sax.getBooks();
            saxParser.parse(new File("src/main/bibliography.xml"), sax);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

