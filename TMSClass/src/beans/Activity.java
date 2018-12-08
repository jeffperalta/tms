/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package beans;

import beans.pages.ActivityPage;
import utilities.Formatter;
  
/**
 *
 * @author Abree
 */
public class Activity {
    private String ActivityID = " ";
    private String ActivityName = " ";
    private String ActivityType = " ";
    private int ActivityStatus = 0;
    private String Remarks = " ";
    private String ProjectID = " ";
    
    public Activity() {
    }
     
    public Activity(ActivityPage myPage) {
        ActivityID = Formatter.toProperString(myPage.getActivityID());
        ActivityName = Formatter.toProperString(myPage.getActivityName());
        ActivityType = Formatter.toProperString(myPage.getActivityTypeID());
        
        if(Formatter.isNumber(myPage.getStatusID())) {
            ActivityStatus = Integer.parseInt(myPage.getStatusID());
        }else{
            ActivityStatus = 0;
        }
        
        Remarks = Formatter.toProperString(myPage.getRemarks());
        ProjectID = Formatter.toProperString(myPage.getProjectID());
    }

    public void reset(){
        ActivityID = " ";
        ActivityName = " ";
        ActivityType = " ";
        ActivityStatus = 0;
        Remarks = " ";
        ProjectID = " ";
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

    public int getActivityStatus() {
        return ActivityStatus;
    }

    public void setActivityStatus(int ActivityStatus) {
        this.ActivityStatus = ActivityStatus;
    }

    public String getActivityType() {
        return ActivityType;
    }

    public void setActivityType(String ActivityType) {
        this.ActivityType = ActivityType;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String ProjectID) {
        this.ProjectID = ProjectID;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }
}
