/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.user;
  
/**
 *
 * @author Abree
 */ 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import DatabaseConnection.*;
import beans.pages.UserSearchPage;

public class queryAnalyzer {
 
    private Connection _MyConnection = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;
    private ResultSet _MyResultSet = null;

    public queryAnalyzer() {
    }
    
    public String show(UserSearchPage myPage) {
        String myResult = " ";
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_users.user_id, MID(CONCAT(a_users.last_name, ', ', a_users.first_name),1,20) AS Name, " +
                              "       a_users.contact_info, a_users.date_entered, MID(a_department.dept_name,1,20) " +
                              "FROM a_users JOIN a_department ON a_users.dept_id = a_department.dept_id " +
                              "WHERE first_name LIKE ? AND last_name LIKE ? " +
                              "ORDER BY CONCAT(last_name, ', ', first_name)";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, myPage.getFirstName().trim() + "%");
                _MyPreparedStatement.setString(2, myPage.getLastName().trim() + "%");
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                int iCtr = 0;
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td><a href='/TMS/sUserSearchResult?ID=" + _MyResultSet.getString(1) + 
                                                    "'>" + _MyResultSet.getString(2) + "</a></td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(5) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(3) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(4) + "</td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=4><hr size=1></td></tr>");
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
    
    public String showUserLeaves(String UserID) {
        String myResult = " ";
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_leaves.leave_id, a_leaves.leave_name, a_leave_cnt.max_leave " +
                              "FROM a_leave_cnt JOIN a_leaves ON a_leave_cnt.leave_id = a_leaves.leave_id " +
                              "WHERE user_id=?";
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyPreparedStatement.setString(1, UserID);
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(1) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(2) + "</td>");
                    myResult = myResult.concat("<td><input name='txtLeave" + _MyResultSet.getString(1) + "' id='txtLeave" + _MyResultSet.getString(1) + "' type='text' value='" + _MyResultSet.getString(3) + "' size='10' maxlength='3' /></td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td colspan=3><hr size=1></td></tr>");
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
    
    public static void main(String args[]) {
        UserSearchPage myPage = new UserSearchPage();
        myPage.setFirstName("r");
        myPage.setLastName(" ");
        
        queryAnalyzer qa = new queryAnalyzer();
        System.out.println(qa.show(myPage));
        
        System.out.println(qa.showUserLeaves("20000"));
    }
    
}
