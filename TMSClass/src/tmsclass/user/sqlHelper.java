/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.user;
 
/**
 *
 * @author jperalta
 */
  
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import DatabaseConnection.*;
import beans.User;
import tmsclass.ProjectListing.TransactionWasNotSavedException;

public class sqlHelper {
 
    private Connection _MyConnection = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;
    private ResultSet _MyResultSet = null;

    public sqlHelper() {
    }
    
    public void edit(User myUser) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "UPDATE a_users " +
                              "SET first_name=?, middle_name=?, " +
                              "    last_name=?, date_entered=?, " +
                              "    username=?, pass=?, active=?, " +
                              "    access_type=?, contact_info=?, " +
                              "    dept_id=? " +
                              "WHERE user_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myUser.getFirstName());
                _MyPreparedStatement.setString(2, myUser.getMiddleName());
                _MyPreparedStatement.setString(3, myUser.getLastName());
                _MyPreparedStatement.setString(4, myUser.getDateEntered());
                _MyPreparedStatement.setString(5, myUser.getUserName());
                _MyPreparedStatement.setString(6, myUser.getPassword());
                _MyPreparedStatement.setInt(7, myUser.getActive());
                _MyPreparedStatement.setInt(8, myUser.getAccessType());
                _MyPreparedStatement.setString(9, myUser.getContactInformation());
                _MyPreparedStatement.setString(10, myUser.getDepartmentID());
                _MyPreparedStatement.setString(11, myUser.getUserID());
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
    
    public void delete(User myUser) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "DELETE FROM a_leave_cnt " +
                              "WHERE user_id=?";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myUser.getUserID());
                
                query="DELETE FROM a_users " +
                      "WHERE user_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myUser.getUserID());
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
    
    public void add(User myUser) throws TransactionWasNotSavedException {
   
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "INSERT INTO a_users " +
                              " (user_id, first_name, middle_name, " +
                              "  last_name, date_entered, username, pass, " +
                              "  active, access_type, contact_info, dept_id) " +
                              "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                
                _MyConnection.setAutoCommit(false);
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myUser.getUserID());
                _MyPreparedStatement.setString(2, myUser.getFirstName());
                _MyPreparedStatement.setString(3, myUser.getMiddleName());
                _MyPreparedStatement.setString(4, myUser.getLastName());
                _MyPreparedStatement.setString(5, myUser.getDateEntered());
                _MyPreparedStatement.setString(6, myUser.getUserName());
                _MyPreparedStatement.setString(7, myUser.getPassword());
                _MyPreparedStatement.setInt(8, myUser.getActive());
                _MyPreparedStatement.setInt(9, myUser.getAccessType());
                _MyPreparedStatement.setString(10, myUser.getContactInformation());
                _MyPreparedStatement.setString(11, myUser.getDepartmentID());
                
                _MyPreparedStatement.executeUpdate();
                
                query="INSERT INTO a_leave_cnt " +
                      "     (leave_id, user_id, max_leave) " +
                      "SELECT leave_id, ?, max_leave " +
                      "FROM a_leaves ";
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myUser.getUserID());
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
 
    public String[] getAllMyLeaves(String UserID) {
        String[] myResult = new String[101];
        
        try{

            for(int iCtr = 0; iCtr <= 100; iCtr++){
                myResult[iCtr] = " ";
            }
            
            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT leave_id " +
                              "FROM a_leave_cnt " +
                              "WHERE user_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, UserID);
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult[iCtr++] = _MyResultSet.getString(1);
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
    
}
