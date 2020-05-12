package com.company;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class SemestrRow  extends BaseDbRow {

    private int id;
    private String name;

    public SemestrRow(int id, String name) {
        this.setId(id);
        this.setName(name);
        roleCount = 3;
    }
    public  SemestrRow(){
        roleCount = 2;
    }
    public  SemestrRow(String name){
        this.name =name;
        roleCount = 2;
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
            case 0: return String.valueOf(id);
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
    public void buildFromList(ArrayList<Object> data) {
        if(data.size() != roleCount-1)
            return;
        name = (String)data.get(0);
    }
}
