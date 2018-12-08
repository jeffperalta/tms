/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import beans.LogInfo;
import beans.pages.WorkUtilityPage;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import tmsclass.WorkUtility.sqlHelper;
 
/**
 *
 * @author jperalta
 */
public class sNewUpdate extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private WorkUtilityPage _MyPage = null;
    private ErrorMessage _MyError = null;
    private LogInfo _MyUser = null;
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
        response.sendRedirect("/TMS/webpages/Admin/Utilities/WorkDays.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Utilities/WorkDays.jsp#" + _ButtonClicked);
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
    _MyPage = (WorkUtilityPage)request.getSession().getAttribute("PWorkUtilityPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    _MyUser = (LogInfo)request.getSession().getAttribute("LogUserInfoAdmin");

/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setMessageTitle(request.getParameter("txtTitle").trim());
    _MyPage.setMessageBody(request.getParameter("txtBody").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check for required field -- Title
        if(!_MyError.hasError() && _MyPage.getMessageTitle().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Message title");
            _MyPage.setMessageTitle("UNTITLED");
        }
        
        //Check for required field -- Body
        if(!_MyError.hasError() && _MyPage.getMessageBody().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Message");
        }
        
    }
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyPage.setMessageTitle(Formatter.toProperString(_MyPage.getMessageTitle()));
            _MyPage.setMessageBody(Formatter.toProperString(_MyPage.getMessageBody()));
           
            try {
                _MySqlHelper.addUpdate(_MyPage, _MyUser.getFirstName() + " " + _MyUser.getLastName());
               
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Update");
                
                _MyPage.resetMessage();
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sNewUpdate.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add update information");
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
