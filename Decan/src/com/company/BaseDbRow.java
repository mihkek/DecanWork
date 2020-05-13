package com.company;

import javax.swing.*;
import java.util.ArrayList;

public abstract class BaseDbRow {
    public  BaseDbRow(ArrayList<Object> data){
        buildFromList(data);
    }
    public  BaseDbRow(){}
    public  abstract  String buildForInsert();
    public  abstract  String buildForUpdate();
    public  abstract  String getPrimaryKeyValue();
    public  int roleCount;
    public abstract String getRoleValue(int id);
    public  abstract  void setRoleValue(int id, Object value);
    public  abstract void setPrimaryKeyValue(int value);
    public  abstract void buildFromList(ArrayList<Object> data, boolean isFull);
    public  void buildFromList(ArrayList<Object> data)
    {
        buildFromList(data, false);
    }

}
