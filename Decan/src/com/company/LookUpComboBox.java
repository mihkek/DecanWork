package com.company;

import javax.swing.*;
import java.util.ArrayList;

import static com.company.Main.DBWorking.readQuery;

public class LookUpComboBox extends JComboBox {
    public ArrayList<Object> visibleValues;
    public ArrayList<Object> keyValues;
    public String tableName;
    public String keyField;
    public String valueField;
    public  LookUpComboBox(String tableName, String key, String value)
    {
        super();
        this.keyField = keyField;
        this.valueField = valueField;
        this.tableName = tableName;
        visibleValues = readQuery(tableName, key);
        keyValues = readQuery(tableName, value);
        for(int i = 0;i< visibleValues.size();i++)
        {
            addItem(visibleValues.get(i));
        }
    }
   public  void setSelectedIndex(Object keyFieldValue)
   {
       int id = 0;
       for(int i=0;i<keyValues.size();i++)
       {
           if(keyValues.get(i) == keyFieldValue)
           {id = i; break;}
       }
       setSelectedIndex(id);
   }
    public  Object getKeyValue()
    {
        return keyValues.get(getSelectedIndex());
    }

}
