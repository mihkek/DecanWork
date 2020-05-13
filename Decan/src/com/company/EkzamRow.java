package com.company;

import java.util.ArrayList;

public class EkzamRow extends BaseDbRow {


    private int id;
    private String name;
    private int year;
    private  int idSem;

    public  EkzamRow(){
        roleCount = 4;
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

    public  int getYear() {return year;}
    public  void setYear(int value){year = value;}

    public  void setIdSem(int value) {this.idSem = value;}
    public  int getidSem(){return  idSem;}
    public  String getLookUp_IdSem(){
        ArrayList<Object> data = Main.DBWorking.readOneFieldQuery("semestr", "name", " id = '"+idSem+"'");
        return  (String)data.get(0);
    }
    @Override
    public String buildForInsert() {
        return "null, '"+name+"'" + ","+"'"+year+"'"+ ","+ "'"+idSem+"'";
    }

    @Override
    public String buildForUpdate() {
        return "name = '"+name +"'"+ "," +"year = '" + year+"'"+","+ " idSem = '"+idSem+"'";
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
            case 2:return  String.valueOf(year);
            case 3: return  String.valueOf(idSem);
        }
        return  "invalid role index";
    }

    @Override
    public void setRoleValue(int id, Object value) {
        switch (id)
        {
            case 0: this.id = (int) value; break;
            case 1: name = (String) value; break;
            case 2: year = (int) value;break;
            case 3: idSem = (int) value;break;
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
            year = (int)data.get(2);
            idSem = (int)data.get(3);
            return;
        }
        if(data.size() != roleCount-1)
            return;
        name = (String)data.get(1);
        year = Integer.parseInt((String)data.get(0));
        idSem = (int)data.get(2);
    }

    @Override
    public void buildFromList(ArrayList<Object> data) {
        buildFromList(data, false);
    }
}
