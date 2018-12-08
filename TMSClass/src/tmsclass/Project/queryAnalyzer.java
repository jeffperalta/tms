/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tmsclass.Project;
  
/**
 *
 * @author jperalta
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

import DatabaseConnection.*;
import beans.pages.ProjectSearchPage;

public class queryAnalyzer {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;
    
    public queryAnalyzer() {
    }

    public String showProject(ProjectSearchPage myPage) {
        String myResult = " ";
        boolean hasClientIdField = false;
        boolean hasProjectNameField = false;
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT a_project.project_id, a_project.project_name, " +
                              "       a_project.remarks, a_client.c_name " + 
                              "FROM a_project JOIN a_client ON a_project.client_id = a_client.client_id ";
                
                if(myPage.getClientID().trim().length() > 0 || myPage.getProjectName().trim().length() > 0) {
                    query = query.concat(" WHERE ");
                    boolean hasLeadingCriteria = false;
                    
                    if(myPage.getClientID().trim().length() > 0) {
                        if(hasLeadingCriteria) query = query.concat(" AND ");
                        query=query.concat(" a_client.client_id = ? ");
                         hasLeadingCriteria = true;
                         hasClientIdField = true;
                    }
                    
                    if(myPage.getProjectName().trim().length() > 0) {
                        if(hasLeadingCriteria) query = query.concat(" AND ");
                        query=query.concat(" a_project.project_name LIKE ? ");
                        hasLeadingCriteria = true;
                        hasProjectNameField = true;
                    }
                }            
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                
                if(hasClientIdField && hasProjectNameField) {
                    _MyPreparedStatement.setString(1, myPage.getClientID());
                    _MyPreparedStatement.setString(2, myPage.getProjectName() + "%");
                }
                
                if(hasClientIdField && !hasProjectNameField) {
                    _MyPreparedStatement.setString(1, myPage.getClientID());
                }
                
                if(!hasClientIdField && hasProjectNameField) {
                    _MyPreparedStatement.setString(1, myPage.getProjectName() + "%");
                }
                
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td><a href='/TMS/ProjectSearchResult?PI=" + _MyResultSet.getString(1) + "'>" + _MyResultSet.getString(2) + "</a></td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(3) + "</td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(4) + "</td>");
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
    
    public static void main(String[] args) {
        ProjectSearchPage x = new ProjectSearchPage();
        x.setClientID("1000");
        
        queryAnalyzer y = new queryAnalyzer();
        y.showProject(x);
    }
    
}
