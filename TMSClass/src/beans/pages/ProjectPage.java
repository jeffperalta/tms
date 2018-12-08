/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.pages;
   
/**
 *
 * @author jperalta
 */
public class ProjectPage {
 
    private String ProjectID = " ";
    private String ProjectName = " ";
    private String StartDate = " ";
    private String EndDate = " ";
    private String ClientID = " ";
    private String ClientName = " ";
    private String ProjectAmount = " ";
    private String Remarks = " ";
    private String ButtonClicked = " ";
    
    private String ProjectType = " ";
    private String ProjectStatus = " ";
    
    public ProjectPage() {
    }

    public void reset() {
        ProjectID = " ";
        ProjectName = " ";
        StartDate = " ";
        EndDate = " ";
        ClientID = " ";
        ClientName = " ";
        ProjectAmount = " ";
        Remarks = " ";
        ButtonClicked = " ";
        ProjectType = " ";
        ProjectStatus = " ";
    }

    public String getButtonClicked() {
        return ButtonClicked;
    }

    public void setButtonClicked(String ButtonClicked) {
        this.ButtonClicked = ButtonClicked;
    }
    
    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getProjectAmount() {
        return ProjectAmount;
    }

    public void setProjectAmount(String ProjectAmount) {
        this.ProjectAmount = ProjectAmount;
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

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getProjectType() {
        return ProjectType;
    }

    public void setProjectType(String ProjectType) {
        this.ProjectType = ProjectType;
    }

    public String getProjectStatus() {
        return ProjectStatus;
    }

    public void setProjectStatus(String ProjectStatus) {
        this.ProjectStatus = ProjectStatus;
    }
    
    
    
}
