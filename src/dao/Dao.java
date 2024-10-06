package dao;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import model.Product;
import model.Employee;

public interface Dao {
    
    public void connect();

    public void disconnect();

    public Employee getEmployee(int employeeId, String password);

    // New method to retrieve inventory
    public List<Product> getInventory();

    // New method to write inventory
    public boolean writeInventory(List<Product> inventory);
}
