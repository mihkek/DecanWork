package com.company;

import java.util.ArrayList;

public class ReportRow extends  BaseDbRow {
    public int idSt;
    public  int idSem;
    public int year;
    public  ReportRow(){
        roleCount = 3;
    }

    @Override
    public String buildForInsert() {
        return null;
    }

    @Override
    public String buildForUpdate() {
        return null;
    }

    @Override
    public String getPrimaryKeyValue() {
        return  null;
    }

    @Override
    public String getRoleValue(int id) {
        switch (id)
        {
            case 0: return String.valueOf(this.idSt);
            case 1: return  String.valueOf(idSem);
            case 2: return  String.valueOf(year);
        }
        return  "invalid";
    }

    @Override
    public void setRoleValue(int id, Object value) {
    }

    @Override
    public String checkErrors() {
        return "-1";
    }

    @Override
    public void setPrimaryKeyValue(int value) {
    }

    @Override
    public void buildFromList(ArrayList<Object> data, boolean isFull) {
        if(data.get(0).equals(""))
            year = -1;
        else
            year = Integer.parseInt((String)data.get(0));
        idSt = (int) data.get(2);
        idSem = (int)data.get(1);

    }

    @Override
    public void buildFromList(ArrayList<Object> data) {
        buildFromList(data, false);
    }
}
