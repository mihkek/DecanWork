package com.company;

import javax.swing.*;
import java.util.ArrayList;

public class DecanRow extends BaseDbRow {

    private int id;
    private String name;
    public  DecanRow(){
        roleCount = 2;
    }
    public  DecanRow(ArrayList<Object> data)
    {
        super(data);
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String buildForInsert() {
        return "null, '"+name+"'";
    }

    @Override
    public String buildForUpdate() {
        return "name = '"+name +"'";
    }

    @Override
    public String getPrimaryKeyValue() {
        return String.valueOf(id);
    }

    @Override
    public String getRoleValue(int id) {
        switch (id)
        {
            case 0: return String.valueOf(this.id);
            case 1: return  name;
        }
        return  "invalid role index";
    }

    @Override
    public void setRoleValue(int id, Object value) {
        switch (id)
        {
            case 0: this.id = (int) value; break;
            case 1: name = (String) value; break;
        }
    }

    @Override
    public String checkErrors() {
        if(name.equals(""))
            return "Название деканата не может быть пустым";
        return  "-1";
    }

    @Override
    public void setPrimaryKeyValue(int value) {
        id = value;
    }

    @Override
    public void buildFromList(ArrayList<Object> data, boolean isFull) {
        if(isFull) {
            setPrimaryKeyValue((int)data.get(0));
            name = (String)data.get(1);
            return;
        }
        if(data.size() != roleCount-1)
            return;
        name = (String)data.get(0);

    }

    @Override
    public void buildFromList(ArrayList<Object> data) {
        buildFromList(data, false);
    }




}
