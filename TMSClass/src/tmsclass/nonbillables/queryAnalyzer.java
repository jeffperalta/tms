/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.nonbillables;
 
/**
 *
 * @author jperalta
 */

import DatabaseConnection.tmsConnection;
import beans.LogInfo;
import beans.pages.NonBillableStatusPage;
import beans.pages.NonBillableTimeLogPage;
import beans.pages.NonBillableTypeSearchPage;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utilities.DateUtility;
 
public class queryAnalyzer {

    private Connection _MyConnection = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;
    private ResultSet _MyResultSet = null;
    
    public queryAnalyzer(){
    
    }
    
    public String showNonBillables(NonBillableTypeSearchPage MyPage){
        String myResult = " ";
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT * " +
                              "FROM a_non_billable_list " +
                              "WHERE non_bill_name LIKE ? ";
                if(MyPage.getShowAll().trim().compareToIgnoreCase("0") == 0) {
                    query = query.concat(" AND status=1 ");
                }
                query = query.concat(" ORDER BY non_bill_name ");
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, MyPage.getName() + "%");
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td><a href='/TMS/NonBillableSearchResult?" +
                                               "NI=" + _MyResultSet.getString("non_bill_id") +
                                               "'>" + _MyResultSet.getString("non_bill_name") + "</a></td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString("remarks") + "</td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=2><hr size=1></td></tr>");
                    iCtr++;
                }
                myResult = myResult.concat("<tr><td><strong>Result:" + iCtr + "</strong></td></tr>");
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
    
    public String showNonBillableSchedule(NonBillableTimeLogPage myPage, LogInfo myUser) {
        String myResult = " ";
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT * " +
                              "FROM a_non_billable JOIN a_non_billable_list ON a_non_billable.non_bill_type=a_non_billable_list.non_bill_id " +
                              "WHERE a_non_billable.user_id = ? ";
                if(myPage.getDateFrom().trim().length() > 0 && myPage.getDateTo().trim().length() > 0) {
                    query = query.concat(" AND (trans_date BETWEEN ? AND ?) ");
                }
                
                query = query.concat("ORDER BY trans_date DESC ");
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myUser.getUserId());
                if(myPage.getDateFrom().trim().length() > 0 && myPage.getDateTo().trim().length() > 0) {
                    _MyPreparedStatement.setString(2, myPage.getDateFrom());
                    _MyPreparedStatement.setString(3, myPage.getDateTo());
                }
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iCtr = 0;
                double dblTotalHoursElapsed = 0.00;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    if(_MyResultSet.getInt("locked") == 0 || 
                        DateUtility.DateCompare(_MyResultSet.getString("actual_date"), DateUtility.now(), constants.DateFormat.DATE_FORMAT) == 0) {
                        myResult = myResult.concat("<td><input type=checkbox name=checkbox value='" + _MyResultSet.getInt("schedule_id") + "'></td>");
                    }else{
                        myResult = myResult.concat("<td><img src='../../images/inactiveChk.jpg' width='10' height='10' /></td>");
                    }
                    
                    myResult = myResult.concat("<td>" + _MyResultSet.getString("a_non_billable.trans_date") + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString("a_non_billable_list.non_bill_name") + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString("a_non_billable.remarks") + "</td>");
                    myResult = myResult.concat("<td><div align='right'>" + _MyResultSet.getString("a_non_billable.hrs_elapsed") + "</div></td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=5><hr size=1></td></tr>");
                    iCtr++;
                    dblTotalHoursElapsed += _MyResultSet.getDouble("a_non_billable.hrs_elapsed");
                }
                myResult = myResult.concat("<tr><td><strong>Result:" + iCtr + "</strong></td><td></td><td></td><td></td><td><div align='right'><strong>" + dblTotalHoursElapsed + "</strong></div></td></tr>");
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
    
    public String showNonBillables(NonBillableStatusPage myPage) {
        String myResult = " ";
        boolean hasDateRangeCriteria = false;
     
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_non_billable.schedule_id, " +
                              "       a_non_billable.remarks, a_non_billable.trans_date, " +
                              "       a_non_billable.locked, a_non_billable_list.non_bill_name " +
                              "FROM a_non_billable JOIN a_non_billable_list ON " +
                              "     a_non_billable.non_bill_type = a_non_billable_list.non_bill_id " +
                              "WHERE a_non_billable.user_id=? ";
                if(myPage.getEndDate().trim().length() > 0 && myPage.getStartDate().trim().length() > 0) {
                    query = query.concat(" AND a_non_billable.trans_date BETWEEN ? AND ? ");
                    hasDateRangeCriteria = true;
                }
                query = query.concat(" ORDER BY a_non_billable.trans_date DESC ");
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
