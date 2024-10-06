// Shop.java
package main;

import model.Product;
import model.Sale;
import model.Amount;
import model.Client;
import model.Employee;
import dao.Dao;
import dao.DaoImplFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {
    private Amount cash = new Amount(100.00);
    private List<Product> inventory;
    private ArrayList<Sale> sales;
    private Dao dao;

    final static double TAX_RATE = 1.04;

    public Shop() {
        // Initialize the DAO with DaoImplFile for file-based inventory management
        this.dao = new DaoImplFile();
        this.inventory = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    public Amount getCash() {
        return cash;
    }

    public void setCash(Amount cash) {
        this.cash = cash;
    }

    public List<Product> getInventory() {
        return inventory;
    }

    public void setInventory(List<Product> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Sale> sales) {
        this.sales = sales;
    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        // Load inventory from the file system using DaoImplFile
        shop.loadInventory();

        // Start employee session
        shop.initSession();

        Scanner scanner = new Scanner(System.in);
        int opcion;
        boolean exit = false;

        do {
            System.out.println("\n===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("0) Exportar inventario");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver venta total");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 0:
                    boolean success = shop.writeInventory();
                    if (success) {
                        System.out.println("Inventario exportado correctamente.");
                    } else {
                        System.out.println("Error al exportar el inventario.");
                    }
                    break;
                case 1:
                    shop.showCash();
                    break;
                case 2:
                    shop.addProduct();
                    break;
                case 3:
                    shop.addStock();
                    break;
                case 4:
                    shop.setExpired();
                    break;
                case 5:
                    shop.showInventory();
                    break;
                case 6:
                    shop.sale();
                    break;
                case 7:
                    shop.showSales();
                    break;
                case 8:
                    shop.showSalesAmount();
                    break;
                case 9:
                    shop.removeProduct();
                    break;
                case 10:
                    System.out.println("Cerrando programa ...");
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    private void initSession() {
        Employee employee = new Employee("test");
        boolean logged = false;

        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduzca numero de empleado: ");
            int employeeId = scanner.nextInt();
            System.out.println("Introduzca contraseña: ");
            String password = scanner.next();

            logged = employee.login(employeeId, password);
            if (logged) {
                System.out.println("Login correcto.");
            } else {
                System.out.println("Usuario o password incorrectos.");
            }
        } while (!logged);
    }

    public void loadInventory() {
        // Load inventory using DaoImplFile
        this.inventory = dao.getInventory();
        System.out.println("Inventario cargado correctamente.");
    }

    public boolean writeInventory() {
        // Write inventory using DaoImplFile
        return dao.writeInventory(inventory);
    }

    private void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    // Updated addProduct method to accept a Product object directly
    public void addProduct(Product product) {
        if (isInventoryFull()) {
            System.out.println("No se pueden añadir más productos.");
            return;
        }
        inventory.add(product);
        System.out.println("Producto añadido: " + product);
    }

    // Existing method to add a product via input (optional)
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        Product product = new Product(name, new Amount(wholesalerPrice), true, stock);
        inventory.add(product);
        System.out.println("Producto añadido: " + product);
    }

    public void removeProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            inventory.remove(product);
            System.out.println("Producto " + name + " eliminado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            System.out.print("Seleccione la cantidad a añadir: ");
            int stock = scanner.nextInt();
            product.setStock(product.getStock() + stock);
            System.out.println("Stock actualizado para " + product.getName() + ": " + product.getStock());
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("Producto marcado como caducado: " + product.getPublicPrice());
        }
    }

    public void showInventory() {
        System.out.println("Inventario actual:");
        for (Product product : inventory) {
            System.out.println(product);
        }
    }

    public void sale() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Realizar venta, escribir nombre cliente: ");
        String clientName = scanner.nextLine();
        Client client = new Client(clientName);

        ArrayList<Product> shoppingCart = new ArrayList<>();
        Amount totalAmount = new Amount(0.0);
        String productName = "";

        while (!productName.equals("0")) {
            System.out.println("Introduce el nombre del producto (0 para terminar): ");
            productName = scanner.nextLine();

            if (!productName.equals("0")) {
                Product product = findProduct(productName);
                if (product != null && product.isAvailable()) {
                    totalAmount.setValue(totalAmount.getValue() + product.getPublicPrice().getValue());
                    product.setStock(product.getStock() - 1);
                    shoppingCart.add(product);

                    if (product.getStock() == 0) {
                        product.setAvailable(false);
                    }
                    System.out.println("Producto añadido al carrito.");
                } else {
                    System.out.println("Producto no disponible.");
                }
            }
        }

        totalAmount.setValue(totalAmount.getValue() * TAX_RATE);
        System.out.println("Total venta: " + totalAmount);

        if (!client.pay(totalAmount)) {
            System.out.println("El cliente debe: " + client.getBalance());
        }

        Sale sale = new Sale(client, shoppingCart, totalAmount);
        sales.add(sale);
        cash.setValue(cash.getValue() + totalAmount.getValue());
    }

    public void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            System.out.println(sale);
        }
    }

    public void showSalesAmount() {
        Amount totalAmount = new Amount(0.0);
        for (Sale sale : sales) {
            totalAmount.setValue(totalAmount.getValue() + sale.getAmount().getValue());
        }
        System.out.println("Total ventas: " + totalAmount);
    }

    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    // Helper method to check if inventory is full
    public boolean isInventoryFull() {
        return inventory.size() >= 10;  // Assuming maximum inventory size is 10
    }
}
