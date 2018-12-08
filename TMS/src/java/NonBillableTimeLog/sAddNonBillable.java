/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package NonBillableTimeLog;

import beans.NonBillableType;
import beans.pages.NonBillablePage;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import tmsclass.nonbillables.sqlHelper;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class sAddNonBillable extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private NonBillablePage _MyPage = null;
    private ErrorMessage _MyError = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private NonBillableType _MyBean = new NonBillableType();
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
        response.sendRedirect("/TMS/webpages/Admin/NonBillable/NonBillablePlus.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0)  {
        response.sendRedirect("/TMS/webpages/Admin/NonBillable/NonBillablePlus.jsp#" + _ButtonClicked);
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
    _MyPage = (NonBillablePage)request.getSession().getAttribute("myAddNonBillablePage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setNonBillableID(request.getParameter("txtNonBillableID").trim());
    _MyPage.setName(request.getParameter("txtName").trim());
    _MyPage.setRemarks(request.getParameter("txtRemarks").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check for required field -- Non billable ID
        if(!_MyError.hasError() && _MyPage.getNonBillableID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Non-billable ID");
        }
        
        //Check for duplicate field -- non billable ID
        if(!_MyError.hasError() && _MyGeneralSqlHelper.isExist("a_non_billable_list", "non_bill_id=" + Formatter.toProperSqlWhereClause(_MyPage.getNonBillableID()))) {
            //A unique %1 is required.
            _MyError.setMessageNumber("10013");
            _MyError.addParameter("non-billable ID");
        }
        
        //Check for required field -- Non billable name
        if(!_MyError.hasError() && _MyPage.getName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Non-billable name");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyBean.setNonBillableTypeID(Formatter.toProperString(_MyPage.getNonBillableID()));
            _MyBean.setStatus(1);
            _MyBean.setNonBillableTypeName(Formatter.toProperString(_MyPage.getName()));
            _MyBean.setRemarks(Formatter.toProperString(_MyPage.getRemarks()));
            
            try {

                _MySqlHelper.add(_MyBean);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Non-billable");

                _MyPage.reset();
                _MyBean.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sAddNonBillable.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add non-billable record");
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
