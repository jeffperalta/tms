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
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import DatabaseConnection.*;
import java.util.ArrayList;
  
public class generalSqlHelper {

    Connection _MyConnection = null;
    Statement _MyStatement = null;
    PreparedStatement _MyPreparedStatement = null;
    ResultSet _MyResultSet = null;
    
    public generalSqlHelper(){
    
    }
    
    public ArrayList getDataElement(String TableName, ArrayList FieldList, String WhereClause) {
        ArrayList myResult = new ArrayList();
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT ";
                
                for(int iCtr = 0; iCtr <FieldList.size(); iCtr++) {
                    if(iCtr ==0) {
                        query = query.concat(" " + FieldList.get(iCtr) + " ");
                    }else{
                        query = query.concat(", " + FieldList.get(iCtr) + " ");
                    }
                }
                
                query = query.concat(" FROM " + TableName + " ");
                query = query.concat(" WHERE " + WhereClause + " ");

                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()){
                    for(int iCtr= 0; iCtr < FieldList.size(); iCtr++) {
                        myResult.add(_MyResultSet.getString(iCtr + 1));
                    }
                    break;
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
        
        return myResult;
    }
    
    public boolean isExist(String sqlTableName, String WhereClause) {
        boolean myResult = false;
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT 'EXIST' " +
                              "FROM " + sqlTableName + " " +
                              "WHERE " + WhereClause;
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()) {
                    myResult = true;
                    break;
                }
                           
            }

        }catch(Exception e){
            System.out.println(e.toString());
            myResult = false;
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
        
        return myResult;
    }
    
    public static void main(String args[]) {
        generalSqlHelper _MySqlHelper = new generalSqlHelper();
       
        if("1".trim().length() > 0) {
            java.util.ArrayList<String> myFieldList = new java.util.ArrayList<String>(), 
                                        myResultList = new java.util.ArrayList<String>(); 
            myFieldList.clear();
            myResultList.clear();

            myFieldList.add("status_name");
            myResultList = _MySqlHelper.getDataElement("a_activity_status", myFieldList, "status_id=" + "1" );

            if(myResultList.size() != 0) {
                System.out.println(myResultList.get(0));
            }else{
                System.out.println("NO VALUE");
            }

        }
        
    }
}
