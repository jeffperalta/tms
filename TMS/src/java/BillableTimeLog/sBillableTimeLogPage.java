/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BillableTimeLog;

import java.io.*;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

import beans.pages.BillableTimeLogPage;
import beans.ActivityList;
import beans.pages.WorkUtilityPage;
import utilities.Formatter;
import message.*;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.DateUtility;
import tmsclass.ProjectListing.sqlHelper;
import tmsclass.WorkUtility.queryAnalyzer;


/**
 *
 * @author Abree
 */
public class sBillableTimeLogPage extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private String[] _CheckBox = null;
    private BillableTimeLogPage _MyPage = null;
    private ActivityList _MyActivityList = null;
    private ErrorMessage _MyError = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
    
    private queryAnalyzer _MyQueryAnalyzer = new queryAnalyzer();
    private WorkUtilityPage _MyWorkUtilityPage = null, _MyWorkUtilityPageTemp = null;
    
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
            response.sendRedirect("/TMS/webpages/TimeLog/BillableTimeLog.jsp#" + _ButtonClicked );
            _ButtonClicked = " ";
        }

        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            response.sendRedirect("/TMS/webpages/TimeLog/BillableTimeLog.jsp#" + _ButtonClicked);
        }

        if(_ButtonClicked.compareToIgnoreCase("filter") == 0) {
            response.sendRedirect("/TMS/webpages/TimeLog/BillableTimeLog.jsp#" + _ButtonClicked);
        }

        if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
            response.sendRedirect("/TMS/webpages/TimeLog/BillableTimeLog.jsp#" + _ButtonClicked);
        }
        
        if(_ButtonClicked.compareToIgnoreCase("back") == 0) {
            response.sendRedirect("/TMS/webpages/TimeLog/ProjectSelection.jsp#main");
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
    _CheckBox = request.getParameterValues("checkbox");
    _MyPage = (BillableTimeLogPage)request.getSession().getAttribute("myBillableTimeLogPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManager");
    _MyWorkUtilityPage =(WorkUtilityPage)request.getSession().getAttribute("PWorkUtilityPage");
    
    //Use to determine if transaction date is editable
    _MyActivityList = (ActivityList)request.getSession().getAttribute("myActivityList");
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Back") != 0) { 
        _MyPage.setTransactionDate(request.getParameter("txtTransactionDate").trim());
        _MyPage.setRHoursWorked(request.getParameter("txtHoursWorked").trim());
        _MyPage.setRemarks(request.getParameter("txtRemarks").trim());
        _MyPage.setFromDate(request.getParameter("txtFromDate").trim());
        _MyPage.setToDate(request.getParameter("txtToDate").trim());
        
        _MyPage.setDefaultActivityID(request.getParameter("cdbActivityType").trim());
        
        //Determine The Starting Date For Deadline and The Deadline itself
        _MyWorkUtilityPageTemp = _MyQueryAnalyzer.reset();
        _MyWorkUtilityPage.setStartDateForDeadline(_MyWorkUtilityPageTemp.getStartDateForDeadline());
        _MyWorkUtilityPage.setDeadline(_MyWorkUtilityPageTemp.getDeadline());
        _MyWorkUtilityPage.setAllowedDays(_MyWorkUtilityPageTemp.getAllowedDays());
    }
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        
        //Check for required Field - Transaction Date
        if(!_MyError.hasError() && _MyPage.getTransactionDate().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Date");
        }
        
        //Check for valid format - Transaction date
        if(!_MyError.hasError() && 
                !DU.isProperDate(_MyPage.getTransactionDate().trim())) {        
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Check for valid Field - Transaction Date (No Future)
        try {
            if(!_MyError.hasError()  && 
                    (DateUtility.DateCompare(_MyPage.getTransactionDate(), DateUtility.now(), constants.DateFormat.DATE_FORMAT) > 0)) {
                //You can only include entries for today or from the past
                _MyError.setMessageNumber("10001");
            }
        }catch (Exception e) {
            if(!_MyError.hasError()) {
                //%1 field is not in the proper format (%2)
                _MyError.setMessageNumber("10002");
                _MyError.addParameter("Date");
                _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
            }
        }
        
        try {
            //Check for valid field -- Transaction Date must be within the date range for past dates
            if (!_MyError.hasError() && _MyActivityList.getAllowPastDate() == 0 && (
                    DateUtility.DateCompare(DateUtility.addDays(_MyWorkUtilityPage.getDeadline(), -(Integer.parseInt(_MyWorkUtilityPage.getAllowedDays()))), _MyPage.getTransactionDate(), constants.DateFormat.DATE_FORMAT) > 0 )
                    ) {
                //%1 is invalid (%2)
                _MyError.setMessageNumber("10004");
                _MyError.addParameter("Date");
                _MyError.addParameter("The entered date does not fall within the specified period.");
            }
        } catch (ParseException ex) {
            Logger.getLogger(sBillableTimeLogPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Check for required Field - Activity Type
        if(!_MyError.hasError() && _MyPage.getDefaultActivityID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Activity");
        }
        
        //Check for required Field - Hours Worked
        if(!_MyError.hasError() && _MyPage.getRHoursWorked().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Hours worked");
        }
        
        //Check if numeric - Hours Worked
        if(!_MyError.hasError() && !Formatter.isNumber(_MyPage.getRHoursWorked())) {
            //%1 field must be numeric
            _MyError.setMessageNumber("10000");
            _MyError.addParameter("Hours worked");
        }
        
        //Check if range for numeric - Hours Worked (1 to 24 only)
        if(!_MyError.hasError()) {
            _MyPage.setHoursWorked(Double.parseDouble(_MyPage.getRHoursWorked()));
            if(_MyPage.getHoursWorked() <= 0 || _MyPage.getHoursWorked() > 24) {
                //%1 is invalid (%2)
                _MyError.setMessageNumber("10004");
                _MyError.addParameter("Hours worked");
                _MyError.addParameter("Valid value is from 1 and 24 hours only");
            }
        }
        
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    
    if(!_MyError.hasError() && _ButtonClicked.compareToIgnoreCase("save") == 0) {
        try {
            _MySqlHelper.saveSchedule(_MyActivityList, _MyPage);
            
            _MyPage.setToDate(_MyPage.getTransactionDate());
            _MyPage.setFromDate(_MyPage.getTransactionDate());
            
            //Clear save input fields
            _MyPage.setTransactionDate(" ");
            _MyPage.setRHoursWorked(" ");
            _MyPage.setRemarks(" ");
            
        }catch(TransactionWasNotSavedException e) {
            if(!_MyError.hasError()) {
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Save Schedule: sBillableTimeLogPage");
            }
        }
    }
    
    if(!_MyError.hasError() && _ButtonClicked.compareToIgnoreCase("delete") == 0) {
        if(_CheckBox != null) {
            _MyPage.setScheduleID(_CheckBox);
             try {
                _MySqlHelper.deleteSchedule(_MyPage);
            }catch(TransactionWasNotSavedException e) {
                if(!_MyError.hasError()) {
                    //Transaction was not saved (%1)
                    _MyError.setMessageNumber("10005");
                    _MyError.addParameter("Delete Schedule: sBillableTimeLogPage");
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
