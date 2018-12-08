/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ActivityPage;

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
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author user
 */
public class sAddDefaultActivity extends HttpServlet {
    private String _ButtonClicked = " ";
    private DefaultActivityPage _MyPage = null;
    private generalSqlHelper _MyGenSqlHelper = new generalSqlHelper();
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
        response.sendRedirect("/TMS/webpages/Admin/Activity/AdminDefaultActivityPlus.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") ==0 ){
        response.sendRedirect("/TMS/webpages/Admin/Activity/AdminDefaultActivityPlus.jsp#" + _ButtonClicked);
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
    _MyPage = (DefaultActivityPage)request.getSession().getAttribute("myAddDefaultActivityPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    ArrayList<String> myFieldList = new ArrayList<String>();
    ArrayList<String> myResultList = new ArrayList<String>();
        
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setActivityID(request.getParameter("txtActivityID").trim());
    
    _MyPage.setDepartmentID(request.getParameter("cmbDepartmentID").trim());
    //Get department Name
    if(_MyPage.getDepartmentID().trim().length() != 0) {
        myFieldList.clear();
        myResultList.clear();
        myFieldList.add("dept_name");
        myResultList = _MyGenSqlHelper.getDataElement("a_department", 
                myFieldList, "dept_id='" + _MyPage.getDepartmentID() + "'");
        if(!myResultList.isEmpty()) {
            _MyPage.setDepartmentName(myResultList.get(0));
        }else{
            _MyPage.setDepartmentName(" ");
        }
    }else{
        _MyPage.setDepartmentName(" ");
    }
    
    _MyPage.setActivityName(request.getParameter("txtName").trim());
    
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
        
        //Check for duplicate -- Activity ID
        if(!_MyError.hasError() && _MyGenSqlHelper.isExist("a_def_activity", "act_id='" + Formatter.toProperSqlWhereClause(_MyPage.getActivityID()) + "'")) {
            //A unique %1 is required.
            _MyError.setMessageNumber("10013");
            _MyError.addParameter("activity ID");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("Save") == 0) {
            try {
                _MySqlHelper.add(_MyPage);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Activity");

                _MyPage.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sAddDefaultActivity.class.getName()).log(Level.SEVERE, null, ex);
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
