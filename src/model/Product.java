package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "product")
public class Product {

    // Fields
    @XmlAttribute
    private int id;

    @XmlAttribute
    private String name;

    @XmlElement
    private Boolean available;

    @XmlElement
    private Amount wholesalerPrice;

    @XmlElement
    private Amount publicPrice;

    @XmlElement
    private int stock;

    // Static variable for generating IDs
    private static int totalProducts = 0;

    // No-arg constructor for JAXB
    public Product() {
    }

    // Constructor
    public Product(String name, Amount wholesalerPrice, Boolean available, int stock) {
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.available = available;
        this.stock = stock;
        this.publicPrice = new Amount(wholesalerPrice.getValue() * 2);
        initializeProduct();
    }

    // Initialize missing fields after unmarshalling or when creating new products
    public void initializeProduct() {
        // Assign ID
        this.id = ++totalProducts;

        // Set available to true if null
        if (this.available == null) {
            this.available = true;
        }

        // Calculate publicPrice if not set
        if (this.publicPrice == null && this.wholesalerPrice != null) {
            double wholesalerValue = this.wholesalerPrice.getValue();
            this.publicPrice = new Amount(wholesalerValue * 2);
        }
    }

    // Add the expire() method
    public void expire() {
        // Logic for expiring a product
        // For example, reduce the public price by 50%
        if (publicPrice != null) {
            double newPrice = publicPrice.getValue() * 0.5; // Reduce price by 50%
            publicPrice.setValue(newPrice);
        }
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    // No setter for id to prevent changing it after assignment

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Amount getWholesalerPrice() {
        return wholesalerPrice;
    }

    public void setWholesalerPrice(Amount wholesalerPrice) {
        this.wholesalerPrice = wholesalerPrice;
    }

    public Amount getPublicPrice() {
        return publicPrice;
    }

    public void setPublicPrice(Amount publicPrice) {
        this.publicPrice = publicPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // toString method
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", available=" + available +
               ", wholesalerPrice=" + wholesalerPrice + ", publicPrice=" + publicPrice +
               ", stock=" + stock + "]";
    }
}
