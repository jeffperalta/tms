/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Reports;

import Report.ReportData;
import beans.PageNavigation;
import beans.Project;
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
public class sSchedulePerProjectReport extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ReportData _MyReportData = null;
    private ErrorMessage _MyError = null;
    private Project _MyProject = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
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
        response.sendRedirect("/TMS/webpages/ReportPages/SchedulePerProject.jsp");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("Submit") == 0) {
        response.sendRedirect("/TMS/sReportCenter");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("projectid") == 0) {
        if(_MyProject.getProjectName().trim().length() == 0) {
            _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ProjectSearchNav");
            _MyPageNavigation.setFileName("/TMS/webpages/ReportPages/SchedulePerProject.jsp");
            response.sendRedirect("/TMS/webpages/SearchPages/ProjectSearch.jsp");
        }else{
            response.sendRedirect("/TMS/webpages/ReportPages/SchedulePerProject.jsp");
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
    _MyProject = (Project)request.getSession().getAttribute("BProject");
    ArrayList<String> FieldList = new ArrayList<String>();
    ArrayList<String> ResultList = new ArrayList<String>();
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyReportData.setProjectID(Formatter.toProperString(Formatter.toProperSqlWhereClause(request.getParameter("txtProjectID").trim())));
    if(_MyReportData.getProjectID().trim().length() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("project_name");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_project", FieldList, "project_id='" + _MyReportData.getProjectID() + "'");
        
        if(!ResultList.isEmpty()) {
            _MyProject.setProjectID(_MyReportData.getProjectID());
            _MyProject.setProjectName(ResultList.get(0));
        }else{
            _MyProject.reset();
        }
    }else{
        _MyProject.reset();
    }
    
    _MyReportData.setDate1(Formatter.toProperString(request.getParameter("txtStartDate").trim()));
    _MyReportData.setDate2(Formatter.toProperString(request.getParameter("txtEndDate").trim()));
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
        //Check for required field -- Project ID
        if(!_MyError.hasError() && _MyReportData.getProjectID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Project ID");
        }
        
        //Check for validity -- Project ID
        if(!_MyError.hasError() && !_MyGeneralSqlHelper.isExist("a_project", "project_id='" + _MyReportData.getProjectID() + "'") ){
            //%1 is invalid
            _MyError.setMessageNumber("10006");
            _MyError.addParameter("Project ID");
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
        if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
            _MyReportData.setReportCode("SCHPRJ");
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
