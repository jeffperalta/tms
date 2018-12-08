/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;

/**
 *
 * @author user
 */
public class DefaultActivitySearchPage {
    private String ActivityID = " ", 
            ActivityName = " ", 
            DepartmentID = " ",
            Status = " ",
            DepartmentName = " ";

    public DefaultActivitySearchPage() {
    }

    public void reset() {
        ActivityID = " ";
        ActivityName = " ";
        DepartmentID = " ";
        Status = " ";
        DepartmentName = " ";
    }
    
    public String getActivityID() {
        return ActivityID;
    }

    public void setActivityID(String ActivityID) {
        this.ActivityID = ActivityID;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String ActivityName) {
        this.ActivityName = ActivityName;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String DepartmentID) {
        this.DepartmentID = DepartmentID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }
    
           
}
