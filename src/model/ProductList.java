package model;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductList {

    @XmlAttribute
    private int total;

    @XmlElement(name = "product")
    private List<Product> products;

    // Getters and Setters

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
