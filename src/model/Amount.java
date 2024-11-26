package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Amount {

    @XmlValue
    private double value;

    @XmlAttribute
    private String currency;

    // No-arg constructor for JAXB
    public Amount() {
        this.currency = "€";
    }

    public Amount(double value) {
        this.value = value;
        this.currency = "€";
    }

    // Getters and Setters

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // toString method
    @Override
    public String toString() {
        return value + currency;
    }
}
