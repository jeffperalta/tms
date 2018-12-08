/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

/**
 *
 * @author user
 */
public class DefaultActivity {

    private String ActivityID = " ";
    private String ActivityName = " ";
    private String DepartmentID = " ";
    private int Status = 1;
    
    public DefaultActivity() {
    }

    public void reset(){
        ActivityID = " ";
        ActivityName = " ";
        DepartmentID = " ";
        Status = 1;
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
    
}
