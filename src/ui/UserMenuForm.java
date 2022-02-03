package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserMenuForm extends JFrame{
    private JPanel mainPanel;
    private JLabel userMenu_label;
    private JPanel menuPanel;
    private JButton view_button;
    private JButton search_button;
    private JButton sql_button;
    private JButton goToSign_button;
    private JLabel player1_label;
    private JLabel player2_label;

    public UserMenuForm(String windowTitle) {
        super(windowTitle);
        this.setContentPane(mainPanel);
        player1_label.setIcon(new ImageIcon("C:\\ForPictures\\player1.jpg"));
        player2_label.setIcon(new ImageIcon("C:\\ForPictures\\player2.jpg"));
        this.setBounds(550, 300, 400, 290);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        search_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SearchingForm("Поиск в базе данных");
            }
        });
        sql_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AllStatisticsForm("Статистика лиги");
            }
        });

        this.setVisible(true);
    }
}
