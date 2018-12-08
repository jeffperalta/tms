/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ProjectPage;

import beans.Client;
import beans.LogInfo;
import beans.PageNavigation;
import beans.Project;
import beans.pages.ProjectPage;
import constants.DateFormat;
import java.io.*;
import java.net.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import message.MessageType;
import tmsclass.Project.sqlHelper;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import utilities.DateUtility;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class sAddProject extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ProjectPage _MyPage = null;
    private ErrorMessage _MyError = null;
    ArrayList<String> _FieldList = new ArrayList<String>();
    ArrayList<String> _ResultList = new ArrayList<String>();
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private sqlHelper _MySqlHelper = new sqlHelper();
    private Project _MyProject = null;
    private PageNavigation _MyPageNavigation = null;
    private Client _MyClient = null;
    private LogInfo _MyLogInfo = null;
    
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
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProjectPlus.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProjectPlus.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("clientid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ClientSearchNav");
        _MyPageNavigation.setFileName("/TMS/sAddProject");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ClientSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProjectPlus.jsp#Save");
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
    _ButtonClicked = "doGet";
    _MyPage = (ProjectPage)request.getSession().getAttribute("myAddProjectPage");    
    _MyClient = (Client)request.getSession().getAttribute("BClient");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(_MyPage.getButtonClicked().compareToIgnoreCase("clientid") == 0) {
        //Get Client ID and Name
        _MyPage.setClientID(_MyClient.getClientId().trim());
        _MyPage.setClientName(_MyClient.getName().trim());
        //-->
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
    _MyPage = (ProjectPage)request.getSession().getAttribute("myAddProjectPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    _MyLogInfo = (LogInfo)request.getSession().getAttribute("LogUserInfoAdmin");
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setButtonClicked(_ButtonClicked);
    _MyPage.setProjectID(request.getParameter("txtProjectID").trim());
    _MyPage.setClientID(request.getParameter("txtClientID").trim());
    if(_MyPage.getClientID().trim().length() != 0) {
        _FieldList.clear();
        _ResultList.clear();
        
        _FieldList.add("c_name");
        _ResultList = _MyGeneralSqlHelper.getDataElement("a_client", 
                        _FieldList, "client_id='" + Formatter.toProperSqlWhereClause(_MyPage.getClientID()) + "'");
        if(!_ResultList.isEmpty()) {
            _MyPage.setClientName(_ResultList.get(0));
        }else{
            _MyPage.setClientName(" ");
        } 
    }else{
       _MyPage.setClientName(" ");
    }
    
    _MyPage.setProjectName(request.getParameter("txtName").trim());
    _MyPage.setStartDate(request.getParameter("txtStartDate").trim());
    _MyPage.setEndDate(request.getParameter("txtEndDate").trim());
    _MyPage.setProjectAmount(request.getParameter("txtAmount").trim());
    _MyPage.setRemarks(request.getParameter("txtRemarks").trim());
    _MyPage.setProjectType(_MyLogInfo.getDepartment());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
        
        //Check for required field -- project ID
        if (!_MyError.hasError() && _MyPage.getProjectID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Project ID");
        }
        
        //Check for duplicate -- Project ID
        if(!_MyError.hasError() && _MyGeneralSqlHelper.isExist("a_project", "project_id='" + Formatter.toProperSqlWhereClause(_MyPage.getProjectID()) + "'")) {
            //A unique %1 is required.
            _MyError.setMessageNumber("10013");
            _MyError.addParameter("project ID");
        }
        
        //Check for required field -- Client ID
        if (!_MyError.hasError() && _MyPage.getClientID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Client ID");
        }
        
        //Check if valid -- Client ID
        if (!_MyError.hasError() && !_MyGeneralSqlHelper.isExist("a_client", "client_id='" + Formatter.toProperSqlWhereClause(_MyPage.getClientID()) + "'")) {
            //%1 is invalid
            _MyError.setMessageNumber("10006");
            _MyError.addParameter("Client ID");
        }
        
        //Check for required field -- project name
        if (!_MyError.hasError() && _MyPage.getProjectName().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Project name");
        }

        //Check for numeric -- Project Amount
        if (!_MyError.hasError() && _MyPage.getProjectAmount().trim().length() != 0 && !Formatter.isNumber(_MyPage.getProjectAmount())) {
            //%1 field must be numeric
            _MyError.setMessageNumber("10000");
            _MyError.addParameter("Project amount");
        }

        //Check if valid - amount must be a positive number
        if (!_MyError.hasError() && _MyPage.getProjectAmount().trim().length() != 0 && Formatter.toProperNumber(_MyPage.getProjectAmount()) < 0) {
            //%1 is invalid (%2)
            _MyError.setMessageNumber("10004");
            _MyError.addParameter("Project amount entry");
            _MyError.addParameter("The amount must be a positive number");
        }

        //Check format of start date
        if (!_MyError.hasError() && _MyPage.getStartDate().trim().length() != 0 && !DU.isProperDate(_MyPage.getStartDate())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Start date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }

        //Check format of end date
        if (!_MyError.hasError() && _MyPage.getEndDate().trim().length() != 0 && !DU.isProperDate(_MyPage.getEndDate())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("End date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
            
        try {
            //Check validity -- Start Date < End Date
            if (!_MyError.hasError() && _MyPage.getStartDate().trim().length() != 0 && _MyPage.getEndDate().trim().length() != 0 && DateUtility.DateCompare(_MyPage.getStartDate(), _MyPage.getEndDate(), DateFormat.DATE_FORMAT) > 0) {
                //%1 must comes first before %2
                _MyError.setMessageNumber("10007");
                _MyError.addParameter("Start date entry");
                _MyError.addParameter("the end date");
            }
        } catch (ParseException ex) {
            Logger.getLogger(sEditProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyProject = new Project(_MyPage);
            try {
                _MySqlHelper.add(_MyProject);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Project");

                _MyPage.reset();
                _MyProject.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sAddProject.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Add new project record");
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
