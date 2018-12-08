/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author Abree
 */
public class ActivityPage {
    
    private String ActivityID = " ";
    private String ProjectID = " ";
    private String ProjectName = " ";
    private String ActivityName = " ";
    private String StatusID = " ";
    private String StatusName = " ";
    private String ActivityTypeID = " ";
    private String ActivityTypeName = " ";
    private String Remarks = " ";
    private String ButtonClicked = " ";

    public ActivityPage() {
    }
    
    public void reset(){
        ActivityID = " ";
        ProjectID = " ";
        ProjectName = " ";
        ActivityName = " ";
        StatusID = " ";
        StatusName = " ";
        ActivityTypeID = " ";
        ActivityTypeName = " ";
        Remarks = " ";
        ButtonClicked = " ";
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

    public String getActivityTypeID() {
        return ActivityTypeID;
    }

    public void setActivityTypeID(String ActivityTypeID) {
        this.ActivityTypeID = ActivityTypeID;
    }

    public String getActivityTypeName() {
        return ActivityTypeName;
    }

    public void setActivityTypeName(String ActivityTypeName) {
        this.ActivityTypeName = ActivityTypeName;
    }

    public String getButtonClicked() {
        return ButtonClicked;
    }

    public void setButtonClicked(String ButtonClicked) {
        this.ButtonClicked = ButtonClicked;
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

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
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
      
}

