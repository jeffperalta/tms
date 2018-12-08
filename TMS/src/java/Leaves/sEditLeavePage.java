/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Leaves;

import beans.LeaveType;
import beans.PageNavigation;
import beans.pages.LeavePage;
import beans.pages.LeaveTypeSearchPage;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.Leaves.sqlHelper;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class sEditLeavePage extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private LeavePage _MyPage = null;
    private ErrorMessage _MyError = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private sqlHelper _MySqlHelper = new sqlHelper();
    private LeaveType _MyLeave = null;
    private PageNavigation _MyPageNavigation = null;
    private LeaveTypeSearchPage _MyLeaveTypeSearchPage = null;
    
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
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeave.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeave.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeave.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("leaveid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("LeaveTypeSearchNav");
        _MyPageNavigation.setFileName("/TMS/sEditLeavePage");
        
        _MyLeaveTypeSearchPage = (LeaveTypeSearchPage)request.getSession().getAttribute("PLeaveTypePage");
        _MyLeaveTypeSearchPage.setShowStatusField(1);
        
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/LeaveTypeSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeave.jsp#Save");
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
    _MyPage =(LeavePage)request.getSession().getAttribute("myEditLeavePage");
    _MyLeave = (LeaveType)request.getSession().getAttribute("BLeaveType");    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setLeaveID(_MyLeave.getLeaveID()+"");
    _MyPage.setLeaveName(_MyLeave.getLeaveName());
    _MyPage.setRemarks(_MyLeave.getRemarks());
    _MyPage.setDefaultMaxLeave(_MyLeave.getMaxLeave() + "");
    _MyPage.setStatus(_MyLeave.getStatus() +"");
    
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
    _MyPage =(LeavePage)request.getSession().getAttribute("myEditLeavePage");
    _MyError =(ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setLeaveName(request.getParameter("txtName").trim());
    _MyPage.setDefaultMaxLeave(request.getParameter("txtMaxLeave").trim());
    
    if(request.getParameter("checkbox") != null) {
        _MyPage.setStatus("1");
    }else{
        _MyPage.setStatus("0");
    }
    
    _MyPage.setRemarks(request.getParameter("txtRemarks").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check for required field -- Leave ID
        if(!_MyError.hasError() && _MyPage.getLeaveID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Leave ID");
        }
        
        //Check for required field -- Leave Name
        if(!_MyError.hasError() && _MyPage.getLeaveName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Leave Name");
        }
        
        //Check for required field -- Leave Status
        if(!_MyError.hasError() && _MyPage.getStatus().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Leave status");
        }
        
        //Check for required field -- Leave Maximum Count
        if(!_MyError.hasError() && _MyPage.getDefaultMaxLeave().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Max leave count");
        }
        
        //Check if numeric -- Leave Maximum Count
        if(!_MyError.hasError() && !Formatter.isNumber(_MyPage.getDefaultMaxLeave())) {
            //%1 field must be numeric
            _MyError.setMessageNumber("10000");
            _MyError.addParameter("Max leave count");
        }
        
        //Check if Integer -- Leave Maximum Count
        if(!_MyError.hasError() && !Formatter.isInteger(_MyPage.getDefaultMaxLeave())) {
            //%1 is invalid (%2)
            _MyError.setMessageNumber("10004");
            _MyError.addParameter("Max leave count");
            _MyError.addParameter("Must be a whole number");
        }
        
        //Check if valid -- Greater than 0
        if(!_MyError.hasError() && Integer.parseInt(_MyPage.getDefaultMaxLeave()) <=0) {
            //%1 is invalid (%2)
            _MyError.setMessageNumber("10004");
            _MyError.addParameter("Max leave count");
            _MyError.addParameter("Must be greater than zero[0]");
        }
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0){
        //Check for required field -- Leave ID
        if(!_MyError.hasError() && _MyPage.getLeaveID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Leave ID");
        }
        
        //Cannot delete a reference record
        if(!_MyError.hasError() && _MyGeneralSqlHelper.isExist("a_leave_details", "leave_id=" + Formatter.toProperSqlWhereClause(_MyPage.getLeaveID()))) {
            //Cannot delete a referenced record. (%1)
            _MyError.setMessageNumber("10011");
            _MyError.addParameter("This leave is already availed by a user.");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            try { 
                _MyLeave = new LeaveType(_MyPage);
                _MySqlHelper.edit(_MyLeave);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Leave");

                _MyPage.reset();
                _MyLeave.reset();
            
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditLeavePage.class.getName()).log(Level.SEVERE, null, ex);
                 //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Edit leave record");
            }
        }

        if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
            try {
                _MyLeave = new LeaveType(_MyPage);
                _MySqlHelper.delete(_MyLeave);
                
                //%1 was successfully deleted.
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10012");
                _MyError.addParameter("Leave record");
                
                _MyPage.reset();
                _MyLeave.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditLeavePage.class.getName()).log(Level.SEVERE, null, ex);
                 //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Delete leave record");
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
