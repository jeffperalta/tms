/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ActivityAssignment;

import beans.ActivityAssignment;
import beans.PageNavigation;
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
public class sActivityAssignmentEdit extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ActivityAssignmentPage _MyPage = null;
    private ErrorMessage _MyError = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
    private ActivityAssignment _MyActivityAssignment = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private PageNavigation _MyPageNavigation  = null;
    private ArrayList<String> _FieldList = new ArrayList<String>();
    private ArrayList<String> _ResultList = new ArrayList<String>();
    
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
        response.sendRedirect("/TMS/webpages/Admin/ActivityAssignment/AdminActivityAssignment.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/ActivityAssignment/AdminActivityAssignment.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/ActivityAssignment/AdminActivityAssignment.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("activityassignmentid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ActivityAssignmentSearchNav");
        _MyPageNavigation.setFileName("/TMS/sActivityAssignmentEdit");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ActivityAssignmentSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/ActivityAssignment/AdminActivityAssignment.jsp#Save");
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
    _MyActivityAssignment =(ActivityAssignment)request.getSession().getAttribute("BActivityAssignment");
    _MyPage = (ActivityAssignmentPage)request.getSession().getAttribute("myActivityAssignmentPage");
    String strProjectID = " ";
    _ButtonClicked = "doGet";
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setActivityListID(_MyActivityAssignment.getActivityListID() + "");
    _MyPage.setActivityID(_MyActivityAssignment.getActivityID());
    if(_MyPage.getActivityID().trim().length() != 0) {
        _FieldList.clear();
        _ResultList.clear();
        
        _FieldList.add("activity_name");
        _FieldList.add("project_id");
        _ResultList = _MyGeneralSqlHelper.getDataElement("a_activity", _FieldList, "activity_id='" + Formatter.toProperSqlWhereClause(_MyPage.getActivityID()) + "'");
        
        if(!_ResultList.isEmpty()) {
            _MyPage.setActivityName(_ResultList.get(0));
            strProjectID = _ResultList.get(1);
        }
    }
    
    if(strProjectID.trim().length() != 0) {
        _FieldList.clear();
        _ResultList.clear();
        
        _FieldList.add("project_name");
        _ResultList = _MyGeneralSqlHelper.getDataElement("a_project", _FieldList, "project_id='" + strProjectID + "'");
        
        if(!_ResultList.isEmpty()) {
            _MyPage.setProjectName(_ResultList.get(0));
        }
        
    }
    
    _MyPage.setEmployeeID(_MyActivityAssignment.getUserID());
    if(_MyPage.getEmployeeID().trim().length()!= 0) {
        _FieldList.clear();
        _ResultList.clear();
        
        _FieldList.add("CONCAT(SUBSTRING(first_name,1,1), '. ', last_name)");
        _ResultList = _MyGeneralSqlHelper.getDataElement("a_users", _FieldList, "user_id='" + _MyPage.getEmployeeID() + "'");
        if(!_ResultList.isEmpty()) {
            _MyPage.setEmployeeName(_ResultList.get(0));
        }
    }
    
    _MyPage.setHoursToWork(Formatter.toDouble(_MyActivityAssignment.getHoursToWork()));
    _MyPage.setDeadline(_MyActivityAssignment.getDeadline());
    _MyPage.setDateAssigned(_MyActivityAssignment.getDateAssigned());
    _MyPage.setAllowPastDate(_MyActivityAssignment.getAllowPastDate() + "");
    
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
    _MyPage = (ActivityAssignmentPage)request.getSession().getAttribute("myActivityAssignmentPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setHoursToWork(request.getParameter("txtHourToWork").trim());
    _MyPage.setDeadline(request.getParameter("txtDeadline").trim());
    _MyPage.setDateAssigned(request.getParameter("txtDateAssigned").trim());
    
    if(request.getParameter("checkbox") != null) {
        _MyPage.setAllowPastDate("0");
    }else{
        _MyPage.setAllowPastDate("1");
    }
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
       //Check for required field - ActivityListID
       if(!_MyError.hasError() && _MyPage.getActivityListID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Project assignment ID");
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
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        //Check for required field - ActivityListID
       if(!_MyError.hasError() && _MyPage.getActivityListID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Project assignment ID");
       } 
       
       //Cannot delete referenced record.
       if(!_MyError.hasError() && _MyGeneralSqlHelper.isExist("a_schedule", "activity_list_id=" + Formatter.toProperSqlWhereClause(_MyPage.getActivityListID()))) {
            //Cannot delete a referenced record. (%1)
            _MyError.setMessageNumber("10011");
            _MyError.addParameter("This record already has a schedule.");
       }
       
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyActivityAssignment = new ActivityAssignment(_MyPage);
            try {
                _MySqlHelper.edit(_MyActivityAssignment);
                
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
                _MyError.addParameter("Edit activity assignment record");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
            _MyActivityAssignment = new ActivityAssignment(_MyPage);
            try {
                _MySqlHelper.delete(_MyActivityAssignment);
                
                //%1 was successfully deleted.
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10012");
                _MyError.addParameter("Activity assignment record");
                
                _MyPage.reset();
                _MyActivityAssignment.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sActivityAssignmentEdit.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Delete activity assignment record");
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
