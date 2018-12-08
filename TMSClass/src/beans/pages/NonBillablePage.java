/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author jperalta
 */
public class NonBillablePage {

    private String NonBillableID = " ";
    private String Name = " ";
    private String Status = " ";
    private String Remarks = " ";

    public NonBillablePage() {
    }
    
    public void reset(){
        NonBillableID = " ";
        Name = " ";
        Status = " ";
        Remarks = " ";
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNonBillableID() {
        return NonBillableID;
    }

    public void setNonBillableID(String NonBillableID) {
        this.NonBillableID = NonBillableID;
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
