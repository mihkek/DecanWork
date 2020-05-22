package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class KafedraTable extends BaseDbTable {
    public  KafedraTable()
    {
        columnModelCount = 3;
        dbTableName = "kafedra";
        columnCount = columnModelCount;
        records = new ArrayList<>();
        Main.DBWorking.tables.add(this);
        readData();
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Название";
            case  2:
                return  "Деканат";
        }
        return "";
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        KafedraRow row = (KafedraRow) records.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getId();
            case 1:
                return row.getName();
            case  2:
                return  row.getLookUp_IdDec();
        }
        return "";
    }

    @Override
    public BaseDbRow constructTableRow() {
        return  new KafedraRow();
    }

    @Override
    public String getDbRoleName(int id) {
        switch (id)
        {
            case 0: return "id";
            case 1: return  "name";
            case 2: return  "idDec";
        }
        return  "invalid role index";
    }
    @Override
    public HashMap<String, JTextField> generateInputFields(int id) {
        HashMap<String, JTextField> fields = new HashMap<>();

        JTextField field = new JTextField();
        if(id != -1)
            field.setText(records.get(id).getRoleValue(1));
        fields.put("Название",field);
        return  fields;
    }

    @Override
    public HashMap<String, Integer> generateLookUpFields(int id) {
        String params ="decanat:::name:::id:::Деканат";
        HashMap<String, Integer> data = new HashMap<>();
        if(id != -1)
          data.put(params, Integer.parseInt(records.get(id).getRoleValue(2)));
        else
            data.put(params, -1);
        return  data;
    }
}
