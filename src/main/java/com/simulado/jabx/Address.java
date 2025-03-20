package com.simulado.jabx;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Address {

    @XmlAttribute(name = "id")
    private int id;
    @XmlAttribute(name = "a1")
    private String a1;
    @XmlAttribute(name = "a2")
    private String a2;
    @XmlAttribute(name = "district")
    private String district;
    @XmlAttribute(name = "city_id")
    private int city_id;
    @XmlAttribute(name = "postal_code")
    private String postal_code;
    @XmlAttribute(name = "phone")
    private String phone;

    public Address() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", a1='" + a1 + '\'' +
                ", a2='" + a2 + '\'' +
                ", district='" + district + '\'' +
                ", city_id=" + city_id +
                ", postal_code='" + postal_code + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
