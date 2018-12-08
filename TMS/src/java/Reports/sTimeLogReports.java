/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Reports;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import Report.ReportData;
import message.ErrorMessage;
import utilities.DateUtility;
import utilities.Formatter;

/**
 *
 * @author jperalta
 */
public class sTimeLogReports extends HttpServlet {
    
    String _ButtonClicked = " ";
    private ReportData _MyReportData = null;
    private ErrorMessage _MyError = null;
    
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
        response.sendRedirect("/TMS/webpages/ReportPages/TimeLogReports.jsp");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("Submit") == 0) {
        response.sendRedirect("/TMS/sReportCenter");
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
    _MyReportData = (ReportData)request.getSession().getAttribute("myReportData");
    _MyError =(ErrorMessage)request.getSession().getAttribute("MessageManager");
    DateUtility DU = new DateUtility();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyReportData.reset();
    _MyReportData.setDate1(Formatter.toProperString(request.getParameter("txtDate1").trim()));
    _MyReportData.setDate2(Formatter.toProperString(request.getParameter("txtDate2").trim()));
    _MyReportData.setType(request.getParameter("cmbType").trim());
    _MyReportData.setLeaveID(request.getParameter("cmbLeaveType").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Submit") == 0) {
        //Required field -- Date
        if(!_MyError.hasError() && _MyReportData.getDate1().trim().length() == 0 ) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Start date");
        }

        //Input format -- Date
        if(!_MyError.hasError() && !DU.isProperDate(_MyReportData.getDate1())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("Start date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }
        
        //Required field -- Date2
        if(!_MyError.hasError() && _MyReportData.getDate2().trim().length() == 0 ) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("End date");
        }

        //Input format -- Date2
        if(!_MyError.hasError() && !DU.isProperDate(_MyReportData.getDate2())) {
            //%1 field is not in the proper format (%2)
            _MyError.setMessageNumber("10002");
            _MyError.addParameter("End date");
            _MyError.addParameter(constants.DateFormat.DATE_FORMAT);
        }

        //required field -- Type
        if(!_MyError.hasError() && _MyReportData.getType().trim().length() == 0 ) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Type");
        }

        //Valid input -- type
        if(!_MyError.hasError() && (_MyReportData.getType().compareToIgnoreCase("ALL") != 0 && 
                                    _MyReportData.getType().compareToIgnoreCase("PAC") != 0 &&
                                    _MyReportData.getType().compareToIgnoreCase("NBA") != 0 &&
                                    _MyReportData.getType().compareToIgnoreCase("WLV") != 0) ) {
            //%1 is invalid
            _MyError.setMessageNumber("10006");
            _MyError.addParameter("Type field");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("Submit") == 0) {
            if(_MyReportData.getType().compareToIgnoreCase("ALL") == 0) {
                _MyReportData.setReportCode("TMLALL");
            }else if(_MyReportData.getType().compareToIgnoreCase("PAC") == 0) {
                _MyReportData.setReportCode("TMLPAC");
            }else if(_MyReportData.getType().compareToIgnoreCase("NBA") == 0) {
                _MyReportData.setReportCode("TMLNBA");
            }else if(_MyReportData.getType().compareToIgnoreCase("WLV") == 0) {
                _MyReportData.setReportCode("TMLWLV");
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
