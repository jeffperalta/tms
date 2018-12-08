/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
  
/**
 *
 * @author Abree
 */
public class ChangePasswordPage {
    
    private String UserName = "";
    private String OldPassword = "";
    private String NewPassword = "";
    private String ConfirmPassword = "";

    public ChangePasswordPage() {
    }

    public void reset(){
        UserName = "";
        OldPassword = "";
        NewPassword = "";
        ConfirmPassword = "";
    }
    
    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String ConfirmPassword) {
        this.ConfirmPassword = ConfirmPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String OldPassword) {
        this.OldPassword = OldPassword;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    
    
    
}
