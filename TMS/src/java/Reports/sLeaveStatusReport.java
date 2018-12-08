/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Reports;

import Report.ReportData;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import utilities.Formatter;

/**
 *
 * @author user
 */
public class sLeaveStatusReport extends HttpServlet {
   private String _ButtonClicked = " ";
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
        response.sendRedirect("/TMS/webpages/ReportPages/LeaveStatusReport.jsp");
    }
    
    if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
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
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManager");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyReportData.reset();
    _MyReportData.setYearNum(request.getParameter("txtYear").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Submit") == 0) {
        
        //Check for required field -- Year
        if(!_MyError.hasError() && _MyReportData.getYearNum().trim().length() == 0) {
            //%1 field is required
            _MyError.setMessageNumber("10003");
            _MyError.addParameter("Year");
        }
        
        //Check for validity -- Year must be numeric
        if(!_MyError.hasError() && !Formatter.isNumber(_MyReportData.getYearNum())) {
            //%1 field must be numeric
            _MyError.setMessageNumber("10000");
            _MyError.addParameter("Year");
        }
    }
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
        
    if(!_MyError.hasError()) {
        if(_ButtonClicked.compareToIgnoreCase("submit") == 0) {
            _MyReportData.setReportCode("WKLRMG");
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
