/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.ProjectListing;
 
/**
 *
 * @author jperalta
 */
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DatabaseConnection.*;
import beans.*;
import beans.pages.*;

public class sqlHelper {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;
    
    public sqlHelper(){
    
    }
    
    public void setStatus(String[] ActivityListID, String UserID) throws TransactionWasNotSavedException{
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_activity_list " +
                              "SET status=0 " +
                              "WHERE user_id=? AND activity_id NOT IN(" +
                              "             SELECT activity_id " +
                              "             FROM a_activity JOIN a_activity_status ON a_activity.status = a_activity_status.status_id " +
                              "             WHERE end_activity=1)";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, UserID);
                _MyPreparedStatement.executeUpdate();
                
                query = "UPDATE a_activity_list " +
                        "SET status=1 " +
                        "WHERE activity_list_id=?";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                for(int iCtr = 0; iCtr<ActivityListID.length; iCtr++) {
                    _MyPreparedStatement.setString(1, ActivityListID[iCtr]);
                    _MyPreparedStatement.executeUpdate();
                }

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
    
    public void setActivityList(ActivityList myActivityList) {
    
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT * " +
                              "FROM a_activity_list " +
                              "WHERE activity_id=? AND " +
                              "      user_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myActivityList.getActivityID());
                _MyPreparedStatement.setString(2, myActivityList.getUserID());
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()) {
                    myActivityList.setActivityListID(_MyResultSet.getInt("activity_list_id"));
                    myActivityList.setActivityListStatus(_MyResultSet.getInt("status"));
                    myActivityList.setHoursToWork(_MyResultSet.getDouble("hrs_to_work"));
                    myActivityList.setHoursWorked(_MyResultSet.getDouble("hrs_worked"));
                    myActivityList.setDeadLine(_MyResultSet.getString("deadline"));
                    myActivityList.setAllowPastDate(_MyResultSet.getInt("allow_past_date"));
                    myActivityList.setDateAssigned(_MyResultSet.getString("dateassigned"));
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
    
    public void saveSchedule(ActivityList myActivityList, BillableTimeLogPage MyPage) throws TransactionWasNotSavedException {
    
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                
                String query1 = "SELECT allow_past_date " +
                                "FROM a_activity_list " +
                                "WHERE activity_list_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query1);
                _MyPreparedStatement.setInt(1, myActivityList.getActivityListID());
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iLocked = 1;
                while(_MyResultSet.next()) {
                    iLocked = _MyResultSet.getInt(1);
                    break;
                }
                
                String query= "INSERT INTO a_schedule " +
                              "         (trans_date, remarks, " +
                              "          hrs_worked, activity_list_id, " +
                              "          actual_date, locked, activity_type) " +
                              "VALUES(?,?,?,?,?,?,?) ";
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                 
                _MyPreparedStatement.setString(1, MyPage.getTransactionDate());
                _MyPreparedStatement.setString(2, MyPage.getRemarks());
                _MyPreparedStatement.setDouble(3, MyPage.getHoursWorked());
                _MyPreparedStatement.setInt(4, myActivityList.getActivityListID());
                _MyPreparedStatement.setString(5, utilities.DateUtility.now());
                _MyPreparedStatement.setInt(6, iLocked);
                _MyPreparedStatement.setString(7, MyPage.getDefaultActivityID());
                _MyPreparedStatement.executeUpdate();
                
                query = "UPDATE a_activity_list " +
                        "SET hrs_worked = hrs_worked + ? " +
                        "WHERE activity_list_id=? ";
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setDouble(1, MyPage.getHoursWorked());
                _MyPreparedStatement.setInt(2, myActivityList.getActivityListID());
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
    
    public void deleteSchedule(BillableTimeLogPage MyPage) throws TransactionWasNotSavedException {
        boolean hasError= false;
        PreparedStatement pUpdateActivityList = null;
        PreparedStatement pSelectHoursWorked = null;
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "DELETE FROM a_schedule " +
                              "WHERE schedule_id = ? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                
                query = "SELECT hrs_worked " +
                        "FROM a_schedule " +
                        "WHERE schedule_id=? ";
                pSelectHoursWorked = _MyConnection.prepareStatement(query);
                
                query = "UPDATE a_activity_list " +
                        "SET a_activity_list.Hrs_worked = a_activity_list.Hrs_worked - ? " +
                        "WHERE a_activity_list.activity_list_id = (" +
                        "       SELECT a_schedule.activity_list_id " +
                        "       FROM a_schedule " +
                        "       WHERE schedule_id=? " +
                        ") ";
                pUpdateActivityList = _MyConnection.prepareStatement(query);
                
                double dblHrsWorked = 0.00;
                for(int iCtr = 0; iCtr < MyPage.getScheduleID().length; iCtr++) {
                    pSelectHoursWorked.setInt(1, Integer.parseInt(MyPage.getScheduleID()[iCtr]));
                    _MyResultSet = pSelectHoursWorked.executeQuery();
                    _MyResultSet.next();
                    dblHrsWorked = _MyResultSet.getDouble(1);
                    
                    //Update ActivityList Subtracting from the Total Hours worked
                    pUpdateActivityList.setDouble(1, dblHrsWorked);
                    pUpdateActivityList.setString(2, MyPage.getScheduleID()[iCtr]);
                    pUpdateActivityList.executeUpdate();
                    
                    //Actual deletion at Schedule Table
                    _MyPreparedStatement.setString(1, MyPage.getScheduleID()[iCtr]);
                    _MyPreparedStatement.executeUpdate();
                }
                
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

                if(pUpdateActivityList != null) {
                    pUpdateActivityList.close();
                }
                
                if(pSelectHoursWorked != null) {
                    pSelectHoursWorked.close();
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
