package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EkzstTable extends BaseDbTable {
    public  EkzstTable()
    {
        columnCount = 4;
        dbTableName = "ekzst";
        records = new ArrayList<>();
        Main.DBWorking.tables.add(this);
        readData();
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Номер";
            case 1:
                return "Студент";
            case  2:
                return  "Экзамен";
            case 3:
                return  "Оценка";
        }
        return "";
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        ExzstRow row = (ExzstRow) records.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getId();
            case 1:
                return row.getLookUp_IdSt();
            case  2:
                return  row.getLookUp_idEkz();
            case  3:
                return  row.getScore();
        }
        return "";
    }

    @Override
    public BaseDbRow constructTableRow() {
        return  new ExzstRow();
    }

    @Override
    public String getDbRoleName(int id) {
        switch (id)
        {
            case 0: return "id";
            case 1: return  "idSt";
            case 2: return  "idEkz";
            case 3:return  "score";
        }
        return  "invalid role index";
    }
    @Override
    public HashMap<String, JTextField> generateInputFields(int id) {
        HashMap<String, JTextField> fields = new HashMap<>();

        JTextField field = new JTextField();
        if(id != -1)
            field.setText(records.get(id).getRoleValue(3));
        fields.put("Оценка",field);
        return  fields;
    }

    @Override
    public HashMap<String, Integer> generateLookUpFields(int id) {
        //format - tableName$$$keyField$$$ValueField
        String params ="student:::name:::id:::Студент";
        HashMap<String, Integer> data = new HashMap<>();
        if(id != -1) {
            data.put(params, Integer.parseInt(records.get(id).getRoleValue(1)));
            data.put("ekzam:::name:::id:::Экзамен", Integer.parseInt(records.get(id).getRoleValue(2)));
        }
        else
        {
            data.put(params, -1);
            data.put("ekzam:::name:::id:::Экзамен", -1);
        }
        return  data;
    }
}
