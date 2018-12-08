/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author jperalta
 */
public class NonBillableTimeLogPage {

    private String TransactionDate = utilities.DateUtility.now();
    private String Type = " ";
    private String HoursElapsed = " ";
    private String Remarks = " ";
    private String DateFrom = utilities.DateUtility.now();
    private String DateTo = utilities.DateUtility.now();
    private String[] ScheduleID = null;

    public NonBillableTimeLogPage(){
    
    }
    
    public void reset() {
        TransactionDate = utilities.DateUtility.now();
        Type = " ";
        HoursElapsed = " ";
        Remarks = " ";
        DateFrom = utilities.DateUtility.now();
        DateTo = utilities.DateUtility.now();
        ScheduleID = null;
    }
    
    public String[] getScheduleID() {
        return ScheduleID;
    }

    public void setScheduleID(String[] ScheduleID) {
        this.ScheduleID = ScheduleID;
    }

    public String getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(String DateFrom) {
        this.DateFrom = DateFrom;
    }

    public String getDateTo() {
        return DateTo;
    }

    public void setDateTo(String DateTo) {
        this.DateTo = DateTo;
    }

    public String getHoursElapsed() {
        return HoursElapsed;
    }

    public void setHoursElapsed(String HoursElapsed) {
        this.HoursElapsed = HoursElapsed;
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

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }
    
    
    
}
