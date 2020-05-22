package com.company;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.*;


public abstract class BaseDbTable  implements TableModel
{
    //С этими полями будет основная работа
    protected List<BaseDbRow> records;
    protected  int columnModelCount;
    protected  int columnCount;
    protected String dbTableName;
    public  abstract BaseDbRow constructTableRow();

    public  abstract  String getDbRoleName(int id);
    ///
    public  abstract HashMap<String, JTextField> generateInputFields(int id);
    public  abstract  HashMap<String, Integer> generateLookUpFields(int id);
    public  void search(BaseDbRow example, ArrayList<Boolean> chechedFields){
        Main.DBWorking.queryConditionsBuilder conditions = new Main.DBWorking.queryConditionsBuilder();
        for(int i = 1; i< columnModelCount; i++)
        {
            if(chechedFields.get(i-1))
                conditions.addCondition(getDbRoleName(i), example.getRoleValue(i));
        }
        String res = conditions.buildData();
        readData(res);
    }

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
    public int getRowCount() {
        return records.size();
    }
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }
    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }
    public int getColumnCount() {
        return columnCount;
    }
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        records.set(rowIndex, (DecanRow)value);
    }
    public  void add(Object value)
    {
        records.add((BaseDbRow)value);
    }
    public   void writeData(BaseDbRow row)
    {
        Main.DBWorking.writeQuery(dbTableName, row.buildForInsert());
        readData();
    }
    public   void readData(String conditions)  {
        records.clear();
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        BaseDbRow row;
        ArrayList<String> cols = new ArrayList<>();
        for(int i = 0; i< columnModelCount; i++)
        {
            cols.add(getDbRoleName(i));
        }
        data = Main.DBWorking.readSomeFieldQuery(dbTableName, cols, conditions);
        for(int j =0;j<data.size();j++)
        {
            row = constructTableRow();
            row.buildFromList(data.get(j), true);
            records.add(row);
        }
       // sort();
    }
    public  void clearData()
    {
        records.clear();
    }

    public  void readData(){

        readData(null);
    }
    public void sort()
    {
        int f = 0;
        for (int i = 0; i < records.size(); i++) {
            for (int j = 0; j < records.size() -1; j++) {
                String val1 = records.get(j).getRoleValue(0);
                String val2 =  records.get(j + 1).getRoleValue(0);
                if (Integer.parseInt(records.get(j).getRoleValue(0)) > Integer.parseInt(records.get(j + 1).getRoleValue(0))) {
                    BaseDbRow b = records.get(j); // создали дополнительную переменную
                    records.set(j, records.get(j+1));// digitals[j + 1]; // меняем местами
                    records.set(j + 1, b);// = b; // значения элементов
                }
            }
        }
    }

    public   void updateRow(int id, BaseDbRow row)
    {
        Main.DBWorking.updateQuery(dbTableName,"id",  records.get(id).getPrimaryKeyValue(), row.buildForUpdate());
        readData();
    }
    public   void deleteRow(int id)
    {
        Main.DBWorking.f_delete_query(dbTableName, "id", records.get(id).getPrimaryKeyValue());
        readData();
    }
}
