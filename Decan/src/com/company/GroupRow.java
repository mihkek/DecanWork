package com.company;

import java.util.ArrayList;

public class GroupRow extends BaseDbRow {
    private int id;
    private String name;
    private  int idKaf;

    public GroupRow(int id, String name, int idDec) {
        this.setId(id);
        this.setName(name);
        roleCount = 3;
    }
    public  GroupRow(){
        roleCount = 3;
    }
    public  GroupRow(String name){
        this.name =name;
        roleCount = 3;
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

    public  void setIdKaf(int value) {this.idKaf = value;}
    public  int getIdKaf(){return idKaf;}
    public  String getLookUp_IdDec(){
        ArrayList<Object> data = Main.DBWorking.readOneFieldQuery("kafedra", "name", " id = '"+ idKaf +"'");
        return  (String)data.get(0);
    }
    @Override
    public String buildForInsert() {
        return "null, '"+name+"'"+ "," + "'"+ idKaf +"'";
    }

    @Override
    public String buildForUpdate() {
        return "name = '"+name +"'"+ "," + " idKaf = '"+ idKaf +"'";
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
            case 2: return  String.valueOf(idKaf);
        }
        return  "invalid role index";
    }

    @Override
    public void setRoleValue(int id, Object value) {
        switch (id)
        {
            case 0: this.id = (int) value; break;
            case 1: name = (String) value; break;
            case 2: idKaf = (int) value;break;
        }
    }

    @Override
    public String checkErrors() {
        if(name.equals(""))
            return "Название группы не может быть пустым";
        if(idKaf == -1)
            return  "Укажите кафедру";
        return "-1";
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
            idKaf = (int)data.get(2);
            return;
        }
        if(data.size() != roleCount-1)
            return;
        name = (String)data.get(0);
        idKaf = (int)data.get(1);
    }

    @Override
    public void buildFromList(ArrayList<Object> data) {
         buildFromList(data, false);
    }
}
