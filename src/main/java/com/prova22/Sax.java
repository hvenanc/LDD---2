package com.prova22;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Sax extends DefaultHandler {

    Map<String, List<Double>> orders = new HashMap<>();
    String orderNumber = null;
    boolean isPrice = false;
    boolean isQtd = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase("order")) {
            orderNumber = attributes.getValue("number");
            orders.putIfAbsent(orderNumber, new ArrayList<>());
        } else if (qName.equalsIgnoreCase("priceEach")) {
            isPrice = true;
        }
        else if(qName.equalsIgnoreCase("quantityOrdered")) {
            isQtd = true;
        }
    }

    //Validar a contagem do total no momento da prova.
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(isPrice && isQtd && orderNumber != null) {
            String priceStr = new String(ch, start, length).trim();
            String qtdStr = new String(ch, start, length).trim();

            if(!priceStr.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceStr);
                    double qtd = Double.parseDouble(qtdStr);
                    orders.get(orderNumber).add(price * qtd);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
            isPrice = false;
            isQtd = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("order")) {
            orderNumber = null;
        }
    }

    public void result() {
        for (Map.Entry<String, List<Double>> entry : orders.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void gerarHtml1(String outputPath) {
        try {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream(outputPath), "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");

            writer.writeStartElement("h2");
            writer.writeCharacters("Tabela de Pedidos");
            writer.writeEndElement(); // </h2>

            writer.writeStartElement("table");
            writer.writeAttribute("border", "1");
            writer.writeAttribute("cellpadding", "5");

            // Cabeçalho da tabela
            writer.writeStartElement("tr");

            writer.writeStartElement("th");
            writer.writeCharacters("Order Number");
            writer.writeEndElement();

            writer.writeStartElement("th");
            writer.writeCharacters("Value");
            writer.writeEndElement();

            writer.writeEndElement();

            // Dados da tabela
            for (Map.Entry<String, List<Double>> entry : orders.entrySet()) {
                String orderId = entry.getKey();
                double total = entry.getValue().stream().mapToDouble(Double::doubleValue).sum();

                writer.writeStartElement("tr");

                writer.writeStartElement("td");
                writer.writeCharacters(orderId);
                writer.writeEndElement(); // </td>

                writer.writeStartElement("td");
                writer.writeCharacters(String.format("%.2f", total));
                writer.writeEndElement(); // </td>

                writer.writeEndElement(); // </tr>
            }

            writer.writeEndElement(); // </table>
            writer.writeEndDocument();

            writer.flush();
            writer.close();

            System.out.println("Arquivo HTML gerado com sucesso: " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gerarHtml2(String outputPath) {
        try {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream(outputPath), "UTF-8");

            // Formato numérico brasileiro
            NumberFormat formatoMoeda = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
            formatoMoeda.setMinimumFractionDigits(2);
            formatoMoeda.setMaximumFractionDigits(2);

            // Ordena os pedidos pela soma dos valores de forma decrescente
            List<Map.Entry<String, List<Double>>> pedidosOrdenados = orders.entrySet()
                    .stream()
                    .sorted((e1, e2) -> {
                        double total1 = e1.getValue().stream().mapToDouble(Double::doubleValue).sum();
                        double total2 = e2.getValue().stream().mapToDouble(Double::doubleValue).sum();
                        return Double.compare(total2, total1); // decrescente
                    })
                    .collect(Collectors.toList());

            writer.writeStartDocument("UTF-8", "1.0");

            writer.writeStartElement("h2");
            writer.writeCharacters("Tabela de Pedidos");
            writer.writeEndElement(); // </h2>

            writer.writeStartElement("table");
            writer.writeAttribute("border", "1");

            // Cabeçalho
            writer.writeStartElement("tr");

            writer.writeStartElement("th");
            writer.writeCharacters("Order Number");
            writer.writeEndElement();

            writer.writeStartElement("th");
            writer.writeCharacters("Value");
            writer.writeEndElement();

            writer.writeEndElement(); // </tr>

            // Conteúdo
            for (Map.Entry<String, List<Double>> entry : pedidosOrdenados) {
                String orderId = entry.getKey();
                double total = entry.getValue().stream().mapToDouble(Double::doubleValue).sum();
                String totalFormatado = formatoMoeda.format(total);

                writer.writeStartElement("tr");

                writer.writeStartElement("td");
                writer.writeCharacters(orderId);
                writer.writeEndElement();

                writer.writeStartElement("td");
                writer.writeCharacters(totalFormatado);
                writer.writeEndElement();

                writer.writeEndElement(); // </tr>
            }

            writer.writeEndElement(); // </table>
            writer.writeEndDocument();

            writer.flush();
            writer.close();

            System.out.println("Arquivo HTML gerado com sucesso: " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            Sax sax = new Sax();
            saxParser.parse(new File("src/main/orderdetails.xml"), sax);
            sax.result();
            sax.gerarHtml2("saida.html");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
