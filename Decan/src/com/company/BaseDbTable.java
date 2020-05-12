package com.company;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.*;


public abstract class BaseDbTable  implements TableModel
{
    //С этими полями будет основная работа
    protected List<BaseDbRow> records;
    protected  int columnCount;
    protected String dbTableName;
    public  abstract BaseDbRow constructTableRow();

    public  abstract  String getDbRoleName(int id);
    ///
    public  abstract HashMap<String, JTextField> generateInputFields(int id);
    public  abstract  HashMap<String, Integer> generateLookUpFields(int id);

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
        for(int i =0;i<columnCount;i++)
        {
            ArrayList<Object> columnValues = Main.DBWorking.readQuery(dbTableName, getDbRoleName(i), conditions);
            data.add(columnValues);
        }
        for(int j =0;j<data.get(0).size();j++)
        {
            row = constructTableRow();
            for(int i =0; i < columnCount;i++)
             {
                 Object value = data.get(i).get(j);
                 int ff = 0;
                 row.setRoleValue(i, data.get(i).get(j));
            }
            records.add(row);
        }
    }
    public  void readData(){

        readData(null);
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
