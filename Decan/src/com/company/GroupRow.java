package com.company;

import java.util.ArrayList;

public class GroupRow extends BaseDbRow {

    private int id;
    private String fio;

    public GroupRow(int id, String fio) {
        this.setId(id);
        this.setFio(fio);
    }
    public  GroupRow(){ }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFio(String size) {
        this.fio = size;
    }

    public String getFio() {
        return fio;
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
        return null;
    }

    @Override
    public String getRoleValue(int id) {
        switch (id)
        {
            case 0: return String.valueOf(this.id);
            case 1: return  fio;
        }
        return  "invalid role index";
    }

    @Override
    public void setRoleValue(int id, Object value) {
        switch (id)
        {
            case 0: this.id = (int) value;
            case 1: fio = (String) value;
        }
    }

    @Override
    public void buildFromList(ArrayList<Object> data) {
        if(data.size() != roleCount-1)
            return;
        fio = (String)data.get(0);
    }
}
