/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;
 
/**
 *
 * @author Abree
 */
public class WorkUtilityTemp {

    private String Deadline =" ";
    private int AllowedDays = 0;

    public WorkUtilityTemp() {
    }

    public void reset() {
        Deadline = " ";
        AllowedDays = 0;
    }
    
    public int getAllowedDays() {
        return AllowedDays;
    }

    public void setAllowedDays(int AllowedDays) {
        this.AllowedDays = AllowedDays;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String Deadline) {
        this.Deadline = Deadline;
    }
    
}
