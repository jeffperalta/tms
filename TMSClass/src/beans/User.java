/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package beans;
 
import beans.pages.UserPage;
import utilities.Formatter;
 
/**
 *
 * @author jperalta
 */
public class User {
 
    private String UserID = " ";
    private String FirstName = " ";
    private String MiddleName = " ";
    private String LastName = " ";
    private String DateEntered = " ";
    private String UserName = "";
    private String Password = "";
    private int Active =  1;
    private int AccessType = 0;
    private String ContactInformation = " ";
    private String ConfirmPassword = "";
    private String DepartmentID = "";

    
    public User() {
    }
    
    public User(UserPage myPage) {
        UserID = Formatter.toProperString(myPage.getUserID());
        FirstName = Formatter.toProperString(myPage.getFirstName());
        MiddleName = Formatter.toProperString(myPage.getMiddleName());
        LastName = Formatter.toProperString(myPage.getLastName());
        DateEntered = Formatter.toProperString(myPage.getDateEntered());
        UserName = Formatter.toProperString(myPage.getUserName());
        Password = Formatter.toProperString(myPage.getPassword());
        Active = Integer.parseInt(Formatter.toProperString(myPage.getActiveR()));
        AccessType = Integer.parseInt(Formatter.toProperString(myPage.getAccessTypeR()));
        ContactInformation = Formatter.toProperString(myPage.getContactInformation());
        ConfirmPassword = Formatter.toProperString(myPage.getConfirmPassword());
        DepartmentID = Formatter.toProperString(myPage.getDepartmentID());
    }

    public void reset(){
        UserID = " ";
        FirstName = " ";
        MiddleName = " ";
        LastName = " ";
        DateEntered = " ";
        UserName = "";
        Password = "";
        Active = 1;
        AccessType = 0;
        ContactInformation = " ";
        ConfirmPassword = "";
        DepartmentID = "";
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String ConfirmPassword) {
        this.ConfirmPassword = ConfirmPassword;
    }
    
    public String getContactInformation() {
        return ContactInformation;
    }

    public void setContactInformation(String ContactInformation) {
        this.ContactInformation = ContactInformation;
    }
    
    public int getAccessType() {
        return AccessType;
    }

    public void setAccessType(int AccessType) {
        this.AccessType = AccessType;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int Active) {
        this.Active = Active;
    }

    public String getDateEntered() {
        return DateEntered;
    }

    public void setDateEntered(String DateEntered) {
        this.DateEntered = DateEntered;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String MiddleName) {
        this.MiddleName = MiddleName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String DepartmentID) {
        this.DepartmentID = DepartmentID;
    }
           
}
