/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package logIn;
 
/**
 *
 * @author jperalta
 */
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import beans.*;
import DatabaseConnection.*;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
 
public class LogInManager {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;
    
    public LogInManager(){
        
    }
    
    public void changePassword(LogInfo myUser) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_users " +
                              "SET pass=?" +
                              "WHERE username=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myUser.getPassword());
                _MyPreparedStatement.setString(2, myUser.getUsername());

                _MyPreparedStatement.executeUpdate();
                _MyConnection.commit();
            }

        }catch(Exception e){
            System.out.println(e.toString());

            try{
                _MyConnection.rollback();
            }catch (Exception e2){
                System.out.println(e2.toString());
            }finally {
                hasError = true;
            }

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
        
        if(hasError) {
            throw new TransactionWasNotSavedException();
        }
        
    }
    
    public boolean isValidAdminUser(LogInfo MyUser) {
        boolean myResult = false;
        MyUser.setAuthenticated(false);
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT * " +
                              "FROM a_users, a_access_type " +
                              "WHERE " +
                              "      a_users.username=? AND " +
                              "      a_users.pass=? AND " +
                              "      a_users.active = 1 AND " +
                              "      a_users.access_type = a_access_type.access_type_id AND " +
                              "      a_access_type.sysdefault=1 ";

                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, MyUser.getUsername());
                _MyPreparedStatement.setString(2, MyUser.getPassword());
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                while(_MyResultSet.next()) {
                    myResult = true;
                    
                    //Populated with Data from the database
                    MyUser.setFirstName(_MyResultSet.getString("first_name"));
                    MyUser.setLastName(_MyResultSet.getString("last_name"));
                    MyUser.setUserId(_MyResultSet.getString("user_id"));
                    MyUser.setDepartment(_MyResultSet.getString("dept_id"));
                    MyUser.setAuthenticated(true);
                    break;
                }
                
                if(myResult) {
                    query = "SELECT type_name " +
                            "FROM a_activity_type " +
                            "WHERE type_id=? ";
                    _MyPreparedStatement = _MyConnection.prepareStatement(query);
                    _MyPreparedStatement.setString(1, MyUser.getDepartment());
                    _MyResultSet = _MyPreparedStatement.executeQuery();
                    
                    while(_MyResultSet.next()) {
                        MyUser.setDepartmentName(_MyResultSet.getString(1));
                        break;
                    }
                }
            }

        }catch(Exception e){
            System.out.println(e.toString());
            MyUser.setAuthenticated(false);
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
    
    /**
     * Validates user credential and populate with Log Information
     * @param MyUser
     * @return
     */
    public boolean isValid(LogInfo MyUser) {
        boolean myResult = false;
        MyUser.setAuthenticated(false);
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT * " +
                              "FROM a_users " +
                              "WHERE username=? AND " +
                              "      pass=? AND " +
                              "      active=1 ";
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, MyUser.getUsername());
                _MyPreparedStatement.setString(2, MyUser.getPassword());
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                while(_MyResultSet.next()) {
                    myResult = true;
                    
                    //Populated with Data from the database
                    MyUser.setFirstName(_MyResultSet.getString("first_name"));
                    MyUser.setLastName(_MyResultSet.getString("last_name"));
                    MyUser.setUserId(_MyResultSet.getString("user_id"));
                    MyUser.setDepartment(_MyResultSet.getString("dept_id"));
                    
                    MyUser.setAuthenticated(true);
                    break;
                }
                
                if(myResult) {
                    query = "SELECT type_name " +
                            "FROM a_activity_type " +
                            "WHERE type_id=? ";
                    _MyPreparedStatement = _MyConnection.prepareStatement(query);
                    _MyPreparedStatement.setString(1, MyUser.getDepartment());
                    _MyResultSet = _MyPreparedStatement.executeQuery();
                    
                    while(_MyResultSet.next()) {
                        MyUser.setDepartmentName(_MyResultSet.getString(1));
                        break;
                    }
                }
            }

        }catch(Exception e){
            System.out.println(e.toString());
            myResult = false;
            MyUser.setAuthenticated(false);
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
    
    public void setActions(LogInfo MyUser) {
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query = "SELECT actions " +
                               "FROM a_access_type_details " +
                               "WHERE access_type = " +
                               "                   (SELECT access_type " +
                               "                    FROM a_users " +
                               "                    WHERE username=?) ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, MyUser.getUsername());
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()) {
                    MyUser.addAction(_MyResultSet.getString(1));
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
    
}
