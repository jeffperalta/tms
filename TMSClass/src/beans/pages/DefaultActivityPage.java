/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;

/**
 *
 * @author user
 */
public class DefaultActivityPage {

    private String ActivityID = " ";
    private String ActivityName = " ";
    private String ActivityStatus = " ";
    private String DepartmentID = " ";
    private String DepartmentName = " ";
    
    public DefaultActivityPage() {
    }

    public void reset(){
        ActivityID = " ";
        ActivityName = " ";
        ActivityStatus = " ";
        DepartmentID = " ";
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

    public String getActivityStatus() {
        return ActivityStatus;
    }

    public void setActivityStatus(String ActivityStatus) {
        this.ActivityStatus = ActivityStatus;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String DepartmentID) {
        this.DepartmentID = DepartmentID;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

}
