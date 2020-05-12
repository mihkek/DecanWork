package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

public class InputForm extends JDialog {
    public  BaseDbRow result;
    public  boolean hasResult = false;
    private ArrayList<JTextField> textFields;
    private  ArrayList<LookUpComboBox> lookUpFields;
    enum formMode
    {
        SEARCH,EDIT
    }
    //call - JDialog frm = new InputForm(this); frm.setVisible(true);
    public  InputForm(JFrame owner,  HashMap<String, JTextField> inputFields, BaseDbRow row, HashMap<String, Integer> lookUpFk)
    {
        super(owner, "Редактирование данных", true);
        int rowCount = inputFields.size();
        if(lookUpFk != null)
            rowCount += lookUpFk.size();
        textFields = new ArrayList<>();
        setSize(600, generateHeight(rowCount));
        JPanel panelFiels = new JPanel();
        panelFiels.setLayout(new GridLayout(rowCount, 2, 1, 1));

        Object[] keys =  inputFields.keySet().toArray();
        Object[] values = inputFields.values().toArray();
        for(int i =0;i<inputFields.size();i++)
        {
            panelFiels.add(new JLabel((String)keys[i]));
            panelFiels.add((JTextField)values[i]);
            textFields.add((JTextField)values[i]);
        }
        if(lookUpFk != null)
        {
            lookUpFields = new ArrayList<>();
            //format - tableName:::keyField:::ValueField:::header
             keys =  lookUpFk.keySet().toArray();
             values = lookUpFk.values().toArray();
             for(int i =0;i<keys.length;i++)
             {
                 String field = (String)keys[i];
                 String[] data = field.split(":::");
                 if(data.length != 4)
                     continue;
                 LookUpComboBox lookUp = new LookUpComboBox(data[0], data[1], data[2]);
                 lookUp.setSelectedIndex(values[i]);
                 lookUpFields.add(lookUp);
                 panelFiels.add(new JLabel(data[3]));
                 panelFiels.add(lookUp);
             }
        }
        add(panelFiels);
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 2, 1, 1));
        JButton  butAdd = new JButton("Сохранить");
        butAdd.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            saveClick();
        }
    });
        panelButtons.add(butAdd);
        JButton butCancel = new JButton("Отмена");
        butCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });
        panelButtons.add(butCancel);
        add(panelButtons, BorderLayout.SOUTH);
        result = row;
    }
    public  int generateHeight(int count)
    {
        int res = 0;
        for(int i =0;i<count;i++)
        {
            res += 80;
        }
        return  res;
    }

    public  void  saveClick()
    {
        ArrayList<Object> data= new ArrayList<>();
        for(int i =0;i<textFields.size();i++)
        {
            data.add(textFields.get(i).getText());

        }
        if(lookUpFields != null){
            for(int i=0;i<lookUpFields.size();i++)
            {
                data.add(lookUpFields.get(i).getKeyValue());
            }
        }
        result.buildFromList(data);
        hasResult = true;
        setVisible(false);
    }

}
