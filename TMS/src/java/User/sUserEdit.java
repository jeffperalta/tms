/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package User;

import beans.PageNavigation;
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
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class sUserEdit extends HttpServlet {
    private ErrorMessage _MyError= null;
    private User _MyUser= null;
    private UserPage _MyPage = null;
    private String _ButtonClicked = " ";
    private generalSqlHelper _MyGenSqlHelper = new generalSqlHelper();
    ArrayList<String> _FieldList = new ArrayList<String>();
    ArrayList<String> _ResultList = new ArrayList<String>();
    String _StrActiveCheckbox = " ";
    private sqlHelper _MySqlHelper = new sqlHelper();
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
        response.sendRedirect("/TMS/webpages/Admin/User/AdminUser.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/User/AdminUser.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/User/AdminUser.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/User/AdminUser.jsp#Save");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("userid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BUserPageNav");
        _MyPageNavigation.setFileName("/TMS/sUserEdit");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/UserSearch.jsp#main");
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
    _MyUser = (User)request.getSession().getAttribute("BUser");
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
    _MyPage.setUserID(_MyUser.getUserID());
    _MyPage.setFirstName(_MyUser.getFirstName());
    _MyPage.setLastName(_MyUser.getLastName());
    _MyPage.setMiddleName(_MyUser.getMiddleName());
    _MyPage.setContactInformation(_MyUser.getContactInformation());
    _MyPage.setUserName(_MyUser.getUserName());
    _MyPage.setPassword(_MyUser.getPassword());
    _MyPage.setConfirmPassword(_MyUser.getPassword());
    _MyPage.setActiveR(_MyUser.getActive() + "");
    _MyPage.setDateEntered(_MyUser.getDateEntered());
    
    _MyPage.setAccessTypeR(_MyUser.getAccessType() + "");
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
    
    _MyPage.setDepartmentID(_MyUser.getDepartmentID());
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
    _MyPage = (UserPage)request.getSession().getAttribute("myUserEditPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    _StrActiveCheckbox = request.getParameter("chkValue");
    _MyUser = (User)request.getSession().getAttribute("BUser");
        
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setFirstName(request.getParameter("txtFirstName").trim());
    _MyPage.setLastName(request.getParameter("txtLastName").trim());
    _MyPage.setMiddleName(request.getParameter("txtMiddleName").trim());
    _MyPage.setContactInformation(request.getParameter("txtContact").trim());
    _MyPage.setUserName(request.getParameter("txtUserName").trim());
    _MyPage.setPassword(request.getParameter("txtPassword").trim());
    _MyPage.setConfirmPassword(request.getParameter("txtConfirm").trim());
    if(_StrActiveCheckbox == null) {
        _MyPage.setActiveR(" ");
    }else{
        _MyPage.setActiveR("1");
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
        
        //Check for required field -- user id
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
                
        //Check for uniqueness - Username if the value was changed
        if(!_MyError.hasError() && _MyUser.getUserName().trim().compareToIgnoreCase(_MyPage.getUserName())!=0 && 
                _MyGenSqlHelper.isExist("a_users", "username='" + _MyPage.getUserName().trim() + "'")) {
            //A unique %1 is required.
            _MyError.setMessageNumber("10013");
            _MyError.addParameter("username");
        }
        
        //Check for required field -- AccessType
        if(!_MyError.hasError() && _MyPage.getAccessTypeR().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Access type");
        }
        
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        
         //Check for required field -- user id
        if(!_MyError.hasError() && _MyPage.getUserID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("User ID");
        }
        
        //Cannot delete referenced record
        if(_MyGenSqlHelper.isExist("a_activity_list", "user_id='" + _MyPage.getUserID() + "'") || 
           _MyGenSqlHelper.isExist("a_non_billable", "user_id='" + _MyPage.getUserID() + "'") ||
           _MyGenSqlHelper.isExist("a_leave_details", "user_id='" + _MyPage.getUserID() + "'")) {
           //Cannot delete a referenced record. (%1)
           _MyError.setMessageNumber("10011");
           _MyError.addParameter("This user already has an activity.");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
        
    if(!_MyError.hasError()) {
        
        if(_ButtonClicked.compareToIgnoreCase("Save") == 0) {
            try {
                
                _MyUser.setContactInformation(Formatter.toProperString(_MyPage.getContactInformation()));
                _MyUser.setFirstName(Formatter.toProperString(_MyPage.getFirstName()));
                _MyUser.setLastName(Formatter.toProperString(_MyPage.getLastName()));
                _MyUser.setMiddleName(Formatter.toProperString(_MyPage.getMiddleName()));
                _MyUser.setPassword(Formatter.toProperString(_MyPage.getPassword()));
                _MyUser.setUserName(Formatter.toProperString(_MyPage.getUserName()));
                _MyUser.setUserID(Formatter.toProperString(_MyPage.getUserID()));
                _MyUser.setAccessType(Integer.parseInt(_MyPage.getAccessTypeR()));
                if(_MyPage.getActiveR().trim().compareToIgnoreCase("1") == 0) {
                    _MyUser.setActive(1);
                }else{
                    _MyUser.setActive(0);
                }
                _MyUser.setDepartmentID(_MyPage.getDepartmentID());
                
                _MySqlHelper.edit(_MyUser);

                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("User");

                _MyPage.reset();
                _MyUser.reset();

            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sUserEdit.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Edit user record");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("Delete") == 0) {
            try {
                _MyUser.setUserID(Formatter.toProperString(_MyPage.getUserID()));
                _MySqlHelper.delete(_MyUser);
                
                //%1 was successfully deleted.
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10012");
                _MyError.addParameter("User record");
                
                _MyPage.reset();
                _MyUser.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sUserEdit.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Delete user record");
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
