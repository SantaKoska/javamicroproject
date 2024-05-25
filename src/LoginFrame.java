import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class LoginFrame extends Frame implements ActionListener {
    private TextField nameField;
    private TextField passwordField;
    private Button loginButton;
    private Button registerButton;

    public LoginFrame() {
        setLayout(new FlowLayout());

        add(new Label("Name:"));
        nameField = new TextField(20);
        add(nameField);

        add(new Label("Password:"));
        passwordField = new TextField(20);
        passwordField.setEchoChar('*');
        add(passwordField);

        loginButton = new Button("Login");
        loginButton.addActionListener(this);
        add(loginButton);

        registerButton = new Button("Register");
        registerButton.addActionListener(this);
        add(registerButton);

        setSize(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String name = nameField.getText();
            String password = passwordField.getText();
            UserDao userDao = new UserDao();
            User user = userDao.loginUser(name, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new MainFrame(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Inavlid User name or password");
            }
        } else if (e.getSource() == registerButton) {
            new RegisterFrame();
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
