/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author jperalta
 */
public class NonBillableTypeSearchPage {
    private String Name = " ";
    private String ShowAll = "0";
    
    public void reset() {
        Name = " ";
        ShowAll = "0";
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getShowAll() {
        return ShowAll;
    }

    public void setShowAll(String ShowAll) {
        this.ShowAll = ShowAll;
    }
    
}
