// ProductView.java
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Product;
import model.Amount;
import utils.Constants;

public class ProductView extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Shop shop;
    private int option;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField textFieldName;
    private JTextField textFieldStock;
    private JTextField textFieldPrice;
    private final JPanel contentPanel = new JPanel();

    /**
     * Create the dialog.
     */
    public ProductView(Shop shop, int option) {
        this.shop = shop;
        this.option = option;

        // Main configuration dialog
        switch (option) {
            case Constants.OPTION_ADD_PRODUCT:
                setTitle("Añadir Producto");
                break;
            case Constants.OPTION_ADD_STOCK:
                setTitle("Añadir Stock");
                break;
            case Constants.OPTION_REMOVE_PRODUCT:
                setTitle("Eliminar Producto");
                break;
            default:
                break;
        }
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Name section
        JLabel lblName = new JLabel("Nombre producto:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblName.setBounds(33, 10, 150, 19);
        contentPanel.add(lblName);
        textFieldName = new JTextField();
        textFieldName.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldName.setBounds(200, 10, 200, 25);
        contentPanel.add(textFieldName);
        textFieldName.setColumns(10);

        // Stock section
        JLabel lblStock = new JLabel("Stock producto:");
        lblStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblStock.setBounds(33, 50, 150, 19);
        contentPanel.add(lblStock);
        textFieldStock = new JTextField();
        textFieldStock.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldStock.setBounds(200, 50, 200, 25);
        contentPanel.add(textFieldStock);
        textFieldStock.setColumns(10);
        if (option == Constants.OPTION_ADD_PRODUCT || option == Constants.OPTION_ADD_STOCK) {
            lblStock.setVisible(true);
            textFieldStock.setVisible(true);
        } else {
            lblStock.setVisible(false);
            textFieldStock.setVisible(false);
        }

        // Price section
        JLabel lblPrice = new JLabel("Precio mayorista:");
        lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPrice.setBounds(33, 90, 150, 19);
        contentPanel.add(lblPrice);
        textFieldPrice = new JTextField();
        textFieldPrice.setHorizontalAlignment(SwingConstants.RIGHT);
        textFieldPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldPrice.setBounds(200, 90, 200, 25);
        contentPanel.add(textFieldPrice);
        textFieldPrice.setColumns(10);
        if (option == Constants.OPTION_ADD_PRODUCT) {
            lblPrice.setVisible(true);
            textFieldPrice.setVisible(true);
        } else {
            lblPrice.setVisible(false);
            textFieldPrice.setVisible(false);
        }

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
                okButton.addActionListener(this);
            }
            {
                cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                cancelButton.addActionListener(this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            Product product;
            switch (this.option) {
                case Constants.OPTION_ADD_PRODUCT:
                    // Check product does not exist
                    product = shop.findProduct(textFieldName.getText());

                    if (product != null) {
                        JOptionPane.showMessageDialog(null, "Producto ya existe ", "Error",
                                JOptionPane.ERROR_MESSAGE);

                    } else {
                        try {
                            product = new Product(
                                    textFieldName.getText(),
                                    new Amount(Double.parseDouble(textFieldPrice.getText())),
                                    true,
                                    Integer.parseInt(textFieldStock.getText())
                            );
                            shop.addProduct(product);
                            JOptionPane.showMessageDialog(null, "Producto añadido ", "Información",
                                    JOptionPane.INFORMATION_MESSAGE);
                            // Release current screen
                            dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Precio o stock inválido.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    break;

                case Constants.OPTION_ADD_STOCK:
                    // Check product exists
                    product = shop.findProduct(textFieldName.getText());

                    if (product == null) {
                        JOptionPane.showMessageDialog(null, "Producto no existe ", "Error",
                                JOptionPane.ERROR_MESSAGE);

                    } else {
                        try {
                            int additionalStock = Integer.parseInt(textFieldStock.getText());
                            product.setStock(product.getStock() + additionalStock);
                            JOptionPane.showMessageDialog(null, "Stock actualizado ", "Información",
                                    JOptionPane.INFORMATION_MESSAGE);
                            // Release current screen
                            dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Stock inválido.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    break;

                case Constants.OPTION_REMOVE_PRODUCT:
                    // Check product exists
                    product = shop.findProduct(textFieldName.getText());

                    if (product == null) {
                        JOptionPane.showMessageDialog(null, "Producto no existe ", "Error",
                                JOptionPane.ERROR_MESSAGE);

                    } else {
                        shop.getInventory().remove(product);
                        JOptionPane.showMessageDialog(null, "Producto eliminado", "Información",
                                JOptionPane.INFORMATION_MESSAGE);
                        // Release current screen
                        dispose();
                    }

                    break;

                default:
                    break;
            }

        }

        if (e.getSource() == cancelButton) {
            // Release current screen
            dispose();
        }
    }
}
