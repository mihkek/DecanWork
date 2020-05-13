package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SemestrTable extends  BaseDbTable {
    public  SemestrTable()
    {
        records = new ArrayList<>();
        columnCount = 2;
        dbTableName = "semestr";
        Main.DBWorking.tables.add(this);
        readData();
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Номер";
            case 1:
                return "Название";
        }
        return "";
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        SemestrRow row = (SemestrRow) records.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getId();
            case 1:
                return row.getName();
        }
        return "";
    }

    @Override
    public BaseDbRow constructTableRow() {
        return  new SemestrRow();
    }

    @Override
    public String getDbRoleName(int id) {
        switch (id)
        {
            case 0: return "id";
            case 1: return  "name";
        }
        return  "invalid role index";
    }
    @Override
    public HashMap<String, JTextField> generateInputFields(int id) {
        HashMap<String, JTextField> fields = new HashMap<>();

        JTextField field = new JTextField();
        if(id != -1){
            field.setText(records.get(id).getRoleValue(1));
        }
        fields.put("Название",field);
        return  fields;
    }

    @Override
    public HashMap<String, Integer> generateLookUpFields(int id) {
        return null;
    }
}
