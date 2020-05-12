package com.company;
import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class GroupModel
    extends BaseDbTable {
        // private List<BaseDbRow> beans;
        public GroupModel(List<BaseDbRow> beans) {
            this.records = beans;
            columnCount = 2;
        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Номер";
                case 1:
                    return "ФИО";
            }
            return "";
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            GroupRow bean = (GroupRow) records.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return bean.getId();
                case 1:
                    return bean.getFio();
            }
            return "";
        }
        public void setValueAt(Object value, int rowIndex, int columnIndex) {

        }

    @Override
    public BaseDbRow constructTableRow() {
        return  new GroupRow();
    }

    @Override
    public String getDbRoleName(int id) {
        switch (id)
        {
            case 0: return "id";
            case 1: return  "fio";
        }
        return  "invalid role index";
    }

    @Override
    public HashMap<String, JTextField> generateInputFields(int id) {
        HashMap<String, JTextField> fields = new HashMap<>();
        fields.put("Название", new JTextField());
        return  fields;
    }

    @Override
    public HashMap<String, Integer> generateLookUpFields(int id) {
        return null;
    }
}
