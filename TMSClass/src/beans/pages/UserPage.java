/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author jperalta
 */
public class UserPage extends beans.User{

    private String AccessTypeName = " ";
    private String Active =  " ";
    private String AccessType = " ";
    private String DepartmentName = " ";
    
    public UserPage() {
        super();
    }

    @Override
    public void reset(){
        super.reset();
        AccessTypeName = " ";
        Active = " ";
        AccessType = " ";
        DepartmentName = " ";
    }

    public String getAccessTypeR() {
        return AccessType;
    }

    public void setAccessTypeR(String AccessType) {
        this.AccessType = AccessType;
    }

    public String getActiveR() {
        return Active;
    }

    public void setActiveR(String Active) {
        this.Active = Active;
    }

    
    
    public String getAccessTypeName() {
        return AccessTypeName;
    }

    public void setAccessTypeName(String AccessTypeName) {
        this.AccessTypeName = AccessTypeName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }
    
    
}
