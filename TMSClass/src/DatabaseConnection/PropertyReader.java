/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DatabaseConnection;
 
/**
 *
 * @author jperalta
 */

import java.util.Properties;
import java.io.FileInputStream;

public class PropertyReader {

    private String _fileName = "";
    
    public PropertyReader(){
        _fileName = constants.DatabaseConstants.PROPERTY_LOCATION;
    }
    
    public DatabaseVariables initialize(){
    
        DatabaseVariables objDbVariables = new DatabaseVariables();
        Properties objProperties = new Properties();
        
        try{
            
            objProperties.load(new FileInputStream(_fileName));
            
            objDbVariables.setLocation(objProperties.getProperty("db_location"));
            objDbVariables.setPassword(objProperties.getProperty("db_password"));
            objDbVariables.setUserName(objProperties.getProperty("db_username"));
            objDbVariables.setDbDriver(objProperties.getProperty("db_driver"));
            objDbVariables.setDatabaseName(objProperties.getProperty("db_name"));
            
        } catch(Exception e){
            System.out.println("[PropertyReader.java] Error " + e.toString());
        }
        
        return objDbVariables;
        
    }
    
    public static void main(String[] args){
        PropertyReader x = new PropertyReader();
        System.out.println(x._fileName);
    }
    
}
