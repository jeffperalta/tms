/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package beans;

import beans.pages.ActivityAssignmentPage;
import utilities.DateUtility;
import utilities.Formatter;
 
/**
 *
 * @author Abree
 */
public class ActivityAssignment {

    private int ActivityListID = 0;
    private String ActivityID = " ";
    private String UserID = " ";
    private double HoursToWork = 0.00;
    private double HoursWorked = 0.00;
    private int Status = 0;
    private String Deadline = " ";
    private String DateAssigned = " ";
    private int AllowPastDate = 0;
    
    public ActivityAssignment() {
    }
    
    public ActivityAssignment(ActivityAssignmentPage myPage) {
        if(myPage.getActivityListID().trim().length() != 0) {
            ActivityListID = Integer.parseInt(myPage.getActivityListID());
        }else{
            ActivityListID = 0;
        }
        
        ActivityID = Formatter.toProperString(myPage.getActivityID());
        UserID = Formatter.toProperString(myPage.getEmployeeID());
        HoursToWork = Formatter.toProperNumber(myPage.getHoursToWork());
        HoursWorked = 0.00;
        
        if(myPage.getStatusID().trim().length() != 0) {
            Status = Integer.parseInt(Formatter.toProperString(myPage.getStatusID()));
        }else{
            Status = 0;
        }
        
        Deadline = myPage.getDeadline().trim();
        
        DateAssigned = myPage.getDateAssigned().trim();
        if(DateAssigned.trim().length() ==0) {
            DateAssigned = DateUtility.now();
        }
        
        if(myPage.getAllowPastDate().trim().length() != 0) {
            AllowPastDate = Integer.parseInt(myPage.getAllowPastDate());
        }else{
            AllowPastDate = 0;
        }
    }
    
    public void reset(){
        ActivityListID = 0;
        ActivityID = " ";
        UserID = " ";
        HoursToWork = 0.00;
        HoursWorked = 0.00;
        Status = 0;
        Deadline  = " ";
        DateAssigned = " ";
        AllowPastDate = 0;
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

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String Deadline) {
        this.Deadline = Deadline;
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    
    
}
