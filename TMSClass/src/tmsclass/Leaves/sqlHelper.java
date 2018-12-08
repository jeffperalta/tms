/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.Leaves;
   
/**
 *
 * @author jperalta
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

import DatabaseConnection.*;
import beans.LeaveType;
import beans.LogInfo;
import beans.pages.LeaveTimeLogPage;
import tmsclass.ProjectListing.TransactionWasNotSavedException;


public class sqlHelper {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;
    
    public sqlHelper(){
    
    }
    
    public void save(LeaveTimeLogPage myPage, LogInfo myUser) throws TransactionWasNotSavedException {
        boolean hasError= false;
        PreparedStatement pSelectMax = null;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                
                //Selection of the maximum primary key
                String query= "SELECT MAX(leave_details_id) " +
                              "FROM a_leave_details ";
                pSelectMax = _MyConnection.prepareStatement(query);
                
                query = "SELECT MAX(grouping)+1 " +
                        "FROM a_leave_details ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iMaxGrouping = 0;
                while(_MyResultSet.next()) {
                    iMaxGrouping= _MyResultSet.getInt(1);
                    break;
                }
                iMaxGrouping++;
                
                //Insert a leave record
                query = "INSERT INTO a_leave_details " +
                        "   (leave_id, user_id, trans_date, " +
                        "    remarks, leave_details_id, actual_date, grouping, locked, count) " +
                        "VALUES(?,?,?,?,?,?,?,?,?) ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                
                
                for(int iCtr=0; iCtr <= utilities.DateUtility.DateDifference(myPage.getTransactionDate(), myPage.getEndDate()); iCtr++) {
                    _MyResultSet = pSelectMax.executeQuery();
                    int iPrimaryKey = 0;
                    while(_MyResultSet.next()) {
                        iPrimaryKey = _MyResultSet.getInt(1);
                        iPrimaryKey++;
                        break;
                    }
                    
                    //Save to User's Leave List
                    _MyPreparedStatement.setInt(1, Integer.parseInt(myPage.getType()));
                    _MyPreparedStatement.setString(2, myUser.getUserId());
                    _MyPreparedStatement.setString(3, utilities.DateUtility.addDays(myPage.getTransactionDate(), iCtr));
                    _MyPreparedStatement.setString(4, myPage.getRemarks());
                    _MyPreparedStatement.setInt(5, iPrimaryKey);
                    _MyPreparedStatement.setString(6, utilities.DateUtility.now());
                    _MyPreparedStatement.setInt(7, iMaxGrouping);
                    _MyPreparedStatement.setInt(8, 1);
                    
                    if(myPage.getHalfDay()) {
                        _MyPreparedStatement.setDouble(9, 0.5);
                    }else{
                        _MyPreparedStatement.setDouble(9, 1.0);
                    }
                    
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
                
                if(pSelectMax != null) {
                    pSelectMax.close();
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
    
    public void delete(LeaveTimeLogPage myPage) throws TransactionWasNotSavedException{
        boolean hasError= false;
        int iMaxGrouping = 0;
        PreparedStatement pEditGrouping = null;
        PreparedStatement pGetMaxGrouping = null;
        PreparedStatement pGetMyDate = null;
        PreparedStatement pGetMyGrouping = null;
        
        int myPreviousGrouping = 0;
        String myPreviousDate = " ";
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                
                String query = "SELECT MAX(grouping)+1 " +
                               "FROM a_leave_details ";
                pGetMaxGrouping = _MyConnection.prepareStatement(query);
                
                query = "UPDATE a_leave_details " +
                        "SET grouping =? " +
                        "WHERE trans_date > ? AND " +
                        "      grouping = ? ";
                pEditGrouping = _MyConnection.prepareStatement(query);
                
                query = "SELECT trans_date FROM a_leave_details WHERE leave_details_id=?";
                pGetMyDate = _MyConnection.prepareStatement(query);
                
                query = "SELECT grouping FROM a_leave_details WHERE leave_details_id=?";
                pGetMyGrouping = _MyConnection.prepareStatement(query);
                
                query= "DELETE FROM a_leave_details " +
                        "WHERE leave_details_id=?";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                
                for(int iCtr = 0; iCtr < myPage.getItemsToDelete().length; iCtr++) {
                    
                    //Update Groupings - Get Max Grouping
                    _MyResultSet = pGetMaxGrouping.executeQuery();
                    while(_MyResultSet.next()) {
                        iMaxGrouping= _MyResultSet.getInt(1);
                        break;
                    }
                    
                    //Get Previous Grouping
                    pGetMyGrouping.setInt(1, Integer.parseInt(myPage.getItemsToDelete()[iCtr]));
                    _MyResultSet = pGetMyGrouping.executeQuery();
                    while(_MyResultSet.next()) {
                        myPreviousGrouping= _MyResultSet.getInt(1);
                        break;
                    }
                    
                    //Get Previous Date
                    pGetMyDate.setInt(1, Integer.parseInt(myPage.getItemsToDelete()[iCtr]));
                    _MyResultSet = pGetMyDate.executeQuery();
                    while(_MyResultSet.next()) {
                        myPreviousDate = _MyResultSet.getString(1);
                        break;
                    }
                    
                    //Update grouping - Edit grouping of all that are below the deleted item
                    pEditGrouping.setInt(1, iMaxGrouping);
                    pEditGrouping.setString(2, myPreviousDate);
                    pEditGrouping.setInt(3, myPreviousGrouping);
                    pEditGrouping.executeUpdate();
                   
                    //Actual Deletion
                    _MyPreparedStatement.setInt(1, Integer.parseInt(myPage.getItemsToDelete()[iCtr]));
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
                
                if(pEditGrouping != null) {
                    pEditGrouping.close();
                }
                
                if(pGetMaxGrouping != null) {
                    pGetMaxGrouping.close();
                }
                
                if(pGetMyDate != null) {
                    pGetMyDate.close();
                }
                
                if(pGetMyGrouping != null) {
                    pGetMyGrouping.close();
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
    
    public boolean isExcedeLeave(LeaveTimeLogPage myPage, LogInfo myUser) {
        boolean myResult = true;
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT 'VALID' " +
                              "FROM  a_leave_cnt " +
                              "WHERE a_leave_cnt.leave_id=? AND " + 
                              "      a_leave_cnt.user_id=? AND " + 
                              "      ((a_leave_cnt.max_leave ) >= " + 
                              "       (SELECT IF(ISNULL(SUM(count)),0,SUM(count)) " + 
                              "        FROM a_leave_details " + 
                              "        WHERE a_leave_details.user_id=? AND " + 
                              "              a_leave_details.leave_id=? AND " + 
                              "              YEAR(a_leave_details.trans_date)=? ) + ?) ";

                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, Integer.parseInt(myPage.getType()));
                _MyPreparedStatement.setString(2, myUser.getUserId());
                _MyPreparedStatement.setString(3, myUser.getUserId());
                _MyPreparedStatement.setInt(4, Integer.parseInt(myPage.getType()));
                _MyPreparedStatement.setInt(5, Integer.parseInt(utilities.DateUtility.getYear(myPage.getTransactionDate())));
                _MyPreparedStatement.setLong(6, utilities.DateUtility.DateDifference(myPage.getTransactionDate(), myPage.getEndDate()) + 1);
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()) {
                    myResult=false; //Does it excede the total allowed leave
                    break;
                }
            }

        }catch(Exception e){
            System.out.println(e.toString());
            myResult=true;
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
    
    public void refreshLeaveDetails(LeaveTimeLogPage myPage, LogInfo myUser) {
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT IF(ISNULL(SUM(count)),0,SUM(count)) " + 
                              "FROM a_leave_details " + 
                              "WHERE a_leave_details.user_id=? AND " + 
                              "      a_leave_details.leave_id=? AND " + 
                              "      YEAR(a_leave_details.trans_date)=?  ";

                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myUser.getUserId());
                _MyPreparedStatement.setInt(2, Integer.parseInt(myPage.getType()));
                _MyPreparedStatement.setInt(3, Integer.parseInt(utilities.DateUtility.getYear(myPage.getTransactionDate())));
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                myPage.setMaxUsedLeave(" ");
                while(_MyResultSet.next()) {
                    if(_MyResultSet.getString(1) == null) {
                        myPage.setMaxUsedLeave("0");
                    }else{
                        myPage.setMaxUsedLeave(_MyResultSet.getString(1));
                    }
                    
                    break;
                }
                
                query = "SELECT max_leave " +
                        "FROM a_leave_cnt " +
                        "WHERE leave_id=? AND " +
                        "      user_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, Integer.parseInt(myPage.getType()));
                _MyPreparedStatement.setString(2, myUser.getUserId());
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                myPage.setMaxLeave(" ");
                while(_MyResultSet.next()) {
                    myPage.setMaxLeave(_MyResultSet.getString(1));
                    break; 
                } 
                
                query = "SELECT * " +
                        "FROM a_leaves " +
                        "WHERE leave_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, Integer.parseInt(myPage.getType()));
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                myPage.setLeaveName(" ");
                myPage.setLeaveRemarks(" ");
                while(_MyResultSet.next()) {
                    myPage.setLeaveName(_MyResultSet.getString("leave_name"));
                    myPage.setLeaveRemarks(_MyResultSet.getString("remarks"));
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
    
    public void add(LeaveType myLeave) throws TransactionWasNotSavedException {
    
        boolean hasError= false;
        int iMaxID = 0;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "SELECT MAX(Leave_id) " +
                              "FROM a_leaves ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()) {
                    iMaxID = _MyResultSet.getInt(1) + 1;
                    break;
                }
                
                query = "INSERT INTO a_leaves " +
                        "(leave_id, leave_name, status, remarks, max_leave) " +
                        "VALUES(?,?,?,?,?) ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, iMaxID);
                _MyPreparedStatement.setString(2, myLeave.getLeaveName());
                _MyPreparedStatement.setInt(3, myLeave.getStatus());
                _MyPreparedStatement.setString(4, myLeave.getRemarks());
                _MyPreparedStatement.setInt(5, myLeave.getMaxLeave());
                _MyPreparedStatement.executeUpdate();
                
                query = "INSERT INTO a_leave_cnt " +
                        "(leave_id, user_id, max_leave) " +
                        "SELECT ?, user_id, ? " +
                        "FROM a_users ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, iMaxID);
                _MyPreparedStatement.setInt(2, myLeave.getMaxLeave());
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
    
    public void edit(LeaveType myLeave) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query= "UPDATE a_leaves " +
                              "SET leave_name=?, status=?, remarks=?, max_leave=? " +
                              "WHERE leave_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myLeave.getLeaveName());
                _MyPreparedStatement.setInt(2, myLeave.getStatus());
                _MyPreparedStatement.setString(3, myLeave.getRemarks());
                _MyPreparedStatement.setInt(4, myLeave.getMaxLeave());
                _MyPreparedStatement.setInt(5, myLeave.getLeaveID());
                
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
    
    public void delete(LeaveType myLeave) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                _MyConnection.setAutoCommit(false);
                String query = "DELETE " +
                               "FROM a_leave_cnt " +
                               "WHERE leave_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, myLeave.getLeaveID());
                _MyPreparedStatement.executeUpdate();
                
                query = "DELETE " +
                        "FROM a_leaves " +
                        "WHERE leave_id=? ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, myLeave.getLeaveID());
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
   
    public void saveUserLeave(String[] myData, String UserID) throws TransactionWasNotSavedException {
        
        boolean hasError= false;
        String[] Split;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_leave_cnt " +
                              "SET max_leave=? " +
                              "WHERE user_id=? AND leave_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(2, UserID);
                
                for(int iCtr=0; iCtr < myData.length; iCtr++) {
                    if(myData[iCtr].trim().length() == 0)  break;
                    
                    Split = myData[iCtr].split(constants.TextFormat.SEPARATOR);
                    //LeaveID~~~~max leave
                    _MyPreparedStatement.setInt(3, Integer.parseInt(Split[0]));
                    _MyPreparedStatement.setInt(1, Integer.parseInt(Split[1]));
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
    
    public void editStatus(String[] myData, String UserID) throws TransactionWasNotSavedException {
        boolean hasError= false;
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "UPDATE a_leave_details " +
                              "SET locked=0 " +
                              "WHERE user_id=? ";
                _MyConnection.setAutoCommit(false);
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, UserID);
                _MyPreparedStatement.executeUpdate();
                
                if(myData != null) {
                     query  = "UPDATE a_leave_details " +
                              "SET locked=1 " +
                              "WHERE leave_details_id=? ";
                    _MyPreparedStatement = _MyConnection.prepareStatement(query);
                    
                    for(int iCtr=0; iCtr < myData.length; iCtr++) {
                        if(myData[iCtr].trim().length() == 0)  break;
                        _MyPreparedStatement.setInt(1, Integer.parseInt(myData[iCtr]));
                        _MyPreparedStatement.executeUpdate();
                    }
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
}
