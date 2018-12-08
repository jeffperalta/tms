/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author jperalta
 */
public class LeaveTypeSearchPage {
    
    private String Name = " ";
    private int ShowStatusField = 0;
    private String StatusID = "-1";
    private String StatusName = " ";
    
    public LeaveTypeSearchPage(){
        
    }
    
    public void reset() {
        Name = " ";
        ShowStatusField = 0;
        StatusID = "-1";
        StatusName = " ";
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getShowStatusField() {
        return ShowStatusField;
    }

    public void setShowStatusField(int ShowStatusField) {
        this.ShowStatusField = ShowStatusField;
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
