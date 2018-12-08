/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package beans.pages;
   
/**
 *
 * @author jperalta
 */
public class ProjectSearchPage {

    private String ProjectName = " ";
    private String ClientID = " ";
    
    public ProjectSearchPage() {
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public void reset() {
        ProjectName = " ";
        ClientID = " ";
    }
    
}
