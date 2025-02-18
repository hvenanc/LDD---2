package com.jabx.bibliography;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.List;

@XmlRootElement(name = "bibliography")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bibliography {

    private List<Book> book;

    public Bibliography() {
    }

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Bibliography{" +
                "book=" + book +
                '}';
    }

    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Bibliography.class);
        Unmarshaller um = context.createUnmarshaller();
        Bibliography b = (Bibliography) um.unmarshal(new File("src/main/bibliography.xml"));
        System.out.println(b);
    }
}
