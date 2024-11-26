package dao;

import java.util.List;
import dao.jaxb.JaxbMarshaller;
import dao.jaxb.JaxbUnMarshaller;
import model.Employee;
import model.Product;
import model.ProductList;

public class DaoImplJaxb implements Dao {

    private JaxbUnMarshaller unmarshaller;
    private JaxbMarshaller marshaller;

    public DaoImplJaxb() {
        this.unmarshaller = new JaxbUnMarshaller();
        this.marshaller = new JaxbMarshaller();
    }

    @Override
    public void connect() {
        // No connection needed for JAXB
    }

    @Override
    public void disconnect() {
        // No disconnection needed for JAXB
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        throw new UnsupportedOperationException("Not supported in DaoImplJaxb.");
    }

    @Override
    public List<Product> getInventory() {
        ProductList productList = unmarshaller.unmarshalProducts("jaxb/inputInventory.xml");
        return productList != null ? productList.getProducts() : null;
    }

    @Override
    public boolean writeInventory(List<Product> inventory) {
        ProductList productList = new ProductList();
        productList.setProducts(inventory);
        productList.setTotal(inventory.size());
        return marshaller.marshalProducts(productList);
    }
}
