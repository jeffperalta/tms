/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ActivityPage;

import beans.Activity;
import beans.PageNavigation;
import beans.Project;
import beans.pages.ActivityPage;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.Activity.sqlHelper;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author Abree
 */
public class sAddActivity extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ActivityPage _MyPage = null;
    private ArrayList<String> FieldList = new ArrayList<String>();
    private ArrayList<String> ResultList = new ArrayList<String>();
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private ErrorMessage _MyError = null;
    private Activity _MyActivity = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
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
    if(_MyError.hasError()) {
        _ButtonClicked = " ";
        response.sendRedirect("/TMS/webpages/Admin/Activity/AdminActivityPlus.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Activity/AdminActivityPlus.jsp#" + _ButtonClicked);
    }
  
    
    if(_ButtonClicked.compareToIgnoreCase("projectid")== 0) {
       _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ProjectSearchNav"); 
       _MyPageNavigation.setFileName("/TMS/sAddActivity");
       response.sendRedirect("/TMS/webpages/Admin/SearchPage/ProjectSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Activity/AdminActivityPlus.jsp#Save");
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
    _ButtonClicked = "doGet";
    _MyPage = (ActivityPage)request.getSession().getAttribute("myAddActivityPage");
    _MyProject = (Project)request.getSession().getAttribute("BProject");
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(_MyPage.getButtonClicked().compareToIgnoreCase("projectid") == 0 ) {
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
    _MyPage = (ActivityPage)request.getSession().getAttribute("myAddActivityPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
   _MyPage.setButtonClicked(_ButtonClicked);
   _MyPage.setActivityID(request.getParameter("txtActivityID").trim());
   _MyPage.setProjectID(request.getParameter("txtProjectID").trim());
   if(_MyPage.getProjectID().trim().length() != 0) {
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
   
   _MyPage.setActivityName(request.getParameter("txtName").trim());
   _MyPage.setStatusID(request.getParameter("cmbStatus").trim());
   if(_MyPage.getStatusID().trim().length() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("status_name");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_activity_status", FieldList, "status_id='" + Formatter.toProperSqlWhereClause(_MyPage.getStatusID()) + "'");
        if(!ResultList.isEmpty()) {
            _MyPage.setStatusName(ResultList.get(0));
        }else{
            _MyPage.setStatusName(" ");
        }
   }else{
        _MyPage.setStatusName(" ");
   }
    
   _MyPage.setActivityTypeID(request.getParameter("cmbActivityType").trim());
   if(_MyPage.getActivityTypeID().trim().length() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("type_name");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_activity_type", FieldList, "type_id='" + Formatter.toProperSqlWhereClause(_MyPage.getActivityTypeID()) + "'");
        if(!ResultList.isEmpty()) {
            _MyPage.setActivityTypeName(ResultList.get(0));
        }else{
            _MyPage.setActivityTypeName(" ");
        }
   }else{
        _MyPage.setActivityTypeName(" ");
   }
   
   _MyPage.setRemarks(request.getParameter("txtRemarks").trim());
    
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
       //Check for required field -- Activity ID
       if(!_MyError.hasError() && _MyPage.getActivityID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Activity ID");
       }
       
       //Check for duplicate -- Activity ID
       if(!_MyError.hasError() && _MyGeneralSqlHelper.isExist("a_activity", "activity_id = '" + Formatter.toProperSqlWhereClause(_MyPage.getActivityID()) + "'") ) {
            //A unique %1 is required.
           _MyError.setMessageNumber("10013");
           _MyError.addParameter("activity ID");
       }
        
       //Check for required field -- Project ID
       if(!_MyError.hasError() && _MyPage.getProjectID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Project ID");
       }
       
       //Check for validity -- Project ID
       if(!_MyError.hasError() && !_MyGeneralSqlHelper.isExist("a_project", "project_id='" + Formatter.toProperSqlWhereClause(_MyPage.getProjectID()) + "'")){
            //%1 is invalid
           _MyError.setMessageNumber("10006");
           _MyError.addParameter("Project ID");
       }
       
       //Check for required field -- Activity Name
       if(!_MyError.hasError() && _MyPage.getActivityName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Activity name");
       }
       
       //check for required field -- activity status
       if(!_MyError.hasError() && _MyPage.getStatusID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Status");
       }
   }
   
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyActivity = new Activity(_MyPage);
            try {
                _MySqlHelper.add(_MyActivity);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Activity");

                _MyPage.reset();
                _MyActivity.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditActivity.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add activity record");
            }
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
