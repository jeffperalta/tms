/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.WorkUtility;

/**
 *
 * @author Abree
 */

import DatabaseConnection.tmsConnection;
import beans.pages.WorkUtilityPage;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class queryAnalyzer {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;

    public queryAnalyzer() {
    }
    
    public WorkUtilityPage reset(){
        WorkUtilityPage myPage= new WorkUtilityPage();
         
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT DLofSubmission, DaysAllowed, DATE_ADD(DLofSubmission,INTERVAL -DaysAllowed DAY) " +
                              "FROM a_work_days ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyResultSet = _MyPreparedStatement.executeQuery();
                while(_MyResultSet.next()) {
                    myPage.setDeadline(_MyResultSet.getString(1));
                    myPage.setAllowedDays(_MyResultSet.getString(2));
                    myPage.setStartDateForDeadline(_MyResultSet.getString(3));
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
        
        return myPage;
    }
    
    /**
     * @param myTitle
     * @return
     */ 
    public String showAllMessages(String myTitle, boolean showall) {
        String myResult = " ";
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT id, DateCreated, CreatedBy, myTitle " +
                              "FROM c_updates " +
                              "WHERE myTitle LIKE ? ";
                
                if(!showall) {
                    query = query.concat(" AND show_this=1 ");
                }
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myTitle + "%");
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td><a href='/TMS/sUpdateSearchResult?ID=" + _MyResultSet.getString(1) + "'>" + _MyResultSet.getString(4) + "</a></td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(3) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(2) + "</td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=3><hr size=1></td></tr>");
                    iCtr++;
                }
                myResult = myResult.concat("<tr><td colspan=3><strong>Results: " + iCtr + "</strong></td></tr>");

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
    
    public String showUpdates() {
        String myResult = " ";
       
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT id, DATE_FORMAT(DateCreated,'%M %d, %Y'), myTitle, CONCAT(SUBSTRING(myBody,1,30), '...') " +
                              "FROM c_updates " +
                              "WHERE show_this=1 " +
                              "ORDER BY DateCreated DESC " +
                              "LIMIT 0,5 ";
                              
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iCtr =0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<li><h3>" + _MyResultSet.getString(2) + "</h3></li>");
                    myResult = myResult.concat("<li><a href='/TMS/sUpdateSearchResult?ID=" + 
                                _MyResultSet.getString(1) + "'>" + _MyResultSet.getString(3) + ". " + 
                                _MyResultSet.getString(4) + "</a></li>");
                    iCtr++;
                }
                
                if(iCtr > 0) {
                    myResult = myResult.concat("<li><a href='/TMS/updatesviewall.jsp'><h4><strong>View All<strong></h4></a></li>");
                }else{
                    myResult = myResult.concat("<li>No Updates Yet</li>");
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
