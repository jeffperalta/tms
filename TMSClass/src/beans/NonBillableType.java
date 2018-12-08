/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package beans;

import beans.pages.NonBillablePage;
import utilities.Formatter;
 
/**
 *
 * @author jperalta
 */
public class NonBillableType {

    private String NonBillableTypeID = " ";
    private String NonBillableTypeName = " ";
    private String Remarks = " ";
    private int Status = 0;
    
    public NonBillableType(){
    
    }

    public NonBillableType(NonBillablePage myPage){
        NonBillableTypeID = Formatter.toProperString(myPage.getNonBillableID());
        NonBillableTypeName = Formatter.toProperString(myPage.getName());
        if(myPage.getStatus().trim().length() !=0) Status = Integer.parseInt(myPage.getStatus());
        Remarks = Formatter.toProperString(myPage.getRemarks());
    }
    
    public void reset() {
        NonBillableTypeID = " ";
        NonBillableTypeName = " ";
        Status = 0;
        Remarks = " ";
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
    
    
    public String getNonBillableTypeID() {
        return NonBillableTypeID;
    }

    public void setNonBillableTypeID(String NonBillableTypeID) {
        this.NonBillableTypeID = NonBillableTypeID;
    }

    public String getNonBillableTypeName() {
        return NonBillableTypeName;
    }

    public void setNonBillableTypeName(String NonBillableTypeName) {
        this.NonBillableTypeName = NonBillableTypeName;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }
    
    
    
}
