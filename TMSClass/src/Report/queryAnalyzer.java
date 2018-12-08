/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Report;
 
/**
 *
 * @author jperalta
 */ 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import DatabaseConnection.*;

public class queryAnalyzer {

    private Connection _MyConnection = null;
    private PreparedStatement _MyPreparedStatement = null;
    private Statement _MyStatement = null;
    private ResultSet _MyResultSet = null;
    
    public queryAnalyzer() {
    }

    private String showReports(String SystemUser) {
        String myResult = " ";
        
        try{

            tmsConnection MyConn = new tmsConnection();
            _MyConnection = MyConn.CreateConnection();

            if(!_MyConnection.isClosed()){

                String query= "SELECT ReportName, ReportLink, ReportDesc " +
                              "FROM a_report_with_links " +
                              "WHERE system_user=" + SystemUser;
                
                _MyPreparedStatement = _MyConnection.prepareStatement(query);
                _MyResultSet = _MyPreparedStatement.executeQuery();
                
                while(_MyResultSet.next()) {
                    myResult = myResult.concat("<tr>");
                    myResult = myResult.concat("<td><a href='" + _MyResultSet.getString(2) + "'>" + _MyResultSet.getString(1) + "</a></td>");
                    myResult = myResult.concat("<td>" + _MyResultSet.getString(3) + "</td>");
                    myResult = myResult.concat("</tr>");
                    myResult = myResult.concat("<tr><td>&nbsp;</td></tr>");
                    myResult = myResult.concat("<tr><td colspan=2><hr size=1></td></tr>");
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
    
    public String showTransReports() {
        return showReports("0");
    }
    
    public String showSysReports(){
        return showReports("1");
    }
    
}
