/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ClientPage;

import beans.Client;
import beans.PageNavigation;
import beans.pages.EditClientPage;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import tmsclass.Client.sqlHelper;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.Formatter;
import message.MessageType;
import utilities.generalSqlHelper;

/**
 *
 * @author Abree
 */
public class sEditClientPage extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private EditClientPage _MyPage = null;
    private Client _MyClient = null;
    private ErrorMessage _MyError = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
    private generalSqlHelper _MyGenSqlHelper = new generalSqlHelper();
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
        response.sendRedirect("/TMS/webpages/Admin/Client/AdminClient.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }        
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
         response.sendRedirect("/TMS/webpages/Admin/Client/AdminClient.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("Delete") == 0) {
         response.sendRedirect("/TMS/webpages/Admin/Client/AdminClient.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("clientid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ClientSearchNav");
        _MyPageNavigation.setFileName("/TMS/webpages/Admin/Client/AdminClient.jsp#" + _ButtonClicked);
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ClientSearch.jsp#main");
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
    _MyPage = (EditClientPage)request.getSession().getAttribute("myEditClientPage");
    _MyClient = (Client)request.getSession().getAttribute("BClient");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setClientID(_MyClient.getClientId().trim());
    _MyPage.setClientName(request.getParameter("txtName").trim());
    _MyPage.setAddress(request.getParameter("txtAddress").trim());
    _MyPage.setContactInformation(request.getParameter("txtContact").trim());
    _MyPage.setFax(request.getParameter("txtFax").trim());
    _MyPage.setContactPerson(request.getParameter("txtContactPerson").trim());
    _MyPage.setRemarks(request.getParameter("txtRemarks").trim());
  
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check for required field - Client Name
        if(!_MyError.hasError() && _MyPage.getClientName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Name");
        }
        
        //Check for required field - Client ID
        if(!_MyError.hasError() && _MyPage.getClientID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Client ID");
        }
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        //Check for required field - Client ID
        if(!_MyError.hasError() && _MyPage.getClientID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Client ID");
        }
        
        //Cannot Delete Reference Record - Client Record
        if(!_MyError.hasError() && _MyGenSqlHelper.isExist("a_project", "client_id='" + _MyPage.getClientID() + "'")) {
            //Cannot delete a referenced record. (%1)
            _MyError.setMessageNumber("10011");
            _MyError.addParameter("The client already has an assigned project.");
        }
    }
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    
    if(!_MyError.hasError() && _ButtonClicked.compareToIgnoreCase("save") ==0) {
        _MyClient.setClientId(_MyPage.getClientID());
        _MyClient.setAddress(Formatter.toProperString(_MyPage.getAddress()));
        _MyClient.setComment(Formatter.toProperString(_MyPage.getRemarks()));
        _MyClient.setContact(Formatter.toProperString(_MyPage.getContactInformation()));
        _MyClient.setContactPerson(Formatter.toProperString(_MyPage.getContactPerson()));
        _MyClient.setFax(Formatter.toProperString(_MyPage.getFax()));
        _MyClient.setName(Formatter.toProperString(_MyPage.getClientName()));
        try {
            _MySqlHelper.edit(_MyClient);
            //%1 information was saved
            _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
            _MyError.setMessageNumber("10010");
            _MyError.addParameter("Client");
            
            _MyPage.reset();
            _MyClient.clear();

        } catch (TransactionWasNotSavedException ex) {
            Logger.getLogger(sEditClientPage.class.getName()).log(Level.SEVERE, null, ex);
            if(!_MyError.hasError()) {
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Edit client record");
            }
        }    
    }
    
    if(!_MyError.hasError() && _ButtonClicked.compareToIgnoreCase("delete") == 0) {
        _MyClient.setClientId(_MyPage.getClientID());
        try {
            _MySqlHelper.delete(_MyClient);
            _MyPage.reset();
            _MyClient.clear();
            //%1 was successfully deleted.
            _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
            _MyError.setMessageNumber("10012");
            _MyError.addParameter("Client record");
        } catch (TransactionWasNotSavedException ex) {
            Logger.getLogger(sEditClientPage.class.getName()).log(Level.SEVERE, null, ex);
            if(!_MyError.hasError()) {
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Delete client record");
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
