/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Leaves;

import beans.LeaveType;
import beans.pages.LeavePage;
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

/**
 *
 * @author jperalta
 */
public class sAddLeavePage extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private LeavePage _MyPage = null;
    private ErrorMessage _MyError = null;
    private LeaveType _MyLeave = null;
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
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeavePlus.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Leaves/AdminLeavePlus.jsp#" + _ButtonClicked);
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
    _MyPage = (LeavePage)request.getSession().getAttribute("myAddLeavePage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
        
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setLeaveName(request.getParameter("txtName").trim());
    _MyPage.setDefaultMaxLeave(request.getParameter("txtMaxLeave").trim());
    _MyPage.setRemarks(request.getParameter("txtRemarks").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check for required field -- Leave Name
        if(!_MyError.hasError() && _MyPage.getLeaveName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Leave Name");
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
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyLeave = new LeaveType(_MyPage);
            try {
                _MySqlHelper.add(_MyLeave);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Leave");

                _MyPage.reset();
                _MyLeave.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sAddLeavePage.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add leave record");
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
