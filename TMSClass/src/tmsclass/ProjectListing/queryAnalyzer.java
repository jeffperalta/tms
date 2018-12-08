/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.ProjectListing;
 
/**
 *
 * @author Abree
 */
import DatabaseConnection.*;
import beans.ActivityList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import beans.pages.BillableTimeLogPage;
import utilities.DateUtility;
import utilities.Formatter;

public class queryAnalyzer {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;
    
    public queryAnalyzer(){
    
    }
    
    public String showProjectListing(String UserId, String ProjectStatusId, String ProjectID, String OrderBy) {
        
        UserId = utilities.Formatter.toProperString(UserId);
        ProjectStatusId = utilities.Formatter.toProperString(ProjectStatusId);
        
        String myResult = " ";
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_activity.activity_name, " +
                              "       a_activity_list.hrs_to_work-a_activity_list.hrs_worked AS Remain, " +
                              "       a_activity_list.deadline, " +
                              "       a_activity_status.end_activity, " +
                              "       a_activity_list.activity_list_id, " +
                              "       a_activity.activity_id," +
                              "       a_activity_list.status " +
                              "FROM a_activity_status RIGHT JOIN (" +
                              "         a_activity RIGHT JOIN a_activity_list ON " +
                              "         a_activity.activity_id = a_activity_list.activity_id) " +
                              "     ON a_activity_status.status_id = a_activity.status " +
                              "WHERE a_activity.status LIKE ?  AND " +
                              "      a_activity_list.user_id=? ";
                              
                if(ProjectID.trim().length() > 0 ) {
                   query = query.concat(" AND a_activity.project_id=? ");
                }
                
                if(OrderBy.trim().length() > 0 ) {
                    query = query.concat(" ORDER BY " + OrderBy);
                }
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                
                if(ProjectStatusId.trim().length() > 0) {
                    _MyPreparedStatement.setString(1, ProjectStatusId + "%");
                }else {
                    _MyPreparedStatement.setString(1, ("%"));
                }
                
                _MyPreparedStatement.setString(2, UserId);
                if(ProjectID.trim().length() > 0 ) {
                   _MyPreparedStatement.setString(3, ProjectID);
                }
                _MyResultSet = _MyPreparedStatement.executeQuery();
                        
                int iTotal = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    //Check if the activity selected is already in the END state
                    if(_MyResultSet.getInt(4) == 1) { 
                        if(_MyResultSet.getInt(7) == 1) {
                            myResult = myResult.concat("<td><img src='../../images/inactiveChkChecked.jpg' width='12' height='12' /></td>");
                        }else{
                            myResult = myResult.concat("<td><img src='../../images/inactiveChk.jpg' width='12' height='12' /></td>");
                        }
                        myResult = myResult.concat("<td>" + _MyResultSet.getString(1) + "</td>");
                    }else{
                        if(_MyResultSet.getInt(7) == 1) {
                            myResult = myResult.concat("<td><input type='checkbox' name='checkbox' value='" + _MyResultSet.getString(5) + "' checked/></td>");
                        }else{
                            myResult = myResult.concat("<td><input type='checkbox' name='checkbox' value='" + _MyResultSet.getString(5) + "' /></td>");
                        }
                        myResult = myResult.concat("<td><a href='/TMS/ProjectSelectionResult?" +
                                                   "ALI=" + _MyResultSet.getString(5) + "&&" + //Activity List ID
                                                   "AI=" + _MyResultSet.getString(6)  + //Activity ID
                                                   "'>" +
                                                   _MyResultSet.getString(1) + "</a></td>");
                    }
                    myResult = myResult.concat("<td><div align='right'>" + Formatter.toDouble(_MyResultSet.getDouble(2)) + "</div></td>");
                    myResult = myResult.concat("<td><div align='center'>" + _MyResultSet.getString(3) + "</div></td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=4><hr size=1></td></tr>");
                    iTotal ++;
                }
                 
                myResult = myResult.concat("<tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td><strong>Result: " + iTotal + "</strong></td></tr>");

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
    
    public String showActivitySchedule(ActivityList myActivityList, BillableTimeLogPage myPage) {
        String myResult = " ";
   
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_schedule.trans_date, " +
                              "       a_schedule.remarks, " +
                              "       a_schedule.hrs_worked, " +
                              "       a_schedule.locked, " +
                              "       a_schedule.schedule_id, " +
                              "       a_schedule.actual_date, " +
                              "       a_def_activity.act_name " +
                              "FROM a_schedule JOIN a_def_activity ON a_def_activity.act_id = a_schedule.activity_type " +
                              "WHERE a_schedule.activity_list_id = ? ";
                if(myPage.getFromDate().trim().length() > 0 && myPage.getToDate().trim().length() > 0) {
                    query = query.concat(" AND (a_schedule.trans_date BETWEEN ? AND ?) ");
                }
                
                query = query.concat(" ORDER BY a_schedule.trans_date DESC ");
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setInt(1, myActivityList.getActivityListID());
                
                if(myPage.getFromDate().trim().length() > 0 && myPage.getToDate().trim().length() > 0) {
                    _MyPreparedStatement.setString(2, myPage.getFromDate());
                    _MyPreparedStatement.setString(3, myPage.getToDate());
                }
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                double dblTotalTimeSpent = 0.00;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    
                    //Have to check for the current date
                    //Cannot delete a record that was created in the past
                    if(_MyResultSet.getInt(4) == 0 || 
                            DateUtility.DateCompare(_MyResultSet.getString(6), DateUtility.now(), constants.DateFormat.DATE_FORMAT) == 0) {
                        myResult = myResult.concat("<td><input type=checkbox name=checkbox value='" + _MyResultSet.getInt(5) + "'></td>");
                    }else{
                        myResult = myResult.concat("<td><img src='../../images/inactiveChk.jpg' width='10' height='10' /></td>");
                    }
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(1) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(7) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(2) + "</td>");
                    myResult = myResult.concat("<td><div align=right>" + _MyResultSet.getString(3) + "</div></td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=5><hr size=1></td></tr>");
                    
                    dblTotalTimeSpent += _MyResultSet.getDouble(3);
                }
                myResult = myResult.concat("<tr><td>&nbsp;</td>" +
                                           "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>" +
                                           "<td><div align=right><strong>" + utilities.Formatter.toDouble(dblTotalTimeSpent) + "</strong></div></td>" +
                                           "</tr>");
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
