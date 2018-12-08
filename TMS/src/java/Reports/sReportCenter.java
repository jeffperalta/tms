/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Reports;

import Report.ReportData;
import Report.ReportGenerator;
import beans.LogInfo;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author jperalta
 */
public class sReportCenter extends HttpServlet {
   
    private ReportData _MyReportData = null;
    private ReportGenerator _MyGenerator = null;
    private LogInfo _MyLogInfo = null;
    private String _Parameter = " ";
    
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
       if(_MyReportData.getSystemReport() == 0) {
            response.sendRedirect("/TMS/webpages/ReportPages/redirectme.jsp?code="+ _Parameter + "&&direct=" + "/TMS/webpages/ReportPages/ReportSelection.jsp");
       }else if(_MyReportData.getSystemReport() ==1) {
            response.sendRedirect("/TMS/webpages/ReportPages/redirectme.jsp?code="+ _Parameter + "&&direct=" + "/TMS/webpages/Admin/ReportPages/ReportSelection.jsp");
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
        
    _MyReportData = (ReportData)request.getSession().getAttribute("myReportData"); 
    _MyLogInfo = (LogInfo)request.getSession().getAttribute("LogUserInfo"); 
    
    _MyReportData.setUserID(_MyLogInfo.getUserId()); 
    _MyReportData.initialize(); 
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
     _MyGenerator = new ReportGenerator(_MyReportData);  
     
     if(_MyReportData.getReportType().compareToIgnoreCase(constants.Reports.REPORT_TYPE_PDF) == 0) { 
        _MyGenerator.dataOut(); 
        _MyGenerator.genPdfReport(); 
        _Parameter = _MyReportData.getWebReportLocation() + _MyReportData.getReportName() + ".pdf"; 
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
