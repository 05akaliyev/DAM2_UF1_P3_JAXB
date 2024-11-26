// Employee.java
package model;

public class Employee extends Person {
    private int employeeId;
    private String password;

    // Constructors
    public Employee(String name) {
        super(name);
    }

    public Employee(int employeeId, String name, String password) {
        super(name);
        this.employeeId = employeeId;
        this.password = password;
    }

    public Employee() {
        super();
    }

    /**
     * @return the employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Simplified login method without database authentication.
     *
     * @param user     from application
     * @param password from application
     * @return true if credentials are correct or false if not
     */
    public boolean login(int user, String password) {
        // Hardcoded credentials
        int hardcodedUserId = 123;
        String hardcodedPassword = "test";

        if (user == hardcodedUserId && password.equals(hardcodedPassword)) {
            this.employeeId = user;
            this.password = password;
            this.setName("Default Employee"); // Set a default name
            return true;
        } else {
            return false;
        }
    }
}
