/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author Abree
 */
public class ProjectSelectionPage {

    private String ProjectStatus = "On Going";
    private String OrderBy = " ";
    private String ProjectStatusId = "1";
    private String OrderById = " ";
    private String ProjectID = " ";
    private String ProjectName = " ";

    
    public ProjectSelectionPage(){
    
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String ProjectID) {
        this.ProjectID = ProjectID;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }
    
    
    
    public String getOrderById() {
        return OrderById;
    }

    public void setOrderById(String OrderById) {
        this.OrderById = OrderById;
    }

    public String getProjectStatusId() {
        return ProjectStatusId;
    }

    public void setProjectStatusId(String ProjectStatusId) {
        this.ProjectStatusId = ProjectStatusId;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String OrderBy) {
        this.OrderBy = OrderBy;
    }

    public String getProjectStatus() {
        return ProjectStatus;
    }

    public void setProjectStatus(String ProjectStatus) {
        this.ProjectStatus = ProjectStatus;
    }
    
    
    
    
}
