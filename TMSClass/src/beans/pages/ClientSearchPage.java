/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author jperalta
 */
public class ClientSearchPage {

    private String Name = " ";
    
    public ClientSearchPage() {
    }

    public void reset(){
        Name = " ";
    }
    
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
