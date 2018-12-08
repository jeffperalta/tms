/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ActivityPage;

import beans.DefaultActivity;
import beans.PageNavigation;
import beans.pages.DefaultActivityPage;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.Activity.sqlHelper;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.generalSqlHelper;

/**
 *
 * @author user
 */
public class sEditDefaultActivity extends HttpServlet {
    private String _ButtonClicked = " ";
    private DefaultActivityPage _MyPage = null;
    private PageNavigation _MyPageNavigation = null;
    private DefaultActivity _MyBean = null;
    private ArrayList<String> _MyFieldList = new ArrayList<String>();
    private ArrayList<String> _MyResultList = new ArrayList<String>();
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private ErrorMessage _MyError = null;
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
        response.sendRedirect("/TMS/webpages/Admin/Activity/AdminDefaultActivity.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Activity/AdminDefaultActivity.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Activity/AdminDefaultActivity.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("activityid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BDefaultActivitySearchNav");
        _MyPageNavigation.setFileName("/TMS/sEditDefaultActivity");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/DefaultActivitySearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Activity/AdminDefaultActivity.jsp#Save");
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
    _MyBean = (DefaultActivity)request.getSession().getAttribute("BDefaultActivity");
    _MyPage = (DefaultActivityPage) request.getSession().getAttribute("myEditDefaultActivityPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    _ButtonClicked = "doGet";
 
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
   
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/    
    _MyPage.setActivityID(_MyBean.getActivityID());
    _MyPage.setActivityName(_MyBean.getActivityName());
    _MyPage.setActivityStatus(_MyBean.getStatus() + "");
    _MyPage.setDepartmentID(_MyBean.getDepartmentID());
    
    if(_MyPage.getDepartmentID().trim().length() != 0) {
        _MyFieldList.clear();
        _MyResultList.clear();
        
        _MyFieldList.add("dept_name");
        _MyResultList = _MyGeneralSqlHelper.getDataElement("a_department", 
                _MyFieldList, "dept_id='" + _MyPage.getDepartmentID() + "'");
        if(!_MyResultList.isEmpty()) {
            _MyPage.setDepartmentName(_MyResultList.get(0));
        }else{
            _MyPage.setDepartmentName(" ");
        }        
    }else{
        _MyPage.setDepartmentName(" ");
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
    _MyPage = (DefaultActivityPage)request.getSession().getAttribute("myEditDefaultActivityPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setActivityName(request.getParameter("txtName").trim());
    
    if(request.getParameter("chkStatus") == null) {
        _MyPage.setActivityStatus("0");
    }else{
        _MyPage.setActivityStatus("1");
    }
    
    _MyPage.setDepartmentID(request.getParameter("cmbDepartmentID").trim());
    if(_MyPage.getDepartmentID().trim().length() != 0) {
        _MyFieldList.clear();
        _MyResultList.clear();
        
        _MyFieldList.add("dept_name");
        _MyResultList = _MyGeneralSqlHelper.getDataElement("a_department", 
                _MyFieldList, "dept_id='" + _MyPage.getDepartmentID() + "'");
        if(!_MyResultList.isEmpty()) {
            _MyPage.setDepartmentName(_MyResultList.get(0));
        }else{
            _MyPage.setDepartmentName(" ");
        }        
    }else{
        _MyPage.setDepartmentName(" ");
    }
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
   if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        //Check for required field -- activity ID
        if (!_MyError.hasError() && _MyPage.getActivityID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Activity ID");
        }
        
        //Check for required field -- department ID
        if (!_MyError.hasError() && _MyPage.getDepartmentID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Department ID");
        }
        
        //Check for required field -- Activity Name
        if (!_MyError.hasError() && _MyPage.getActivityName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Activity Name");
        }
   }
    
   if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
       //Check for required field -- activity ID
        if (!_MyError.hasError() && _MyPage.getActivityID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Activity ID");
        }
       
        //Cannot delete referenced record
        if(!_MyError.hasError() && _MyGeneralSqlHelper.isExist("a_schedule", "activity_type='" + _MyPage.getActivityID() + "'")) {
           //Cannot delete a referenced record. (%1)
           _MyError.setMessageNumber("10011");
           _MyError.addParameter("This activity is already assigned to an employee.");
        }
   }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
   if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            try {
                _MySqlHelper.edit(_MyPage);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Activity");

                _MyPage.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditDefaultActivity.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add new activity record");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("Delete") == 0) { 
            try {
                _MySqlHelper.delete(_MyPage);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Activity");

                _MyPage.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditDefaultActivity.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add new activity record");
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
