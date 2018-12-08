/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
 
/**
 *
 * @author Abree
 */
public class WorkUtilityPageTemp {

    private String Deadline = " ";
    private String AllowedDays = " ";

    public WorkUtilityPageTemp() {
    }

    public void reset(){
        Deadline = " ";
        AllowedDays = " ";
    } 
    
    public String getAllowedDays() {
        return AllowedDays;
    }

    public void setAllowedDays(String AllowedDays) {
        this.AllowedDays = AllowedDays;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String Deadline) {
        this.Deadline = Deadline;
    } 
    
}
