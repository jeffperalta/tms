/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
  
/**
 *
 * @author jperalta
 */
public class ActivityAssignmentPage {

    private String ProjectName = " ";
    private String ActivityName = " ";
    private String ActivityID = " ";
    private String EmployeeName = " ";
    private String EmployeeID =  " ";
    private String HoursToWork = " ";
    private String Deadline = " ";
    private String DateAssigned = " ";
    private String AllowPastDate = " ";
    private String ActivityListID = " ";
    private String StatusName = " ";
    private String StatusID = " ";
    private String ButtonClicked = " ";

    public ActivityAssignmentPage() {
    }

    public void reset(){
        ProjectName = " ";
        ActivityName = " ";
        EmployeeName = " ";
        HoursToWork = " ";
        Deadline = " ";
        DateAssigned = " ";
        AllowPastDate = " ";
        ActivityListID = " ";
        ActivityID = " ";
        EmployeeID = " ";
        StatusName = " ";
        StatusID = " ";
        ButtonClicked = " ";
    }

    public String getButtonClicked() {
        return ButtonClicked;
    }

    public void setButtonClicked(String ButtonClicked) {
        this.ButtonClicked = ButtonClicked;
    }

    public String getStatusID() {
        return StatusID;
    }

    public void setStatusID(String StatusID) {
        this.StatusID = StatusID;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
    }
    
    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getActivityID() {
        return ActivityID;
    }

    public void setActivityID(String ActivityID) {
        this.ActivityID = ActivityID;
    }
    
    public String getActivityListID() {
        return ActivityListID;
    }

    public void setActivityListID(String ActivityListID) {
        this.ActivityListID = ActivityListID;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String ActivityName) {
        this.ActivityName = ActivityName;
    }

    public String getAllowPastDate() {
        return AllowPastDate;
    }

    public void setAllowPastDate(String AllowPastDate) {
        this.AllowPastDate = AllowPastDate;
    }

    public String getDateAssigned() {
        return DateAssigned;
    }

    public void setDateAssigned(String DateAssigned) {
        this.DateAssigned = DateAssigned;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String Deadline) {
        this.Deadline = Deadline;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String EmployeeName) {
        this.EmployeeName = EmployeeName;
    }

    public String getHoursToWork() {
        return HoursToWork;
    }

    public void setHoursToWork(String HoursToWork) {
        this.HoursToWork = HoursToWork;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }
    
    
    
}
