/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package NonBillableTimeLog;

import beans.NonBillableType;
import beans.PageNavigation;
import beans.pages.NonBillablePage;
import beans.pages.NonBillableTypeSearchPage;
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
public class sEditNonBillable extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private NonBillablePage _MyPage = null;
    private ErrorMessage _MyError = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private sqlHelper _MySqlHelper = new sqlHelper();
    private NonBillableType _MyNonBillable = null;
    private PageNavigation _MyPageNavigation = null;
    private NonBillableTypeSearchPage _MyNonBillableTypeSearchPage = null;
    
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
        response.sendRedirect("/TMS/webpages/Admin/NonBillable/NonBillable.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save")== 0) {
        response.sendRedirect("/TMS/webpages/Admin/NonBillable/NonBillable.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete")==0) {
        response.sendRedirect("/TMS/webpages/Admin/NonBillable/NonBillable.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("nonbillableid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("NonBillableTypeSearchNav");
        _MyPageNavigation.setFileName("/TMS/sEditNonBillable");
        
        _MyNonBillableTypeSearchPage =(NonBillableTypeSearchPage)request.getSession().getAttribute("PNonBillableTypePage");
        _MyNonBillableTypeSearchPage.setShowAll("1");
        
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/NonBillableTypeSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/NonBillable/NonBillable.jsp#Save");
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
    _MyPage = (NonBillablePage)request.getSession().getAttribute("myEditNonBillablePage");
    _MyNonBillable = (NonBillableType)request.getSession().getAttribute("BNonBillableType");
    _ButtonClicked = "doGet";
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

    _MyPage.setName(_MyNonBillable.getNonBillableTypeName());
    _MyPage.setNonBillableID(_MyNonBillable.getNonBillableTypeID());
    _MyPage.setRemarks(_MyNonBillable.getRemarks());
    _MyPage.setStatus(_MyNonBillable.getStatus()+"");
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
        
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
    _MyPage = (NonBillablePage)request.getSession().getAttribute("myEditNonBillablePage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setName(request.getParameter("txtName").trim());
    _MyPage.setRemarks(request.getParameter("txtRemarks").trim());
    
    if(request.getParameter("checkbox") != null) {
        _MyPage.setStatus("1");
    }else{
        _MyPage.setStatus("0");
    }
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check for required field -- Non Billable ID
        if(!_MyError.hasError() && _MyPage.getNonBillableID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Non billable ID");
        }
        
        //Check for required field -- Non Billable Name
        if(!_MyError.hasError() && _MyPage.getName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Non billable name");
        }
        
        //Check for required field -- Status
        if(!_MyError.hasError() && _MyPage.getStatus().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Status");
        }
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        //Check for required field -- Non Billable ID
        if(!_MyError.hasError() && _MyPage.getNonBillableID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Non billable ID");
        }
        
        //Cannot delete referenced record -- Non Billable
        if(!_MyError.hasError() && _MyGeneralSqlHelper.isExist("a_non_billable", "non_bill_type="+ Formatter.toProperSqlWhereClause(_MyPage.getNonBillableID()))){
            //Cannot delete a referenced record. (%1)
            _MyError.setMessageNumber("10011");
            _MyError.addParameter("This non billable type is already availed by a user.");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyNonBillable = new NonBillableType(_MyPage);
            try {
                _MySqlHelper.edit(_MyNonBillable);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Non-billable");

                _MyPage.reset();
                _MyNonBillable.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditNonBillable.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Edit non-billable record");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
            _MyNonBillable = new NonBillableType(_MyPage);
            try {
                _MySqlHelper.delete(_MyNonBillable);
                
                //%1 was successfully deleted.
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10012");
                _MyError.addParameter("Non-billable record");
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditNonBillable.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Delete non-billable record");
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
