/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package LogInPage;

import beans.LogInfo;
import beans.pages.ChangePasswordPage;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import logIn.LogInManager;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.Formatter;

/**
 *
 * @author Abree
 */
public class sChangePassword extends HttpServlet {
    private String _ButtonClicked = " ";
    private ChangePasswordPage _MyPage = null;
    private LogInManager _MySqlHelper = new LogInManager();
    private ErrorMessage _MyError = null;
    private LogInfo _MyLogInfo = new LogInfo();
    
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
        response.sendRedirect("/TMS/changepassword.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/changepassword.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("back") == 0) {
        response.sendRedirect("/TMS/index.jsp");
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
    _MyPage =(ChangePasswordPage)request.getSession().getAttribute("myChangePasswordPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManager");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setUserName(request.getParameter("txtUserName").trim());
    _MyPage.setOldPassword(request.getParameter("txtOldPassword").trim());
    _MyPage.setNewPassword(request.getParameter("txtNewPassword").trim());
    _MyPage.setConfirmPassword(request.getParameter("txtConfirm").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save")== 0) {
        //Check user credential
        _MyLogInfo.setUsername(_MyPage.getUserName());
        _MyLogInfo.setPassword(_MyPage.getOldPassword());
        if(!_MyError.hasError() && !_MySqlHelper.isValid(_MyLogInfo)) {
            //%1 is invalid (%2)
            _MyError.setMessageNumber("10004");
            _MyError.addParameter("User credential");
            _MyError.addParameter("Must enter a valid username and password.");
        }
        
        //Check for required field -- New Password
        if(!_MyError.hasError() && _MyPage.getNewPassword().trim().length()==0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("New password");
        }
        
        //Check if length is 6-20 alpha numeric -- New Password
        if(!_MyError.hasError() && _MyPage.getNewPassword().trim().length() < 6) {
            //%1 is invalid (%2)
            _MyError.setMessageNumber("10004");
            _MyError.addParameter("New password field");
            _MyError.addParameter("Entry must have at least 6 to 20 characters");
        }
        
        //Check for required field -- confirmation
        if(!_MyError.hasError() && _MyPage.getConfirmPassword().trim().length()==0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Confirm password");
        }
        
        //Check if New Password is equal to Confirmation
        if(!_MyError.hasError() && _MyPage.getNewPassword().compareToIgnoreCase(_MyPage.getConfirmPassword()) != 0) {
            //Password confirmation is required
            _MyError.setMessageNumber("10015");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
     if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyLogInfo.setUsername(Formatter.toProperString(_MyPage.getUserName()));
            _MyLogInfo.setPassword(Formatter.toProperString(_MyPage.getNewPassword()));
            try {

                _MySqlHelper.changePassword(_MyLogInfo);
            //%1 information was saved
            _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
            _MyError.setMessageNumber("10010");
            _MyError.addParameter("New user");
            
            _MyPage.reset();

        } catch (TransactionWasNotSavedException ex) {
            Logger.getLogger(sChangePassword.class.getName()).log(Level.SEVERE, null, ex);
            if(!_MyError.hasError()) {
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("change password.");
            }
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
