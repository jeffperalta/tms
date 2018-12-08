/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ClientPage;

import beans.Client;
import beans.pages.EditClientPage;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.Client.sqlHelper;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class sAddClientPage extends HttpServlet {
    private ErrorMessage _MyError = null;
    private String _ButtonClicked = " ";
    private EditClientPage _MyPage = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private sqlHelper _MySqlHelper = new sqlHelper();
    private Client _MyClient = null;
    
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
           
            if(_MyError.hasError()) {
                response.sendRedirect("/TMS/webpages/Admin/Client/AdminClientPlus.jsp#" + _ButtonClicked);
                _ButtonClicked = " ";
            }
            
            if(_ButtonClicked.compareToIgnoreCase("Save") == 0) {
                response.sendRedirect("/TMS/webpages/Admin/Client/AdminClientPlus.jsp#" + _ButtonClicked); 
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
    _MyPage = (EditClientPage)request.getSession().getAttribute("myAddClientPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    _MyClient = new Client();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

    _MyPage.setClientID(request.getParameter("txtClientID").trim());
    _MyPage.setClientName(request.getParameter("txtName").trim());
    _MyPage.setAddress(request.getParameter("txtAddress").trim());
    _MyPage.setContactInformation(request.getParameter("txtContact").trim());
    _MyPage.setFax(request.getParameter("txtFax").trim());
    _MyPage.setContactPerson(request.getParameter("txtContactPerson").trim());
    _MyPage.setRemarks(request.getParameter("txtRemarks").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

    if(_ButtonClicked.compareToIgnoreCase("Save") == 0) {
        
        //Check for required field - Client ID
        if(!_MyError.hasError() && _MyPage.getClientID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Client ID");
        }
        
        //Check for required field - Client Name
        if(!_MyError.hasError() && _MyPage.getClientName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Name");
        }
        
        //Check for duplicate -> ClientID
        if(!_MyError.hasError() && _MyGeneralSqlHelper.isExist("a_client", "client_id='" + Formatter.toProperString(_MyPage.getClientID()) + "'")) {
            //A unique %1 is required.
            _MyError.setMessageNumber("10013");
            _MyError.addParameter("client ID");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
        
    if(!_MyError.hasError()) {
        try {
            _MyClient.setAddress(Formatter.toProperString(_MyPage.getAddress()));
            _MyClient.setClientId(Formatter.toProperString(_MyPage.getClientID()));
            _MyClient.setComment(Formatter.toProperString(_MyPage.getRemarks()));
            _MyClient.setContact(Formatter.toProperString(_MyPage.getContactInformation()));
            _MyClient.setContactPerson(Formatter.toProperString(_MyPage.getContactPerson()));
            _MyClient.setFax(Formatter.toProperString(_MyPage.getFax()));
            _MyClient.setName(Formatter.toProperString(_MyPage.getClientName()));

            _MySqlHelper.add(_MyClient);
            //%1 information was saved
            _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
            _MyError.setMessageNumber("10010");
            _MyError.addParameter("New client");
            
            _MyClient.clear();
            _MyPage.reset();
            
        } catch (TransactionWasNotSavedException ex) {
            Logger.getLogger(sAddClientPage.class.getName()).log(Level.SEVERE, null, ex);
            if(!_MyError.hasError()) {
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add client record");
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
