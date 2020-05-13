package com.company;

import java.util.ArrayList;

public class KafedraRow  extends BaseDbRow {

    private int id;
    private String name;
    private  int idDec;

    public KafedraRow(int id, String name, int idDec) {
        this.setId(id);
        this.setName(name);
        roleCount = 3;
    }
    public  KafedraRow(){
        roleCount = 3;
    }
    public  KafedraRow(String name){
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

    public  void setIdDec(int value) {this.idDec = value;}
    public  int getIdDec(){return  idDec;}
    public  String getLookUp_IdDec(){
          ArrayList<Object> data = Main.DBWorking.readOneFieldQuery("decanat", "name", " id = '"+idDec+"'");
          return  (String)data.get(0);
    }
    @Override
    public String buildForInsert() {
        return "null, '"+name+"'"+ "," + "'"+idDec+"'";
    }

    @Override
    public String buildForUpdate() {
        return "name = '"+name +"'"+ "," + " idDec = '"+idDec+"'";
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
            case 2: return  String.valueOf(idDec);
        }
        return  "invalid role index";
    }

    @Override
    public void setRoleValue(int id, Object value) {
        switch (id)
        {
            case 0: this.id = (int) value; break;
            case 1: name = (String) value; break;
            case 2: idDec = (int) value;break;
        }
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
            idDec = (int)data.get(2);
            return;
        }
        if(data.size() != roleCount-1)
            return;
        name = (String)data.get(0);
        idDec = (int)data.get(1);
    }

    @Override
    public void buildFromList(ArrayList<Object> data) {
       buildFromList(data, false);
    }
}
