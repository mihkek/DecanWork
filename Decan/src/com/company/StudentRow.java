package com.company;

import java.util.ArrayList;

public class StudentRow extends BaseDbRow {
    private int id;
    private String name;
    private  int idGroup;

    public StudentRow(int id, String name, int idDec) {
        this.setId(id);
        this.setName(name);
        roleCount = 3;
    }
    public  StudentRow(){
        roleCount = 3;
    }
    public  StudentRow(String name){
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

    public  void setIdGroup(int value) {this.idGroup = value;}
    public  int getIdGroup(){return idGroup;}
    public  String getLookUp_IdGroup(){
        ArrayList<Object> data = Main.DBWorking.readOneFieldQuery("groupa", "name", " id = '"+ idGroup +"'");
        return  (String)data.get(0);
    }
    @Override
    public String buildForInsert() {
        return "null, '"+name+"'"+ "," + "'"+ idGroup +"'";
    }

    @Override
    public String buildForUpdate() {
        return "name = '"+name +"'"+ "," + " idGroup = '"+ idGroup +"'";
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
            case 2: return  String.valueOf(idGroup);
        }
        return  "invalid role index";
    }

    @Override
    public void setRoleValue(int id, Object value) {
        switch (id)
        {
            case 0: this.id = (int) value; break;
            case 1: name = (String) value; break;
            case 2: idGroup = (int) value;break;
        }
    }

    @Override
    public String checkErrors() {
        if(name.equals(""))
            return  "ФИО студента не может быть пустым";
        if(idGroup == -1)
            return "Укажите группу";
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
            idGroup = (int)data.get(2);
            return;
        }
        if(data.size() != roleCount-1)
            return;
        name = (String)data.get(0);
        idGroup = (int)data.get(1);
    }

    @Override
    public void buildFromList(ArrayList<Object> data) {
        buildFromList(data, false);
    }
}
