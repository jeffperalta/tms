/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Leaves;

import beans.LeaveType;
import beans.LogInfo;
import beans.PageNavigation;
import beans.pages.LeaveTimeLogPage;
import beans.pages.LeaveTypeSearchPage;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import tmsclass.Leaves.sqlHelper;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.DateUtility;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class jspLeaveScheduleLog extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private sqlHelper _MySqlHelper = new sqlHelper();
    private LeaveTimeLogPage _MyPage = null;
    private ErrorMessage _MyError = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private LeaveType _MyLeaveType = null;
    private LogInfo _MyUser = null;
    private String[] _MyCheckBox = null;
    private PageNavigation _MyPageNavigation = null;
    private LeaveTypeSearchPage _MyLeaveTypeSearchPage = null;
    
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
        response.sendRedirect("/TMS/webpages/Leaves/LeaveTimeLog.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Leaves/LeaveTimeLog.jsp#Save");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Leaves/LeaveTimeLog.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("filter") == 0) {
        response.sendRedirect("/TMS/webpages/Leaves/LeaveTimeLog.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        response.sendRedirect("/TMS/webpages/Leaves/LeaveTimeLog.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("nonbilltype") == 0) {
        if(_MyLeaveType.getLeaveID()==0) {
            _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("LeaveTypeSearchNav");
            _MyPageNavigation.setFileName("/TMS/jspLeaveScheduleLog");
            
            _MyLeaveTypeSearchPage = (LeaveTypeSearchPage)request.getSession().getAttribute("PLeaveTypePage");
            _MyLeaveTypeSearchPage.setShowStatusField(0);
            
            response.sendRedirect("/TMS/webpages/SearchPages/LeaveTypeSearch.jsp#main");
        }else{
            response.sendRedirect("/TMS/webpages/Leaves/LeaveTimeLog.jsp#" + _ButtonClicked);
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
        
/******************************************************************************
 *--Response Section--                                                        *
 ******************************************************************************/
    _MyLeaveType = (LeaveType)request.getSession().getAttribute("BLeaveType");
    _MyPage = (LeaveTimeLogPage)request.getSession().getAttribute("PLeaveTimeLogPage");
    _MyUser =(LogInfo)request.getSession().getAttribute("LogUserInfo");

/******************************************************************************
 *--Declaration/Instantiation Section--                                       *
 ******************************************************************************/

/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if((_MyLeaveType.getLeaveID() + "").trim().length() != 0) {
        _MyPage.setType(_MyLeaveType.getLeaveID()+"");
        _MySqlHelper.refreshLeaveDetails(_MyPage, _MyUser);
        _ButtonClicked = "doGet";
    }
        
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
    _MyPage = (LeaveTimeLogPage)request.getSession().getAttribute("PLeaveTimeLogPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManager");
    _MyLeaveType = (LeaveType)request.getSession().getAttribute("BLeaveType");
    _MyUser =(LogInfo)request.getSession().getAttribute("LogUserInfo");
    _MyCheckBox = request.getParameterValues("checkbox");
    
    ArrayList<String> myFieldList = new ArrayList<String>();
    ArrayList<String> myResultList = new ArrayList<String>();
    
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setTransactionDate(Formatter.toProperString(request.getParameter("txtDate").trim()));
    _MyPage.setType(Formatter.toProperString(request.getParameter("txtType").trim()));
    _MyPage.setRemarks(Formatter.toProperString(request.getParameter("txtRemarks").trim()));
    _MyPage.setFromDate(Formatter.toProperString(request.getParameter("txtFromDate").trim()));
    _MyPage.setToDate(Formatter.toProperString(request.getParameter("txtToDate").trim()));
    _MyPage.setEndDate(Formatter.toProperString(request.getParameter("txtEndDate").trim()));
    
    if(request.getParameter("chkHalfDay") != null) {
        _MyPage.setHalfDay(true);
    }else{
        _MyPage.setHalfDay(false);
    }
    
    if(_MyPage.getType().trim().length() != 0 && Formatter.isNumber(_MyPage.getType())) {
        myFieldList.clear();
        myResultList.clear();
        
        myFieldList.add("leave_name");
        myResultList = _MyGeneralSqlHelper.getDataElement("a_leaves", myFieldList, "leave_id=" + _MyPage.getType() + " AND status=1");
        
        if(!myResultList.isEmpty()) {
            _MyLeaveType.setLeaveID(Integer.parseInt(_MyPage.getType()));
            _MyLeaveType.setLeaveName(myResultList.get(0));
            _MySqlHelper.refreshLeaveDetails(_MyPage, _MyUser); 
        }else{
            _MyLeaveType.reset();
            _MyPage.setLeaveName(" ");
            _MyPage.setLeaveRemarks(" ");
            _MyPage.setMaxLeave(" ");
            _MyPage.setMaxUsedLeave(" ");
        }
    }else{
        _MyLeaveType.reset();
        _MyPage.setLeaveName(" ");
        _MyPage.setLeaveRemarks(" ");
        _MyPage.setMaxLeave(" ");
        _MyPage.setMaxUsedLeave(" ");
    }
    
    _MyPage.setTypeSearchID(request.getParameter("cmbTypeSearch").trim());
    _MyPage.setTypeSearchName(" ");
    if(_MyPage.getTypeSearchID().compareToIgnoreCase("-1") != 0) {
        myFieldList.clear();
        myResultList.clear();
        
        myFieldList.add("leave_name");
        myResultList = _MyGeneralSqlHelper.getDataElement("a_leaves", myFieldList, "leave_id=" + _MyPage.getTypeSearchID().trim());
        
        if(!myResultList.isEmpty()) {
            _MyPage.setTypeSearchName(myResultList.get(0));
        }else{
            _MyPage.setTypeSearchID("-1");
            _MyPage.setTypeSearchName(" ");
        }
    }
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Required fields - Start Date
        if(!_MyError.hasError() && _MyPage.getTransactionDate().trim().length() == 0){
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Start date");
        }
        
        //Invalid format - Start Date
        if(!_MyError.hasError() && !DU.isProperDate(_MyPage.getTransactionDate())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Start date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Required fields - End Date
        if(!_MyError.hasError() && _MyPage.getEndDate().trim().length() == 0){
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("End date");
        }
        
        //Invalid format - End Date
        if(!_MyError.hasError() && !DU.isProperDate(_MyPage.getEndDate())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("End date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Invalid Entry - End Date comes before the Start Date
        try {
            if(!_MyError.hasError() && DateUtility.DateCompare(_MyPage.getTransactionDate(), _MyPage.getEndDate(), constants.DateFormat.DATE_FORMAT) > 0 ) {
                //%1 must comes first before %2
                _MyError.setMessageNumber("10007");
                _MyError.addParameter("Start date");
                _MyError.addParameter("the end date");
            }
        }catch(Exception e) {
            Logger.getLogger(jspLeaveScheduleLog.class.getName()).log(Level.SEVERE, null, e);
        }
        
        //Required field - Type
        if(!_MyError.hasError() && _MyPage.getType().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Type");
        }
        
        //Validity - Type
        if(!_MyError.hasError() && !_MyGeneralSqlHelper.isExist("a_leaves", "status=1 AND leave_id=" + _MyPage.getType())) {
            //%1 is invalid
            _MyError.setMessageNumber("10006");
            _MyError.addParameter("Type field");
        }
        
        if(!_MyError.hasError() && (utilities.DateUtility.getYear(_MyPage.getTransactionDate()).compareToIgnoreCase(utilities.DateUtility.getYear(utilities.DateUtility.now())) !=0  || 
                                    utilities.DateUtility.getYear(_MyPage.getEndDate()).compareToIgnoreCase(utilities.DateUtility.getYear(utilities.DateUtility.now())) !=0)) {
                                    
            //File a leave for this year only
            _MyError.setMessageNumber("10016");
        }
        
        if(!_MyError.hasError()) {
            //Validity - Cannot have the other leave types on the same date
            long lDateDifference = utilities.DateUtility.DateDifference(_MyPage.getTransactionDate(), _MyPage.getEndDate());
            String strDate = " ";
            for(int iCtr = 0; iCtr <= lDateDifference; iCtr++) {
                strDate = utilities.DateUtility.addDays(_MyPage.getTransactionDate(), iCtr);
                if(_MyGeneralSqlHelper.isExist("a_leave_details",
                                "user_id='" + _MyUser.getUserId() + "' AND " +
                                "trans_date='" + strDate + "'")) {
                                
                    //Cannot file multiple leave on %1
                    _MyError.setMessageNumber("10008");
                    try {
                        _MyError.addParameter(utilities.DateUtility.FormatDate(strDate, constants.DateFormat.DATE_FORMAT, constants.DateFormat.DATE_DISPLAY_FORMAT));
                    } catch (Exception ex) {
                        Logger.getLogger(jspLeaveScheduleLog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }

            }
        }
        
        //Check if the employee exceeds limit.
        if(!_MyError.hasError() && _MySqlHelper.isExcedeLeave(_MyPage, _MyUser)) {
            //Maximum number of leaves has been reached
            _MyError.setMessageNumber("10009");
        }
        
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
        
    if(!_MyError.hasError() && _ButtonClicked.compareToIgnoreCase("save") == 0) {
        try {
            _MySqlHelper.save(_MyPage, _MyUser);
            
            String strTransactionDate = _MyPage.getTransactionDate();
            String strEndDate = _MyPage.getEndDate();
            
            _MyPage.reset();
            
            //To view the previous record set the query date parameters;
            _MyPage.setFromDate(strTransactionDate);
            _MyPage.setToDate(strEndDate);
            
        } catch (TransactionWasNotSavedException ex) {
            Logger.getLogger(jspLeaveScheduleLog.class.getName()).log(Level.SEVERE, null, ex);

            if(!_MyError.hasError()) {
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Saving Schedule");
            }
        }
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0 && !_MyError.hasError() && _MyCheckBox != null) {
        try {
            _MyPage.setItemsToDelete(_MyCheckBox);
            _MySqlHelper.delete(_MyPage);
        } catch (TransactionWasNotSavedException ex) {
            Logger.getLogger(jspLeaveScheduleLog.class.getName()).log(Level.SEVERE, null, ex);
            
            if(!_MyError.hasError()) {
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Saving Schedule");
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
