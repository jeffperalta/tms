/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import beans.PageNavigation;
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
import utilities.Formatter;
 
/**
 *
 * @author jperalta
 */
public class sEditUpdate extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private WorkUtilityPage _MyPage = null;
    private WorkUtilityPage _MyMessage = null;
    private ErrorMessage _MyError = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
    private PageNavigation _MyPageNavigation = null;
    
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
        response.sendRedirect("/TMS/webpages/Admin/Utilities/EditUpdate.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Utilities/EditUpdate.jsp#" + _ButtonClicked);
    }
         
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Utilities/EditUpdate.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("updateid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("MessageUpdateSearchNav");
        _MyPageNavigation.setFileName("/TMS/sEditUpdate");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/UpdateSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Utilities/EditUpdate.jsp#Save");
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
    _MyPage = (WorkUtilityPage)request.getSession().getAttribute("PEditMessagePage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    _MyMessage = (WorkUtilityPage)request.getSession().getAttribute("BMessageUpdate");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    _MyPage.setMessageNumber(_MyMessage.getMessageNumber());
    _MyPage.setMessageTitle(_MyMessage.getMessageTitle());
    _MyPage.setMessageBody(_MyMessage.getMessageBody());
    _MyPage.setDateEntered(_MyMessage.getDateEntered());
    _MyPage.setVisible(_MyMessage.getVisible());
    _MyPage.setAuthor(_MyMessage.getAuthor());
    
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
    _MyPage = (WorkUtilityPage)request.getSession().getAttribute("PEditMessagePage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setMessageTitle(request.getParameter("txtTitle").trim());
    _MyPage.setMessageBody(request.getParameter("txtBody").trim());
    
    if(request.getParameter("checkbox") == null) {
        _MyPage.setVisible("0");
    }else{
        _MyPage.setVisible("1");
    }
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
   if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check for required field -- message number
        if(!_MyError.hasError() && _MyPage.getMessageNumber().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Message number");
        }
       
       
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
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
         //Check for required field -- message number
        if(!_MyError.hasError() && _MyPage.getMessageNumber().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Message number");
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
                _MySqlHelper.editUpdate(_MyPage);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Update");
                
                _MyPage.resetMessage();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditUpdate.class.getName()).log(Level.SEVERE, null, ex);
                
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Edit update information");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
            try {
                _MySqlHelper.deleteUpdate(_MyPage);
                
                //%1 was successfully deleted.
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10012");
                _MyError.addParameter("Update record");
                
                _MyPage.resetMessage();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditUpdate.class.getName()).log(Level.SEVERE, null, ex);
                 //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Delete update information");
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
