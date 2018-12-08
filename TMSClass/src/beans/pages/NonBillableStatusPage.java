/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;

/**
 *
 * @author user
 */
public class NonBillableStatusPage {

    private String UserID = " ";
    private String UserName = " ";
    private String StartDate = " ";
    private String EndDate = " ";

    public NonBillableStatusPage() {
    }

    public void reset() {
        UserID = " ";
        UserName = " ";
        StartDate = " ";
        EndDate = " ";
    }
    
    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
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
    
}
