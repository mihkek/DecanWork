package com.company;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

class framePage
{
    public  JTable tbl;
    public  JScrollPane scroll;
    public  JPanel panel;
    public  framePage(BaseDbTable model)
    {
        panel = new JPanel();
            panel.setLayout(new GridLayout(1, 1, 1, 1));
        tbl = new JTable(model);
        tbl.setFont(new Font("Verdana", Font.PLAIN, 14));
        panel.add(tbl,BorderLayout.CENTER);
        scroll = new JScrollPane(tbl);
        panel.add(scroll);
        panel.setVisible(true);
    }
    public  void addComponentsToFrame(JFrame frame)
    {
        frame.add(panel);
        panel.setVisible(true);
    }
    public  void removeComponentsFromFrame(JFrame frame)
    {
        panel.setVisible(false);
        frame.remove(panel);
    }
    public  BaseDbTable getModel()
    {
        return  (BaseDbTable)tbl.getModel();
    }
    public void updateUi()
    {
        tbl.updateUI();
        panel.updateUI();
    }
    public  int getSelectedRow()
    {
        return  tbl.getSelectedRow();
    }
}
public class ModFrame extends JFrame {

    static int i = 0;
    framePage pageDecan;
    framePage pageGroup;
    framePage pageSemestr;
    framePage pageKafedra;
    framePage pageEkzam;
    framePage pageStudent;
    framePage pageEkzst;
    framePage pageSearch;
    framePage currentPage;


    public ModFrame() {
        super("Деканат");
        if(Main.DBWorking.hasConnection)
           init();
        else
        {
            JOptionPane.showMessageDialog(this, "Не удалось подключится к базе данных. Проверьте параметры подключения", "Ошибка", JOptionPane.ERROR_MESSAGE);
            actionDBParams();
            if(Main.DBWorking.hasConnection)
                init();
        }
    }
    public  void init()
    {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pageEkzst = new framePage(new EkzstTable());
        pageStudent = new framePage(new StudentTable());
        pageEkzam = new framePage(new EkzamTable());
        pageDecan = new framePage( new DecanModel());
        pageGroup = new framePage(new GroupModel());
        pageSemestr = new framePage(new SemestrTable());
        pageKafedra = new framePage(new KafedraTable());
        pageSearch = new framePage((new StudentTable()));
        pageDecan.addComponentsToFrame(this);
        createActionsPanel();
        createMenu();
        setPreferredSize(new Dimension(800, 700));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        currentPage = pageDecan;
    }
    public  void createActionsPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3, 6, 6));
        JButton btn = new JButton("Добавить");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionAdd();
            }
        });
        panel.add(btn, BorderLayout.EAST);
        JButton butEdit = new JButton("Изменить");
        butEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actionEdit();
            }
        });
        panel.add(butEdit, BorderLayout.NORTH);
        JButton butDel = new JButton("Удалить");
        butDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actionDelete();
            }
        });
        panel.add(butDel, BorderLayout.NORTH);
        add(panel, BorderLayout.SOUTH);
    }
    private JMenuItem addMenuItem(String name, JMenu menu, Font font)
    {
        JMenuItem newMenu = new JMenuItem(name);
        newMenu.setFont(font);
        menu.add(newMenu);
        return  newMenu;
    }

    private  JMenu addMenu(String name, Font font)
    {
        JMenu menu = new JMenu(name);
        menu.setFont(font);
        return  menu;
    }
    private  void setCurrentPage(framePage active)
    {
        pageGroup.removeComponentsFromFrame(this);
        pageSemestr.removeComponentsFromFrame(this);
        pageDecan.removeComponentsFromFrame(this);
        pageKafedra.removeComponentsFromFrame(this);
        pageEkzam.removeComponentsFromFrame(this);
        pageStudent.removeComponentsFromFrame(this);
        pageEkzst.removeComponentsFromFrame(this);
        pageSearch.removeComponentsFromFrame(this);
        active.addComponentsToFrame(this);
        currentPage = active;
    }
    public  void actionDBParams()
    {
        DBSettingsWindow frm = new DBSettingsWindow(this);
        frm.setVisible(true);

    }

    public  void actionDecan()
    {
        setCurrentPage(pageDecan);
    }
    public  void actionGroup() { setCurrentPage(pageGroup); }
    public  void actionSemestr()
    {
      setCurrentPage(pageSemestr);
    }
    public  void actionKafedra()
    {
        setCurrentPage(pageKafedra);
    }
    public  void actionEkzam(){setCurrentPage(pageEkzam);}
    public  void  actionStudent(){setCurrentPage(pageStudent);}
    public  void  actionEkzst(){setCurrentPage(pageEkzst);}
    public  void actionAdd()
    {
        InputForm frm = new InputForm(this, currentPage.getModel().generateInputFields(-1),
                currentPage.getModel().constructTableRow(), currentPage.getModel().generateLookUpFields(-1), InputForm.AddEdit);
        frm.setVisible(true);
        if(frm.hasResult) {
            currentPage.getModel().writeData(frm.result);
            currentPage.updateUi();
        }
    }
    public  void actionEdit()
    {
        BaseDbTable model = currentPage.getModel();
        int id = currentPage.getSelectedRow();
        InputForm frm = new InputForm(this,
                currentPage.getModel().generateInputFields(id), model.records.get(id),
                currentPage.getModel().generateLookUpFields(id), InputForm.AddEdit);//Integer.parseInt(model.records.get(currentPage.getSelectedRow()).getRoleValue(0))));
        frm.setVisible(true);
        if(frm.hasResult) {
           currentPage.getModel().updateRow(id, frm.result);
            currentPage.updateUi();
        }
    }
    public void actionDelete()
    {
        BaseDbTable model = currentPage.getModel();
        int res = JOptionPane.showConfirmDialog(this, "Вы действительно хотите удалить запись?", "Подтверждение",  JOptionPane.YES_NO_OPTION);
        if(res == 0)
        {
            model.deleteRow(currentPage.getSelectedRow());
            currentPage.updateUi();
        }
    }
    public void actionSearchStudent()
    {
        pageSearch = new framePage(new StudentTable());
        runSearch();
    }
    public  void actionSearchGroup()
    {
        pageSearch = new framePage(new GroupModel());
        runSearch();
    }
    private  void runSearch()
    {
        setCurrentPage(pageSearch);
        pageSearch.getModel().clearData();
        pageSearch.updateUi();

        InputForm frm = new InputForm(this, pageSearch.getModel().generateInputFields(-1),
                pageSearch.getModel().constructTableRow(), pageSearch.getModel().generateLookUpFields(-1), InputForm.Search);
        frm.setVisible(true);
        if(frm.hasResult) {
            boolean notVoid = false;
            for(int i =0;i<frm.checkedConditions.size();i++)
            {
                if(frm.checkedConditions.get(i))
                    notVoid = true;
            }
            if(!notVoid)
            {
                JOptionPane.showMessageDialog(this, "Не выбрано ни одно ключевое поле", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            pageSearch.getModel().search(frm.result, frm.checkedConditions);
            pageSearch.updateUi();
        }
    }





    public  void createMenu()
    {
        JMenuBar menuBar = new JMenuBar();
        Font font = new Font("Verdana", Font.PLAIN, 14);

        JMenu fileMenu = addMenu("Деканат", font);
         addMenuItem("Деканаты", fileMenu, font).addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 actionDecan();
             }
         });
        addMenuItem("Кафедры", fileMenu, font).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actionKafedra();
            }
        });
        addMenuItem("Группы", fileMenu, font).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionGroup();
            }
        });;
        addMenuItem("Студенты", fileMenu, font).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionStudent();
            }
        });;
        addMenuItem("Семестр", fileMenu, font).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionSemestr();
            }
        });
        addMenuItem("Экзамены", fileMenu, font).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionEkzam();
            }
        });
        addMenuItem("Успеваемость", fileMenu, font).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionEkzst();
            }
        });
        menuBar.add(fileMenu);
        JMenu dbMenu = addMenu("База данных", font);
        addMenuItem("Обновить соединения", dbMenu, font);
        addMenuItem("Изменить параметры соединения", dbMenu, font).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actionDBParams();
            }
        });

        JMenu searchMenu = addMenu("Поиск", font);
        addMenuItem("Поиск студента", searchMenu, font).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionSearchStudent();
            }
        });;
        addMenuItem("Поиск группы", searchMenu, font).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionSearchGroup();
            }
        });;

        menuBar.add(fileMenu);
        menuBar.add(dbMenu);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);
    }
}