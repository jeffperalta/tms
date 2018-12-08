/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Reports.AdminReports;

import Report.ReportData;
import beans.PageNavigation;
import beans.Project;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class sAdminActivityStatus extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ReportData _MyReportData = null;
    private Project _MyProject = null;
    private PageNavigation _MyPageNavigation = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    
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
    if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
        response.sendRedirect("/TMS/sReportCenter");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("projectid") == 0) {
        if(_MyProject.getProjectID().trim().length() == 0) {
            _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ProjectSearchNav");
            _MyPageNavigation.setFileName("/TMS/webpages/Admin/ReportPages/ActivityStatusReport.jsp");
            response.sendRedirect("/TMS/webpages/Admin/SearchPage/ProjectSearch.jsp");
        }else{
            response.sendRedirect("/TMS/webpages/Admin/ReportPages/ActivityStatusReport.jsp");
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
    _MyProject = (Project)request.getSession().getAttribute("BProject");
    ArrayList<String> FieldList = new ArrayList<String>();
    ArrayList<String> ResultList = new ArrayList<String>();
    
    String strMainStatus = " ", strSecStatus = " ", strEmployeeID = " ", strProjectID = " ";
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyReportData.reset();
    strMainStatus = request.getParameter("cmbStatus").trim();
    strSecStatus = request.getParameter("cmbStatus1").trim();
    strEmployeeID = request.getParameter("cmbUsers").trim();
    strProjectID = request.getParameter("txtProjectID").trim();
    
    if(strProjectID.trim().length() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("project_name");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_projects", FieldList, "project_id='" + Formatter.toProperSqlWhereClause(strProjectID) + "'");
        if(!ResultList.isEmpty()) {
            _MyProject.setProjectID(strProjectID);
            _MyProject.setProjectName(ResultList.get(0));
        }else{
            _MyProject.reset();
        }
    }else{
        _MyProject.reset();
    }
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Submit") == 0) {
        _MyReportData.setReportCode("AACTST");
        _MyReportData.setSystemReport(1);
        if(strMainStatus.trim().length() != 0 || strSecStatus.trim().length() != 0) {
            if(strMainStatus.trim().length() !=0 && strSecStatus.trim().length() == 0) {
                _MyReportData.setWhereClause(" WHERE a_activity.status=" + strMainStatus + " ");
            }else if(strMainStatus.trim().length() ==0 && strSecStatus.trim().length() != 0) {
                _MyReportData.setWhereClause(" WHERE a_activity_list.status=" + strSecStatus + " ");
            }else if(strMainStatus.trim().length() !=0 && strSecStatus.trim().length() != 0) {
                _MyReportData.setWhereClause(" WHERE a_activity.status=" + strMainStatus + " AND a_activity_list.status=" + strSecStatus + " ");
            }
            
            _MyReportData.setWhereClause(_MyReportData.getWhereClause() + " AND a_users.user_id LIKE '" + strEmployeeID + "%'");
        }else{
            _MyReportData.setWhereClause(_MyReportData.getWhereClause() + " WHERE a_users.user_id LIKE '" + strEmployeeID + "%'");
        }
        
        if(_MyReportData.getWhereClause().trim().length()==0) {
            _MyReportData.setWhereClause(" WHERE a_project.project_id LIKE '" + Formatter.toProperSqlWhereClause(strProjectID) + "%'");
        }else{
            _MyReportData.setWhereClause(_MyReportData.getWhereClause() + " AND a_project.project_id LIKE '" + Formatter.toProperSqlWhereClause(strProjectID) + "%'");
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
