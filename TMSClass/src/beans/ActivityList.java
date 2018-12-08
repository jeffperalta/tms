/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package beans;
 
/**
 *
 * @author jperalta
 */
public class ActivityList {

    private String ActivityID = " ";
    private String UserID = " ";
    private double HoursToWork = 0.00;
    private double HoursWorked = 0.00;
    private int ActivityListStatus = 0;
    private int ActivityListID = 0;
    private String DeadLine = " ";
    private String DateAssigned = " ";
    private int AllowPastDate = 0;
    private String ActivityName = " ";
    private String ActivityRemarks = " ";
    
    private String ProjectName = " ";
    private String ClientName = " ";
    
    public ActivityList(){
    
    }
    
    public void reset() {
        ActivityID = " ";
        ActivityName = " ";
        UserID = " ";
        HoursToWork = 0.00;
        HoursWorked = 0.00;
        ActivityListStatus = 0;
        ActivityListID = 0;
        DeadLine = " ";
        DateAssigned = " ";
        AllowPastDate = 0;
        ActivityRemarks = " ";
        ProjectName = " ";
        ClientName = " ";
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getActivityRemarks() {
        return ActivityRemarks;
    }

    public void setActivityRemarks(String ActivityRemarks) {
        this.ActivityRemarks = ActivityRemarks;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String ActivityName) {
        this.ActivityName = ActivityName;
    }

    public int getAllowPastDate() {
        return AllowPastDate;
    }

    public void setAllowPastDate(int AllowPastDate) {
        this.AllowPastDate = AllowPastDate;
    }

    public String getDateAssigned() {
        return DateAssigned;
    }

    public void setDateAssigned(String DateAssigned) {
        this.DateAssigned = DateAssigned;
    }

    public String getDeadLine() {
        return DeadLine;
    }

    public void setDeadLine(String DeadLine) {
        this.DeadLine = DeadLine;
    }

    public String getActivityID() {
        return ActivityID;
    }

    public void setActivityID(String ActivityID) {
        this.ActivityID = ActivityID;
    }

    public int getActivityListID() {
        return ActivityListID;
    }

    public void setActivityListID(int ActivityListID) {
        this.ActivityListID = ActivityListID;
    }

    public int getActivityListStatus() {
        return ActivityListStatus;
    }

    public void setActivityListStatus(int ActivityListStatus) {
        this.ActivityListStatus = ActivityListStatus;
    }

    public double getHoursToWork() {
        return HoursToWork;
    }

    public void setHoursToWork(double HoursToWork) {
        this.HoursToWork = HoursToWork;
    }

    public double getHoursWorked() {
        return HoursWorked;
    }

    public void setHoursWorked(double HoursWorked) {
        this.HoursWorked = HoursWorked;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }
    
    
}
