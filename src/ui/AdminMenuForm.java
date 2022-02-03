package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminMenuForm extends JFrame{
    private JPanel mainPanel;
    private JButton update_button;
    private JButton goToSign_button;
    private JLabel adminMenu_label;
    private JPanel menuPanel;
    private JButton searching_button;
    private JButton view_button;
    private JLabel player4_label;
    private JLabel player3_label;

    public AdminMenuForm(String windowTitle) {
        super(windowTitle);
        this.setContentPane(mainPanel);
        this.setBounds(550, 300, 380, 290);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        player3_label.setIcon(new ImageIcon("C:\\ForPictures\\player3.jpg"));
        player4_label.setIcon(new ImageIcon("C:\\ForPictures\\player4.jpg"));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UIManager.put("OptionPane.yesButtonText", "Да"    );
                UIManager.put("OptionPane.noButtonText", "Нет"   );
                int result = JOptionPane.showConfirmDialog(
                        getContentPane(),
                        "Выйти из системы?",
                        "Окно подтверждения",
                        JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    dispose();
                    new SignInForm("Вход в \"Football Statistics\"");
                }
            }
        });
        goToSign_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignInForm("Вход в \"Football Statistics\"");
            }
        });
        view_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewForm("Просмотр базы данных");
            }
        });
        searching_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SearchingForm("Поиск в базе данных");
            }
        });
        update_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UpdatingForm("Обновление базы данных");
            }
        });
        this.setVisible(true);
    }
}
