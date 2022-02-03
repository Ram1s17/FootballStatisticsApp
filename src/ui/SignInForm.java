package ui;

import connection.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class SignInForm extends JFrame {
    private JPanel mainPanel;
    private JButton signIn_button;
    private JTextField name_textField;
    private JPasswordField passwordField;
    private JLabel name_label;
    private JLabel password_label;
    private JLabel status_label;
    private JPanel centralPanel;
    private JComboBox <String> status_comboBox;
    private JLabel image_label;
    private JLabel FB_label;

    private static boolean isAdmin = false;

    public SignInForm(String windowTitle)
    {
        super(windowTitle);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(480,300, 550, 220);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();
        comboBoxModel.addElement("Пользователь");
        comboBoxModel.addElement("Администратор");
        status_comboBox.setModel(comboBoxModel);
        image_label.setIcon(new ImageIcon("C:\\ForPictures\\ball.jpg"));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UIManager.put("OptionPane.yesButtonText", "Да"    );
                UIManager.put("OptionPane.noButtonText", "Нет"   );
                int result = JOptionPane.showConfirmDialog(
                        getContentPane(),
                        "Завершить работу приложения?",
                        "Окно подтверждения",
                        JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    try {
                        DatabaseConnection.getStatement().close();
                        DatabaseConnection.getConnection().close();
                        System.out.println("Успешное отсоединение от базы данных!");
                        System.exit(0);
                    } catch (SQLException throwables) {
                        System.out.println("Не удалось отсоединиться к базе данных!");
                        throwables.printStackTrace();
                    }
                }
            }
        });
        signIn_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status_comboBox.getSelectedItem() == "Пользователь")
                {
                    checkUser();
                }
                else if (status_comboBox.getSelectedItem() == "Администратор")
                {
                    checkAdmin();
                }
            }
        });
        this.setVisible(true);
    }

    private void checkUser()
    {
        String password = new String(passwordField.getPassword());
        if (name_textField.getText().equals("user") && password.equals("user")) {
            isAdmin = false;
            dispose();
            new UserMenuForm("Меню пользователя");
        }
        else if (name_textField.getText().isEmpty() || password.isEmpty())
            JOptionPane.showMessageDialog(null, "Введите имя/пароль пользователя!", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Неверное имя/пароль пользователя!", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);

    }

    private void checkAdmin()
    {
        String password = new String(passwordField.getPassword());
        if (name_textField.getText().equals("admin") && password.equals("admin")) {
            isAdmin = true;
            dispose();
            new AdminMenuForm("Меню администратора");
        }
        else if (name_textField.getText().isEmpty() || password.isEmpty())
            JOptionPane.showMessageDialog(null, "Введите имя/пароль администратора!", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "Неверное имя/пароль администратора!", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
    }

    public static boolean isAdmin() {
        return isAdmin;
    }
}
