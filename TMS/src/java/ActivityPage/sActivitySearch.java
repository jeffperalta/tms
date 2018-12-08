/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ActivityPage;

import beans.PageNavigation;
import beans.Project;
import beans.pages.ActivitySearchPage;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import utilities.Formatter;
import utilities.generalSqlHelper;
 
/**
 *
 * @author Abree
 */
public class sActivitySearch extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ActivitySearchPage _MyPage = null;
    private ArrayList<String> _FieldList = new ArrayList<String>();
    private ArrayList<String> _ResultList = new ArrayList<String>();
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private PageNavigation _MyPageNavigation = null;
    private Project _MyProject = null;
    
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
    if(_ButtonClicked.compareToIgnoreCase("filter") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ActivitySearch.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("projectid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ProjectSearchNav");
        _MyPageNavigation.setFileName("/TMS/sActivitySearch");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ProjectSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ActivitySearch.jsp#Filter");
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
    _MyPage = (ActivitySearchPage)request.getSession().getAttribute("myActivitySearchPage");
    _MyProject = (Project)request.getSession().getAttribute("BProject");
    _ButtonClicked = "doGet";
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    _MyPage.setProjectID(_MyProject.getProjectID());
    _MyPage.setProjectName(_MyProject.getProjectName());
    
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
    _MyPage = (ActivitySearchPage)request.getSession().getAttribute("myActivitySearchPage");
        
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

    _MyPage.setProjectID(request.getParameter("txtProjectID").trim());
    if(_MyPage.getProjectID().trim().length() != 0) {
        _FieldList.clear();
        _ResultList.clear();
        
        _FieldList.add("project_name");
        _ResultList = _MyGeneralSqlHelper.getDataElement("a_project", _FieldList, "project_id='" + Formatter.toProperSqlWhereClause(_MyPage.getProjectID()) + "'");
        if(!_ResultList.isEmpty()) {
            _MyPage.setProjectName(_ResultList.get(0));
        }else{
            _MyPage.setProjectName(" ");
        }
    }else{
        _MyPage.setProjectName(" ");
    }
    
    _MyPage.setActivityName(request.getParameter("txtActivityName").trim());
    _MyPage.setActivityStatusID(request.getParameter("cmbStatus").trim());
    if(_MyPage.getActivityStatusID().trim().length()!=0) {
        _FieldList.clear();
        _ResultList.clear();
        
        _FieldList.add("status_name");
        _ResultList = _MyGeneralSqlHelper.getDataElement("a_activity_status", _FieldList, "status_id=" + Formatter.toProperSqlWhereClause(_MyPage.getActivityStatusID()));
        if(!_ResultList.isEmpty()) {
            _MyPage.setActivityStatusName(_ResultList.get(0));
        }else{
            _MyPage.setActivityStatusID(" ");
            _MyPage.setActivityStatusName(" ");
        }
    }else{
        _MyPage.setActivityStatusID(" ");
        _MyPage.setActivityStatusName(" ");
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
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
