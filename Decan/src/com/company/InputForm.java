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
    public  static final  int Report =3 ;

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
       if(formMode == Search)
           setTitle("Поиск");
       if(formMode == Report)
           setTitle("Отчет");
        int rowCount = 0;
        if(inputFields != null)
          rowCount = inputFields.size();
        if(lookUpFk != null)
            rowCount += lookUpFk.size();
        textFields = new ArrayList<>();
        setSize(600, generateHeight(rowCount));
        JPanel panelFiels = new JPanel();

        int colsCount = 2;
        if((formMode == Search)||(formMode == Report))
            colsCount += 1;
        panelFiels.setLayout(new GridLayout(rowCount, colsCount, 1, 1));
        Object[] keys;
        Object[] values ;
        if(inputFields != null) {
             keys = inputFields.keySet().toArray();
             values = inputFields.values().toArray();
            checkBoxes = new ArrayList<>();
            for (int i = 0; i < inputFields.size(); i++) {
                panelFiels.add(new JLabel((String) keys[i]));
                panelFiels.add((JTextField) values[i]);
                textFields.add((JTextField) values[i]);

                if ((formMode == Search)||(formMode == Report)) {
                    JCheckBox chb = new JCheckBox("Учитывать");
                    checkBoxes.add(chb);
                    panelFiels.add(chb);
                }
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
                 if(data.length < 4)
                     continue;
                 if(data.length == 6)
                 {
                     ArrayList<Object> elems = new ArrayList<>();
                     for(int k=1;k<data.length;k++)
                     {
                         elems.add(data[k]);
                     }
                     LookUpComboBox cb = new LookUpComboBox(elems);
                     cb.setSelectedIndex(values[i]);
                     lookUpFields.add(cb);

                     panelFiels.add(new JLabel(data[data.length-1]));
                     panelFiels.add(cb);
                 }
                 else {
                     LookUpComboBox lookUp = new LookUpComboBox(data[0], data[1], data[2]);
                     lookUp.setSelectedIndex(values[i]);
                     lookUpFields.add(lookUp);
                     panelFiels.add(new JLabel(data[3].replace("$$", "")));
                     panelFiels.add(lookUp);
                 }
                 if((formMode == Search)||(formMode == Report)) {
                     JCheckBox chb = new JCheckBox("Учитывать");
                     if(!data[data.length-1].contains("$$")) {
                         checkBoxes.add(chb);
                         panelFiels.add(chb);
                     }
                     else
                         panelFiels.add(new JLabel(""));
                 }
             }
        }
        add(panelFiels);
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 2, 1, 1));
        String butName = "";
        switch (formMode)
        {
            case AddEdit: butName= "Сохранить";break;
            case Search: butName= "Найти";break;
            case Report: butName= "Составить";break;
        }
        JButton  butAdd = new JButton(butName);
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
            if ((mode == Search)||(mode == Report)) {
                checkedConditions = new ArrayList<>();
                for (int i = 0; i < checkBoxes.size(); i++) {
                    checkedConditions.add(checkBoxes.get(i).isSelected());
                }
            }

            result.buildFromList(data);
        }
        catch (NumberFormatException w)
        {
            if(mode == AddEdit) {
                JOptionPane.showMessageDialog(this, "Введена строка в поле, которое может содержать только цифры", "Введены не корректные данные", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }


       if(result.checkErrors() == "-1") {
           hasResult = true;
           setVisible(false);
       }
       else {
           if(mode == Search)
           {
               hasResult = true;
               setVisible(false);
           }
           else
              JOptionPane.showMessageDialog(this, result.checkErrors(), "Введены не корректные данные", JOptionPane.ERROR_MESSAGE);
       }
    }

}
