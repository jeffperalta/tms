/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import beans.WorkUtility;
import beans.WorkUtilityTemp;
import beans.pages.WorkUtilityPage;
import beans.pages.WorkUtilityPageTemp;
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
 * @author Abree
 */
public class sEditUtilities extends HttpServlet {
   private String _ButtonClicked = " ";
   private WorkUtilityPage _MyPage = null;
   private ErrorMessage _MyError = null;
   private sqlHelper _MySqlHelper = new sqlHelper();
   private WorkUtility _MyWorkUtility = new WorkUtility();
   
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
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setAllowedDays(request.getParameter("txtAllowedDays").trim());
    _MyPage.setDeadline(request.getParameter("txtDeadline").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check Required field -- Deadline
        if(!_MyError.hasError() && _MyPage.getDeadline().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Deadline");
        }
        
        //Check for validity -- Deadline
        if(!_MyError.hasError() && !DU.isProperDate(_MyPage.getDeadline())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Deadline");
            _MyError.addParameter(constants.DateFormat.DATE_DISPLAY_FORMAT);
        }
        
        //Check Required field -- Allowed Days
        if(!_MyError.hasError() && _MyPage.getAllowedDays().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Allowed days");
        }
        
        //Check for Validity -- Allowed Days must be Numeric
        if(!_MyError.hasError() && !Formatter.isNumber(_MyPage.getAllowedDays())) {
            //%1 field must be numeric
            _MyError.setMessageNumber("10000");
            _MyError.addParameter("Allowed days");
        }
        
        if(!_MyError.hasError() && !Formatter.isInteger(_MyPage.getAllowedDays())) {
            //%1 is invalid (%2)
            _MyError.setMessageNumber("10004");
            _MyError.addParameter("Allowed days");
            _MyError.addParameter("Entry must be a positive whole number");
        }
        
        //Check for validity -- allowed days must be positive including zero
        if(!_MyError.hasError() && Integer.parseInt(_MyPage.getAllowedDays()) < 0) {
            //%1 is invalid (%2)
            _MyError.setMessageNumber("10004");
            _MyError.addParameter("Allowed days field");
            _MyError.addParameter("Value must be greater than or equal to zero");
        }
        
    }
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyWorkUtility.setAllowedDays(Integer.parseInt(_MyPage.getAllowedDays()));
            _MyWorkUtility.setDeadline(_MyPage.getDeadline());
            try {
                _MySqlHelper.edit(_MyWorkUtility);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Work utility");
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditUtilities.class.getName()).log(Level.SEVERE, null, ex);
                 //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Edit work utility");
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
