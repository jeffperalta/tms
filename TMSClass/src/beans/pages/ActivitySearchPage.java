/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author Abree
 */
public class ActivitySearchPage {

    private String ProjectID =  "";
    private String ProjectName = "";
    private String ActivityName = "";
    private String ActivityStatusID = "";
    private String ActivityStatusName = "";

    public ActivitySearchPage() {
    }

    public void reset() {
        ProjectID = "";
        ProjectName= "";
        ActivityName= "";
        ActivityStatusID="";
        ActivityStatusName = "";
    }
    
    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String ActivityName) {
        this.ActivityName = ActivityName;
    }

    public String getActivityStatusID() {
        return ActivityStatusID;
    }

    public void setActivityStatusID(String ActivityStatusID) {
        this.ActivityStatusID = ActivityStatusID;
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

    public String getActivityStatusName() {
        return ActivityStatusName;
    }

    public void setActivityStatusName(String ActivityStatusName) {
        this.ActivityStatusName = ActivityStatusName;
    }
    
    
    
}
