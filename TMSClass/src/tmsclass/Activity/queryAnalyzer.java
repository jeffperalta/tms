/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.Activity;
 
/**
 *
 * @author Abree
 */
import DatabaseConnection.tmsConnection;
import beans.pages.ActivitySearchPage;
import beans.pages.DefaultActivitySearchPage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import utilities.Formatter;

public class queryAnalyzer {

    private Connection _MyConnection = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;
    private ResultSet _MyResultSet = null;

    public queryAnalyzer() {
    }

    public String show(ActivitySearchPage myPage){
        String myResult = " ";

        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_activity.activity_id, " +
                              "       a_activity.activity_name, " +
                              "       a_activity.remarks, " +
                              "       a_activity_status.status_name " +
                              "FROM a_activity JOIN a_activity_status ON " +
                              "     a_activity.status = a_activity_status.status_id " +
                             "WHERE a_activity.project_id LIKE ? AND " +
                             "      a_activity.activity_name LIKE ? ";
                if(Formatter.isNumber(myPage.getActivityStatusID())) {
                    query = " AND a_activity.status=? ";
                }
                
                              
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myPage.getProjectID() + "%");
                _MyPreparedStatement.setString(2, myPage.getActivityName() + "%");
                if(Formatter.isNumber(myPage.getActivityStatusID())) {
                    _MyPreparedStatement.setInt(3, Integer.parseInt(myPage.getActivityStatusID()));
                }
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td><a href='/TMS/sActivitySearchResult?AI=" + 
                                _MyResultSet.getString(1) + "'>" + 
                                _MyResultSet.getString(2) + "</a>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(3) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(4) + "</td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=3><hr size=1></td></tr>");
                    iCtr++;
                }
                myResult = myResult.concat("<tr><td><strong>Result: " + iCtr + "</strong></td></tr>");

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
    
    public String show(DefaultActivitySearchPage myPage) {
        String myResult = " ";
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_def_activity.act_id, a_def_activity.act_name, " +
                              "       a_def_activity.dept_id, a_def_activity.status, " +
                              "       a_department.dept_name " +
                              "FROM a_def_activity JOIN a_department " +
                              "    ON a_def_activity.dept_id = a_department.dept_id " +
                              "WHERE a_def_activity.act_name LIKE ? AND " +
                              "      a_department.dept_id LIKE ? ";
                
                if(myPage.getStatus().trim().length() != 0) {
                    query = query.concat(" AND a_def_activity.status=? ");
                }
                
                              
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myPage.getActivityName() + "%");
                _MyPreparedStatement.setString(2, myPage.getDepartmentID() + "%");
                if(myPage.getStatus().trim().length() != 0) {
                    _MyPreparedStatement.setInt(3, Integer.parseInt(myPage.getStatus()));
                }
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td><a href='/TMS/sDefaultActivitySearchResult?AI=" + 
                                _MyResultSet.getString(1) + "'>" + 
                                _MyResultSet.getString(2) + "</a>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(5) + "</td>");
                    
                    if(_MyResultSet.getInt(4) == 0) {
                        myResult = myResult.concat("<td>Inactive</td>");
                    }else if(_MyResultSet.getInt(4) == 1) {
                        myResult = myResult.concat("<td>Active</td>");
                    }
                    
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=3><hr size=1></td></tr>");
                    iCtr++;
                }
                myResult = myResult.concat("<tr><td><strong>Result: " + iCtr + "</strong></td></tr>");

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

    public static void main(String[] args) {
        ActivitySearchPage myPage = new ActivitySearchPage();
        queryAnalyzer QA = new queryAnalyzer();
        myPage.setActivityName("De");
        System.out.println(QA.show(myPage));
    }
    
}
