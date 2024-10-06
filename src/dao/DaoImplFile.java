package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import model.Amount;
import model.Employee;

public class DaoImplFile implements Dao {
    
    @Override
    public void connect() {
    	
    }

    @Override
    public void disconnect() {

    }

    @Override
    public Employee getEmployee(int employeeId, String password) {

        return null;
    }

    @Override
    public List<Product> getInventory() {
        List<Product> inventory = new ArrayList<>();
        File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            while (line != null) {
                String[] sections = line.split(";");
                String name = "";
                double wholesalerPrice = 0.0;
                int stock = 0;

                for (int i = 0; i < sections.length; i++) {
                    String[] data = sections[i].split(":");

                    switch (i) {
                        case 0:
                            name = data[1];
                            break;
                        case 1:
                            wholesalerPrice = Double.parseDouble(data[1]);
                            break;
                        case 2:
                            stock = Integer.parseInt(data[1]);
                            break;
                    }
                }
                inventory.add(new Product(name, new Amount(wholesalerPrice), true, stock));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inventory;
    }

    // Implement writeInventory by writing to the file
    @Override
    public boolean writeInventory(List<Product> inventory) {
        String fileName = System.getProperty("user.dir") + File.separator + "files/outputInventory.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, false))) {
            for (Product product : inventory) {
                pw.println("Product:" + product.getName() + ";Wholesaler Price:" + product.getWholesalerPrice().getValue() + ";Stock:" + product.getStock());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
