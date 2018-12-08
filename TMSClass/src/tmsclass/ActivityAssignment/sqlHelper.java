/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.ActivityAssignment;
 
/**
 *
 * @author jperalta
 */

import DatabaseConnection.tmsConnection;
import beans.ActivityAssignment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import tmsclass.ProjectListing.TransactionWasNotSavedException;

public class sqlHelper {

    private Connection _MyConnection = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;
    private ResultSet _MyResultSet = null;
    
    public sqlHelper() {
    }

    public void edit(ActivityAssignment myBean) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_activity_list " +
                              "SET hrs_to_work=?, " +
                              "    deadline=?, " +
                              "    dateassigned=?, " +
                              "    allow_past_date=? " +
                              "WHERE activity_list_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setDouble(1, myBean.getHoursToWork());
                _MyPreparedStatement.setString(2, myBean.getDeadline());
                _MyPreparedStatement.setString(3, myBean.getDateAssigned());
                _MyPreparedStatement.setInt(4, myBean.getAllowPastDate());
                _MyPreparedStatement.setInt(5, myBean.getActivityListID());
                _MyPreparedStatement.executeUpdate();
                
                query = "UPDATE a_schedule " +
                        "SET locked=? " +
                        "WHERE activity_list_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                
                if(myBean.getAllowPastDate() == 1) {
                    _MyPreparedStatement.setInt(1, 0);
                }else{
                    _MyPreparedStatement.setInt(1, 1);
                }
                _MyPreparedStatement.setInt(2, myBean.getActivityListID());
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
    
    public void add(ActivityAssignment myBean) throws TransactionWasNotSavedException {
        boolean hasError= false;
        int intMaxID = 0;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "SELECT MAX(activity_list_id) " +
                              "FROM a_activity_list ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()) {
                    intMaxID = _MyResultSet.getInt(1) + 1;
                    break;
                }
                
                query = "INSERT INTO a_activity_list " +
                        "(activity_list_id, activity_id, " +
                        "   user_id, hrs_to_work, hrs_worked, status, " +
                        "   deadline, dateAssigned, allow_past_date) " +
                        "VALUES(?,?,?,?,?,?,?,?,?) ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, intMaxID);
                _MyPreparedStatement.setString(2, myBean.getActivityID());
                _MyPreparedStatement.setString(3, myBean.getUserID());
                _MyPreparedStatement.setDouble(4, myBean.getHoursToWork());
                _MyPreparedStatement.setDouble(5, 0.00);
                _MyPreparedStatement.setInt(6, 0);
                _MyPreparedStatement.setString(7, myBean.getDeadline());
                _MyPreparedStatement.setString(8, myBean.getDateAssigned());
                _MyPreparedStatement.setInt(9, 0);
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
    
    public void delete(ActivityAssignment myBean) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "DELETE " +
                              "FROM a_activity_list " +
                              "WHERE activity_list_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, myBean.getActivityListID());
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
    
}
