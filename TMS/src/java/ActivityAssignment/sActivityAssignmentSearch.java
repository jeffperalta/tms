/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ActivityAssignment;

import beans.Activity;
import beans.PageNavigation;
import beans.Project;
import beans.User;
import beans.pages.ActivityAssignmentSearchPage;
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
public class sActivityAssignmentSearch extends HttpServlet {
  
    private String _ButtonClicked = " ";
    private ActivityAssignmentSearchPage _MyPage = new ActivityAssignmentSearchPage();
    private ArrayList<String> FieldList = new ArrayList<String>();
    private ArrayList<String> ResultList = new ArrayList<String>();
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private PageNavigation _MyPageNavigation = new PageNavigation();
    private Activity _MyActivity = null;
    private Project _MyProject = null;
    private User _MyUser = null;
    private ActivitySearchPage _MyActivitySearchPage = null;
    
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
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ActivityAssignmentSearch.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("projectid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ProjectSearchNav");
        _MyPageNavigation.setFileName("/TMS/sActivityAssignmentSearch");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ProjectSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("userid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BUserPageNav");
        _MyPageNavigation.setFileName("/TMS/sActivityAssignmentSearch");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/UserSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("activityid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BActivitySearchNav");
        _MyPageNavigation.setFileName("/TMS/sActivityAssignmentSearch");
        
        //The user already searched for the project ID and project name
        //Must pass this information to the Activity Search page Project ID field
        _MyActivitySearchPage =(ActivitySearchPage)request.getSession().getAttribute("myActivitySearchPage");
        _MyActivitySearchPage.setProjectName(_MyPage.getProjectName());
        _MyActivitySearchPage.setProjectID(_MyPage.getProjectID());
        
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ActivitySearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ActivityAssignmentSearch.jsp#Save");
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
    _MyPage = (ActivityAssignmentSearchPage)request.getSession().getAttribute("myActivityAssignmentSearchPage");
    _MyActivity = (Activity)request.getSession().getAttribute("BActivity");
    _MyUser = (User)request.getSession().getAttribute("BUser");
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
    if(_MyPage.getButtonClicked().compareToIgnoreCase("userid") == 0) {
        _MyPage.setEmployeeID(_MyUser.getUserID());
        _MyPage.setEmployeeName(_MyUser.getFirstName().substring(0, 1) + ". " + _MyUser.getLastName());
    }
    
    if(_MyPage.getButtonClicked().compareToIgnoreCase("activityid") == 0) {
        _MyPage.setActivityID(_MyActivity.getActivityID());
        _MyPage.setActivityName(_MyActivity.getActivityName());
    }
    
    if(_MyPage.getButtonClicked().compareToIgnoreCase("projectid") == 0) {
        _MyPage.setProjectID(_MyProject.getProjectID());
        _MyPage.setProjectName(_MyProject.getProjectName());
    }
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
    _MyPage = (ActivityAssignmentSearchPage)request.getSession().getAttribute("myActivityAssignmentSearchPage");
        
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setButtonClicked(_ButtonClicked);
    _MyPage.setActivityID(" ");
    if(_MyPage.getActivityID().trim().length()!=0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("activity_name");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_activity", FieldList, "Activity_id='" + Formatter.toProperSqlWhereClause(_MyPage.getActivityID()) + "'");
        if(!ResultList.isEmpty()) {
            _MyPage.setActivityName(ResultList.get(0));
        }else{
            _MyPage.setActivityName(" ");
        }
    }else{
        _MyPage.setActivityName(" ");
    }
    
    _MyPage.setEmployeeID(request.getParameter("txtEmployeeID").trim());
    if(_MyPage.getEmployeeID().trim().length()!=0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("CONCAT(SUBSTRING(First_name,1,1), ', ', last_name)");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_users", FieldList, "user_id='" + Formatter.toProperSqlWhereClause(_MyPage.getEmployeeID()) + "'");
        if(!ResultList.isEmpty()) {
            _MyPage.setEmployeeName(ResultList.get(0));
        }else{
            _MyPage.setEmployeeName(" ");
        }
    }else{
        _MyPage.setEmployeeName(" ");
    }
    
    _MyPage.setProjectID(request.getParameter("txtProjectID").trim());
    if(_MyPage.getProjectID().trim().length()!=0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("project_name");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_project", FieldList, "project_id='" + Formatter.toProperSqlWhereClause(_MyPage.getProjectID()) + "'");
        if(!ResultList.isEmpty()) {
            _MyPage.setProjectName(ResultList.get(0));
        }else{
            _MyPage.setProjectName(" ");
        }
    }else{
        _MyPage.setProjectName(" ");
    }
    
    //allow past date filter
    if(request.getParameter("checkbox")!= null) {
        _MyPage.setAllowedPastDate("0");
    }else{
        _MyPage.setAllowedPastDate("1");
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
