package com.company;

import java.util.ArrayList;

public class ExzstRow extends BaseDbRow {
    private int id;
    private int idSt;
    private  int idEkz;
    private String score;

    public ExzstRow(int id, String name, int idDec) {
        this.setId(id);
        roleCount = 4;
    }
    public  ExzstRow(){
        roleCount = 4;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public  void setIdEkz(int value) {this.idEkz = value;}
    public  int getIdEkz(){return idEkz;}

    public  void setIdSt(int value){this.idSt = value;}
    public  int getIdSt(){return  idSt;}

    public  void setScore(String value){score = value;}
    public  String getScore(){return  score;}
    public  String getLookUp_idEkz(){
        ArrayList<Object> data = Main.DBWorking.readOneFieldQuery("ekzam", "name", " id = '"+ idEkz +"'");
        return  (String)data.get(0);
    }
    public  String getLookUp_IdSt(){
        ArrayList<Object> data = Main.DBWorking.readOneFieldQuery("student", "name", " id = '"+ idEkz +"'");
        return  (String)data.get(0);
    }
    @Override
    public String buildForInsert() {
        return "null, '"+idSt+"'"+ "," + "'"+ idEkz +"'"+","+ "'"+score+"'";
    }

    @Override
    public String buildForUpdate() {
        return "idSt = '"+idSt +"'"+ "," + " idEkz = '"+ idEkz +"'"+","+ "score = '"+score+"'";
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
            case 1: return  String.valueOf(idSt);
            case 2: return  String.valueOf(idEkz);
            case 3: return  score;
        }
        return  "invalid role index";
    }

    @Override
    public void setRoleValue(int id, Object value) {
        switch (id)
        {
            case 0: this.id = (int) value; break;
            case 1: idSt = (int) value; break;
            case 2: idEkz = (int) value;break;
            case 3: score = (String) value;break;
        }
    }

    @Override
    public String checkErrors() {
        if(idSt ==- 1)
            return  "Укажите студента";
        if(idEkz == -1)
            return  "Укажите экзамен";
        if(score == "")
            return "Укажите оценку";
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
            idSt = (int)data.get(1);
            idEkz = (int)data.get(2);
            score = (String) data.get(3);

            return;
        }
        if(data.size() != roleCount-1)
            return;
        score = (String) data.get(0);
        idSt = (int)data.get(1);
        idEkz = (int)data.get(2);

    }

    @Override
    public void buildFromList(ArrayList<Object> data) {
        buildFromList(data, false);
    }
}
