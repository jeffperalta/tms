/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package NonBillableTimeLog;

import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.pages.NonBillableTimeLogPage;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.Formatter;
import message.*;
import utilities.DateUtility;
import utilities.generalSqlHelper;
import tmsclass.nonbillables.sqlHelper;
import beans.LogInfo;
import beans.NonBillableType;
import beans.PageNavigation;
import beans.pages.NonBillableTypeSearchPage;
import java.util.ArrayList;

/**
 *
 * @author jperalta
 */
public class jspNonBillableScheduleLog extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private NonBillableTimeLogPage _MyPage = null;
    private ErrorMessage _MyError = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private sqlHelper _MySqlHelper = new sqlHelper();
    private LogInfo _MyUser = null;
    private String[] _CheckBox = null;
    private PageNavigation _MyPageNavigation = null;
    private NonBillableType _MyNonBillableType = null;
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
        response.sendRedirect("/TMS/webpages/TimeLog/NonBillableTimeLog.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/TimeLog/NonBillableTimeLog.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("filter") == 0) {
        response.sendRedirect("/TMS/webpages/TimeLog/NonBillableTimeLog.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        response.sendRedirect("/TMS/webpages/TimeLog/NonBillableTimeLog.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("nonbilltype") == 0) {
        if(_MyNonBillableType.getNonBillableTypeID().trim().length() == 0) {
            _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("NonBillableTypeSearchNav");
            _MyPageNavigation.setFileName("/TMS/webpages/TimeLog/NonBillableTimeLog.jsp#" + _ButtonClicked);
            
            _MyNonBillableTypeSearchPage = (NonBillableTypeSearchPage)request.getSession().getAttribute("PNonBillableTypePage");
            _MyNonBillableTypeSearchPage.setShowAll("0");
            
            response.sendRedirect("/TMS/webpages/SearchPages/NonBillableTypeSearch.jsp#main");
        }else{
            response.sendRedirect("/TMS/webpages/TimeLog/NonBillableTimeLog.jsp#" + _ButtonClicked);
        }
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
    _MyPage = (NonBillableTimeLogPage)request.getSession().getAttribute("PNonBillableTimeLogPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManager");
    _MyUser = (LogInfo)request.getSession().getAttribute("LogUserInfo");
    _CheckBox = request.getParameterValues("checkbox");
    _MyNonBillableType = (NonBillableType)request.getSession().getAttribute("BNonBillableType");
    ArrayList<String> myFieldList = new ArrayList<String>();
    ArrayList<String> myResultList = new ArrayList<String>();
            
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Back") != 0 ) {
        _MyPage.setDateFrom(Formatter.toProperString(request.getParameter("txtFromDate").trim()));
        _MyPage.setDateTo(Formatter.toProperString(request.getParameter("txtToDate").trim()));
        _MyPage.setHoursElapsed(Formatter.toProperString(request.getParameter("txtHoursElapsed").trim()));
        _MyPage.setRemarks(Formatter.toProperString(request.getParameter("txtRemarks").trim()));
        _MyPage.setTransactionDate(Formatter.toProperString(request.getParameter("txtDate").trim()));
        _MyPage.setType(Formatter.toProperString(request.getParameter("txtType").trim()));

        if(_MyPage.getType().trim().length() != 0) {
            myFieldList.clear();
            myResultList.clear();

            myFieldList.add("non_bill_name");
            myFieldList.add("remarks");
            myResultList = _MyGeneralSqlHelper.getDataElement("a_non_billable_list", myFieldList, "non_bill_id=" + _MyPage.getType().trim());

            if(!myResultList.isEmpty()) {
                _MyNonBillableType.setNonBillableTypeID(_MyPage.getType().trim());
                _MyNonBillableType.setNonBillableTypeName(myResultList.get(0));
                _MyNonBillableType.setRemarks(myResultList.get(1));
            }else{
                _MyNonBillableType.reset();
            }    
        }else{
            _MyNonBillableType.reset();
        }   
    }
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check required Fields - Transaction Date
        if(!_MyError.hasError() && _MyPage.getTransactionDate().trim().length() == 0 ) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Date");
        }
        
        //Check Format - Transaction Date
        if(!_MyError.hasError() && !DU.isProperDate(_MyPage.getTransactionDate())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Check Validity - Transaction Date Cannot be in the future
        try {
            if(!_MyError.hasError() && DateUtility.DateCompare(_MyPage.getTransactionDate(), DateUtility.now(), constants.DateFormat.DATE_FORMAT) > 0) {
                //You can only include entries for today or from the past
                _MyError.setMessageNumber("10001");
            }
        }catch(Exception e) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Check required fields - Type
        if(!_MyError.hasError() && _MyPage.getType().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Type");
        }
        
        //Check Validity - Type
        if(!_MyError.hasError() && !_MyGeneralSqlHelper.isExist("a_non_billable_list", "status=1 AND non_bill_id=" + _MyPage.getType())) {
            //%1 is invalid
            _MyError.setMessageNumber("10006");
            _MyError.addParameter("Type field");
        }
        
        //Check required Field - Hours Elapsed
        if(!_MyError.hasError() && _MyPage.getHoursElapsed().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Hours elapsed");
        }
        
        //Check for numeric - hours elapsed 
        if(!_MyError.hasError() && !Formatter.isNumber(_MyPage.getHoursElapsed())) {
            //%1 field must be numeric
            _MyError.setMessageNumber("10000");
            _MyError.addParameter("Hours elapsed");
        }
        
        //Check for validity - Hours elapsed must be between 1 and  24 hours only
        if(!_MyError.hasError() && 
                (Double.parseDouble(_MyPage.getHoursElapsed()) <= 0.00 ||
                Double.parseDouble(_MyPage.getHoursElapsed()) > 24.00)) {
            //%1 is invalid (%2)
            _MyError.setMessageNumber("10004");
            _MyError.addParameter("Hours elapsed");
            _MyError.addParameter("Valid value is from 1 and 24 hours only");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
        
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            try {
                _MySqlHelper.save(_MyPage, _MyUser);
                
                String strTransactionDate = _MyPage.getTransactionDate();
                
                _MyPage.reset();
                _MyNonBillableType.reset();
                
                //To view the recently added item set the from and to date query parameter to the strTransaction Date
                _MyPage.setDateFrom(strTransactionDate);
                _MyPage.setDateTo(strTransactionDate);
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(jspNonBillableScheduleLog.class.getName()).log(Level.SEVERE, null, ex);
                
                if(!_MyError.hasError()) {
                    //Transaction was not saved (%1)
                    _MyError.setMessageNumber("10005");
                    _MyError.addParameter("Saving Schedule");
                }
                
            }
        }
    }
    
    if(!_MyError.hasError()) {
        if(_CheckBox != null && _ButtonClicked.compareToIgnoreCase("delete") == 0) {
                try {
                    _MyPage.setScheduleID(_CheckBox);
                    _MySqlHelper.delete(_MyPage);
                } catch (TransactionWasNotSavedException ex) {
                    Logger.getLogger(jspNonBillableScheduleLog.class.getName()).log(Level.SEVERE, null, ex);
                    
                    if(!_MyError.hasError()) {
                        //Transaction was not saved (%1)
                        _MyError.setMessageNumber("10005");
                        _MyError.addParameter("Deleting Schedule");
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
