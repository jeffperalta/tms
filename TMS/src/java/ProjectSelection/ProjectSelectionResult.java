/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ProjectSelection;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import beans.*;
import beans.pages.WorkUtilityPage;
import java.util.ArrayList;
import tmsclass.ProjectListing.sqlHelper;
import tmsclass.WorkUtility.queryAnalyzer;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class ProjectSelectionResult extends HttpServlet {
    
    private ActivityList _MyActivityList = null;
    private LogInfo _MyLogInfo = null;
    private generalSqlHelper _MyGenSqlHelper = new generalSqlHelper();
    private queryAnalyzer _MyQueryAnalyzer = new queryAnalyzer();
    private WorkUtilityPage _MyWorkUtilityPage = null;
    private WorkUtilityPage _MyWorkUtilityPageTemp = null;
    boolean _BlnValidentry = false;
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        
/******************************************************************************
 *--Response Section--                                                        *
 ******************************************************************************/
        if(_BlnValidentry) {
            response.sendRedirect("/TMS/webpages/TimeLog/BillableTimeLog.jsp#Save");
        }else{
            response.sendRedirect("/TMS/LogOut");
        }
            
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
/******************************************************************************
 *--Declaration/Instantiation Section--                                       *
 ******************************************************************************/
    _MyActivityList = (ActivityList)request.getSession().getAttribute("myActivityList");
    _MyLogInfo = (LogInfo)request.getSession().getAttribute("LogUserInfo");
    _MyWorkUtilityPage = (WorkUtilityPage)request.getSession().getAttribute("PWorkUtilityPage");
    String strProjectID = " ", strClientID = " ";
    
    ArrayList<String> FieldList = new ArrayList<String>();
    ArrayList<String> ResultList = new ArrayList<String>();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    //Determine The Starting Date For Deadline and The Deadline itself
    _MyWorkUtilityPageTemp = _MyQueryAnalyzer.reset();
    _MyWorkUtilityPage.setStartDateForDeadline(_MyWorkUtilityPageTemp.getStartDateForDeadline());
    _MyWorkUtilityPage.setDeadline(_MyWorkUtilityPageTemp.getDeadline());
    _MyWorkUtilityPage.setAllowedDays(_MyWorkUtilityPageTemp.getAllowedDays());
    
    _MyActivityList.setActivityID(request.getParameter("AI").trim());
    _MyActivityList.setActivityListID(Integer.parseInt(request.getParameter("ALI").trim()));
    _MyActivityList.setUserID(_MyLogInfo.getUserId());
    
    //Check if this is your project
    _BlnValidentry = _MyGenSqlHelper.isExist("a_activity_list", "activity_id='" + _MyActivityList.getActivityID() + "' AND user_id='" + _MyLogInfo.getUserId() + "' AND activity_list_id=" + _MyActivityList.getActivityListID());
     
     
    //Refresh-- continued
    if(_BlnValidentry && _MyActivityList.getActivityID().trim().length() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("activity_name");
        FieldList.add("remarks");
        FieldList.add("project_id");
        
        ResultList = _MyGenSqlHelper.getDataElement("a_activity", FieldList, "activity_id='" + _MyActivityList.getActivityID() + "'");
        
        if(ResultList.size() != 0) {
            _MyActivityList.setActivityName(ResultList.get(0));
            _MyActivityList.setActivityRemarks(ResultList.get(1));
            strProjectID = ResultList.get(2);
        }else{
            _MyActivityList.setActivityName(" ");
            _MyActivityList.setActivityRemarks(" ");   
            strProjectID = " ";
        }
       
        //Get ProjectName 
        if(strProjectID.trim().length() != 0) {
            FieldList.clear();
            ResultList.clear();

            FieldList.add("project_name");
            FieldList.add("client_id");
            ResultList = _MyGenSqlHelper.getDataElement("a_project", FieldList, "project_id='" + strProjectID + "'");
            
            if(ResultList.size()!= 0) {
                _MyActivityList.setProjectName(ResultList.get(0));
                strClientID = ResultList.get(1);
            }else{
                _MyActivityList.setProjectName(" ");
                strClientID = " ";
            }
        }
        
        //Get Client Name
        if(strClientID.trim().length() != 0) {
            FieldList.clear();
            ResultList.clear();
            
            FieldList.add("c_name");
            ResultList = _MyGenSqlHelper.getDataElement("a_client", FieldList, "client_id='" + strClientID + "'");
            
            if(ResultList.size() != 0) {
                _MyActivityList.setClientName(ResultList.get(0));
            }else{
                _MyActivityList.setClientName(" ");
            }
        }
    }
    
    if(_BlnValidentry && _MyActivityList.getActivityListID() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("hrs_to_work");
        FieldList.add("hrs_worked");
        FieldList.add("status");
        FieldList.add("deadline");
        FieldList.add("DateAssigned");
        FieldList.add("allow_past_date");
        
        ResultList = _MyGenSqlHelper.getDataElement("a_activity_list", FieldList, "activity_list_id=" + _MyActivityList.getActivityListID());
        
        if(ResultList.size() != 0) {
            _MyActivityList.setHoursToWork(Double.parseDouble(ResultList.get(0)));
            _MyActivityList.setHoursWorked(Double.parseDouble(ResultList.get(1)));
            _MyActivityList.setActivityListStatus(Integer.parseInt(ResultList.get(2)));
            _MyActivityList.setDeadLine(ResultList.get(3));
            _MyActivityList.setDateAssigned(ResultList.get(4));
            _MyActivityList.setAllowPastDate(Integer.parseInt(ResultList.get(5)));
        }else{
            _MyActivityList.setHoursToWork(0.00);
            _MyActivityList.setHoursWorked(0.00);
            _MyActivityList.setActivityListStatus(0);
            _MyActivityList.setDeadLine(" ");
            _MyActivityList.setDateAssigned(" ");
            _MyActivityList.setAllowPastDate(0);
        }
    }

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
             
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
