package com.company;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.company.Main.DBWorking.hasConnection;
import static com.company.Main.DBWorking.readOneFieldQuery;

public class LookUpComboBox extends JComboBox {
    public ArrayList<Object> visibleValues;
    public ArrayList<Object> keyValues;
    public String tableName;
    public String keyField;
    public String valueField;
    public  LookUpComboBox(String tableName, String key, String value)
    {
        super();
        this.keyField = key;
        this.valueField = value;
        this.tableName = tableName;
        keyValues = new ArrayList<>();
        visibleValues = new ArrayList<>();
        lookUpQuery();
        for(int i = 0;i< visibleValues.size();i++)
        {
            addItem(visibleValues.get(i));
        }
    }
    private void lookUpQuery()
    {
        if(!hasConnection)
            return ;
        String query = "select "+keyField+", " +valueField+ " from "+tableName+";";
        Statement stmt = null;
        try {
            stmt = Main.DBWorking.connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                Object key = rs.getObject(2);
                Object value = rs.getObject(1);
                keyValues.add(key);
                visibleValues.add(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
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
