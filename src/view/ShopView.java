// ShopView.java
package view;

import main.Shop;
import utils.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShopView extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private Shop shop;

    private JPanel contentPane;
    private JButton btnExportInventory;
    private JButton btnShowCash;
    private JButton btnAddProduct;
    private JButton btnAddStock;
    private JButton btnRemoveProduct;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ShopView frame = new ShopView();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ShopView() {
        setTitle("MiTienda.com - Menu principal");
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        shop = new Shop();
        shop.loadInventory();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblShowCash = new JLabel("Seleccione o pulse una opción:");
        lblShowCash.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblShowCash.setBounds(57, 20, 236, 14);
        contentPane.add(lblShowCash);

        // Option 0 - Export Inventory
        btnExportInventory = new JButton("0. Exportar inventario");
        btnExportInventory.setHorizontalAlignment(SwingConstants.LEFT);
        btnExportInventory.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnExportInventory.setBounds(99, 240, 236, 40);
        contentPane.add(btnExportInventory);
        btnExportInventory.addActionListener(this);

        // Option 1 - Show Cash
        btnShowCash = new JButton("1. Contar caja");
        btnShowCash.setHorizontalAlignment(SwingConstants.LEFT);
        btnShowCash.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnShowCash.setBounds(99, 40, 236, 40);
        contentPane.add(btnShowCash);
        btnShowCash.addActionListener(this);

        // Option 2 - Add Product
        btnAddProduct = new JButton("2. Añadir producto");
        btnAddProduct.setHorizontalAlignment(SwingConstants.LEFT);
        btnAddProduct.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnAddProduct.setBounds(99, 90, 236, 40);
        contentPane.add(btnAddProduct);
        btnAddProduct.addActionListener(this);

        // Option 3 - Add Stock
        btnAddStock = new JButton("3. Añadir stock");
        btnAddStock.setHorizontalAlignment(SwingConstants.LEFT);
        btnAddStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnAddStock.setBounds(99, 140, 236, 40);
        contentPane.add(btnAddStock);
        btnAddStock.addActionListener(this);

        // Option 9 - Remove Product
        btnRemoveProduct = new JButton("9. Eliminar producto");
        btnRemoveProduct.setHorizontalAlignment(SwingConstants.LEFT);
        btnRemoveProduct.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnRemoveProduct.setBounds(99, 190, 236, 40);
        contentPane.add(btnRemoveProduct);
        btnRemoveProduct.addActionListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == '0') {
            boolean success = shop.writeInventory();
            if (success) {
                JOptionPane.showMessageDialog(null, "Inventario exportado correctamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al exportar el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getKeyChar() == '1') {
            openCashView();
        }
        if (e.getKeyChar() == '2') {
            openProductView(Constants.OPTION_ADD_PRODUCT);
        }
        if (e.getKeyChar() == '3') {
            openProductView(Constants.OPTION_ADD_STOCK);
        }
        if (e.getKeyChar() == '9') {
            openProductView(Constants.OPTION_REMOVE_PRODUCT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnExportInventory) {
            boolean success = shop.writeInventory();
            if (success) {
                JOptionPane.showMessageDialog(null, "Inventario exportado correctamente.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al exportar el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == btnShowCash) {
            openCashView();
        }
        if (e.getSource() == btnAddProduct) {
            openProductView(Constants.OPTION_ADD_PRODUCT);
        }
        if (e.getSource() == btnAddStock) {
            openProductView(Constants.OPTION_ADD_STOCK);
        }
        if (e.getSource() == btnRemoveProduct) {
            openProductView(Constants.OPTION_REMOVE_PRODUCT);
        }
    }

    public void openCashView() {
        CashView dialog = new CashView(shop);
        dialog.setSize(400, 400);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    public void openProductView(int option) {
        ProductView dialog = new ProductView(shop, option);
        dialog.setSize(400, 400);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
