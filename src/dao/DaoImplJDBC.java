// DaoImplJDBC.java
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {
    Connection connection;

    @Override
    public void connect() {
        // Connection parameters
        String url = "jdbc:mysql://localhost:3306/shop";
        String user = "root";
        String pass = "Adisha0682";
        try {
            this.connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        Employee employee = null;
        String query = "SELECT * FROM employee WHERE employeeId = ? AND password = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, employeeId);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public List<Product> getInventory() {
        throw new UnsupportedOperationException("getInventory() is not supported in DaoImplJDBC.");
    }

    @Override
    public boolean writeInventory(List<Product> inventory) {
        throw new UnsupportedOperationException("writeInventory() is not supported in DaoImplJDBC.");
    }
}
