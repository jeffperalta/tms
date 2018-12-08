/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author Abree
 */
public class ActivityAssignmentSearchPage {

    private String ProjectID = " ";
    private String ProjectName = " ";
    private String EmployeeID = " ";
    private String EmployeeName = " ";
    private String ActivityID = " ";
    private String ActivityName = " ";
    private String ButtonClicked = " ";
    private String AllowedPastDate = " ";
    
    public ActivityAssignmentSearchPage() {
    }

    public String getAllowedPastDate() {
        return AllowedPastDate;
    }

    public void setAllowedPastDate(String AllowedPastDate) {
        this.AllowedPastDate = AllowedPastDate;
    }
    
    public String getButtonClicked() {
        return ButtonClicked;
    }
    
    public void setButtonClicked(String ButtonClicked) {
        this.ButtonClicked = ButtonClicked;
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

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String EmployeeName) {
        this.EmployeeName = EmployeeName;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String ProjectID) {
        this.ProjectID = ProjectID;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    
    
}
