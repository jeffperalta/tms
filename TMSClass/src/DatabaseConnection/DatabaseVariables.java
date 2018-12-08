/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DatabaseConnection;
 
/**
 *
 * @author jperalta
 */
public class DatabaseVariables {

    private String _Location;
    private String _UserName;
    private String _Password;
    private String _Name;
    private String _DatabaseDriver;
    
    public DatabaseVariables(){
        
        _Location = " ";
        _UserName = " ";
        _Password = " ";
        _DatabaseDriver=" ";
        _Name = " ";
         
    }
    
    public void setLocation(String value){
        _Location = value;
    }
    
    public void setUserName(String value){
        _UserName = value;
    }
    
    public void setPassword(String value){
        _Password = value;
    }
    
    public void setDbDriver(String value){
        _DatabaseDriver = value;
    }
    
    public void setDatabaseName(String value){
        _Name = value;
    }

    public String getLocation(){
        return _Location;
    }
    
    public String getUserName(){
        return _UserName;
    }
    
    public String getPassword(){
       return _Password;
    }
    
    public String getDbDriver(){
        return _DatabaseDriver;
    }
    
    public String getDBName(){
        return _Name;
    }
    
}
