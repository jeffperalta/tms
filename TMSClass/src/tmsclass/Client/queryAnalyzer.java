/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package tmsclass.Client;
 
/**
 *
 * @author Abree
 */

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DatabaseConnection.*;
import beans.pages.ClientSearchPage;

public class queryAnalyzer {

    private Connection _MyConnection = null;
    private Statement _MyStatement = null;
    private PreparedStatement _MyPreparedStatement = null;
    private ResultSet _MyResultSet = null;
    
    public queryAnalyzer() {
    
    }
    
    public String show(ClientSearchPage myPage) {
        
        myPage.setName(utilities.Formatter.toProperString(myPage.getName().trim()));
        
        String myResult = " ";
        
        try{

                tmsConnection MyConn = new tmsConnection();
                _MyConnection = MyConn.CreateConnection();

                if(!_MyConnection.isClosed()){

                    String query= "SELECT a_client.client_id, a_client.c_name, " +
                                  "       a_client.c_address, a_client.c_contact, " +
                                  "       a_client.c_fax, a_client.c_contact_person, " +
                                  "       a_client.comment " +
                                  "FROM a_client " +
                                  "WHERE a_client.c_name Like ?";

                    _MyPreparedStatement = _MyConnection.prepareStatement(query);
                    _MyPreparedStatement.setString(1, myPage.getName() + "%");
                    _MyResultSet = _MyPreparedStatement.executeQuery();
                    
                    int iTotal = 0;
                    while(_MyResultSet.next()) {
                        myResult = myResult.concat("<tr>");
                        myResult = myResult.concat("<td><a href='/TMS/ClientSearchResult?" +
                                                   "CI=" + _MyResultSet.getString(1) + //Client Id
                                                   "'>" + _MyResultSet.getString(2) + "</td>");
                        myResult = myResult.concat("<td>" + _MyResultSet.getString(3) + "</td>");
                        myResult = myResult.concat("<td>" + _MyResultSet.getString(4) + "</td>");
                        myResult = myResult.concat("</tr>");
                        myResult = myResult.concat("<tr><td colspan=3><hr size=1></td></tr>");
                        iTotal ++;
                    }
                    
                    myResult = myResult.concat("<tr><td><strong>Result: " + iTotal + "</strong></td></tr>");
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
    }
    
}
