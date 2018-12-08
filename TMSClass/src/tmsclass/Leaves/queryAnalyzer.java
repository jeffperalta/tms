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
import beans.LogInfo;
import beans.pages.LeaveStatusPage;
import beans.pages.LeaveTimeLogPage;
import beans.pages.LeaveTypeSearchPage;

public class queryAnalyzer {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;
    
    public queryAnalyzer(){
    
    }
    
    public String showLeaveTypes(LeaveTypeSearchPage myPage) {
        String myResult = " ";
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){
            
                String query= "SELECT * " +
                              "FROM a_leaves " +
                              "WHERE leave_name LIKE ? ";
                
                if(myPage.getShowStatusField() == 1) {
                    //just do nothing
                }else{
                    query = query.concat(" AND status=1 ");
                }
                
                query = query.concat(" ORDER BY a_leaves.leave_name ");
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myPage.getName() + "%");
                
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td><a href='/TMS/LeaveTypeSearchResult?" +
                                                "LI=" + _MyResultSet.getString("leave_id") +
                                                "'>" + _MyResultSet.getString("leave_name") + "</a></td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString("remarks") + "</td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=2><hr size=1></td></tr>");
                    iCtr++;
                }
                
                myResult = myResult.concat("<tr><td colspan=2><strong>Result: " + iCtr + "</strong></td></tr>");
                
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
    
    public String showLeaves(LeaveTimeLogPage myPage, LogInfo myUser) {
        String myResult = " ";
        boolean hasDateRangeCriteria = false;
        
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_leave_details.leave_details_id, a_leave_details.trans_date, " +
                              "       a_leave_details.remarks, a_leaves.leave_name, a_leave_details.actual_date, " +
                              "       a_leave_details.locked, a_leave_details.count " +
                              "FROM a_leave_details JOIN a_leaves ON a_leave_details.leave_id = a_leaves.leave_id " +
                              "WHERE a_leave_details.user_id = ? ";
                if(myPage.getFromDate().trim().length() > 0 && myPage.getToDate().trim().length() > 0){
                    query = query.concat(" AND (trans_date BETWEEN ? AND ?) ");
                }
                
                if(myPage.getTypeSearchID().trim().compareToIgnoreCase("-1") != 0 && myPage.getTypeSearchName().trim().length() != 0) {
                    query = query.concat(" AND a_leave_details.leave_id = ? ");
                }
                
                query = query.concat(" ORDER BY a_leave_details.trans_date DESC ");
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myUser.getUserId());
                if(myPage.getFromDate().trim().length() > 0 && myPage.getToDate().trim().length() > 0){
                    _MyPreparedStatement.setString(2, myPage.getFromDate());
                    _MyPreparedStatement.setString(3, myPage.getToDate());
                    hasDateRangeCriteria=true;
                }
                
                if(myPage.getTypeSearchID().trim().compareToIgnoreCase("-1") != 0 && myPage.getTypeSearchName().trim().length() != 0) {
                    if(hasDateRangeCriteria) {
                        _MyPreparedStatement.setInt(4, Integer.parseInt(myPage.getTypeSearchID()));
                    }else{
                        _MyPreparedStatement.setInt(2, Integer.parseInt(myPage.getTypeSearchID()));
                    }
                }
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    if(utilities.DateUtility.DateCompare(_MyResultSet.getString(5), utilities.DateUtility.now(), constants.DateFormat.DATE_FORMAT)==0 ||
                            utilities.DateUtility.DateCompare(_MyResultSet.getString(2), utilities.DateUtility.now(), constants.DateFormat.DATE_FORMAT)>=0 ) {
                            
                        myResult = myResult.concat("<td><input type=checkbox name=checkbox value='" + _MyResultSet.getInt(1) + "'></td>");
                    }else{
                        if(_MyResultSet.getInt(6) == 1) { 
                            myResult = myResult.concat("<td><img src='../../images/inactiveChk.jpg' width='10' height='10' /></td>");                    
                        }else{
                            myResult = myResult.concat("<td><input type=checkbox name=checkbox value='" + _MyResultSet.getInt(1) + "'></td>");
                        }
                    }
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(2) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(3) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(4) + "</td>");
                    
                    if(_MyResultSet.getDouble(7) == 0.5) {
                        myResult = myResult.concat("<td><FONT COLOR='MAROON'>Half Day</FONT></td>");
                    }else{
                        myResult = myResult.concat("<td>Whole Day</td>");
                    }
                        
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=5><hr size=1></td></tr>");
                    iCtr++;
                }
                myResult = myResult.concat("<tr><td colspan=5><strong>Result: " + iCtr + "</strong></td></tr>");
                
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
   
    public String showLeaves(LeaveStatusPage myPage) {
        String myResult = " ";
        boolean hasDateRangeCriteria = false;
     
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_leave_details.leave_details_id, " +
                              "       a_leave_details.remarks, a_leave_details.trans_date, " +
                              "       a_leave_details.locked, a_leaves.leave_name " +
                              "FROM a_leave_details JOIN a_leaves ON a_leave_details.leave_id = a_leaves.leave_id " +
                              "WHERE a_leave_details.user_id=? ";
                if(myPage.getEndDate().trim().length() > 0 && myPage.getStartDate().trim().length() > 0) {
                    query = query.concat(" AND a_leave_details.trans_date BETWEEN ? AND ? ");
                    hasDateRangeCriteria = true;
                }
                query = query.concat(" ORDER BY a_leave_details.trans_date DESC ");
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
               
                _MyPreparedStatement.setString(1, myPage.getUserID());
                if(hasDateRangeCriteria) {
                    _MyPreparedStatement.setString(2, myPage.getStartDate());
                    _MyPreparedStatement.setString(3, myPage.getEndDate());
                }
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    if(_MyResultSet.getInt(4) == 1) { 
                        myResult = myResult.concat("<td><input type=checkbox name=checkbox value='" + _MyResultSet.getInt(1) + "' checked='checked'></td>");                    
                    }else{
                        myResult = myResult.concat("<td><input type=checkbox name=checkbox value='" + _MyResultSet.getInt(1) + "'></td>");
                    }
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(3) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(5) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(2) + "</td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=4><hr size=1></td></tr>");
                    iCtr++; 
                }
                myResult = myResult.concat("<tr><td colspan=4><strong>Result: " + iCtr + "</strong></td></tr>");
                
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
