/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
  
/**
 *
 * @author jperalta
 */
public class LeaveTimeLogPage {
  
    private String TransactionDate = utilities.DateUtility.now();
    private String EndDate = utilities.DateUtility.now();
    private String Type = " ";
    private String Remarks = " ";
    private String ToDate = utilities.DateUtility.getYear(utilities.DateUtility.now()) + constants.DateFormat.DATE_SEPARATOR + "12" + constants.DateFormat.DATE_SEPARATOR + "31";
    private String FromDate = utilities.DateUtility.getYear(utilities.DateUtility.now()) + constants.DateFormat.DATE_SEPARATOR + "01" + constants.DateFormat.DATE_SEPARATOR + "01";
    private String[] ItemsToDelete = null;
    private String MaxLeave = " ";
    private String MaxUsedLeave = " ";
    private String LeaveRemarks = " ";
    private String LeaveName = " ";
    
    private Boolean HalfDay = false;
    
    //Combo Box
    private String TypeSearchID = " ";
    private String TypeSearchName = " ";

    public LeaveTimeLogPage(){
    
    }
    
    public void reset() {
        TransactionDate = utilities.DateUtility.now();
        Type = utilities.DateUtility.now();
        Remarks = " ";
        EndDate = TransactionDate;
        FromDate = " ";
        ItemsToDelete = null;
        ToDate = " ";
        MaxLeave = " ";
        MaxUsedLeave = " ";
        TypeSearchID = " ";
        TypeSearchName = " ";
        LeaveRemarks = " ";
        LeaveName = " ";
        HalfDay = false;
    }

    public String getLeaveName() {
        return LeaveName;
    }

    public void setLeaveName(String LeaveName) {
        this.LeaveName = LeaveName;
    }

    public String getLeaveRemarks() {
        return LeaveRemarks;
    }

    public void setLeaveRemarks(String LeaveRemarks) {
        this.LeaveRemarks = LeaveRemarks;
    }

    public String getTypeSearchID() {
        return TypeSearchID;
    }

    public void setTypeSearchID(String TypeSearchID) {
        this.TypeSearchID = TypeSearchID;
    }

    public String getTypeSearchName() {
        return TypeSearchName;
    }

    public void setTypeSearchName(String TypeSearchName) {
        this.TypeSearchName = TypeSearchName;
    }
    
     public String getMaxLeave() {
        return MaxLeave;
    }

    public void setMaxLeave(String MaxLeave) {
        this.MaxLeave = MaxLeave;
    }

    public String getMaxUsedLeave() {
        return MaxUsedLeave;
    }

    public void setMaxUsedLeave(String MaxUsedLeave) {
        this.MaxUsedLeave = MaxUsedLeave;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String[] getItemsToDelete() {
        return ItemsToDelete;
    }

    public void setItemsToDelete(String[] ItemsToDelete) {
        this.ItemsToDelete = ItemsToDelete;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
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

    public Boolean getHalfDay() {
        return HalfDay;
    }

    public void setHalfDay(Boolean HalfDay) {
        this.HalfDay = HalfDay;
    }
    
    
}
