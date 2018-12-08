/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Report;
 
import java.util.ArrayList;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class ReportData {
    
    private String ReportCode = " ";
    private String ReportName = " ";
    private String JRXMLPath = " ";
    private String SqlText = " ";
    private String ReportType = constants.Reports.REPORT_TYPE_PDF;
   
    private String ReportLocation = "F:\\New_Thinkers\\TMS\\build\\web\\myReports\\";
    private String WebReportLocation = "http://192.168.1.116:8084/TMS/myReports/";
    private int SystemReport = 0;
    
    
    //WhereClause --
    private String Date1 = " ";
    private String Date2 = " ";
    private String Type = " ";
    private String UserID = " ";
    private String ClientID = " ";
    private String ProjectID = " ";
    private String ActivityID = " ";
    private String MonthNum = " ";
    private String YearNum = " ";
    private String MonthName = " ";
    private String ActivityParameter = " "; //Use in activity status report where activity id is optional
                                            //: AND a_activity.activity_id = 'xxxx';
    private String WhereClause = " ";
    private String LeaveID = " ";
    private String UserIDParameter = " ";
    //-->
    
    public ReportData() {
    }
    
    public void reset(){
        Date1 = " ";
        Type = " ";
        UserID = " ";
        ReportCode = " ";
        ReportName = " "; 
        JRXMLPath = " "; 
        SqlText= " "; 
        ReportType = constants.Reports.REPORT_TYPE_PDF;
        Date2 = " ";
        ClientID = " ";
        ProjectID = " ";
        ActivityID = " ";
        MonthNum = " ";
        YearNum = " ";
        MonthName = " ";
        ActivityParameter = " ";
        SystemReport = 0;
        WhereClause = " ";
        LeaveID = " ";
        UserIDParameter = " ";
    }
    
    /**
     * Set Report Code First
     */
    public void initialize(){
        ArrayList<String> FieldList = new ArrayList<String>();
        ArrayList<String> ResultList = new ArrayList<String>();
        generalSqlHelper myGeneralSqlHelper = new generalSqlHelper();
        
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("report_name");
        FieldList.add("jrxml_full_path");
        FieldList.add("sql_text");
        
        ResultList = myGeneralSqlHelper.getDataElement("a_reports", FieldList, 
                "report_code='" + utilities.Formatter.toProperSqlWhereClause(ReportCode) + "'");
        
        if(!ResultList.isEmpty()){
            ReportName = ResultList.get(0);
            JRXMLPath = ResultList.get(1);
            SqlText = ResultList.get(2);
            
            ReportName = ReportName.replaceAll(" ", "_");
        }else{
            ReportName = " ";
            JRXMLPath = " ";
            SqlText = " ";
        }
        
        SqlText = SqlText.replaceAll("xUserID", UserID);
        SqlText = SqlText.replaceAll("xTransDate1", Date1);
        SqlText = SqlText.replaceAll("xTransDate2", Date2);
        SqlText = SqlText.replaceAll("xClientID", ClientID);
        SqlText = SqlText.replaceAll("xProjectID", ProjectID);
        SqlText = SqlText.replaceAll("xActivityID", ActivityID);
        SqlText = SqlText.replaceAll("xYear", YearNum);
        SqlText = SqlText.replaceAll("xMonth", MonthNum);
        SqlText = SqlText.replaceAll("xMName", MonthName);
        SqlText = SqlText.replaceAll("xActivityParameter", ActivityParameter);
        SqlText = SqlText.replaceAll("xWhereClause", WhereClause);
        SqlText = SqlText.replaceAll("xLeaveID", LeaveID);
        SqlText = SqlText.replaceAll("xUserMyIDParam", UserIDParameter);
        
    }

    public String getReportLocation() {
        return ReportLocation;
    }

    public String getWebReportLocation() {
        return WebReportLocation;
    }
    
    public String getJRXMLPath() {
        return JRXMLPath;
    }

    public void setJRXMLPath(String JRXMLPath) {
        this.JRXMLPath = JRXMLPath;
    }

    public String getReportCode() {
        return ReportCode;
    }

    public void setReportCode(String ReportCode) {
        this.ReportCode = ReportCode;
    }

    public String getReportName() {
        return ReportName;
    }

    public void setReportName(String ReportName) {
        this.ReportName = ReportName;
    }

    public String getReportType() {
        return ReportType;
    }

    public void setReportType(String ReportType) {
        this.ReportType = ReportType;
    }

    public String getSqlText() {
        return SqlText;
    }

    public void setSqlText(String SqlText) {
        this.SqlText = SqlText;
    }

    public int getSystemReport() {
        return SystemReport;
    }

    public void setSystemReport(int SystemReport) {
        this.SystemReport = SystemReport;
    }
    
    
    
    //--->WHERE CLAUSE --
    public String getDate1() {
        return Date1;
    }

    public void setDate1(String Date1) {
        this.Date1 = Date1;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getDate2() {
        return Date2;
    }

    public void setDate2(String Date2) {
        this.Date2 = Date2;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String ProjectID) {
        this.ProjectID = ProjectID;
    }

    public String getActivityID() {
        return ActivityID;
    }

    public void setActivityID(String ActivityID) {
        this.ActivityID = ActivityID;
    }

    public String getMonthName() {
        return MonthName;
    }

    public void setMonthName(String MonthName) {
        this.MonthName = MonthName;
    }

    public String getMonthNum() {
        return MonthNum;
    }

    public void setMonthNum(String MonthNum) {
        this.MonthNum = MonthNum;
    }

    public String getYearNum() {
        return YearNum;
    }

    public void setYearNum(String YearNum) {
        this.YearNum = YearNum;
    }

    public String getActivityParameter() {
        return ActivityParameter;
    }

    public void setActivityParameter(String ActivityParameter) {
        this.ActivityParameter = ActivityParameter;
    }

    public String getWhereClause() {
        return WhereClause;
    }

    public void setWhereClause(String WhereClause) {
        this.WhereClause = WhereClause;
    }

    public String getLeaveID() {
        return LeaveID;
    }

    public void setLeaveID(String LeaveID) {
        this.LeaveID = LeaveID;
    }

    public String getUserIDParameter() {
        return UserIDParameter;
    }

    public void setUserIDParameter(String UserIDParameter) {
        this.UserIDParameter = UserIDParameter;
    }
    
    
    
    //--->
    
}
