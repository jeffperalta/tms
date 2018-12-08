/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Reports.AdminReports;

import Report.ReportData;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author user
 */
public class sListing extends HttpServlet {
   private ReportData _MyReportData = null;
   private String _ReturnTo = " ";
    
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
    if(_ReturnTo.trim().length() == 0) {
        response.sendRedirect("/TMS/sReportCenter");
    }else{
        response.sendRedirect(_ReturnTo);
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
    String ReportType =" ";
   
    _MyReportData = (ReportData)request.getSession().getAttribute("myReportData");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    try {
        ReportType =request.getParameter("rt");
    }catch(Exception e) {
        _ReturnTo = "/TMS/index.jsp";
    }
    
    _MyReportData.reset();
    _MyReportData.setSystemReport(1);
    if(ReportType.compareToIgnoreCase("UL") == 0) {
        _MyReportData.setReportCode("USRLST");
    }else if(ReportType.compareToIgnoreCase("CL") == 0) {
        _MyReportData.setReportCode("CLTLST");
    }else if(ReportType.compareToIgnoreCase("AL") == 0) {
        _MyReportData.setReportCode("ACTLST");
    }else if(ReportType.compareToIgnoreCase("NBL") == 0) {
        _MyReportData.setReportCode("NBLLST");
    }else if(ReportType.compareToIgnoreCase("WL") == 0) {
        _MyReportData.setReportCode("WLVLST");
    }else {
        _ReturnTo = "/TMS/index.jsp";
    }
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/        
        
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
