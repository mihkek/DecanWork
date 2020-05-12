package com.company;

import com.mysql.cj.jdbc.admin.MiniAdmin;
import javafx.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBSettingsWindow extends JDialog {
    public  static  String dbName;
    public  static  String serverName;
    public  static  String user;
    public  static  String password;
    public  DBSettingsWindow(JFrame owner)
    {
        super(owner, "Параметры соединения с БД", true);
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(5, 2, 1, 1));
        setSize(600, 200);
        panelInput.add(new Label("Сервер"));
        JTextField serverName = new JTextField();
        serverName.setText(Main.DBWorking.serverName);
        panelInput.add(serverName);
        JTextField dbName = new JTextField();
        dbName.setText(Main.DBWorking.dbName);
        panelInput.add(new JLabel("База данных"));
        panelInput.add(dbName);

        JTextField userName = new JTextField();
        userName.setText(Main.DBWorking.userName);
        panelInput.add(new JLabel("Пользователь"));
        panelInput.add(userName);

        JTextField userPassword = new JTextField();
        userPassword.setText(Main.DBWorking.userPassword);
        panelInput.add(new JLabel("Пароль"));
        panelInput.add(userPassword);


        JCheckBox chbSave = new JCheckBox("Запомнить параметры");
        panelInput.add(chbSave);
        add(panelInput,BorderLayout.NORTH);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 2, 1, 1));

        JButton butConnect = new JButton("Соеденить");
        butConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                     Main.DBWorking.dbName = dbName.getText();
                Main.DBWorking.serverName = serverName.getText();
                Main.DBWorking.userPassword = userPassword.getText();
                Main.DBWorking.userName = userName.getText();
                Main.DBWorking.connect();
                if(Main.DBWorking.hasConnection)
                    setVisible(false);
                else
                {
                    JOptionPane.showMessageDialog(null, "Не удалось подключится к базе данных. Измените параметры соединения", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                if(chbSave.isSelected())
                    Main.DBWorking.writeData();
            }
        });

        JButton butCancel = new JButton("Закрыть");
        butCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!Main.DBWorking.hasConnection)
                {
                    System.exit(0);
                }
                setVisible(false);
            }
        });
        panelButtons.add(butConnect);
        panelButtons.add(butCancel);
        add(panelButtons, BorderLayout.SOUTH);



    }
}
