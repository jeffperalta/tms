/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Leaves;

import beans.PageNavigation;
import beans.User;
import beans.pages.LeaveStatusPage;
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
import utilities.DateUtility;

/**
 *
 * @author user
 */
public class sAdminLeaveStatus extends HttpServlet {
    private String _ButtonClicked = " ";
    private LeaveStatusPage _MyPage = null;
    private ErrorMessage _MyError = null;
    private DateUtility DU = new DateUtility();
    private PageNavigation _MyPageNavigation  = null;
    private User _MyUser = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
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
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeaveStatus.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("userid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BUserPageNav");
        _MyPageNavigation.setFileName("/TMS/sAdminLeaveStatus");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/UserSearch.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeaveStatus.jsp#Save");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("Filter") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeaveStatus.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeaveStatus.jsp#" + _ButtonClicked);
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
    _MyPage = (LeaveStatusPage)request.getSession().getAttribute("myLeaveStatusPage");
    _MyUser = (User)request.getSession().getAttribute("BUser"); 
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    _MyPage.setUserID(_MyUser.getUserID());
    _MyPage.setUserName(_MyUser.getFirstName() + " " + _MyUser.getLastName());
    
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
    _MyPage = (LeaveStatusPage)request.getSession().getAttribute("myLeaveStatusPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    String[] leaveIDs = request.getParameterValues("checkbox");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setEndDate(request.getParameter("txtEndDate").trim());
    _MyPage.setStartDate(request.getParameter("txtStartDate").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Filter") == 0) {
        //Check for required field -- User ID
        if(!_MyError.hasError() && _MyPage.getUserID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("User ID");
        }
        
        //Check for valid format - Start date
        if(!_MyError.hasError() && _MyPage.getStartDate().trim().length() > 0 &&
                !DU.isProperDate(_MyPage.getStartDate().trim())) {        
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Start Date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Check for valid format - End date
        if(!_MyError.hasError() && _MyPage.getEndDate().trim().length() > 0 &&
                !DU.isProperDate(_MyPage.getEndDate().trim())) {        
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("End Date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check for required field -- User ID
        if(!_MyError.hasError() && _MyPage.getUserID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("User ID");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/ 
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            try {
                _MySqlHelper.editStatus(leaveIDs, _MyPage.getUserID());
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Leave status");
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sAdminLeaveStatus.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Leave status edit");
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
