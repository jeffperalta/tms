/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package Leaves;

import beans.LeaveType;
import beans.LogInfo;
import beans.PageNavigation;
import beans.pages.LeaveTimeLogPage;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class LeaveTypeSearchResult extends HttpServlet {
   private LeaveType _MyLeave = null;
   private PageNavigation _MyPageNavigation = null;
   private generalSqlHelper _MyGenSqlHelper = new generalSqlHelper();
   private LeaveTimeLogPage _MyLeaveTimeLogPage = null;
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
        response.sendRedirect(_MyPageNavigation.getFileName()); 
            
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
    _MyLeave = (LeaveType)request.getSession().getAttribute("BLeaveType");
    _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("LeaveTypeSearchNav");
    ArrayList<String> FieldList = new ArrayList<String>();
    ArrayList<String> ResultList = new ArrayList<String>();
    
    if(_MyPageNavigation.getFileName().compareToIgnoreCase("/TMS/webpages/Leaves/LeaveTimeLog.jsp") == 0) {
        _MyLeaveTimeLogPage = (LeaveTimeLogPage)request.getSession().getAttribute("PLeaveTimeLogPage");
        _MyLogInfo = (LogInfo)request.getSession().getAttribute("LogUserInfo");
    }
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyLeave.setLeaveID(Integer.parseInt(request.getParameter("LI").trim()));
    if(_MyLeave.getLeaveID() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("leave_name");
        FieldList.add("status");
        FieldList.add("remarks");
        FieldList.add("max_leave");
        
        ResultList = _MyGenSqlHelper.getDataElement("a_leaves", FieldList, "leave_id=" + _MyLeave.getLeaveID());
        if(!ResultList.isEmpty()) {
            _MyLeave.setLeaveName(ResultList.get(0));
            _MyLeave.setStatus(Integer.parseInt(ResultList.get(1)));
            _MyLeave.setRemarks(ResultList.get(2));
            _MyLeave.setMaxLeave(Integer.parseInt(ResultList.get(3)));
        }else{
            _MyLeave.reset();
        }
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
