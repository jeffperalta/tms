/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;

import utilities.DateUtility;

/**
 *
 * @author jperalta
 */
public class BillableTimeLogPage {

    private String TransactionDate = DateUtility.now();
    private Double HoursWorked = 0.00; 
    private String Remarks = " ";
    
    private String RHoursWorked = " ";
    private String FromDate = utilities.DateUtility.now();
    private String ToDate = utilities.DateUtility.now();
    private String[] ScheduleID = null;

    private String DefaultActivityID = " ";
    
    public BillableTimeLogPage(){
    
    }
    
    public void reset() {
        TransactionDate = " ";
        HoursWorked = 0.00;
        Remarks = " ";
        RHoursWorked = " ";
        DefaultActivityID = " ";
    }

    public String[] getScheduleID() {
        return ScheduleID;
    }

    public void setScheduleID(String[] ScheduleID) {
        this.ScheduleID = ScheduleID;
    }
    
    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
    }
    
    public String getRHoursWorked() {
        return RHoursWorked;
    }
 
    public void setRHoursWorked(String RHoursWorked) {
        this.RHoursWorked = RHoursWorked;
    }

    
    public double getHoursWorked() {
        return HoursWorked;
    }

    public void setHoursWorked(double HoursWorked) {
        this.HoursWorked = HoursWorked;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    public String getDefaultActivityID() {
        return DefaultActivityID;
    }

    public void setDefaultActivityID(String DefaultActivityID) {
        this.DefaultActivityID = DefaultActivityID;
    }
    
    
    
}
