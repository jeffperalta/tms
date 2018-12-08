/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Reports.AdminReports;

import Report.ReportData;
import beans.Client;
import beans.PageNavigation;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import utilities.DateUtility;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class sAdminActivityLogPerClient extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ReportData _MyReportData = null;
    private Client _MyClient = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private ErrorMessage _MyError = new ErrorMessage();
    private PageNavigation _MyPageNavigation = new PageNavigation();
    
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
        _ButtonClicked = " ";
        response.sendRedirect("/TMS/webpages/Admin/ReportPages/ActivityLogPerClient.jsp");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
        response.sendRedirect("/TMS/sReportCenter");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("clientid") == 0) {
        _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ClientSearchNav");
        _MyPageNavigation.setFileName("/TMS/webpages/Admin/ReportPages/ActivityLogPerClient.jsp");
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/ClientSearch.jsp");
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
    _MyReportData= (ReportData)request.getSession().getAttribute("myReportData");
    _MyClient = (Client)request.getSession().getAttribute("BClient");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManagerAdmin");
    DateUtility DU = new DateUtility();
    
    ArrayList<String> FieldList = new ArrayList<String>();
    ArrayList<String> ResultList = new ArrayList<String>();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyReportData.reset();
    _MyReportData.setClientID(request.getParameter("txtClientID").trim());
    if(_MyReportData.getClientID().trim().length() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("c_name");
        ResultList = _MyGeneralSqlHelper.getDataElement("a_client", FieldList, "client_id='" + Formatter.toProperSqlWhereClause(_MyReportData.getClientID()) + "'");
        
        if(!ResultList.isEmpty()) {
            _MyClient.setClientId(_MyReportData.getClientID());
            _MyClient.setName(ResultList.get(0));
        }else{
            _MyClient.setClientId(" ");
            _MyClient.setName(" ");
            _MyReportData.setClientID(" ");
        }
    }else{
        _MyClient.setClientId(" ");
        _MyClient.setName(" ");
        _MyReportData.setClientID(" ");
    }
    
    _MyReportData.setDate1(Formatter.toProperString(request.getParameter("txtDate1").trim()));
    _MyReportData.setDate2(Formatter.toProperString(request.getParameter("txtDate2").trim()));
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
        //Check for required field -- Client ID
        if(!_MyError.hasError() && _MyReportData.getClientID().trim().length() == 0) {
             //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Client ID");
        }
        
        //Check for required field -- Start Date
        if(!_MyError.hasError() && _MyReportData.getDate1().trim().length() == 0) {
             //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Start date");
        }
        
        //Check for validity Format -- start date
        if(!_MyError.hasError() && !DU.isProperDate(_MyReportData.getDate1())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Start date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Check for required field -- End date
        if(!_MyError.hasError() && _MyReportData.getDate2().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("End date");
        }
        
        //Check for date format -- end date
        if(!_MyError.hasError() && !DU.isProperDate(_MyReportData.getDate2())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("End date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
            //Redirect to control panel page -- report selection
            _MyReportData.setSystemReport(1);
            _MyReportData.setReportCode("ALPCLI");
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
