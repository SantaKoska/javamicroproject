import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class RegisterFrame extends Frame implements ActionListener {
    private TextField nameField;
    private TextField passwordField;
    private TextField confirmPasswordField;
    private Button registerButton;

    public RegisterFrame() {
        setLayout(new FlowLayout());

        add(new Label("Name:"));
        nameField = new TextField(20);
        add(nameField);

        add(new Label("Password:"));
        passwordField = new TextField(20);
        passwordField.setEchoChar('*');
        add(passwordField);

        add(new Label("Confirm Password:"));
        confirmPasswordField = new TextField(20);
        confirmPasswordField.setEchoChar('*');
        add(confirmPasswordField);

        registerButton = new Button("Register");
        registerButton.addActionListener(this);
        add(registerButton);

        setSize(300, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Password Don't match");
            return;
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);

        UserDao userDao = new UserDao();
        if (userDao.registerUser(user)) {
            JOptionPane.showMessageDialog(this, "Registartion Succefull");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registation failed","error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
