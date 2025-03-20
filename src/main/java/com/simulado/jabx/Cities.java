package com.simulado.jabx;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.List;

@XmlRootElement(name = "cities")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cities {

    private List<City> city;

    public Cities() {
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Cities{" +
                "cities=" + city +
                '}';
    }

    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Cities.class);
        Unmarshaller um = context.createUnmarshaller();
        Cities c = (Cities) um.unmarshal(new File("src/main/city.xml"));
        System.out.println(c);
    }
}
