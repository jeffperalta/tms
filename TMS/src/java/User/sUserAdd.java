/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package User;

import beans.User;
import beans.pages.UserPage;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import tmsclass.user.sqlHelper;
import utilities.generalSqlHelper;

/**
 *
 * @author Abree
 */
public class sUserAdd extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private UserPage _MyPage = null;
    ArrayList<String> _FieldList = new ArrayList<String>();
    ArrayList<String> _ResultList = new ArrayList<String>();
    private generalSqlHelper _MyGenSqlHelper = new generalSqlHelper();
    private ErrorMessage _MyError = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
    private User _MyUser = null;
    
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
        response.sendRedirect("/TMS/webpages/Admin/User/AdminUserPlus.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/User/AdminUserPlus.jsp#" + _ButtonClicked);
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
    _MyPage = (UserPage)request.getSession().getAttribute("myUserAddPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    //MyUser is instantiated at the process section right before the bean is saved.
    utilities.DateUtility DU = new utilities.DateUtility();
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setUserID(request.getParameter("txtUserID").trim());
    _MyPage.setFirstName(request.getParameter("txtFirstName").trim());
    _MyPage.setLastName(request.getParameter("txtLastName").trim());
    _MyPage.setMiddleName(request.getParameter("txtMiddleName").trim());
    _MyPage.setContactInformation(request.getParameter("txtContact").trim());
    _MyPage.setUserName(request.getParameter("txtUserName").trim());
    _MyPage.setPassword(request.getParameter("txtPassword").trim());
    _MyPage.setConfirmPassword(request.getParameter("txtConfirm").trim());
    _MyPage.setDateEntered(request.getParameter("txtDateEntered").trim());
    if(_MyPage.getDateEntered().trim().length() == 0) {
        _MyPage.setDateEntered(utilities.DateUtility.now());
    }
    
    _MyPage.setAccessTypeR(request.getParameter("cmbAccessType").trim());
    if(_MyPage.getAccessTypeR().trim().length() == 0) {
        _MyPage.setAccessTypeName(" ");
    }else{
        _FieldList.clear();
        _ResultList.clear();
        
        _FieldList.add("access_type_name");
        _ResultList = _MyGenSqlHelper.getDataElement("a_access_type", 
                            _FieldList, "access_type_id=" + _MyPage.getAccessTypeR());
        if(!_ResultList.isEmpty()) {
            _MyPage.setAccessTypeName(_ResultList.get(0));
        }else{
            _MyPage.setAccessTypeR(" ");
            _MyPage.setAccessTypeName(" ");
        } 
    }
    
    _MyPage.setActiveR("1"); //Default to active when a new user information is added.
    
    _MyPage.setDepartmentID(request.getParameter("cmbDepartmentID").trim());
    if(_MyPage.getDepartmentID().trim().length() != 0) {
        _FieldList.clear();
        _ResultList.clear();
        
        _FieldList.add("dept_name");
        _ResultList = _MyGenSqlHelper.getDataElement("a_department", _FieldList, "dept_id='" + _MyPage.getDepartmentID() + "'");
        
        if(!_ResultList.isEmpty()) {
            _MyPage.setDepartmentName(_ResultList.get(0));
        }else{
            _MyPage.setDepartmentID(" ");
            _MyPage.setDepartmentName(" ");
        }
    }
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Save") == 0) {
        
        //Check Format - Date Entered
        if(!_MyError.hasError() && !DU.isProperDate(_MyPage.getDateEntered())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Date entered");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Check for required Field - User ID
        if(!_MyError.hasError() && _MyPage.getUserID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("User ID");
        }
        
        //Check for required Field - First Name
        if(!_MyError.hasError() && _MyPage.getFirstName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("First name");
        }
        
        //Check for required Field - Last Name
        if(!_MyError.hasError() && _MyPage.getLastName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Last name");
        }
        
        //Check for required Field -Department
        if(!_MyError.hasError() && _MyPage.getDepartmentID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Department");
        }
        
        //Check for required Field - Username
        if(!_MyError.hasError() && _MyPage.getUserName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Username");
        }
        
        //Check for field Length - username at least 6 chars
        if(!_MyError.hasError() && _MyPage.getUserName().trim().length() < 6) {
           //A length of %1 characters is required for %2
           _MyError.setMessageNumber("10014");
           _MyError.addParameter("6");
           _MyError.addParameter("username");
        }
        
        //Check for requred Field - password
        if(!_MyError.hasError() && _MyPage.getPassword().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Password");
        }
        
        //Check for field Length - password at least 6 chars
        if(!_MyError.hasError() && _MyPage.getPassword().trim().length() < 6) {
            //A length of %1 characters is required for %2
           _MyError.setMessageNumber("10014");
           _MyError.addParameter("6");
           _MyError.addParameter("password");
        }
        
        //Check for required Field - confirm
        if(!_MyError.hasError() && (_MyPage.getConfirmPassword().trim().length() == 0 || 
                                    _MyPage.getConfirmPassword().trim().compareTo(_MyPage.getPassword().trim()) != 0)) {
            //Password confirmation is required
            _MyError.setMessageNumber("10015");
        }
                
        //Check for uniqueness - Username 
        if(!_MyError.hasError() && 
                _MyGenSqlHelper.isExist("a_users", "username='" + _MyPage.getUserName().trim() + "'")) {
            //A unique %1 is required.
            _MyError.setMessageNumber("10013");
            _MyError.addParameter("username");
        }
        
        //Check for uniqueness - User ID 
        if(!_MyError.hasError() && 
                _MyGenSqlHelper.isExist("a_users", "user_id='" + _MyPage.getUserID().trim() + "'")) {
            //A unique %1 is required.
            _MyError.setMessageNumber("10013");
            _MyError.addParameter("user ID");
        }
        
        //Check for required field -- AccessType
        if(!_MyError.hasError() && _MyPage.getAccessTypeR().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Access type");
        }
        
    }
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyUser = new User(_MyPage);
            try {
                _MySqlHelper.add(_MyUser);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("User");

                _MyPage.reset();
                _MyUser.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sUserAdd.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add user record");
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
