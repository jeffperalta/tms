/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package beans;

import beans.pages.LeavePage;
import utilities.Formatter;
 
/**
 *
 * @author jperalta
 */
public class LeaveType {

    private int LeaveID = 0;
    private String LeaveName = " ";
    private int Status = 1;
    private String Remarks = " ";
    private int MaxLeave = 0;
    
    public LeaveType(){
    
    }
    
    public LeaveType(LeavePage myPage){
        if(myPage.getLeaveID().trim().length() != 0) LeaveID = Integer.parseInt(myPage.getLeaveID());
        LeaveName = Formatter.toProperString(myPage.getLeaveName());
        if(myPage.getStatus().trim().length() !=0 ) Status = Integer.parseInt(myPage.getStatus());
        Remarks = Formatter.toProperString(myPage.getRemarks());
        if(myPage.getDefaultMaxLeave().trim().length() != 0) MaxLeave = Integer.parseInt(myPage.getDefaultMaxLeave());
    }
    
    public void reset(){
        LeaveID = 0;
        LeaveName = " ";
        Status = 1;
        Remarks = " ";
        MaxLeave = 0;
    }

    public int getMaxLeave() {
        return MaxLeave;
    }

    public void setMaxLeave(int MaxLeave) {
        this.MaxLeave = MaxLeave;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public int getLeaveID() {
        return LeaveID;
    }

    public void setLeaveID(int LeaveID) {
        this.LeaveID = LeaveID;
    }

    public String getLeaveName() {
        return LeaveName;
    }

    public void setLeaveName(String LeaveName) {
        this.LeaveName = LeaveName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
    
    
    
}
