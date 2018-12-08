/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

/**
 *
 * @author Abree
 */

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DatabaseConnection.*;
import java.util.ArrayList;
 
public class Combo {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;
    
    private String QueryString = " ";    
    private String Result = " ";
    private ArrayList<Object> Parameter = new ArrayList<Object>();
    private ArrayList<Integer> ParameterPosition = new ArrayList<Integer>();
    private ArrayList<String> ParameterType = new ArrayList<String>();
    
    
    public Combo() {
    }

    public void reset(){
        this.QueryString = " ";
        this.Parameter.clear();
        this.ParameterPosition.clear();
        this.ParameterType.clear();
        this.Result = " ";

        try{

            if(_MyResultSet != null){
                _MyResultSet.close();
            }

            if(_MyStatement != null) {
                _MyStatement.close();
            }

            if(_MyPreparedStatement != null) {
                _MyPreparedStatement.close();
            }

            if(_MyConnection != null) {
                _MyConnection.close();
            } 

        }catch(Exception e){
            System.out.println(e.toString());
        }
        
    }
    
    public String getQueryString() {
        return QueryString;
    }

    /**
     * The first field will be used as the combo value
     * And the second will be the display value
     * @param QueryString
     */
    public void setQueryString(String QueryString) {
        QueryString = QueryString.replaceAll(";", " ");
        if(QueryString.contains("DELETE") || 
                QueryString.contains("TRUNCATE") || 
                QueryString.contains("UPDATE") ||
                QueryString.contains("SET") ||
                QueryString.contains("ROLLBACK") ||
                QueryString.contains("COMMIT") ||
                QueryString.contains("INTO") ||
                QueryString.contains("DROP")) {
            this.QueryString = " ";
        }else{
            this.reset();
            this.QueryString = QueryString;
        }
    }
    
    private void generateComboResult() {
        try{
            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyPreparedStatement = _MyConnection.prepareStatement(this.QueryString);
                
                for(int iCounter = 0; iCounter < this.ParameterPosition.size(); iCounter++) {
                    if(this.ParameterType.get(iCounter).compareToIgnoreCase("String") == 0){
                        _MyPreparedStatement.setString(this.ParameterPosition.get(iCounter), this.Parameter.get(iCounter).toString());
                    }else if(this.ParameterType.get(iCounter).compareToIgnoreCase("Integer") == 0) {
                        _MyPreparedStatement.setInt(this.ParameterPosition.get(iCounter), Integer.parseInt(this.Parameter.get(iCounter).toString()));
                    }else if(this.ParameterType.get(iCounter).compareToIgnoreCase("Double") == 0) {
                        _MyPreparedStatement.setDouble(this.ParameterPosition.get(iCounter), Double.parseDouble(this.Parameter.get(iCounter).toString()));
                    }
                }
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
               
                while(_MyResultSet.next()) {
                    Result = Result.concat("<option value='" + _MyResultSet.getString(1) + "'>" + _MyResultSet.getString(2) + "</option>");
                }
                
            }

        }catch(Exception e){
            System.out.println(e.toString());
        }finally{

            try{

                if(_MyResultSet != null){
                    _MyResultSet.close();
                }

                if(_MyStatement != null) {
                    _MyStatement.close();
                }

                if(_MyPreparedStatement != null) {
                    _MyPreparedStatement.close();
                }

                if(_MyConnection != null) {
                    _MyConnection.close();
                } 

            }catch(Exception e){
                System.out.println(e.toString());
            }

        }
        
    }

    public String getResult() {
        this.generateComboResult();
        return Result;
    }
   
    public void setString(int position, String value) {
        
        if(this.ParameterPosition.contains(position)) {
            int iGetPos = 0;
            iGetPos = this.ParameterPosition.indexOf(position);
            this.ParameterPosition.remove(iGetPos);
            this.ParameterPosition.add(iGetPos, position);
            this.ParameterType.remove(iGetPos);
            this.ParameterType.add(iGetPos, "String");
            this.Parameter.remove(iGetPos);
            this.Parameter.add(iGetPos, value);
        }else{
            this.ParameterPosition.add(position);
            this.ParameterType.add("String");
            this.Parameter.add(value);
        }
        
    }
    
    public void setInteger(int position, int value) {
        if(this.ParameterPosition.contains(position)) {
            int iGetPos = 0;
            iGetPos = this.ParameterPosition.indexOf(position);
            this.ParameterPosition.remove(iGetPos);
            this.ParameterPosition.add(iGetPos, position);
            this.ParameterType.remove(iGetPos);
            this.ParameterType.add(iGetPos, "Integer");
            this.Parameter.remove(iGetPos);
            this.Parameter.add(iGetPos, value);
        }else{
            this.ParameterPosition.add(position);
            this.ParameterType.add("Integer");
            this.Parameter.add(value);
        }
    }
    
    public void setDouble(int position, double value) {
        if(this.ParameterPosition.contains(position)) {
            int iGetPos = 0;
            iGetPos = this.ParameterPosition.indexOf(position);
            this.ParameterPosition.remove(iGetPos);
            this.ParameterPosition.add(iGetPos, position);
            this.ParameterType.remove(iGetPos);
            this.ParameterType.add(iGetPos, "Double");
            this.Parameter.remove(iGetPos);
            this.Parameter.add(iGetPos, value);
        }else{
            this.ParameterPosition.add(position);
            this.ParameterType.add("Double");
            this.Parameter.add(value);
        }
    }
    
    public static void main (String args[]) {
        Combo myCombo = new Combo();
        myCombo.setQueryString("SELECT user_id, CONCAT(first_name, ' ', last_name) " +
                               "FROM a_users  " +
                               "WHERE user_id=? AND user_id=? " +
                               "ORDER BY user_id");
        myCombo.setString(1, "10000");
        myCombo.setString(1, "20000");
        myCombo.setString(2, "20000");
        System.out.println(myCombo.getResult());
        
        myCombo.reset();
        myCombo.setQueryString("DELETE FROM a_users;");
        System.out.println(myCombo.getResult());
    }
    
}
