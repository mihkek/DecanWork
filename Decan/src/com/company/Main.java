package com.company;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.List;


public class Main {
    static ModFrame mainFrame;
    public  static final String dbConfigFileName = "dbConfig.txt";
    public  static  class DBWorking
    {
        public static ArrayList<BaseDbTable> tables = new ArrayList<>();
        public  static  String dbName;
        public  static String serverName;
        public  static  String userName;
        public  static  String userPassword;
        public  static  boolean hasConnection;
        public  static Connection connection;
        enum queryType
        {
            WriteQuery, UpdateQuery, DeleteQuery, SelectQuery
        };
        public  static void connect(){
             connection = null;
            //Class.forName ("com.MySQL.jdbc.Driver").newInstance ();
            String url = "jdbc:mysql://"+serverName+"/"+dbName+"?serverTimezone=Europe/Moscow&useSSL=false";
            try {
                connection = DriverManager.getConnection(url, userName, userPassword);
                hasConnection = true;
            } catch (SQLException e) {
                hasConnection = false;
            }
            catch (NullPointerException e)
            {
                hasConnection = false;
            }

        }
        public static  void loadData()
        {
            try {
                BufferedReader reader = new BufferedReader( new FileReader (dbConfigFileName));
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                String ls = System.getProperty("line.separator");
                while( ( line = reader.readLine() ) != null ) {
                    stringBuilder.append( line );
                    stringBuilder.append( ls );
                }
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                String data = stringBuilder.toString();
                String[] params = data.split(":::");

                serverName = params[0];
                dbName = params[1];
                userName = params[2];
                userPassword = params[3];

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Файл не существует", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }

        }
        public static  void writeData()
        {
            String data = serverName+ ":::" + dbName+ ":::" + userName +":::" +userPassword+ ":::";
            File file = new File(dbConfigFileName);
            FileWriter fr = null;
            try {
                fr = new FileWriter(file);
                fr.write(data);
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        public static class queryConditionsBuilder
        {
            private Map<String, String> conditions = new HashMap<String,String>();
            public  void addCondition( String key,String value)
            {
                conditions.put(key, value);
            }
            public  void removeCondition(String key)
            {
                conditions.remove(key);
            }
            public  String buildData()
            {
                String result = "";
                    for (int i = 0; i < conditions.size(); i++) {
                        result += conditions.keySet().toArray()[i] + " = '" + conditions.values().toArray()[i] + "'";
                        if (i != conditions.size() - 1)
                            result += " and ";
                    }
                return  result;
            }
        }
        public  static ArrayList<ArrayList<Object>> readSomeFieldQuery(String tableName,ArrayList<String> columns, String condition)
        {
            if(!hasConnection)
                return  null;
            String query = "select ";
            for (int i=0;i<columns.size();i++)
            {
                 query+=  columns.get(i);
                 if(i!= columns.size()-1)
                     query += ",";
            }
            query += " from "+tableName;
            if(condition != null)
            {
                query += " where "+condition;
            }
            query += ";";
            ArrayList<ArrayList<Object>> data = new ArrayList<>();
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                ResultSet rs = stmt.executeQuery(query);
                while (rs.next())
                {
                    ArrayList<Object> fieldData = new ArrayList<>();
                    for(int i =1;i<=columns.size();i++)
                    {
                        fieldData.add(rs.getObject(i));
                    }
                    data.add(fieldData);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return  data;
        }
        public static  ArrayList<Object> readOneFieldQuery(String tableName, String columns, String condition)  {
            if(!hasConnection)
                return  null;
            String query = "select "+columns+" from "+tableName;
            if(condition != null)
            {
                query += " where "+condition;
            }
            query += ";";
            ArrayList<Object> data = new ArrayList<>();
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                Object value = rs.getObject(1);
                data.add(value);
            }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return  data;
        }
        public static ArrayList<Object> readOneFieldQuery(String tableName, String columns)  {
             return readOneFieldQuery(tableName, columns, null);
        }
        public  static  void writeQuery(String tableName, String values){
            if(!hasConnection)
                return ;
            String query = "insert into "+tableName+ " values ("+values+");";
            Statement stmt = null;
            try {
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.execute();
            }catch (SQLException e) {
                e.printStackTrace();
                return;
            }

        }
        public static void updateQuery(String tableName, String pkName, String pkValue, String data)
        {
            String command = "update " + tableName + " set " + data + " where " + pkName + " = " + pkValue + ";";
            Statement stmt = null;
            try {
                PreparedStatement preparedStmt = connection.prepareStatement(command);
                preparedStmt.execute();
                // ResultSet rs = stmt.executeQuery(query);
            }catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        }
        public static void f_delete_query(String tableName, String arg_key, String arg_value )
        {
            String command = "delete from " + tableName;
            if (arg_key != "")
            {
                command += " where " + arg_key + "= '" + arg_value + "';";
            }
            Statement stmt = null;
            try {
                PreparedStatement preparedStmt = connection.prepareStatement(command);
                preparedStmt.execute();
                // ResultSet rs = stmt.executeQuery(query);
            }catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        }
        public  static  String executeOneResultQuery(String query)
        {
            if(!hasConnection)
                return  null;

            ArrayList<ArrayList<Object>> data = new ArrayList<>();
            Statement stmt = null;
            String result = "";
            try {
                stmt = connection.createStatement();

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next())
                {
                   result = rs.getObject(1).toString();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            catch (NullPointerException d){
                return null;}
            return  result;
        }
        public  static ArrayList<ArrayList<Object>> executeMultiResultQuery(String query, int columnCount)
        {
            if(!hasConnection)
                return  null;

            ArrayList<ArrayList<Object>> data = new ArrayList<>();
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                ResultSet rs = stmt.executeQuery(query);
                while (rs.next())
                {
                    ArrayList<Object> fieldData = new ArrayList<>();
                    for(int i =1;i<=columnCount;i++)
                    {
                        fieldData.add(rs.getObject(i));
                    }
                    data.add(fieldData);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return  data;
        }
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DBWorking.loadData();
                DBWorking.connect();
                mainFrame =  new ModFrame();
            }
        });
    }
}
