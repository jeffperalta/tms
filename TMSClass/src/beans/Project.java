/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package beans;

import beans.pages.ProjectPage;
import utilities.DateUtility;
import utilities.Formatter;
 
/**
 *
 * @author jperalta
 */
public class Project {
 
    private String ProjectID = " ";
    private String ProjectName = " ";
    private String StartDate = " ";
    private String EndDate = " ";
    private String ClientID = " ";
    private double ProjectAmount = 0.00;
    private String Remarks = " ";
    
    private String ProjectType = " ";
    
    public Project() {
    }
    
    public Project(ProjectPage myPage) {
        ProjectID = Formatter.toProperString(myPage.getProjectID());
        ProjectName = Formatter.toProperString(myPage.getProjectName());
        
        if(myPage.getStartDate().trim().length() != 0) {
            StartDate = Formatter.toProperString(myPage.getStartDate());
        }else{
            StartDate = DateUtility.now();
        }
        if(myPage.getEndDate().trim().length() != 0) {
            EndDate = Formatter.toProperString(myPage.getEndDate());
        }else{
            EndDate= DateUtility.now();
        }
        
        ClientID = Formatter.toProperString(myPage.getClientID());
        
        if(myPage.getProjectAmount().trim().length()!=0) {
            ProjectAmount = utilities.Formatter.toProperNumber(Formatter.toProperString(myPage.getProjectAmount()));
        }else{
            ProjectAmount = 0.00;
        }
        
        Remarks = Formatter.toProperString(myPage.getRemarks());
        ProjectType = myPage.getProjectType();
        
    }
    
    public void reset() {
        ProjectID = " ";
        ProjectName = " ";
        StartDate = " ";
        EndDate = " ";
        ClientID = " ";
        ProjectAmount = 0.00;
        Remarks = " ";
        ProjectType = " ";
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public double getProjectAmount() {
        return ProjectAmount;
    }

    public void setProjectAmount(double ProjectAmount) {
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
    
    

}
