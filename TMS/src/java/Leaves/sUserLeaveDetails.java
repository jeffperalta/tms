/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Leaves;

import beans.PageNavigation;
import beans.User;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import tmsclass.user.sqlHelper;
import utilities.Formatter;

/**
 *
 * @author Abree
 */
public class sUserLeaveDetails extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private String[] _MyLeaveIDs;
    private tmsclass.user.sqlHelper _MyUserLeaveSqlHelper = new tmsclass.user.sqlHelper();
    private tmsclass.Leaves.sqlHelper _MySqlHelper = new tmsclass.Leaves.sqlHelper();
    private User _MyUser = null;
    private ErrorMessage _MyError = null;
    private PageNavigation _MyPageNavigation = null;
    private String[] _MyValues = new String[101];
    
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
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminUserLeave.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminUserLeave.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("userid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BUserPageNav");
        _MyPageNavigation.setFileName("/TMS/webpages/Admin/Leaves/AdminUserLeave.jsp#" + _ButtonClicked);
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/UserSearch.jsp#main");
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
    _MyUser = (User)request.getSession().getAttribute("BUser");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    
    for(int iCtr = 0; iCtr <100; iCtr++) {
        _MyValues[iCtr] = " ";
    }
   
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Required field -- User ID
        if(!_MyError.hasError() && _MyUser.getUserID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("User ID");
        }
        
        if(!_MyError.hasError()) { //Meaning a valid user ID is present
            //Retrieve all leaves assigned to this user
            _MyLeaveIDs = _MyUserLeaveSqlHelper.getAllMyLeaves(_MyUser.getUserID());
            
            String strMaxLeave = " ";
            for(int iCtr = 0; iCtr < _MyLeaveIDs.length; iCtr++) {
                
                if(_MyLeaveIDs[iCtr].trim().length()==0) {
                    break;
                }
                
                strMaxLeave = Formatter.toProperString(request.getParameter("txtLeave" + _MyLeaveIDs[iCtr]).trim());
                //Check for required field -- Max Leave
                if(!_MyError.hasError() && strMaxLeave.trim().length()==0) {
                    //%1 field is required
                    _MyError.setMessageNumber("10003");
                    _MyError.addParameter("(Error at LeaveID:" + _MyLeaveIDs[iCtr] + ") Max leave");
                    break;
                }
                
                //Check if numeric -- MaxLeave
                if(!_MyError.hasError() && !Formatter.isNumber(strMaxLeave)) {
                    //%1 field must be numeric
                    _MyError.setMessageNumber("10000");
                    _MyError.addParameter("(Error at LeaveID:" + _MyLeaveIDs[iCtr] + ") Max leave");
                    break;
                }
                
                //Check if Integer -- Max Leave
                if(!_MyError.hasError() && !(Formatter.isInteger(strMaxLeave) && Integer.parseInt(strMaxLeave) >= 0)) {
                    //%1 is invalid (%2)
                    _MyError.setMessageNumber("10004");
                    _MyError.addParameter("(Error at LeaveID:" + _MyLeaveIDs[iCtr] + ") Max leave");
                    _MyError.addParameter("Entry must be a positive whole number");
                    break;
                }
                
                if(!_MyError.hasError()) {
                    _MyValues[iCtr] = _MyLeaveIDs[iCtr] + constants.TextFormat.SEPARATOR + strMaxLeave;
                }
                
            } // --> loop
        }
    }
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/        
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            try {
                _MySqlHelper.saveUserLeave(_MyValues, _MyUser.getUserID());
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Leave");
                _MyUser.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sUserLeaveDetails.class.getName()).log(Level.SEVERE, null, ex);
                 //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Edit leave record");
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
