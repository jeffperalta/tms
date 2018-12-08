/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ProjectPage;

import beans.Client;
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
import utilities.*;

/**
 *
 * @author jperalta
 */
public class sEditProject extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ProjectPage _MyPage = null;
    private Project _MyProject = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private ErrorMessage _MyError = null;
    private PageNavigation _MyPageNavigation = null;
    private Client _MyClient = null;
    ArrayList<String> _FieldList = new ArrayList<String>();
    ArrayList<String> _ResultList = new ArrayList<String>();
    
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
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProject.jsp#" + _ButtonClicked);
        _ButtonClicked = " ";
    }
    
    if(_ButtonClicked.compareToIgnoreCase("save") == 0){
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProject.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProject.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("Reopen") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProject.jsp#ChangeStatus");
    }

    if(_ButtonClicked.compareToIgnoreCase("Close Project") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProject.jsp#ChangeStatus");
    }

    if(_ButtonClicked.compareToIgnoreCase("Cancel Project") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProject.jsp#ChangeStatus");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("projectid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ProjectSearchNav");
        _MyPageNavigation.setFileName("/TMS/sEditProject");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ProjectSearch.jsp#main");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("clientid") == 0) {
        if(_MyPage.getClientName().trim().length() != 0) {
            response.sendRedirect("/TMS/webpages/Admin/Project/AdminProject.jsp#" + _ButtonClicked);
        }else{
            _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ClientSearchNav");
            _MyPageNavigation.setFileName("/TMS/sEditProject");
            response.sendRedirect("/TMS/webpages/Admin/SearchPage/ClientSearch.jsp#main");
        }
    }
    
    if(_ButtonClicked.compareToIgnoreCase("doGet") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/Project/AdminProject.jsp#Save");
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
    _MyPage = (ProjectPage)request.getSession().getAttribute("myProjectPage");
    _MyProject = (Project)request.getSession().getAttribute("BProject");
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
    if(_MyPage.getButtonClicked().compareToIgnoreCase("projectid") == 0) {
        _MyPage.setProjectID(_MyProject.getProjectID().trim());
        
        //Get Client ID and Name
        _MyPage.setClientID(_MyProject.getClientID().trim());
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
        //-->
        
        _MyPage.setProjectName(_MyProject.getProjectName().trim());
        _MyPage.setStartDate(_MyProject.getStartDate().trim());
        _MyPage.setEndDate(_MyProject.getEndDate().trim());
        _MyPage.setProjectAmount(Formatter.toCurrency(_MyProject.getProjectAmount()));
        _MyPage.setRemarks(_MyProject.getRemarks().trim());
        
        //Get Activity Status
        _FieldList.clear();
        _ResultList.clear();
        _FieldList.add("status");
        _ResultList = _MyGeneralSqlHelper.getDataElement("a_activity", 
                _FieldList, "activity_id='" + _MyPage.getProjectID() + "'");
        if(!_ResultList.isEmpty()){
            String strStatusID = _ResultList.get(0);
            _FieldList.clear();
            _ResultList.clear();
            _FieldList.add("status_name");
            _ResultList = _MyGeneralSqlHelper.getDataElement("a_activity_status", 
                    _FieldList, "status_id='" + strStatusID + "'");
            if(!_ResultList.isEmpty()) {
                _MyPage.setProjectStatus(_ResultList.get(0));
            }
        }
    }
    
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
    _MyPage = (ProjectPage)request.getSession().getAttribute("myProjectPage");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setButtonClicked(_ButtonClicked);
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
    
    if(_ButtonClicked.compareToIgnoreCase("Delete") == 0) {
        
        //Check for required field -- project ID
        if (!_MyError.hasError() && _MyPage.getProjectID().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Project ID");
        }
        
        //Cannot delete a reference record -- a project id
        if(_MyGeneralSqlHelper.isExist("a_activity_list", "activity_id='" + Formatter.toProperSqlWhereClause(_MyPage.getProjectID()) + "'")) {
           //Cannot delete a referenced record. (%1)
           _MyError.setMessageNumber("10011");
           _MyError.addParameter("This project already has an activity assignment.");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
    
        if(_ButtonClicked.compareToIgnoreCase("save") == 0) {
            _MyProject = new Project(_MyPage);
            try {
                _MySqlHelper.edit(_MyProject);
                
                //%1 information was saved
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10010");
                _MyError.addParameter("Project");

                _MyPage.reset();
                _MyProject.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditProject.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Edit project record");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("delete") == 0) {
            _MyProject = new Project(_MyPage);
            try {
                _MySqlHelper.delete(_MyProject);
                
                //%1 was successfully deleted.
                _MyError.setMessageType(MessageType.MESSAGE_TYPE_INFORMATION);
                _MyError.setMessageNumber("10012");
                _MyError.addParameter("Project record");
                
                _MyPage.reset();
                _MyProject.reset();
                
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditProject.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Delete project record");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("Reopen") == 0) {
            _MyProject = new Project(_MyPage);
            try {
                _MySqlHelper.changeStatus(1, _MyProject); //On Going
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditProject.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Change Status to On-Going");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("Close Project") == 0) {
            _MyProject = new Project(_MyPage);
            try {
                _MySqlHelper.changeStatus(3, _MyProject); //Close
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditProject.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Change Status to Close");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("Cancel Project") == 0) {
            _MyProject = new Project(_MyPage);
            try {
                _MySqlHelper.changeStatus(2, _MyProject); //Cancel
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(sEditProject.class.getName()).log(Level.SEVERE, null, ex);
                //Transaction was not saved (%1)
                _MyError.setMessageNumber("10005");
                _MyError.addParameter("Change Status to Cancel");
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("Cancel Project") ==0 || 
                _ButtonClicked.compareToIgnoreCase("Close Project")==0 ||
                _ButtonClicked.compareToIgnoreCase("Reopen") == 0) {
            
            //Get Activity Status
            _FieldList.clear();
            _ResultList.clear();
            _FieldList.add("status");
            _ResultList = _MyGeneralSqlHelper.getDataElement("a_activity", 
                    _FieldList, "activity_id='" + _MyPage.getProjectID() + "'");
            if(!_ResultList.isEmpty()){
                String strStatusID = _ResultList.get(0);
                _FieldList.clear();
                _ResultList.clear();
                _FieldList.add("status_name");
                _ResultList = _MyGeneralSqlHelper.getDataElement("a_activity_status", 
                        _FieldList, "status_id='" + strStatusID + "'");
                if(!_ResultList.isEmpty()) {
                    _MyPage.setProjectStatus(_ResultList.get(0));
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
