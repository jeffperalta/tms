/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Reports;

import Report.ReportData;
import beans.Activity;
import beans.PageNavigation;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import utilities.DateUtility;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author Abree
 */
public class sSchedulePerActivityReport extends HttpServlet {
   private String _ButtonClicked = " ";
   private ReportData _MyReportData = null;
   private ErrorMessage _MyError = null;
   private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
   private Activity _MyActivity = null;
   private PageNavigation _MyPageNavigation = null;
    
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
    if(_MyError.hasError()) {
        _ButtonClicked = " ";
        response.sendRedirect("/TMS/webpages/ReportPages/SchedulePerActivity.jsp");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("Submit") == 0) {
        response.sendRedirect("/TMS/sReportCenter");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("activityid") == 0) {
        if(_MyActivity.getActivityName().trim().length() == 0) {
            _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BActivitySearchNav");
            _MyPageNavigation.setFileName("/TMS/webpages/ReportPages/SchedulePerActivity.jsp");
            response.sendRedirect("/TMS/webpages/SearchPages/ActivitySearch.jsp");
        }else{
            response.sendRedirect("/TMS/webpages/ReportPages/SchedulePerActivity.jsp");
        }
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
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
/******************************************************************************
 *--Declaration/Instantiation Section--                                       *
 ******************************************************************************/
    _ButtonClicked = request.getParameter("glbutton");
    _MyReportData = (ReportData)request.getSession().getAttribute("myReportData");
    _MyError =(ErrorMessage)request.getSession().getAttribute("MessageManager");
    _MyActivity = (Activity)request.getSession().getAttribute("BActivity");
    ArrayList<String> FieldList = new ArrayList<String>();
    ArrayList<String> ResultList = new ArrayList<String>();
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyReportData.setActivityID(Formatter.toProperString(Formatter.toProperSqlWhereClause(request.getParameter("txtActivityID").trim())));
    if(_MyReportData.getActivityID().trim().length() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("activity_name");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_activity", FieldList, "activity_id='" + _MyReportData.getActivityID() + "'");
        
        if(!ResultList.isEmpty()) {
            _MyActivity.setActivityID(_MyReportData.getActivityID());
            _MyActivity.setActivityName(ResultList.get(0));
        }else{
            _MyActivity.reset();
        }
    }else{
        _MyActivity.reset();
    }
    
    _MyReportData.setDate1(Formatter.toProperString(request.getParameter("txtStartDate").trim()));
    _MyReportData.setDate2(Formatter.toProperString(request.getParameter("txtEndDate").trim()));
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("submit") == 0){
        //Check for required field -- Activity ID
        if(!_MyError.hasError() && _MyReportData.getActivityID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Activity ID");
        }
        
        //Check for validity -- Activity ID
        if(!_MyError.hasError() && !_MyGeneralSqlHelper.isExist("a_activity", "activity_id='" + _MyReportData.getActivityID() + "'") ){
            //%1 is invalid
            _MyError.setMessageNumber("10006");
            _MyError.addParameter("Activity ID");
        }
        
        //Check for required field -- Start Date
        if(!_MyError.hasError() && _MyReportData.getDate1().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Start date");
        }
        
        //Input format -- Date
        if(!_MyError.hasError() && !DU.isProperDate(_MyReportData.getDate1())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Start date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Check for required field -- End Date
        if(!_MyError.hasError() && _MyReportData.getDate2().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("End date");
        }
        
        //Input format -- Date
        if(!_MyError.hasError() && !DU.isProperDate(_MyReportData.getDate2())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("End date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("submit") == 0){
            _MyReportData.setReportCode("SCHACT");
        }
    }
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
