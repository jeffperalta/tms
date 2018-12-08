/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package beans;

/**
 *
 * @author jperalta
 */
import java.util.ArrayList;

public class LogInfo {
    
    private String _FirstName = " ",
                   _LastName = " ",
                   _MiddleName = " ",
                   _UserName = " ",
                   _Password = " ",
                   _UserId = " ", 
                   _Department = " ",
                   _DepartmentName = " ";
    private boolean Authenticated = false;
    private String Display = " ";
    
    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }
    
    private ArrayList<String> _Actions = null;
    
    public LogInfo(){
        reset();
    }
    
    public void reset(){
        _FirstName = " ";
        _LastName = " ";
        _MiddleName = " ";
        _UserName = " ";
        _Password = " ";
        _Actions = new ArrayList<String>();
        Authenticated = false;
        Display = " ";
        _Department = " ";
        _DepartmentName = " ";
    }
    
    public void setFirstName(String value) {
        _FirstName = value;
    }
    
    public void setLastName(String value) {
        _LastName = value;
    }
    
    public void setMiddleName(String value) {
        _MiddleName = value;
    }
    
    public void setUsername(String value) {
        _UserName = value;
    }
   
    public void addAction(String value) {
        _Actions.add(value);
    }
    
    public void setPassword(String value) {
        _Password = value;
    }
    
    
    public String getFirstName() {
        return _FirstName;
    }
    
    public String getLastName() {
        return _LastName;
    }
    
    public String getMiddleName() {
        return _MiddleName;
    }
    
    public String getUsername() {
        return _UserName;
    }
    
    public boolean isValidAction(String value) {
        return _Actions.contains(value);
    }
    
    public String getPassword() {
        return _Password;
    }

    public boolean isAuthenticated() {
        return Authenticated;
    }

    public void setAuthenticated(boolean Authenticated) {
        this.Authenticated = Authenticated;
    }
     
    public String checkAccess(String AccessTypeID) {
        String myResult = "disabled";
        if(this.isValidAction(AccessTypeID)) myResult = " ";
        return myResult;
    }

    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String Display) {
        this.Display = Display;
    }

    public String getDepartment() {
        return _Department;
    }

    public void setDepartment(String _Department) {
        this._Department = _Department;
    }

    public String getDepartmentName() {
        return _DepartmentName;
    }

    public void setDepartmentName(String _DepartmentName) {
        this._DepartmentName = _DepartmentName;
    }
    
    
    
}
