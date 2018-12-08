/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
    
/**
 *
 * @author jperalta
 */
public class LeavePage {

    private String LeaveID = " ";
    private String LeaveName = " ";
    private String DefaultMaxLeave = " ";
    private String Status = " ";
    private String Remarks = " ";

    public LeavePage() {
    }

    public void reset(){
        LeaveID = " ";
        LeaveName = " ";
        DefaultMaxLeave = " ";
        Status = " ";
        Remarks = " ";
    }
    
    public String getDefaultMaxLeave() {
        return DefaultMaxLeave;
    }

    public void setDefaultMaxLeave(String DefaultMaxLeave) {
        this.DefaultMaxLeave = DefaultMaxLeave;
    }

    public String getLeaveID() {
        return LeaveID;
    }

    public void setLeaveID(String LeaveID) {
        this.LeaveID = LeaveID;
    }

    public String getLeaveName() {
        return LeaveName;
    }

    public void setLeaveName(String LeaveName) {
        this.LeaveName = LeaveName;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    
    
}
