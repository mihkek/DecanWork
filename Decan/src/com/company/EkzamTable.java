package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EkzamTable extends BaseDbTable {
    public  EkzamTable()
    {
        columnCount = 4;
        dbTableName = "ekzam";
        records = new ArrayList<>();
        Main.DBWorking.tables.add(this);
        readData();
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Номер";
            case 1:
                return "Название";
            case 2:
                return "Год";
            case  3:
                return  "Семестр";
        }
        return "";
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        EkzamRow row = (EkzamRow) records.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getId();
            case 1:
                return row.getName();
            case 2:
                return  row.getYear();
            case  3:
                return  row.getLookUp_IdSem();
        }
        return "";
    }

    @Override
    public BaseDbRow constructTableRow() {
        return  new EkzamRow();
    }

    @Override
    public String getDbRoleName(int id) {
        switch (id)
        {
            case 0: return "id";
            case 1: return  "name";
            case 2: return "year";
            case 3: return  "idSem";
        }
        return  "invalid role index";
    }
    @Override
    public HashMap<String, JTextField> generateInputFields(int id) {
        HashMap<String, JTextField> fields = new HashMap<>();

        JTextField field = new JTextField();
        JTextField yearField = new JTextField();
        if(id != -1) {
            field.setText(records.get(id).getRoleValue(1));
            yearField.setText(records.get(id).getRoleValue(2));
        }
        fields.put("Название",field);
        fields.put("Год", yearField);
        return  fields;
    }

    @Override
    public HashMap<String, Integer> generateLookUpFields(int id) {
        //format - tableName$$$keyField$$$ValueField
        String params ="semestr:::name:::id:::Семестр";
        HashMap<String, Integer> data = new HashMap<>();
        if(id != -1)
            data.put(params, Integer.parseInt(records.get(id).getRoleValue(3)));
        else
            data.put(params, -1);
        return  data;
    }
}
