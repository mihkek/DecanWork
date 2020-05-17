package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

public class InputForm extends JDialog {
    public  static  final  int AddEdit = 1;
    public  static final  int Search =2 ;
    public  BaseDbRow result;
    public  ArrayList<Boolean> checkedConditions;
    public  boolean hasResult = false;
    private ArrayList<JTextField> textFields;
    private  ArrayList<LookUpComboBox> lookUpFields;
    private ArrayList<JCheckBox> checkBoxes;
    public  InputForm(JFrame owner,  HashMap<String, JTextField> inputFields, BaseDbRow row, HashMap<String, Integer> lookUpFk, int formMode)
    {
       super(owner, "", true);
       if(formMode == AddEdit)
          setTitle("Редактирование");
       else
           setTitle("Поиск");
        int rowCount = inputFields.size();
        if(lookUpFk != null)
            rowCount += lookUpFk.size();
        textFields = new ArrayList<>();
        setSize(600, generateHeight(rowCount));
        JPanel panelFiels = new JPanel();

        int colsCount = 2;
        if(formMode == Search)
            colsCount += 1;
        panelFiels.setLayout(new GridLayout(rowCount, colsCount, 1, 1));

        Object[] keys =  inputFields.keySet().toArray();
        Object[] values = inputFields.values().toArray();
        checkBoxes = new ArrayList<>();
        for(int i =0;i<inputFields.size();i++)
        {
            panelFiels.add(new JLabel((String)keys[i]));
            panelFiels.add((JTextField)values[i]);
            textFields.add((JTextField)values[i]);

            if(formMode == Search) {
                JCheckBox chb = new JCheckBox("Учитывать");
                checkBoxes.add(chb);
                panelFiels.add(chb);
            }
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
                 if(formMode == Search) {
                     JCheckBox chb = new JCheckBox("Учитывать");
                     checkBoxes.add(chb);
                     panelFiels.add(chb);
                 }
             }
        }
        add(panelFiels);
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 2, 1, 1));
        JButton  butAdd = new JButton("Сохранить");
        butAdd.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            saveClick(formMode);
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
        int res = 50;
        for(int i =0;i<count;i++)
        {
            res += 35;
        }
        return  res;
    }

    public  void  saveClick(int mode)
    {
        try {
            ArrayList<Object> data = new ArrayList<>();
            for (int i = 0; i < textFields.size(); i++) {
                data.add(textFields.get(i).getText());

            }
            if (lookUpFields != null) {
                for (int i = 0; i < lookUpFields.size(); i++) {
                    data.add(lookUpFields.get(i).getKeyValue());
                }
            }
            if (mode == Search) {
                checkedConditions = new ArrayList<>();
                for (int i = 0; i < checkBoxes.size(); i++) {
                    checkedConditions.add(checkBoxes.get(i).isSelected());
                }
            }

            result.buildFromList(data);
        }
        catch (NumberFormatException w)
        {
            JOptionPane.showMessageDialog(this, "Введена строка в поле, которое может содержать только цифры", "Введены не корректные данные", JOptionPane.ERROR_MESSAGE);
            return;
        }
       if(result.checkErrors() == "-1") {
           hasResult = true;
           setVisible(false);
       }
       else
           JOptionPane.showMessageDialog(this, result.checkErrors(), "Введены не корректные данные", JOptionPane.ERROR_MESSAGE);
    }

}
