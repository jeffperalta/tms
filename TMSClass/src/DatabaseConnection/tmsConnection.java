/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DatabaseConnection;
 
/**
 *
 * @author jperalta
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class tmsConnection {

    private String _ConnectionString ="";
    private PropertyReader _MyPropertyReader = null;
    private DatabaseVariables _MyDbVars = null;
    
    public tmsConnection(){
        PrepareConnectionString();
    }
    
    public String getConnectionString(){
        return _ConnectionString;
    }
    
    private void PrepareConnectionString(){
        _MyPropertyReader = new PropertyReader();
        _MyDbVars = new DatabaseVariables();
        
        _MyDbVars = _MyPropertyReader.initialize();
        _ConnectionString = _MyDbVars.getDbDriver() + "://" + 
                            _MyDbVars.getLocation() + "/" + 
                            _MyDbVars.getDBName();
    }

    /**
     * Create a new instance of GAS Connection
     * @return GAS Connection
     */
    public Connection CreateConnection(){
       
        Connection _MyConnection = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            _MyConnection = DriverManager.getConnection(_ConnectionString, _MyDbVars.getUserName(), _MyDbVars.getPassword() );  
        }catch (Exception e){
            //LOG ERROR
        }
         
        return _MyConnection;
        
    }
}
