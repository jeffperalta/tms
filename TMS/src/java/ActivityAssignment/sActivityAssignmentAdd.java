/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ActivityAssignment;

import beans.Activity;
import beans.ActivityAssignment;
import beans.PageNavigation;
import beans.User;
import beans.pages.ActivityAssignmentPage;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.ActivityAssignment.sqlHelper;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.DateUtility;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class sActivityAssignmentAdd extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ActivityAssignmentPage _MyPage = null;
    private ErrorMessage _MyError = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
    private ActivityAssignment _MyActivityAssignment = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private PageNavigation _MyPageNavigation= null;
    private ArrayList<String> _FieldList = new ArrayList<String>();
    private ArrayList<String> _ResultList = new ArrayList<String>();
    private Activity _MyActivity = null;
    private User _MyUser = null;
    
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
        response.sendRedirect("/TMS/webpages/Admin/ActivityAssignment/AdminActivityAssignmentPlus.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/ActivityAssignment/AdminActivityAssignmentPlus.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/ActivityAssignment/AdminActivityAssignmentPlus.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("activityid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BActivitySearchNav");
        _MyPageNavigation.setFileName("/TMS/sActivityAssignmentAdd");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ActivitySearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("userid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BUserPageNav");
        _MyPageNavigation.setFileName("/TMS/sActivityAssignmentAdd");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/UserSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/ActivityAssignment/AdminActivityAssignmentPlus.jsp#Save");
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
    _MyActivity =(Activity)request.getSession().getAttribute("BActivity");
    _MyUser=(User)request.getSession().getAttribute("BUser");
    _MyPage = (ActivityAssignmentPage)request.getSession().getAttribute("myActivityAssignmentPageAdd");
    String strProjectID = " ";
    _ButtonClicked = "doGet";
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setActivityID(_MyActivity.getActivityID());
    _MyPage.setActivityName(_MyActivity.getActivityName());
    strProjectID = _MyActivity.getProjectID();
     
    if(strProjectID.trim().length() != 0) {
        _FieldList.clear();
        _ResultList.clear();
        
        _FieldList.add("project_name");
        _ResultList = _MyGeneralSqlHelper.getDataElement("a_project", _FieldList, "project_id='" + strProjectID + "'");
        
        if(!_ResultList.isEmpty()) {
            _MyPage.setProjectName(_ResultList.get(0));
        }
        
    }
    
    _MyPage.setEmployeeID(_MyUser.getUserID());
    _MyPage.setEmployeeName(_MyUser.getFirstName().substring(0, 1) + ". " + _MyUser.getLastName());
    
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
        
 /******************************************************************************
 *--Declaration/Instantiation Section--                                       *
 ******************************************************************************/
    _ButtonClicked = request.getParameter("glbutton");
    _MyPage = (ActivityAssignmentPage)request.getSession().getAttribute("myActivityAssignmentPageAdd");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setHoursToWork(request.getParameter("txtHourToWork").trim());
    _MyPage.setDeadline(request.getParameter("txtDeadline").trim());
    _MyPage.setDateAssigned(request.getParameter("txtDateAssigned").trim());
    _MyPage.setButtonClicked(_ButtonClicked);
  
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
       //Check for required field -- Activity ID/Project ID[-
       if(!_MyError.hasError() && _MyPage.getActivityID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Project ID");
       } 
        
       //Check for required field -- Employee ID
       if(!_MyError.hasError() && _MyPage.getEmployeeID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Employee ID");
       }
       
       //Check for duplicate -- ActivityID-EmployeeID
       if(!_MyError.hasError() && _MyGeneralSqlHelper.isExist("a_activity_list", "activity_id='" + Formatter.toProperSqlWhereClause(_MyPage.getActivityID()) + "' AND user_id='" + Formatter.toProperSqlWhereClause(_MyPage.getEmployeeID()) + "'")) {
            //A unique %1 is required.
            _MyError.setMessageNumber("10013");
            _MyError.addParameter("activity assignment");
       }
       
       //Check for required field -- Hours to work
       if(!_MyError.hasError() && _MyPage.getHoursToWork().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Hours to work");
       }
        
        //Check for numeric -- Hours to work
       if(!_MyError.hasError() && !Formatter.isNumber(_MyPage.getHoursToWork())) {
            //%1 field must be numeric
           _MyError.setMessageNumber("10000");
           _MyError.addParameter("Hours to work");
       }
       
        //Check for positive number -- hours to work
       if(!_MyError.hasError() && Formatter.toProperNumber(_MyPage.getHoursToWork()) < 0) {
            //%1 is invalid (%2)
           _MyError.setMessageNumber("10004");
           _MyError.addParameter("Hours to work field");
           _MyError.addParameter("A positive number is required.");
       }
       
        //Check for required field -- deadline
       if(!_MyError.hasError() && _MyPage.getDeadline().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Deadline");
       }
       
        //Check for Date Format -- deadline
       if(!_MyError.hasError() && !DU.isProperDate(_MyPage.getDeadline())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Deadline");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
       }
       
        //Check for DateAssigned --Format
       if(_MyPage.getDateAssigned().trim().length() != 0 && !_MyError.hasError() && 
               !DU.isProperDate(_MyPage.getDateAssigned())) {
               
           //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Date assigned");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
       }
      
    }
  
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyActivityAssignment = new ActivityAssignment(_MyPage);
            try {
                _MySqlHelper.add(_MyActivityAssignment);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Activity assignment");

                _MyPage.reset();
                _MyActivityAssignment.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sActivityAssignmentEdit.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add activity assignment record");
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
