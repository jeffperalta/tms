/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Reports;

import Report.ReportData;
import beans.Activity;
import beans.PageNavigation;
import beans.Project;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author Abree
 */
public class sActivityStatusReport extends HttpServlet {
    private String _ButtonClicked = " ";
    private Project _MyProject = null;
    private ReportData _MyReportData = null;
    private ErrorMessage _MyError = null;
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
        response.sendRedirect("/TMS/webpages/ReportPages/ActivityStatusReport.jsp");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("projectid") == 0) {
        if(_MyProject.getProjectName().trim().length() ==0) {
            _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ProjectSearchNav");
            _MyPageNavigation.setFileName("/TMS/webpages/ReportPages/ActivityStatusReport.jsp");
            response.sendRedirect("/TMS/webpages/SearchPages/ProjectSearch.jsp");
        }else{
        }
    }
    
    if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
        response.sendRedirect("/TMS/sReportCenter");
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
    _MyProject = (Project)request.getSession().getAttribute("BProject");
    _MyReportData = (ReportData)request.getSession().getAttribute("myReportData");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManager");
    ArrayList<String> FieldList = new ArrayList<String>();
    ArrayList<String> ResultList = new ArrayList<String>();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyReportData.reset();
    _MyReportData.setProjectID(Formatter.toProperString(Formatter.toProperSqlWhereClause(request.getParameter("txtProjectID").trim())));
    if(_MyReportData.getProjectID().trim().length() == 0) {
        _MyReportData.setActivityParameter(" "); //If there is a parameter
        _MyProject.setProjectName(" ");
    }else{
        _MyReportData.setActivityParameter(" AND a_project.project_id='" + _MyReportData.getProjectID() + "' ");
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("project_name");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_project", FieldList, "project_id='" + _MyReportData.getProjectID() + "'");
        if(!ResultList.isEmpty()) {
            _MyProject.setProjectName(ResultList.get(0));
        }else{
            _MyProject.setProjectName(" ");
        }
    }
    _MyProject.setProjectID(_MyReportData.getProjectID());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
            _MyReportData.setReportCode("ACTSTT");
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
