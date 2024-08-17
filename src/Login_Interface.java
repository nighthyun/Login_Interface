import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login_Interface {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;   //

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Login_Interface window = new Login_Interface();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                	System.out.println("Error:" + e.getMessage());
                }
            }
        });
    }

    public Login_Interface() {
        frame = new JFrame("Login");
        frame.setBounds(100, 100, 300, 170);     //(x,y,w,h)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        //---uesrname
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(10, 10, 80, 25);
        frame.getContentPane().add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(100, 10, 160, 25);
        frame.getContentPane().add(usernameField);
        
        //---password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(10, 40, 80, 25);
        frame.getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 40, 160, 25);
        frame.getContentPane().add(passwordField);
        
        //---login botton
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        frame.getContentPane().add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
                		"database=Java;" +
                		"user=admin;" +
                		"password=0000;" +
                		"encrypt=false;" ;

                try (Connection conn = DriverManager.getConnection(connectionUrl);) {
                    String sql = "SELECT * FROM Customers WHERE account = ? AND password = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);

                    ResultSet result = statement.executeQuery();

                    if (result.next()) {
                    	JOptionPane.showMessageDialog(frame, "Login successful!");
                    } else {
                    	JOptionPane.showMessageDialog(frame, "Login failed. Please try again.");
                    }
                } catch (SQLException ex) {
                	System.out.println("Error:" + ex.getMessage());
                }
            }
        });
    }
}
