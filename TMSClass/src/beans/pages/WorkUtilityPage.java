/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package beans.pages;
    
/**
 *
 * @author Abree
 */
public class WorkUtilityPage {

    private String StartDateForDeadline = " ";
    private String Deadline = " ";
    private String AllowedDays = " ";
    private String MessageTitle = " ";
    private String MessageBody = " ";
    private String MessageNumber = " ";
    private String DateEntered = " ";
    private String Visible = " ";
    private String Author = " ";

    public WorkUtilityPage() {
    }

    public void reset(){
        Deadline = " ";
        AllowedDays = " ";
        StartDateForDeadline = " ";
    } 
    
    public void resetMessage() {
        MessageTitle = " ";
        MessageBody = " ";
        MessageNumber = " ";
        DateEntered = " ";
        Visible = " ";
        Author = " ";
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

    public String getMessageBody() {
        return MessageBody;
    }

    public void setMessageBody(String MessageBody) {
        this.MessageBody = MessageBody;
    }

    public String getMessageTitle() {
        return MessageTitle;
    }

    public void setMessageTitle(String MessageTitle) {
        this.MessageTitle = MessageTitle;
    }

    public String getMessageNumber() {
        return MessageNumber;
    }

    public void setMessageNumber(String MessageNumber) {
        this.MessageNumber = MessageNumber;
    }

    public String getDateEntered() {
        return DateEntered;
    }

    public void setDateEntered(String DateEntered) {
        this.DateEntered = DateEntered;
    }

    public String getVisible() {
        return Visible;
    }

    public void setVisible(String Visible) {
        this.Visible = Visible;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getStartDateForDeadline() {
        return StartDateForDeadline;
    }

    public void setStartDateForDeadline(String StartDateForDeadline) {
        this.StartDateForDeadline = StartDateForDeadline;
    }
    
    
    
}
