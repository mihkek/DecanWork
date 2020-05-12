package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KafedraTable extends BaseDbTable {
    // private List<BaseDbRow> beans;
    public KafedraTable(List<BaseDbRow> beans) {
        this.records = beans;
        columnCount = 3;
        dbTableName = "kafedra";
    }
    public  KafedraTable()
    {
        columnCount = 3;
        dbTableName = "kafedra";
        records = new ArrayList<>();
        readData();
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Номер";
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
        //format - tableName$$$keyField$$$ValueField
        String params ="decanat:::name:::id:::Деканат";
        HashMap<String, Integer> data = new HashMap<>();
        data.put(params, id);
        return  data;
    }
}
