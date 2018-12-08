/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package tmsclass.ActivityAssignment;
 
/**
 *
 * @author Abree
 */
import DatabaseConnection.tmsConnection;
import beans.pages.ActivityAssignmentSearchPage;
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
    
    public String show(ActivityAssignmentSearchPage myPage) {
        String myResult = " ";
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_activity_list.activity_list_id, " +
                              "       CONCAT(SUBSTRING(a_users.first_name, 1, 1), '. ', a_users.last_name) AS EmployeeName, " +
                              "       a_activity.activity_name, " +
                              "       a_project.project_name, " +
                              "       a_activity_status.status_name " +
                              "FROM a_activity_status RIGHT JOIN ((a_activity RIGHT JOIN (a_activity_list LEFT JOIN a_users ON a_activity_list.user_id = a_users.user_id) ON a_activity.activity_id = a_activity_list.activity_id) LEFT JOIN a_project ON a_activity.project_id = a_project.project_id) ON a_activity_status.status_id = a_activity.status " +
                              "WHERE a_activity_list.user_id LIKE ? AND " +
                              "      a_activity_list.activity_id LIKE ? AND " +
                              "      a_activity.project_id LIKE ? AND " +
                              "      a_activity_list.allow_past_date=? " +
                              "ORDER BY CONCAT(SUBSTRING(a_users.first_name, 1, 1), '. ', a_users.last_name), a_activity.activity_name ";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myPage.getEmployeeID().trim() + "%");
                _MyPreparedStatement.setString(2, myPage.getActivityID().trim() + "%");
                _MyPreparedStatement.setString(3, myPage.getProjectID().trim() + "%");
                _MyPreparedStatement.setInt(4, Integer.parseInt(myPage.getAllowedPastDate()));
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td><a href='/TMS/sActivityAssignmentSearchResult?ALID=" + 
                                _MyResultSet.getString(1) + "'>" + _MyResultSet.getString(2) + "</a></td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(4) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(5) + "</td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=3><hr size=1></td></tr>");
                    iCtr++;
                }
                
                myResult = myResult.concat("<tr><td><strong>Results: " + iCtr + "</strong></td></tr>");

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
        ActivityAssignmentSearchPage _MyPage = new ActivityAssignmentSearchPage();
        queryAnalyzer QA = new queryAnalyzer();
        System.out.println(QA.show(_MyPage));
    }
}
